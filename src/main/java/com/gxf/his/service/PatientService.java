package com.gxf.his.service;

import com.gxf.his.po.vo.PatientVo;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.User;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
public interface PatientService {

    /**
     * 添加一位病人
     *
     * @param patient 病人对象
     */
    void addPatient(Patient patient);

    /**
     * 获取所有病人列表
     *
     * @return 病人列表
     */
    List<PatientVo> getAllPatients();

    /**
     * 根据属性进行模糊或者精确查询病人列表
     *
     * @param patientVo 查询信息
     * @return 病人列表
     */
    List<PatientVo> selectPatientByAttribute(PatientVo patientVo);

    /**
     * 更新某个病人
     *
     * @param patient 病人对象
     * @return 本次更新影响的行数
     */
    int updatePatient(Patient patient);

    /**
     * 删除某个病人，包括病人对象与对应的病人用户
     *
     * @param patientId 病人对象的ID
     * @param userId    病人用户的ID
     * @return 本次删除的病人数
     */
    int deletePatientAndUser(Long patientId, Long userId);

    /**
     * 批量删除病人对象与对应的病人用户
     *
     * @param patients 病人对象列表
     * @param users    病人用户列表
     * @return 本次删除影响的行数
     */
    int deletePatientAndUserBatch(List<Patient> patients, List<User> users);
}
