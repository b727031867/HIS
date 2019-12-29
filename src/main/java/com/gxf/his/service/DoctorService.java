package com.gxf.his.service;

import com.gxf.his.po.Doctor;
import com.gxf.his.po.User;
import com.gxf.his.vo.DoctorUserVo;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
public interface DoctorService {
    /**
     * 添加一位医生
     * @param doctor 需要添加的医生
     */
    Long addDoctor(Doctor doctor);

    /**
     * 删除一位医生
     * @param doctorId 医生的Id
     * @param userId 医生对应的用户Id
     * @return 影响的行数
     */
    void deleteDoctorAndUser (Long doctorId,Long userId) throws Exception;

    /**
     * 批量删除医生和其对应的用户
     * @param doctors 要删除的医生列表
     * @param users 要删除的用户列表
     * @return 影响的行数
     */
    Integer deleteDoctorAndUserBatch(List<Doctor> doctors, List<User> users);

    /**
     * 查询所有的医生
     * @return 所有的医生
     */
    List<DoctorUserVo> getAllDoctors();

    /**
     * 根据部门编号查询医生
     * @param departmentCode 部门编号
     * @return 医生列表
     */
    List<DoctorUserVo> getDoctorsByDepartmentCode(String departmentCode);

    /**
     * 更新此医生的信息
     * @param doctor
     * @return 被更新医生的ID
     */
    Long updateDoctor(Doctor doctor);
}
