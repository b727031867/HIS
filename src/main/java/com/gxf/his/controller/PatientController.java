package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.config.RedisClient;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.Patient;
import com.gxf.his.po.User;
import com.gxf.his.service.DepartmentService;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.UserService;
import com.gxf.his.vo.PatientUserVo;
import com.gxf.his.vo.ServerResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@RestController
public class PatientController {
    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    public PatientController(PatientService dservice, UserService uService, DepartmentService depService, RedisClient redis) {
        this.redis = redis;
        patientService = dservice;
        userService = uService;
        departmentService = depService;
    }

    private RedisClient redis;

    private PatientService patientService;

    private UserService userService;

    private DepartmentService departmentService;

    @GetMapping
    public ServerResponseVO getPatients(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
            PageHelper.startPage(page, size);
            List<PatientUserVo> patients = patientService.getAllPatients();
            PageInfo<PatientUserVo> pageInfo = PageInfo.of(patients);
            return ServerResponseVO.success(pageInfo);
    }

    @GetMapping("/attribute")
    public ServerResponseVO getDoctorsByAttribute(@RequestParam(value = "attribute",defaultValue = "patientName") String attribute
            , @RequestParam(value = "isAccurate") Boolean isAccurate , @RequestParam(value = "value") String value , @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if(value == null || value.trim().length() == 0 || isAccurate == null){
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PatientUserVo patientUserVo =new PatientUserVo();
        patientUserVo.setSearchAttribute(attribute);
        patientUserVo.setValue(value);
//        List<Department> departments = departmentService.getDepartmentsByVaguelyDepartmentName(value);
//            doctorUserVo.setDepartments(departments);
        PageHelper.startPage(page, size);
        List<PatientUserVo> patients = patientService.selectPatientByAttribute(patientUserVo);
        PageInfo<PatientUserVo> pageInfo = PageInfo.of(patients);
        return ServerResponseVO.success(pageInfo);
    }

    @PutMapping
    public ServerResponseVO saveDoctorAndUser(@RequestBody PatientUserVo patientUserVo) {
        logger.info("当前更新的病人信息为：" + patientUserVo.toString());
        Patient doctor = new Patient();
        //更新病人信息
//        doctor.setDoctorId(doctorUserVo.getDoctorId());
//        doctor.setEmployeeId(doctorUserVo.getEmployeeId());
//        doctor.setDoctorName(doctorUserVo.getDoctorName());
//        doctor.setDoctorProfessionalTitle(doctorUserVo.getDoctorProfessionalTitle());
//        doctor.setDoctorIntroduction(doctorUserVo.getDoctorIntroduction());
//        doctor.setDepartmentCode(doctorUserVo.getDepartment().getDepartmentCode());
//        doctor.setSchedulingId(doctorUserVo.getSchedulingId());
//        doctor.setUserId(doctorUserVo.getUser().getUserId());
//        doctor.setTicketDayNum(doctorUserVo.getTicketDayNum());
        patientService.updatePatient(doctor);
        //更新用户信息
        User user = patientUserVo.getUser();
        //重新加密密码,生成新密码盐和密钥
        String password = user.getUserPassword();
        if(!"".equals(password.trim())){
            User tempUser = UserController.doHashedCredentials(user.getUserName(), password);
            user.setUserPassword(tempUser.getUserPassword());
            user.setUserSalt(tempUser.getUserSalt());
            logger.info(user.toString());
            userService.updateUser(user);
        }
        return ServerResponseVO.success();
    }

    @DeleteMapping
    public ServerResponseVO deleteDoctorAndUserByDoctorId(@RequestParam(name = "patientId") Long patientId,
                                                          @RequestParam(name = "userId") Long userId) {
        try {
            patientService.deletePatientAndUser(patientId, userId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.USER_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public ServerResponseVO deleteDoctorsAndUsersByIds(@RequestBody List<PatientUserVo> patientUserVos){
        List<Patient> patients = new ArrayList<>(16);
        List<User> users = new ArrayList<>(16);
        for(PatientUserVo patientUserVo : patientUserVos){
            Patient patient = new Patient();
            patient.setPatientId(patientUserVo.getPatientId());
            patients.add(patient);
            users.add(patientUserVo.getUser());
        }
        int a = patientService.deletePatientAndUserBatch(patients,users);
        //正常情况应该删除n个医生就有n个对应的用户也删除
        int b = 2;
        if(patientUserVos.size()*b == a){
            return ServerResponseVO.success();
        }
        return  ServerResponseVO.error(ServerResponseEnum.PATIENT_DELETE_FAIL);
    }

}
