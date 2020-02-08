package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.PatientException;
import com.gxf.his.mapper.PatientMapper;
import com.gxf.his.po.Patient;
import com.gxf.his.po.User;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.UserService;
import com.gxf.his.vo.PatientUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PatientMapper patientMapper;

    @Autowired
    private UserService userService;

    @Override
    public void addPatient(Patient patient) throws PatientException {
        try {
            patientMapper.insert(patient);
        }catch (Exception e){
            log.error("病人保存失败",e);
            throw new PatientException(ServerResponseEnum.PATIENT_SAVE_FAIL);
        }
    }

    @Override
    public List<PatientUserVo> getAllPatients() {
        List<PatientUserVo> patients;
        try {
            patients = patientMapper.selectAll();
        } catch (Exception e) {
            log.error("查询所有病人失败", e);
            throw new PatientException(ServerResponseEnum.PATIENT_LIST_FAIL);
        }
        return patients;
    }

    @Override
    public List<PatientUserVo> selectPatientByAttribute(PatientUserVo patientUserVo) {
        List<PatientUserVo> patients;
        try {
            if(patientUserVo.getIsAccurate()){
                patients = patientMapper.selectPatientByAccurateAttribute(patientUserVo);
            }else{
                patients = patientMapper.selectPatientByAttribute(patientUserVo);
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
            return patientMapper.updateByPrimaryKey(patient);
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
        a = patientMapper.deleteByPrimaryKey(patientId) + a;
        a = userService.deleteUser(userId) + a;
        if(a != b){
            log.warn("删除时，没有找到ID为"+patientId+"的用户并且ID为"+userId+"的病人！");
            throw new PatientException(ServerResponseEnum.PATIENT_DELETE_FAIL);
        }
        return 1;
    }

    @Override
    public int deletePatientAndUserBatch(List<Patient> patients, List<User> users) {
        try {
            Integer a;
            a = patientMapper.batchPatientDelete(patients);
            a = userService.deleteUserBatch(users)+a;
            return a;
        }catch (Exception e){
            e.printStackTrace();
            throw new PatientException(ServerResponseEnum.PATIENT_DELETE_FAIL);
        }
    }
}
