package com.gxf.his.service;

import com.gxf.his.po.generate.DoctorTicket;

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
     * 根据患者ID获取所有挂号记录 没有使用
     *
     * @param doctorTicketId 挂号信息ID
     * @return 挂号信息
     */
    DoctorTicket getTicketById(Long doctorTicketId);

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

}
