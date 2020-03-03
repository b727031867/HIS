package com.gxf.his.service;

import com.gxf.his.po.generate.DoctorTicket;
import com.gxf.his.po.generate.PatientFile;
import com.gxf.his.po.vo.OrderVo;
import com.gxf.his.po.vo.PatientVo;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.User;
import com.gxf.his.po.vo.TicketVo;

import java.util.HashMap;
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
     * 根据患者ID获取患者
     * @param patientId 患者ID
     * @return 患者
     */
    Patient getPatientByPrimaryKey(Long patientId);

    /**
     * 根据用户UID获取患者
     * @param uid 患者用户的UID
     * @return 患者
     */
    Patient getPatientByUid(Long uid);

    /**
     * 根据用户UID获取患者
     * 并且关联查询档案
     * @param uid 患者用户的UID
     * @return 患者
     */
    PatientVo getPatientByUidRelated(Long uid);

    /**
     * 获取当前未使用和排队中的挂号信息
     * @param patientId 患者ID
     * @return 挂号信息列表
     */
    List<TicketVo> getQueueRegisterOrder(Long patientId);

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

    /**
     * 获取某个患者的所有订单
     * @param patientId 患者ID
     * @return 不同类型的订单列表 包括挂号单 处方单 检查单
     */
    HashMap<String,List<OrderVo>> getAllPatientOrders(Long patientId);

    /**
     * 插入一条病人档案信息
     * @param patientFile 病人档案实体
     */
    void addPatientFile(PatientFile patientFile);

    /**
     * 更新一条病人档案信息
     * @param patientFile 病人档案实体
     */
    void updatePatientFile(PatientFile patientFile);

    /**
     * 根据挂号信息ID查询患者信息
     * @param doctorTicketId 挂号信息ID
     * @return 患者信息
     */
    Patient getPatientByDoctorTicketId(Long doctorTicketId);
}
