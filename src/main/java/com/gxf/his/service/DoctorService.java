package com.gxf.his.service;

import com.gxf.his.po.vo.DoctorVo;
import com.gxf.his.po.generate.Doctor;
import com.gxf.his.po.generate.User;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
public interface DoctorService {
    /**
     * 添加一位医生
     *
     * @param doctor 需要添加的医生
     * @return 返回本次操作影响的行数
     */
    int addDoctor(Doctor doctor);

    /**
     * 删除一位医生及其用户
     *
     * @param doctorId 医生的Id
     * @param userId   医生对应的用户Id
     * @return @return 返回本次操作影响的行数
     * @throws Exception 当删除一位医生时,没找到对应的用户,则会抛出异常
     */
    int deleteDoctorAndUser(Long doctorId, Long userId) throws Exception;

    /**
     * 批量删除医生和其对应的用户
     *
     * @param doctors 要删除的医生列表
     * @param users   要删除的用户列表
     * @return 返回本次操作影响的行数
     */
    Integer deleteDoctorAndUserBatch(List<Doctor> doctors, List<User> users);

    /**
     * 查询所有的医生
     *
     * @return 所有的医生
     */
    List<DoctorVo> getAllDoctors();

    /**
     * 根据部门编号查询医生
     *
     * @param departmentCode 部门编号
     * @return 医生列表
     */
    List<DoctorVo> getDoctorsByDepartmentCode(String departmentCode);

    /**
     * 根据属性查询医生
     *
     * @param doctorVo 医生的业务逻辑对象
     * @return 医生列表
     */
    List<DoctorVo> selectDoctorByAttribute(DoctorVo doctorVo);

    /**
     * 更新此医生的信息
     *
     * @param doctor 医生
     * @return 返回本次操作影响的行数
     */
    int updateDoctor(Doctor doctor);

}
