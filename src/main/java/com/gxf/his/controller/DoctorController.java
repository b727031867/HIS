package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.config.RedisClient;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.Department;
import com.gxf.his.po.Doctor;
import com.gxf.his.po.TicketResource;
import com.gxf.his.po.User;
import com.gxf.his.service.DepartmentService;
import com.gxf.his.service.DoctorService;
import com.gxf.his.service.TicketResourceService;
import com.gxf.his.service.UserService;
import com.gxf.his.vo.DepartmentVo;
import com.gxf.his.vo.DoctorUserVo;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    public DoctorController(DoctorService dservice, UserService uService, DepartmentService depService, RedisClient redis) {
        this.redis = redis;
        doctorService = dservice;
        userService = uService;
        departmentService = depService;
    }

    private RedisClient redis;

    private DoctorService doctorService;

    private UserService userService;

    private DepartmentService departmentService;

    @Resource
    private TicketResourceService ticketResourceService;

    @GetMapping
    public ServerResponseVO getDoctors(@RequestParam(required = false, name = "departmentCode") String departmentCode
            , @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if (null == departmentCode) {
            PageHelper.startPage(page, size);
            List<DoctorUserVo> doctors = doctorService.getAllDoctors();
            PageInfo<DoctorUserVo> pageInfo = PageInfo.of(doctors);
            return ServerResponseVO.success(pageInfo);
        }
        PageHelper.startPage(page, size);
        List<DoctorUserVo> doctors = doctorService.getDoctorsByDepartmentCode(departmentCode);
        PageInfo<DoctorUserVo> pageInfo = PageInfo.of(doctors);
        return ServerResponseVO.success(pageInfo);
    }

    @GetMapping("departmentCode")
    public ServerResponseVO getDoctorsByDepartmentCode(String departmentCode) {
        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        List<DoctorUserVo> departmentVos = doctorService.getDoctorsByDepartmentCode(departmentCode);
        //获取每位医生未来一周内的订票情况
        for (DoctorUserVo doctorUserVo : departmentVos) {
            Date expirationDate = ticketResourceService.getTicketResourceMaxDate();
            Date startDate = ticketResourceService.getTicketResourceMinDate();
            List<TicketResource> ticketResources = ticketResourceService.getTicketResourceByDoctorIdAndAvailableDate(doctorUserVo.getDoctorId(), startDate, expirationDate);
            doctorUserVo.setTicketResources(ticketResources);
        }
        return ServerResponseVO.success(departmentVos);
    }

    @GetMapping("/attribute")
    public ServerResponseVO getDoctorsByAttribute(@RequestParam(value = "attribute", defaultValue = "doctorName") String attribute
            , @RequestParam(value = "value") String value, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if (value == null || value.trim().length() == 0) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        //可能使用的查询属性
        String doctorName = "doctorName";
        String doctorProfessionalTitle = "doctorProfessionalTitle";
        String departmentName = "departmentName";
        DoctorUserVo doctorUserVo = new DoctorUserVo();
        if (doctorName.equals(attribute)) {
            doctorUserVo.setDoctorName(value);
        } else if (doctorProfessionalTitle.equals(attribute)) {
            doctorUserVo.setDoctorProfessionalTitle(value);
        } else if (departmentName.equals(attribute)) {
            List<Department> departments = departmentService.getDepartmentsByVaguelyDepartmentName(value);
            doctorUserVo.setDepartments(departments);
        } else {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PageHelper.startPage(page, size);
        List<DoctorUserVo> doctors = doctorService.selectDoctorByAttribute(doctorUserVo);
        PageInfo<DoctorUserVo> pageInfo = PageInfo.of(doctors);
        return ServerResponseVO.success(pageInfo);
    }

    @PutMapping
    public ServerResponseVO saveDoctorAndUser(@RequestBody DoctorUserVo doctorUserVo) {
        logger.info("当前更新的医生信息为：" + doctorUserVo.toString());
        Doctor doctor = new Doctor();
        //更新医生信息
        doctor.setDoctorId(doctorUserVo.getDoctorId());
        doctor.setEmployeeId(doctorUserVo.getEmployeeId());
        doctor.setDoctorName(doctorUserVo.getDoctorName());
        doctor.setDoctorProfessionalTitle(doctorUserVo.getDoctorProfessionalTitle());
        doctor.setDoctorIntroduction(doctorUserVo.getDoctorIntroduction());
        doctor.setDepartmentCode(doctorUserVo.getDepartment().getDepartmentCode());
        doctor.setSchedulingId(doctorUserVo.getScheduling().getSchedulingId());
        doctor.setUserId(doctorUserVo.getUser().getUserId());
        doctor.setTicketDayNum(doctorUserVo.getTicketDayNum());
        doctorService.updateDoctor(doctor);
        //更新用户信息
        User user = doctorUserVo.getUser();
        userService.updateUser(user);
        return ServerResponseVO.success();
    }

    @PostMapping
    public ServerResponseVO saveDoctor(@RequestBody DoctorUserVo doctorUserVo) {
        if (StringUtils.isEmpty(doctorUserVo.getUser().getUserName().trim()) || StringUtils.isEmpty(doctorUserVo.getUser().getUserPassword().trim())
                || StringUtils.isEmpty(doctorUserVo.getDepartment().getDepartmentCode().trim()) || StringUtils.isEmpty(doctorUserVo.getDoctorIntroduction().trim())
                || StringUtils.isEmpty(doctorUserVo.getDoctorName().trim()) || StringUtils.isEmpty(doctorUserVo.getDoctorProfessionalTitle().trim())
                || StringUtils.isEmpty(doctorUserVo.getEmployeeId().trim())
        ) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if (userService.findByUserName(doctorUserVo.getUser().getUserName()) != null) {
            return ServerResponseVO.error(ServerResponseEnum.USER_REPEAT_ERROR);
        }
        User user = UserController.doHashedCredentials(doctorUserVo.getUser().getUserName(), doctorUserVo.getUser().getUserPassword());
        Long userId = userService.addUser(user);
        Doctor doctor = new Doctor();
        doctor.setDepartmentCode(doctorUserVo.getDepartment().getDepartmentCode());
        doctor.setDoctorIntroduction(doctorUserVo.getDoctorIntroduction());
        doctor.setDoctorName(doctorUserVo.getDoctorName());
        doctor.setDoctorProfessionalTitle(doctorUserVo.getDoctorProfessionalTitle());
        doctor.setEmployeeId(doctorUserVo.getEmployeeId());
        doctor.setUserId(userId);
        doctorService.addDoctor(doctor);
        return ServerResponseVO.success();
    }

    @DeleteMapping
    public ServerResponseVO deleteDoctorAndUserByDoctorId(@RequestParam(name = "doctorId") Long doctorId,
                                                          @RequestParam(name = "userId") Long userId) {
        try {
            doctorService.deleteDoctorAndUser(doctorId, userId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.USER_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public ServerResponseVO deleteDoctorsAndUsersByIds(@RequestBody List<DoctorUserVo> doctorUserVos) {
        List<Doctor> doctors = new ArrayList<>(16);
        List<User> users = new ArrayList<>(16);
        for (DoctorUserVo doctorUserVo : doctorUserVos) {
            Doctor doctor = new Doctor();
            doctor.setDoctorId(doctorUserVo.getDoctorId());
            doctors.add(doctor);
            users.add(doctorUserVo.getUser());
        }
        Integer a = doctorService.deleteDoctorAndUserBatch(doctors, users);
        //正常情况应该删除n个医生就有n个对应的用户也删除
        int b = 2;
        if (doctorUserVos.size() * b == a) {
            return ServerResponseVO.success();
        }
        return ServerResponseVO.error(ServerResponseEnum.DOCTOR_DELETE_FAIL);
    }


}
