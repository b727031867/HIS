package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.PatientFile;
import com.gxf.his.po.generate.User;
import com.gxf.his.po.vo.*;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.TicketService;
import com.gxf.his.service.UserService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    @Resource
    private TicketService ticketService;

    @GetMapping("/doctorTicketId")
    public <T> ServerResponseVO<T> getDoctorByDoctorTicketId(@RequestParam("doctorTicketId") Long doctorTicketId) {
        if (doctorTicketId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Patient patient = patientService.getPatientByDoctorTicketId(doctorTicketId);
        return MyUtil.cast(ServerResponseVO.success(patient));
    }

    @GetMapping
    public <T> ServerResponseVO<T> getPatients(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        PageHelper.startPage(page, size);
        List<PatientVo> patients = patientService.getAllPatients();
        PageInfo<PatientVo> pageInfo = PageInfo.of(patients);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @PostMapping("/queue")
    public <T> ServerResponseVO<T> startPatientQueue(@RequestParam(name = "ticketId") Long ticketId) {
        if (ticketId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if (!ticketService.doQueue(ticketId)) {
            return ServerResponseVO.error(ServerResponseEnum.TICKET_QUEUE_FAIL);
        }
        return ServerResponseVO.success();
    }

    @GetMapping("/queue")
    public <T> ServerResponseVO<T> getPatientInQueue(Long uid) {
        if (uid == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Patient patient = patientService.getPatientByUid(uid);
        List<TicketVo> doctorTicketList = patientService.getQueueRegisterOrder(patient.getPatientId());
        if (doctorTicketList == null) {
            return MyUtil.cast(ServerResponseVO.error(ServerResponseEnum.TICKET_QUEUE_NULL));
        }
        return MyUtil.cast(ServerResponseVO.success(doctorTicketList));
    }

    @GetMapping("/onePatient")
    public <T> ServerResponseVO<T> getPatientByUid(Long uid) {
        if (uid == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PatientVo patient = patientService.getPatientByUidRelated(uid);
        byte bt = 0;
        if (patient.getPatientIsMarriage() == null) {
            patient.setPatientIsMarriage(bt);
        }
        if (patient.getPatientSex() == null) {
            patient.setPatientSex(bt);
        }
        return MyUtil.cast(ServerResponseVO.success(patient));
    }

    @GetMapping("/orderList")
    public <T> ServerResponseVO<T> getPatientOrderList(Long uid) {
        if (null == uid || uid < 0) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Patient patient = patientService.getPatientByUid(uid);
        HashMap<String, List<OrderVo>> allPatientOrders = patientService.getAllPatientOrders(patient.getPatientId());
        return MyUtil.cast(ServerResponseVO.success(allPatientOrders));
    }

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getPatientsByAttribute(@RequestParam(value = "attribute", defaultValue = "patientName") String attribute
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
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @PutMapping
    public <T> ServerResponseVO<T> savePatientAndUser(@RequestBody PatientVo patientVo) {
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
        patient.setUserId(patientVo.getUserId());
        if (null != patientVo.getUser()) {
            patient.setUserId(patientVo.getUser().getUserId());
            //更新用户信息
            User user = patientVo.getUser();
            userService.updateUser(user);
        }
        //更新病人信息
        patientService.updatePatient(patient);
        return ServerResponseVO.success();
    }

    /**
     * 修改患者档案信息，若不存在档案则创建新的档案
     *
     * @param patientFile 患者档案
     * @return 通用响应类
     */
    @PutMapping("/file")
    public <T> ServerResponseVO<T> updateOrSavePatientFile(@RequestBody PatientFile patientFile) {
        log.info("当前更新或的病人信息为：" + patientFile.toString());
        if (null == patientFile.getPatientFileId()) {
            patientFile.setCreateTime(new Date());
            patientService.addPatientFile(patientFile);
        } else {
            patientService.updatePatientFile(patientFile);
        }
        return MyUtil.cast(ServerResponseVO.success(patientFile));
    }

    @PostMapping
    public <T> ServerResponseVO<T> savePatient(@RequestBody PatientVo patientVo) {
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
    public <T> ServerResponseVO<T> deletePatientAndUserByPatientId(@RequestParam(name = "patientId") Long patientId,
                                                                   @RequestParam(name = "userId") Long userId) {
        try {
            patientService.deletePatientAndUser(patientId, userId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.PATIENT_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public <T> ServerResponseVO<T> deletePatientsAndUsersByIds(@RequestBody List<PatientVo> patientVos) {
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
