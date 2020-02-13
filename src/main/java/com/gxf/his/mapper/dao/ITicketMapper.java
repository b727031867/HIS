package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.DoctorTicketMapper;
import com.gxf.his.po.generate.DoctorTicket;
import com.gxf.his.po.vo.TicketVo;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

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
     * 根据患者ID获取患者
     * 暂未排队或正在排队的挂号信息列表
     *
     * @param patientId 患者ID
     * @return 某患者的挂号信息列表
     */
    @Select({
            "select",
            "*",
            "from entity_doctor_ticket",
            "where patient_id = #{patientId,jdbcType=BIGINT} AND (status = 0 OR status = 4) order by ticket_create_time DESC "
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
            @Result(column = "doctor_id", property = "doctorVo", jdbcType = JdbcType.BIGINT,one = @One(select = MapperConst.ONE_TICKET_DOCTOR_ALL)),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT,one = @One(select = MapperConst.ONE_TICKET_PATIENT)),
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
     * 根据票务资源计算挂号排队的名次
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
            @Result(column = "rank", property = "rank", jdbcType = JdbcType.INTEGER)
    })
    TicketVo countTicketQueue(Long ticketResourceId);

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
     * 根据订单ID获取票务信息
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
            @Result(column="ticket_id", property="ticketId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="ticket_number", property="ticketNumber", jdbcType=JdbcType.INTEGER),
            @Result(column="ticket_type", property="ticketType", jdbcType=JdbcType.VARCHAR),
            @Result(column="ticket_time_type", property="ticketTimeType", jdbcType=JdbcType.VARCHAR),
            @Result(column="ticket_create_time", property="ticketCreateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="active_time", property="activeTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="ticket_validity_start", property="ticketValidityStart", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="ticket_validity_end", property="ticketValidityEnd", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
            @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
            @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    DoctorTicket selectByOrderId(Long orderId);

}