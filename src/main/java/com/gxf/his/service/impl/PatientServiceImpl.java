package com.gxf.his.service.impl;

import com.gxf.his.controller.UserController;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.PatientException;
import com.gxf.his.mapper.PatientMapper;
import com.gxf.his.po.Patient;
import com.gxf.his.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
@Service
public class PatientServiceImpl implements PatientService {
    private Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Resource
    private PatientMapper patientMapper;

    @Override
    public void addPatient(Patient patient) throws PatientException {
        try {
            patientMapper.insert(patient);
        }catch (Exception e){
            logger.error("病人保存失败",e);
            throw new PatientException(ServerResponseEnum.PATIENT_SAVE_FAIL);
        }
    }
}
