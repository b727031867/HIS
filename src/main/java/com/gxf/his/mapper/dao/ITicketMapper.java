package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.TicketMapper;
import com.gxf.his.po.generate.Ticket;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 挂号模块的DAO接口
 */
public interface ITicketMapper extends TicketMapper {

    /**
     * 根据患者ID获取患者的挂号信息列表
     * @param patientId 患者ID
     * @return 某患者的挂号信息列表
     */
    @Select({
            "select",
            "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, ",
            "ticket_validity_start, ticket_validity_end, doctor_id, patient_id, order_id,status",
            "from entity_ticket",
            "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="ticket_id", property="ticketId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="ticket_number", property="ticketNumber", jdbcType=JdbcType.INTEGER),
            @Result(column="ticket_type", property="ticketType", jdbcType=JdbcType.VARCHAR),
            @Result(column="ticket_time_type", property="ticketTimeType", jdbcType=JdbcType.VARCHAR),
            @Result(column="ticket_create_time", property="ticketCreateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="ticket_validity_start", property="ticketValidityStart", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="ticket_validity_end", property="ticketValidityEnd", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
            @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
            @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    List<Ticket> getTicketByPatientId(Long patientId);

    /**
     * 根据患者ID获取患者未就诊的挂号信息
     * @param patientId 患者ID
     * @return 某患者未就诊的挂号信息列表
     */
    @Select({
            "select",
            "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, ",
            "ticket_validity_start, ticket_validity_end, doctor_id, patient_id, order_id,status",
            "from entity_ticket",
            "where patient_id = #{patientId,jdbcType=BIGINT} AND status = 0 "
    })
    @Results({
            @Result(column="ticket_id", property="ticketId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="ticket_number", property="ticketNumber", jdbcType=JdbcType.INTEGER),
            @Result(column="ticket_type", property="ticketType", jdbcType=JdbcType.VARCHAR),
            @Result(column="ticket_time_type", property="ticketTimeType", jdbcType=JdbcType.VARCHAR),
            @Result(column="ticket_create_time", property="ticketCreateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="ticket_validity_start", property="ticketValidityStart", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="ticket_validity_end", property="ticketValidityEnd", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
            @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
            @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    List<Ticket> getDidNotUsePatientTicket(Long patientId);

}