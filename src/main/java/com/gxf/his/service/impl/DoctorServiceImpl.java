package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DoctorException;
import com.gxf.his.mapper.DoctorMapper;
import com.gxf.his.po.Doctor;
import com.gxf.his.service.DoctorService;
import com.gxf.his.vo.DoctorUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
@Service
public class DoctorServiceImpl implements DoctorService {
    private Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);
    @Resource
    private DoctorMapper doctorMapper;
    @Override
    public Long addDoctor(Doctor doctor) throws DoctorException {
        try {
            doctorMapper.insert(doctor);
        }catch (Exception e){
            logger.error("添加医生失败",e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_SAVE_FAIL);
        }
        return  doctor.getDoctorId();
    }

    @Override
    public List<DoctorUserVo> getAllDoctors() {
        List<DoctorUserVo> doctors ;
        try {
            doctors = doctorMapper.selectAll();
        }catch (Exception e){
            logger.error("查询所有医生失败",e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public List<DoctorUserVo> getDoctorsByDepartmentCode(String departmentCode) {
        List<DoctorUserVo> doctors ;
        try {
            doctors = doctorMapper.selectDoctorByCondition(departmentCode);
        }catch (Exception e){
            logger.error("按部门查询医生失败",e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public Long updateDoctor(Doctor doctor) {
        try {
            doctorMapper.updateByPrimaryKey(doctor);
        }catch (Exception e){
            logger.error("医生信息更新失败",e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_UPDATE_FAIL);
        }
        return doctor.getDoctorId();
    }
}
