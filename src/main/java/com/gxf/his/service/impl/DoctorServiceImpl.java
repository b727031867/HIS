package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DoctorException;
import com.gxf.his.mapper.dao.IDoctorMapper;
import com.gxf.his.po.vo.DoctorVo;
import com.gxf.his.po.generate.Doctor;
import com.gxf.his.po.generate.User;
import com.gxf.his.service.DoctorService;
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
public class DoctorServiceImpl implements DoctorService {
    @Resource
    private IDoctorMapper iDoctorMapper;
    @Resource
    private UserService userService;

    @Override
    public int addDoctor(Doctor doctor) throws DoctorException {
        try {
            return iDoctorMapper.insert(doctor);
        } catch (Exception e) {
            log.error("添加医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_SAVE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDoctorAndUser(Long doctorId, Long userId) throws Exception {
        int a = 0;
        int b = 2;
        a = iDoctorMapper.deleteByPrimaryKey(doctorId) + a;
        a = userService.deleteUser(userId) + a;
        if(a != b){
            log.warn("删除时，没有找到ID为"+doctorId+"的用户或ID为"+userId+"的医生！");
            throw new Exception();
        }
        return b;
    }

    @Override
    @Transactional(rollbackFor = DoctorException.class)
    public Integer deleteDoctorAndUserBatch(List<Doctor> doctors, List<User> users) {
        try {
            Integer a;
            a = iDoctorMapper.batchDoctorDelete(doctors);
            a = userService.deleteUserBatch(users)+a;
            return a;
        }catch (Exception e){
            e.printStackTrace();
            throw new DoctorException(ServerResponseEnum.DOCTOR_DELETE_FAIL);
        }
    }

    @Override
    public List<DoctorVo> getAllDoctors() {
        List<DoctorVo> doctors;
        try {
            doctors = iDoctorMapper.selectAllDoctorsInfo();
        } catch (Exception e) {
            log.error("查询所有医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public List<DoctorVo> getDoctorsByDepartmentCode(String departmentCode) {
        List<DoctorVo> doctors;
        try {
            doctors = iDoctorMapper.selectDoctorByDepartmentCode(departmentCode);
        } catch (Exception e) {
            log.error("按部门查询医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public List<DoctorVo> selectDoctorByAttribute(DoctorVo doctorVo) {
        List<DoctorVo> doctors;
        try {
            doctors = iDoctorMapper.selectDoctorByAttribute(doctorVo);
        } catch (Exception e) {
            log.error("按属性查询医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public int updateDoctor(Doctor doctor) {
        try {
            return iDoctorMapper.updateByPrimaryKey(doctor);
        } catch (Exception e) {
            log.error("医生信息更新失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_UPDATE_FAIL);
        }
    }



}
