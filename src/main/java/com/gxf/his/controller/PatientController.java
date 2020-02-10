package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.PatientVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.User;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {
    @Resource
    private PatientService patientService;
    @Resource
    private UserService userService;


    @GetMapping
    public ServerResponseVO getPatients(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        PageHelper.startPage(page, size);
        List<PatientVo> patients = patientService.getAllPatients();
        PageInfo<PatientVo> pageInfo = PageInfo.of(patients);
        return ServerResponseVO.success(pageInfo);
    }

    @GetMapping("/attribute")
    public ServerResponseVO getPatientsByAttribute(@RequestParam(value = "attribute", defaultValue = "patientName") String attribute
            , @RequestParam(value = "isAccurate") Boolean isAccurate, @RequestParam(value = "value") String value, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if (value == null || value.trim().length() == 0 || isAccurate == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PatientVo patientVo = new PatientVo();
        patientVo.setSearchAttribute(attribute.trim());
        patientVo.setValue(value.trim());
        patientVo.setIsAccurate(isAccurate);
        PageHelper.startPage(page, size);
        List<PatientVo> patients = patientService.selectPatientByAttribute(patientVo);
        PageInfo<PatientVo> pageInfo = PageInfo.of(patients);
        return ServerResponseVO.success(pageInfo);
    }

    @PutMapping
    public ServerResponseVO savePatientAndUser(@RequestBody PatientVo patientVo) {
        log.info("当前更新的病人信息为：" + patientVo.toString());
        Patient patient = new Patient();
        patient.setPatientId(patientVo.getPatientId());
        patient.setPatientAge(patientVo.getPatientAge());
        patient.setPatientName(patientVo.getPatientName());
        patient.setPatientIsMarriage(patientVo.getPatientIsMarriage());
        patient.setPatientCard(patientVo.getPatientCard());
        patient.setPatientPhone(patientVo.getPatientPhone());
        patient.setPatientSex(patientVo.getPatientSex());
        patient.setPatientMedicareCard(patientVo.getPatientMedicareCard());
        patient.setUserId(patientVo.getUser().getUserId());
        //更新病人信息
        patientService.updatePatient(patient);
        //更新用户信息
        User user = patientVo.getUser();
        userService.updateUser(user);
        return ServerResponseVO.success();
    }

    @PostMapping
    public ServerResponseVO savePatient(@RequestBody PatientVo patientVo) {
        if (StringUtils.isEmpty(patientVo.getUser().getUserName().trim()) ||
                StringUtils.isEmpty(patientVo.getUser().getUserPassword().trim()) ||
                StringUtils.isEmpty(patientVo.getPatientPhone().trim()) ||
                StringUtils.isEmpty(patientVo.getPatientName().trim()) ||
                patientVo.getPatientAge() <= 0 ||
                patientVo.getPatientAge() >= 150
        ) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if (userService.findByUserName(patientVo.getUser().getUserName()) != null) {
            return ServerResponseVO.error(ServerResponseEnum.USER_REPEAT_ERROR);
        }
        User user = UserController.doHashedCredentials(patientVo.getUser().getUserName(), patientVo.getUser().getUserPassword());
        userService.addUser(user);
        Patient patient = new Patient();
        patient.setPatientCard(patientVo.getPatientCard());
        patient.setPatientMedicareCard(patientVo.getPatientMedicareCard());
        patient.setPatientName(patientVo.getPatientName());
        patient.setPatientSex(patientVo.getPatientSex());
        patient.setUserId(user.getUserId());
        patient.setPatientAge(patientVo.getPatientAge());
        patient.setPatientIsMarriage(patientVo.getPatientIsMarriage());
        patient.setPatientPhone(patientVo.getPatientPhone());
        patientService.addPatient(patient);
        return ServerResponseVO.success();
    }

    @DeleteMapping
    public ServerResponseVO deletePatientAndUserByPatientId(@RequestParam(name = "patientId") Long patientId,
                                                            @RequestParam(name = "userId") Long userId) {
        try {
            patientService.deletePatientAndUser(patientId, userId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.PATIENT_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public ServerResponseVO deletePatientsAndUsersByIds(@RequestBody List<PatientVo> patientVos) {
        List<Patient> patients = new ArrayList<>(16);
        List<User> users = new ArrayList<>(16);
        for (PatientVo patientVo : patientVos) {
            Patient patient = new Patient();
            patient.setPatientId(patientVo.getPatientId());
            patients.add(patient);
            users.add(patientVo.getUser());
        }
        int a = patientService.deletePatientAndUserBatch(patients, users);
        //正常情况应该删除n个医生就有n个对应的用户也删除
        int b = 2;
        if (patientVos.size() * b == a) {
            return ServerResponseVO.success();
        }
        return ServerResponseVO.error(ServerResponseEnum.PATIENT_DELETE_FAIL);
    }

}
