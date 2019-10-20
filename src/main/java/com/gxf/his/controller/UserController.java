package com.gxf.his.controller;

import com.gxf.his.Const;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.Doctor;
import com.gxf.his.po.Patient;
import com.gxf.his.po.User;
import com.gxf.his.service.DoctorService;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.UserService;
import com.gxf.his.vo.DoctorUserVo;
import com.gxf.his.vo.PatientUserVo;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;



    @PostMapping("/savePatient")
    public ServerResponseVO savePatient(PatientUserVo patientUserVo) {
        if (StringUtils.isEmpty(patientUserVo.getUserName().trim()) || StringUtils.isEmpty(patientUserVo.getUserPassword().trim())) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        User user = (User) doHashedCredentials(patientUserVo.getUserName(), patientUserVo.getUserPassword());
        Integer userId = userService.addUser(user);
        Patient patient = new Patient();
        patient.setPatientCard(patientUserVo.getPatientCard());
        patient.setPatientMedicareCard(patientUserVo.getPatientMedicareCard());
        patient.setPatientName(patientUserVo.getPatientName());
        patient.setPatientSex(patientUserVo.getPatientSex());
        patient.setUserId(userId);
        patientService.addPatient(patient);
        return ServerResponseVO.success();
    }

    @PostMapping("/saveDoctor")
    public ServerResponseVO saveDoctor(DoctorUserVo doctorUserVo) {
        if (StringUtils.isEmpty(doctorUserVo.getUserName().trim()) || StringUtils.isEmpty(doctorUserVo.getUserPassword().trim())
                || StringUtils.isEmpty(doctorUserVo.getDepartmentCode().trim()) || StringUtils.isEmpty(doctorUserVo.getDoctorIntroduction().trim())
                || StringUtils.isEmpty(doctorUserVo.getDoctorName().trim()) || StringUtils.isEmpty(doctorUserVo.getDoctorProfessionalTitle().trim())
                || StringUtils.isEmpty(doctorUserVo.getEmployeeId().trim())
        ) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        User user = (User) doHashedCredentials(doctorUserVo.getUserName(), doctorUserVo.getUserPassword());
        Integer userId = userService.addUser(user);
        Doctor doctor = new Doctor();
        doctor.setDepartmentCode(doctorUserVo.getDepartmentCode());
        doctor.setDoctorIntroduction(doctorUserVo.getDoctorIntroduction());
        doctor.setDoctorName(doctorUserVo.getDoctorName());
        doctor.setDoctorProfessionalTitle(doctorUserVo.getDoctorProfessionalTitle());
        doctor.setEmployeeId(doctorUserVo.getEmployeeId());
        doctor.setUserId(userId);
        doctorService.addDoctor(doctor);
        return ServerResponseVO.success();
    }

    @PostMapping("/save")
    public ServerResponseVO save(User user) {
        if (StringUtils.isEmpty(user.getUserName().trim()) || StringUtils.isEmpty(user.getUserPassword().trim())) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        user = (User) doHashedCredentials(user.getUserName(), user.getUserPassword());
        if (user != null) {
            Integer i = userService.addUser(user);
            if (i < 1) {
                logger.error("注册时，数据插入异常");
                return ServerResponseVO.error(ServerResponseEnum.REGISTERED_FAIL);
            }
        }
        return ServerResponseVO.success(ServerResponseEnum.SUCCESS);
    }

    @PostMapping("/login")
    public ServerResponseVO login(@RequestParam(value = "username") String userName,
                                  @RequestParam(value = "password") String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Subject userSubject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            // 登录验证
            userSubject.login(token);
            return ServerResponseVO.success();
        } catch (UnknownAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_NOT_EXIST);
        } catch (DisabledAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_IS_DISABLED);
        } catch (IncorrectCredentialsException e) {
            return ServerResponseVO.error(ServerResponseEnum.INCORRECT_CREDENTIALS);
        } catch (Throwable e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.ERROR);
        }
    }


    private Object doHashedCredentials(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        //默认注册的用户状态是正常状态
        user.setUserStatus(new Byte("1"));
        //使用密码和用户名和时间戳混合加密，生成盐
        ByteSource salt =
                ByteSource.Util.bytes(password + System.currentTimeMillis() + userName);
        String saltDeCode;
        String hashDeCode;
        //设置盐和密文的编码方式，并且使用盐加密密码
        if (Const.DECIMAL) {
            saltDeCode = salt.toHex();
            SimpleHash sh = new SimpleHash(Const.ENCRYPTION, password,
                    saltDeCode, Const.ENCRYPTION_NUM);
            hashDeCode = sh.toHex();
        } else {
            saltDeCode = salt.toBase64();
            SimpleHash sh = new SimpleHash(Const.ENCRYPTION, password,
                    saltDeCode, Const.ENCRYPTION_NUM);
            hashDeCode = sh.toBase64();
        }
        user.setUserSalt(saltDeCode);
        user.setUserPassword(hashDeCode);
        //设置当前注册时间
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ft.format(now);
        user.setUserCreateDate(now);
        return user;
    }
}
