package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DoctorException;
import com.gxf.his.mapper.DoctorMapper;
import com.gxf.his.po.Doctor;
import com.gxf.his.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
@Service
public class DoctorServiceImpl implements DoctorService {
    private Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);
    @Autowired
    private DoctorMapper doctorMapper;
    @Override
    public void addDoctor(Doctor doctor) throws DoctorException {
        try {
            doctorMapper.insert(doctor);
        }catch (Exception e){
            logger.error("添加医生失败",e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_SAVE_FAIL);
        }
    }
}
