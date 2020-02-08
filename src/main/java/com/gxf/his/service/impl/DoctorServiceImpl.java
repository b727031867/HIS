package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DoctorException;
import com.gxf.his.mapper.DoctorMapper;
import com.gxf.his.po.Doctor;
import com.gxf.his.po.User;
import com.gxf.his.service.DoctorService;
import com.gxf.his.service.UserService;
import com.gxf.his.vo.DoctorUserVo;
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
public class DoctorServiceImpl implements DoctorService {
    @Resource
    private DoctorMapper doctorMapper;
    @Autowired
    private UserService userService;

    @Override
    public Long addDoctor(Doctor doctor) throws DoctorException {
        try {
            doctorMapper.insert(doctor);
        } catch (Exception e) {
            log.error("添加医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_SAVE_FAIL);
        }
        return doctor.getDoctorId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDoctorAndUser(Long doctorId, Long userId) throws Exception {
        int a = 0;
        int b = 2;
        a = doctorMapper.deleteByPrimaryKey(doctorId) + a;
        a = userService.deleteUser(userId) + a;
        if(a != b){
            log.warn("删除时，没有找到ID为"+doctorId+"的用户或ID为"+userId+"的医生！");
            throw new Exception();
        }
    }

    @Override
    @Transactional(rollbackFor = DoctorException.class)
    public Integer deleteDoctorAndUserBatch(List<Doctor> doctors, List<User> users) {
        try {
            Integer a;
            a = doctorMapper.batchDoctorDelete(doctors);
            a = userService.deleteUserBatch(users)+a;
            return a;
        }catch (Exception e){
            e.printStackTrace();
            throw new DoctorException(ServerResponseEnum.DOCTOR_DELETE_FAIL);
        }
    }

    @Override
    public List<DoctorUserVo> getAllDoctors() {
        List<DoctorUserVo> doctors;
        try {
            doctors = doctorMapper.selectAll();
        } catch (Exception e) {
            log.error("查询所有医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public List<DoctorUserVo> getDoctorsByDepartmentCode(String departmentCode) {
        List<DoctorUserVo> doctors;
        try {
            doctors = doctorMapper.selectDoctorByDepartmentCode(departmentCode);
        } catch (Exception e) {
            log.error("按部门查询医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public List<DoctorUserVo> selectDoctorByAttribute(DoctorUserVo doctorUserVo) {
        List<DoctorUserVo> doctors;
        try {
            doctors = doctorMapper.selectDoctorByAtribute(doctorUserVo);
        } catch (Exception e) {
            log.error("按属性查询医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public Long updateDoctor(Doctor doctor) {
        try {
            doctorMapper.updateByPrimaryKey(doctor);
        } catch (Exception e) {
            log.error("医生信息更新失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_UPDATE_FAIL);
        }
        return doctor.getDoctorId();
    }
}
