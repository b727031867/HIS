package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.Const;
import com.gxf.his.config.RedisClient;
import com.gxf.his.po.Doctor;
import com.gxf.his.po.User;
import com.gxf.his.service.DoctorService;
import com.gxf.his.service.UserService;
import com.gxf.his.vo.DoctorUserVo;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public DoctorController(DoctorService dservice,UserService uService ,RedisClient redis) {
        this.redis = redis;
        doctorService = dservice;
        userService = uService;
    }

    private RedisClient redis;

    private DoctorService doctorService;

    private UserService userService;


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

    @PutMapping
    public ServerResponseVO saveDoctorAndUser(@RequestBody DoctorUserVo doctorUserVo) {
        logger.info("当前更新的医生信息为：" + doctorUserVo.toString());
        Doctor doctor = new Doctor();
        User user = new User();
        //更新医生信息
        doctor.setDoctorId(doctorUserVo.getDoctorId());
        doctor.setEmployeeId(doctorUserVo.getEmployeeId());
        doctor.setDoctorName(doctorUserVo.getDoctorName());
        doctor.setDoctorProfessionalTitle(doctorUserVo.getDoctorProfessionalTitle());
        doctor.setDoctorIntroduction(doctorUserVo.getDoctorIntroduction());
        doctor.setDepartmentCode(doctorUserVo.getDepartmentCode());
        doctor.setSchedulingId(doctorUserVo.getSchedulingId());
        doctor.setUserId(doctorUserVo.getUser().getUserId());
        doctor.setTicketDayNum(doctorUserVo.getTicketDayNum());
        doctorService.updateDoctor(doctor);
        //更新用户信息
        user = doctorUserVo.getUser();
        //重新加密密码,生成新密码盐和密钥
        String password = user.getUserPassword();
        User tempUser = UserController.doHashedCredentials(user.getUserName(),password);
        user.setUserPassword(tempUser.getUserPassword());
        user.setUserSalt(tempUser.getUserSalt());
        logger.info(user.toString());
        userService.updateUser(user);
        return ServerResponseVO.success();
    }


}
