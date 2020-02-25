package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.DoctorTicketMapper;
import com.gxf.his.po.generate.DoctorTicket;
import com.gxf.his.po.vo.TicketVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * 挂号模块的DAO接口
 */
public interface ITicketMapper extends DoctorTicketMapper {

    /**
     * 根据患者ID获取患者的挂号信息列表
     *
     * @param patientId 患者ID
     * @return 某患者的挂号信息列表
     */
    @Select({
            "select",
            "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, active_time,",
            "ticket_validity_start, ticket_validity_end, doctor_id, patient_id, order_id,registered_resource_id,status",
            "from entity_doctor_ticket",
            "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<DoctorTicket> getTicketByPatientId(Long patientId);

    /**
     * 根据医生ID获取此医生当天的挂号信息列表
     *
     * @param doctorId 医生ID
     * @return 某医生的挂号信息列表
     */
    @Select({
            "select",
            "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, active_time,",
            "ticket_validity_start, ticket_validity_end, doctor_id, patient_id, order_id,registered_resource_id,status",
            "from entity_doctor_ticket",
            "where status = 4 AND doctor_id = #{doctorId,jdbcType=BIGINT} ORDER BY ticket_number "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_PATIENT)),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<TicketVo> getUsingTicketsByDoctorId(Long doctorId);

    /**
     * 根据时间范围和医生ID查询挂号列表
     *
     * @param doctorId 医生ID
     * @param start    开始时间
     * @param end      结束时间
     * @return 挂号信息列表
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where (status = 1 OR status = 5) AND ticket_validity_start <= #{end,jdbcType=TIMESTAMP} AND ticket_validity_start >= #{start,jdbcType=TIMESTAMP} order by active_time DESC "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<DoctorTicket> selectTicketByActiveTimeAndDoctorId(Long doctorId, Date start, Date end);


    /**
     * 根据患者ID获取患者
     * 暂未排队或正在排队或错过叫号或者已经过期的的挂号信息列表
     *
     * @param patientId 患者ID
     * @return 某患者的挂号信息列表
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where patient_id = #{patientId,jdbcType=BIGINT} AND (status = 0 OR status = 2 OR status = 3 OR status = 4 OR status = 5 OR status = 6 ) order by ticket_create_time DESC "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_DOCTOR_ALL)),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_PATIENT)),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<TicketVo> getUnusedAndUsingTickets(Long patientId);

    /**
     * 获取过期未使用的挂号信息列表
     *
     * @return 过期的挂号列表
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where ticket_validity_end < NOW() AND status = 0 "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<DoctorTicket> getExpiredAndUnusedTicket();

    /**
     * 获取排队中过期未被叫号的挂号信息列表
     *
     * @return 过期的挂号列表
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where ticket_validity_end < NOW() AND status = 4 "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<DoctorTicket> getExpiredAndUnCallingTicket();

    /**
     * 获取挂号后未付款的挂号信息列表
     *
     * @param orderId 订单编号
     * @return 未付款的挂号信息列表
     */
    @Select({
            "select",
            "ticket_id",
            "from entity_doctor_ticket",
            "where status = -1 AND order_id = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
    })
    List<DoctorTicket> selectUnPayExpireTicket(Long orderId);


    /**
     * 根据患者ID获取患者未就诊的挂号信息
     *
     * @param patientId 患者ID
     * @return 某患者未就诊的挂号信息列表
     */
    @Select({
            "select",
            "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, active_time,",
            "ticket_validity_start, ticket_validity_end, doctor_id, patient_id, order_id,registered_resource_id,status",
            "from entity_doctor_ticket",
            "where patient_id = #{patientId,jdbcType=BIGINT} AND status = 0 "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<DoctorTicket> getDidNotUsePatientTicket(Long patientId);

    /**
     * 根据医生ID和候诊排位信息获取排队中患者的挂号信息
     *
     * @param doctorId 医生ID
     * @param rank     排队的名次
     * @return 患者的挂号信息
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND status = 4 AND ticket_number =  #{rank,jdbcType=INTEGER} "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_DOCTOR_ALL)),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_PATIENT)),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    TicketVo getQueuePatientByDoctorIdAndRank(Long doctorId, Integer rank);

    /**
     * 根据医生ID获取呼叫中患者的票务信息
     *
     * @param doctorId 医生ID
     * @return 呼叫中患者的票务信息
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND status = 6 ORDER BY active_time LIMIT 1 "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_DOCTOR_ALL)),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_PATIENT)),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    TicketVo getCallingPatient(Long doctorId);

    /**
     * 根据票务资源计算某种时间端挂号剩余的候诊数
     *
     * @param ticketResourceId 票务资源ID
     * @return 某患者的挂号信息列表
     */
    @Select({
            "select",
            "count(ticket_id) as queue_number",
            "from entity_doctor_ticket",
            "where registered_resource_id = #{ticketResourceId,jdbcType=BIGINT} AND status = 4 "
    })
    @Results({
            @Result(column = "queue_number", property = "rank", jdbcType = JdbcType.INTEGER)
    })
    TicketVo countTicketQueue(Long ticketResourceId);

    /**
     * 根据医生ID和状态类型
     * 计算某种挂号状态的病人总数
     *
     * @param doctorId      医生ID
     * @param patientStatus 状态类型 同挂号信息的status状态
     * @return 某患者的挂号信息列表
     */
    @Select({
            "select",
            "count(ticket_id) as queue_number",
            "from entity_doctor_ticket",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND status = #{patientStatus,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "queue_number", property = "rank", jdbcType = JdbcType.INTEGER)
    })
    TicketVo countTicketTotalRank(Long doctorId, Integer patientStatus);

    /**
     * 根据医生ID获取最后一位就诊中的票务信息
     *
     * @param doctorId      医生ID
     * @param patientStatus 状态类型 同挂号信息的status状态
     * @return 挂号信息
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND status = #{patientStatus,jdbcType=INTEGER} ORDER BY active_time DESC LIMIT 1 "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_DOCTOR_ALL)),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_PATIENT)),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    TicketVo countLastTicketSeeing(Long doctorId, Integer patientStatus);

    /**
     * 根据医生ID获取
     * 某种挂号状态的最早排队的患者信息
     *
     * @param doctorId      医生ID
     * @param patientStatus 状态类型 同挂号信息的status状态
     * @return 挂号信息
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND status = #{patientStatus,jdbcType=INTEGER} ORDER BY active_time LIMIT 1 "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_DOCTOR_ALL)),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_PATIENT)),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    TicketVo getPatientByShouldBeCalled(Long doctorId, Integer patientStatus);

    /**
     * 根据医生ID计算下一位候诊患者的名次
     *
     * @param doctorId 医生ID
     * @return 某患者的挂号信息列表
     */
    @Select({
            "select",
            "ticket_id,ticket_number,doctor_id,status",
            "from entity_doctor_ticket",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND status = 4 ORDER BY active_time LIMIT 1 "
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    TicketVo getNextUsingTicketRank(Long doctorId);

    /**
     * 根据挂号信息ID关联查询挂号信息
     *
     * @param ticketId 挂号信息ID
     * @return 关联查询的挂号信息
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where ticket_id = #{ticketId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "doctor_id", property = "doctorVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_DOCTOR_ALL)),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_PATIENT))
    })
    TicketVo selectByPrimaryKeyRelative(Long ticketId);

    /**
     * 根据订单ID获取票务信息
     *
     * @param orderId 订单ID
     * @return 票务信息
     */
    @Select({
            "select",
            "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, active_time,",
            "ticket_validity_start, ticket_validity_end, doctor_id, patient_id, order_id,registered_resource_id, ",
            "`status`",
            "from entity_doctor_ticket",
            "where order_id = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ticket_number", property = "ticketNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_type", property = "ticketType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_time_type", property = "ticketTimeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_create_time", property = "ticketCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "active_time", property = "activeTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_start", property = "ticketValidityStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ticket_validity_end", property = "ticketValidityEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    DoctorTicket selectByOrderId(Long orderId);

}