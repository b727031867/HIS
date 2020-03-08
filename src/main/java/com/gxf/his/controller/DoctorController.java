package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.*;
import com.gxf.his.po.vo.*;
import com.gxf.his.service.*;
import com.gxf.his.uitls.MyUtil;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController extends BaseController {

    @Resource
    private DoctorService doctorService;
    @Resource
    private UserService userService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private TicketResourceService ticketResourceService;

    @GetMapping("/outpatients")
    public <T> ServerResponseVO<T> getOutpatients(@RequestParam("uid") Long uid, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        if (uid == null || startDate == null || endDate == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Long doctorId = userService.getLoginEntityId(uid);
        LocalDateTime now = LocalDateTime.now();
        long timestamp = Timestamp.valueOf(now).getTime();
        long endDateTimestamp = endDate.getTime();
        //如果endDate是今天，则按照当前时间查询
        long oneDateTime = 24*60*60*1000;
        if(timestamp - endDateTimestamp < oneDateTime){
            endDate = new Date();
        }
        List<TicketVo> ticketVos = doctorService.getOutpatients(doctorId, startDate, endDate);
        return MyUtil.cast(ServerResponseVO.success(ticketVos));
    }

    @GetMapping("/currentRankInfo")
    public <T> ServerResponseVO<T> getCurrentPatientInfo(@RequestParam("uid") Long uid) {
        if (uid == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Long doctorId = userService.getLoginEntityId(uid);
        HashMap<String, String> info = doctorService.getCurrentRankInfo(doctorId, 4);
        return MyUtil.cast(ServerResponseVO.success(info));
    }

    /**
     * 根据医生的用户ID和总数类型
     * 获取候诊总数或者叫号中的总数
     * totalType状态类型 同挂号信息的status状态
     *
     * @param uid       医生的用户ID
     * @param totalType 总数类型
     * @return 总数
     */
    @GetMapping("/totalRank")
    public <T> ServerResponseVO<T> getTotalRank(@RequestParam("uid") Long uid, @RequestParam("totalType") String totalType) {
        if (uid == null || totalType == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Long doctorId = userService.getLoginEntityId(uid);
        Integer total = doctorService.getTotalRank(doctorId, Integer.valueOf(totalType));
        return MyUtil.cast(ServerResponseVO.success(total));
    }

    @GetMapping("/callingPatient")
    public <T> ServerResponseVO<T> getCallingPatient(@RequestParam("uid") Long uid) {
        if (uid == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Long doctorId = userService.getLoginEntityId(uid);
        TicketVo ticketVo = doctorService.getCallingPatient(doctorId);
        return MyUtil.cast(ServerResponseVO.success(ticketVo));
    }

    @GetMapping("/doctorTicketId")
    public <T> ServerResponseVO<T> getDoctorByDoctorTicketId(@RequestParam("doctorTicketId") Long doctorTicketId) {
        if (doctorTicketId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        DoctorVo doctorVo = doctorService.getDoctorByDoctorTicketId(doctorTicketId);
        return MyUtil.cast(ServerResponseVO.success(doctorVo));
    }

    @GetMapping
    public <T> ServerResponseVO<T> getDoctors(@RequestParam(required = false, name = "departmentCode") String departmentCode
            , @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if (null == departmentCode) {
            PageHelper.startPage(page, size);
            List<DoctorVo> doctors = doctorService.getAllDoctors();
            PageInfo<DoctorVo> pageInfo = PageInfo.of(doctors);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        }
        PageHelper.startPage(page, size);
        List<DoctorVo> doctors = doctorService.getDoctorsByDepartmentCode(departmentCode);
        PageInfo<DoctorVo> pageInfo = PageInfo.of(doctors);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @GetMapping("departmentCode")
    public <T> ServerResponseVO<T> getDoctorsByDepartmentCode(String departmentCode) {
        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        List<DoctorVo> doctorVos = doctorService.getDoctorsByDepartmentCode(departmentCode);
        //获取每位医生未来一周内的订票情况
        for (DoctorVo doctorVo : doctorVos) {
            Date expirationDate = ticketResourceService.getTicketResourceMaxDate();
            Date startDate = ticketResourceService.getTicketResourceMinDate();
            List<DoctorTicketResource> ticketResources = ticketResourceService.getTicketResourceByDoctorIdAndAvailableDate(doctorVo.getDoctorId(), startDate, expirationDate);
            doctorVo.setTicketResources(ticketResources);
        }
        return MyUtil.cast(ServerResponseVO.success(doctorVos));
    }

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getDoctorsByAttribute(@RequestParam(value = "attribute", defaultValue = "doctorName") String attribute
            , @RequestParam(value = "value") String value, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        //可能使用的查询属性
        String doctorName = "doctorName";
        String doctorProfessionalTitle = "doctorProfessionalTitle";
        String departmentName = "departmentName";
        DoctorVo doctorVo = new DoctorVo();
        if (doctorName.equals(attribute)) {
            doctorVo.setDoctorName(value);
        } else if (doctorProfessionalTitle.equals(attribute)) {
            doctorVo.setDoctorProfessionalTitle(value);
        } else if (departmentName.equals(attribute)) {
            List<Department> departments = departmentService.getDepartmentsByVaguelyDepartmentName(value);
            doctorVo.setDepartments(departments);
        } else {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PageHelper.startPage(page, size);
        List<DoctorVo> doctors = doctorService.selectDoctorByAttribute(doctorVo);
        PageInfo<DoctorVo> pageInfo = PageInfo.of(doctors);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @PutMapping
    public <T> ServerResponseVO<T> saveDoctorAndUser(@RequestBody DoctorVo doctorVo) {
        log.info("当前更新的医生信息为：" + doctorVo.toString());
        Doctor doctor = new Doctor();
        //更新医生信息
        doctor.setDoctorId(doctorVo.getDoctorId());
        doctor.setEmployeeId(doctorVo.getEmployeeId());
        doctor.setDoctorName(doctorVo.getDoctorName());
        doctor.setDoctorProfessionalTitle(doctorVo.getDoctorProfessionalTitle());
        doctor.setDoctorIntroduction(doctorVo.getDoctorIntroduction());
        doctor.setDepartmentCode(doctorVo.getDepartment().getDepartmentCode());
        doctor.setSchedulingId(doctorVo.getDoctorScheduling().getSchedulingId());
        doctor.setUserId(doctorVo.getUser().getUserId());
        doctor.setTicketDayNum(doctorVo.getTicketDayNum());
        doctorService.updateDoctor(doctor);
        //更新用户信息
        User user = doctorVo.getUser();
        userService.updateUser(user);
        return ServerResponseVO.success();
    }

    @PostMapping
    public <T> ServerResponseVO<T> saveDoctor(@RequestBody DoctorVo doctorVo) {
        if (StringUtils.isEmpty(doctorVo.getUser().getUserName().trim()) || StringUtils.isEmpty(doctorVo.getUser().getUserPassword().trim())
                || StringUtils.isEmpty(doctorVo.getDepartment().getDepartmentCode().trim()) || StringUtils.isEmpty(doctorVo.getDoctorIntroduction().trim())
                || StringUtils.isEmpty(doctorVo.getDoctorName().trim()) || StringUtils.isEmpty(doctorVo.getDoctorProfessionalTitle().trim())
                || StringUtils.isEmpty(doctorVo.getEmployeeId().trim())
        ) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if (userService.findByUserName(doctorVo.getUser().getUserName()) != null) {
            return ServerResponseVO.error(ServerResponseEnum.USER_REPEAT_ERROR);
        }
        User user = UserController.doHashedCredentials(doctorVo.getUser().getUserName(), doctorVo.getUser().getUserPassword());
        userService.addUser(user);
        Doctor doctor = new Doctor();
        doctor.setDepartmentCode(doctorVo.getDepartment().getDepartmentCode());
        doctor.setDoctorIntroduction(doctorVo.getDoctorIntroduction());
        doctor.setDoctorName(doctorVo.getDoctorName());
        doctor.setDoctorProfessionalTitle(doctorVo.getDoctorProfessionalTitle());
        doctor.setEmployeeId(doctorVo.getEmployeeId());
        doctor.setUserId(user.getUserId());
        doctorService.addDoctor(doctor);
        return ServerResponseVO.success();
    }

    @PostMapping("/endSeeDoctor")
    public <T> ServerResponseVO<T> endSeeDoctor(@RequestParam("uid") Long uid, @RequestParam(value = "doctorTicketId") Long doctorTicketId) {
        if (doctorTicketId == null || uid == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        //将当前挂号置为已完成
        DoctorTicket doctorTicket = doctorService.endSeeDoctor(doctorTicketId);
        return MyUtil.cast(ServerResponseVO.success(doctorTicket));
    }

    @DeleteMapping
    public <T> ServerResponseVO<T> deleteDoctorAndUserByDoctorId(@RequestParam(name = "doctorId") Long doctorId,
                                                                 @RequestParam(name = "userId") Long userId) {
        try {
            doctorService.deleteDoctorAndUser(doctorId, userId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.USER_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public <T> ServerResponseVO<T> deleteDoctorsAndUsersByIds(@RequestBody List<DoctorVo> doctorVos) {
        List<Doctor> doctors = new ArrayList<>(16);
        List<User> users = new ArrayList<>(16);
        for (DoctorVo doctorVo : doctorVos) {
            Doctor doctor = new Doctor();
            doctor.setDoctorId(doctorVo.getDoctorId());
            doctors.add(doctor);
            users.add(doctorVo.getUser());
        }
        Integer a = doctorService.deleteDoctorAndUserBatch(doctors, users);
        //正常情况应该删除n个医生就有n个对应的用户也删除
        int b = 2;
        if (doctorVos.size() * b == a) {
            return ServerResponseVO.success();
        }
        return ServerResponseVO.error(ServerResponseEnum.DOCTOR_DELETE_FAIL);
    }


}
