package com.gxf.his.service;

import com.gxf.his.po.generate.DoctorTicket;
import com.gxf.his.po.generate.Prescription;
import com.gxf.his.po.vo.PrescriptionVo;
import com.gxf.his.po.vo.TicketVo;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/10 16:40
 */
public interface TicketService {

    /**
     * 开始排队
     * @param doctorTicketId 挂号信息ID
     * @return 是否成功排队
     */
    Boolean doQueue(Long doctorTicketId);

    /**
     * 检查是否出现挂号未使用但是过期的
     * 切换挂号状态为过期
     */
    void checkExpiredTicket();

    /**
     * 检查是否出现挂号排队中但是直到过期也没有被叫号的人
     * 每天检查一次
     * 切换挂号状态为过期
     */
    void checkExpiredTicketForUnCalling();

    /**
     * 某医生获取呼叫下一位患者
     *
     * @param doctorId 医生ID
     * @param rank     上一位挂号者ID，如果没有上一位，则为0
     * @return 票务信息
     */
    TicketVo getCurrentRankPatient(Long doctorId, Integer rank);


    /**
     * 插入一条挂号记录
     *
     * @param ticket 挂号信息
     * @return 本次操作影响的行数
     */
    int insertTicket(DoctorTicket ticket);

    /**
     * 根据患者ID获取所有挂号记录 没有使用
     *
     * @param patientId 患者ID
     * @return 挂号记录列表
     */
    List<DoctorTicket> getTicketByPatientId(Long patientId);

    /**
     * 根据挂号信息ID获取所有挂号记录
     *
     * @param doctorTicketId 挂号信息ID
     * @return 挂号信息
     */
    TicketVo getTicketById(Long doctorTicketId);

    /**
     * 根据患者ID获取暂未就诊的挂号信息列表
     *
     * @param patientId 患者ID
     * @return 未就诊的挂号记录列表
     */
    List<DoctorTicket> getDidNotUsePatientTicket(Long patientId);

    /**
     * 修改挂号信息
     *
     * @param ticket 挂号信息
     * @return 本次操作影响的行数
     */
    int updateTicketInfo(DoctorTicket ticket);

    /**
     * 根据挂号信息的ID删除挂号信息
     *
     * @param ticketId 挂号信息的ID
     * @return 本次操作影响的行数
     */
    int deleteTicketByPrimaryKey(Long ticketId);

    /**
     * 获取某位医生应该叫号的号数
     * @param doctorId 医生ID
     * @return 号数
     */
    Integer getCurrentDoctorRank(Long doctorId);
}
