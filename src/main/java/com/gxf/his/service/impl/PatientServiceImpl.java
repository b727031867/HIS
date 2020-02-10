package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.PatientException;
import com.gxf.his.mapper.dao.IPatientMapper;
import com.gxf.his.po.vo.PatientVo;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.User;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Resource
    private IPatientMapper iPatientMapper;

    @Resource
    private UserService userService;

    @Override
    public void addPatient(Patient patient) throws PatientException {
        try {
            iPatientMapper.insert(patient);
        } catch (Exception e) {
            log.error("病人保存失败", e);
            throw new PatientException(ServerResponseEnum.PATIENT_SAVE_FAIL);
        }
    }

    @Override
    public Patient getPatientByPrimaryKey(Long patientId) {
        Patient patient;
        try {
            patient = iPatientMapper.selectByPrimaryKey(patientId);
        } catch (Exception e) {
            log.error("根据ID查询病人失败", e);
            throw new PatientException(ServerResponseEnum.PATIENT_LIST_FAIL);
        }
        return patient;
    }

    @Override
    public Patient getPatientByUid(Long uid) {
        Patient patient;
        try {
            patient = iPatientMapper.selectByUid(uid);
        } catch (Exception e) {
            log.error("根据UID查询病人失败", e);
            throw new PatientException(ServerResponseEnum.PATIENT_LIST_FAIL);
        }
        return patient;
    }

    @Override
    public List<PatientVo> getAllPatients() {
        List<PatientVo> patients;
        try {
            patients = iPatientMapper.selectAllPatientsInfo();
        } catch (Exception e) {
            log.error("查询所有病人失败", e);
            throw new PatientException(ServerResponseEnum.PATIENT_LIST_FAIL);
        }
        return patients;
    }

    @Override
    public List<PatientVo> selectPatientByAttribute(PatientVo patientVo) {
        List<PatientVo> patients;
        try {
            if (patientVo.getIsAccurate()) {
                patients = iPatientMapper.selectPatientByAccurateAttribute(patientVo);
            } else {
                patients = iPatientMapper.selectPatientByAttribute(patientVo);
            }
        } catch (Exception e) {
            log.error("按属性查询病人失败", e);
            throw new PatientException(ServerResponseEnum.PATIENT_LIST_FAIL);
        }
        return patients;
    }

    @Override
    public int updatePatient(Patient patient) {
        try {
            return iPatientMapper.updateByPrimaryKey(patient);
        } catch (Exception e) {
            log.error("病人信息更新失败", e);
            throw new PatientException(ServerResponseEnum.PATIENT_UPDATE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = PatientException.class)
    public int deletePatientAndUser(Long patientId, Long userId) {
        int a = 0;
        int b = 2;
        a = iPatientMapper.deleteByPrimaryKey(patientId) + a;
        a = userService.deleteUser(userId) + a;
        if (a != b) {
            log.warn("删除时，没有找到ID为" + patientId + "的用户并且ID为" + userId + "的病人！");
            throw new PatientException(ServerResponseEnum.PATIENT_DELETE_FAIL);
        }
        return 1;
    }

    @Override
    public int deletePatientAndUserBatch(List<Patient> patients, List<User> users) {
        try {
            Integer a;
            a = iPatientMapper.batchPatientDelete(patients);
            a = userService.deleteUserBatch(users) + a;
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PatientException(ServerResponseEnum.PATIENT_DELETE_FAIL);
        }
    }
}
