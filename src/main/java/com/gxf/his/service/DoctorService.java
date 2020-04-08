package com.gxf.his.service;

import com.gxf.his.po.generate.*;
import com.gxf.his.po.vo.DoctorVo;
import com.gxf.his.po.vo.TicketVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
public interface DoctorService {

    /**
     * 获取某时间段某医生的门诊的患者信息
     * 包括关联的电子病历以及处方单和检查单
     *
     * @param doctorId  医生ID
     * @param startDate 就诊日期开始时间
     * @param endDate   就诊日期结束时间
     * @return 此时间段内就诊的患者信息及关联的病历、处方、检查单
     */
    List<TicketVo> getOutpatients(Long doctorId, Date startDate, Date endDate);

    /**
     * 某医生获取呼叫中患者的票务信息
     *
     * @param doctorId 医生ID
     * @return 叫号中患者的票务信息
     */
    TicketVo getCallingPatient(Long doctorId);

    /**
     * 某医生获取当前候诊总人数以及当前就诊患者的排名
     *
     * @param doctorId 医生ID
     * @param status   状态类型 同挂号信息的status状态
     * @return 当前就诊的排名、以及总候诊数
     */
    HashMap<String, String> getCurrentRankInfo(Long doctorId, Integer status);

    /**
     * 查询某医生当前处于某种状态的患者的总数
     *
     * @param doctorId 医生ID
     * @param status   状态类型 同挂号信息的status状态
     * @return 当前总候诊数
     */
    Integer getTotalRank(Long doctorId, Integer status);

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
     * @param doctorSchedulingId 医生对应的排班信息
     * @return 返回本次操作影响的行数
     * @throws Exception 当删除一位医生时,没找到对应的用户,则会抛出异常
     */
    int deleteDoctorAndUser(Long doctorId, Long userId,Long doctorSchedulingId) throws Exception;

    /**
     * 批量删除医生和其对应的用户
     *
     * @param doctors 要删除的医生列表
     * @param users   要删除的用户列表
     * @return 返回本次操作影响的行数
     */
    Integer deleteDoctorAndUserBatch(List<Doctor> doctors, List<User> users);

    /**
     * 根据医生ID获取医生
     *
     * @param doctorId 医生ID
     * @return 医生
     */
    DoctorVo getDoctorByDoctorId(Long doctorId);

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

    /**
     * 让当前患者看病完毕
     *
     * @param doctorTicketId 挂号信息的ID
     * @return 状态被改为挂号完毕的挂号信息
     */
    DoctorTicket endSeeDoctor(Long doctorTicketId);

    /**
     * 将最近的就诊中的挂号状态置为已完成
     *
     * @param doctorId 医生的ID
     * @return 状态被改为挂号完毕的挂号信息
     */
    DoctorTicket usedRecentVisitingDoctorTicket(Long doctorId);

    /**
     * 医生保存患者电子病历
     *
     * @param doctorTicketId 挂号信息的ID
     * @param content        病历的内容
     */
    PatientMedicalRecord saveCaseHistory(Long doctorTicketId, String content);

    /**
     * 根据挂号信息ID获取医生
     *
     * @param doctorTicketId 挂号信息ID
     * @return 医生的业务类
     */
    DoctorVo getDoctorByDoctorTicketId(Long doctorTicketId);

    /**
     * 添加一个排班信息
     * @param doctorScheduling 排队信息实体
     */
    void addDoctorScheduling(DoctorScheduling doctorScheduling);

    /**
     * 修改医生的信息
     * @param doctor 医生对象
     * @param doctorScheduling 对应的排班信息
     */
    void updateDoctorAndDoctorScheduling(Doctor doctor, DoctorScheduling doctorScheduling);

    /**
     * 根据ID获取医生的信息
     * @param doctorId 医生ID
     * @return 医生业务实体类
     */
    DoctorVo getDoctorDetailById(Long doctorId);
}
