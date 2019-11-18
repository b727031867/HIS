package com.gxf.his.mapper;

import com.gxf.his.po.Ticket;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TicketMapper {
    @Delete({
        "delete from entity_ticket",
        "where ticket_id = #{ticketId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long ticketId);

    @Insert({
        "insert into entity_ticket (ticket_id, ticket_number, ",
        "ticket_type, ticket_time_type, ",
        "ticket_create_time, ticket_validity_start, ",
        "ticket_validity_end, doctor_id, ",
        "patient_id)",
        "values (#{ticketId,jdbcType=BIGINT}, #{ticketNumber,jdbcType=INTEGER}, ",
        "#{ticketType,jdbcType=VARCHAR}, #{ticketTimeType,jdbcType=VARCHAR}, ",
        "#{ticketCreateTime,jdbcType=TIMESTAMP}, #{ticketValidityStart,jdbcType=TIMESTAMP}, ",
        "#{ticketValidityEnd,jdbcType=TIMESTAMP}, #{doctorId,jdbcType=BIGINT}, ",
        "#{patientId,jdbcType=BIGINT})"
    })
    int insert(Ticket record);

    @Select({
        "select",
        "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, ",
        "ticket_validity_start, ticket_validity_end, doctor_id, patient_id",
        "from entity_ticket",
        "where ticket_id = #{ticketId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="ticket_id", property="ticketId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ticket_number", property="ticketNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="ticket_type", property="ticketType", jdbcType=JdbcType.VARCHAR),
        @Result(column="ticket_time_type", property="ticketTimeType", jdbcType=JdbcType.VARCHAR),
        @Result(column="ticket_create_time", property="ticketCreateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ticket_validity_start", property="ticketValidityStart", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ticket_validity_end", property="ticketValidityEnd", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT)
    })
    Ticket selectByPrimaryKey(Long ticketId);

    @Select({
        "select",
        "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, ",
        "ticket_validity_start, ticket_validity_end, doctor_id, patient_id",
        "from entity_ticket"
    })
    @Results({
        @Result(column="ticket_id", property="ticketId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ticket_number", property="ticketNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="ticket_type", property="ticketType", jdbcType=JdbcType.VARCHAR),
        @Result(column="ticket_time_type", property="ticketTimeType", jdbcType=JdbcType.VARCHAR),
        @Result(column="ticket_create_time", property="ticketCreateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ticket_validity_start", property="ticketValidityStart", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ticket_validity_end", property="ticketValidityEnd", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT)
    })
    List<Ticket> selectAll();

    @Update({
        "update entity_ticket",
        "set ticket_number = #{ticketNumber,jdbcType=INTEGER},",
          "ticket_type = #{ticketType,jdbcType=VARCHAR},",
          "ticket_time_type = #{ticketTimeType,jdbcType=VARCHAR},",
          "ticket_create_time = #{ticketCreateTime,jdbcType=TIMESTAMP},",
          "ticket_validity_start = #{ticketValidityStart,jdbcType=TIMESTAMP},",
          "ticket_validity_end = #{ticketValidityEnd,jdbcType=TIMESTAMP},",
          "doctor_id = #{doctorId,jdbcType=BIGINT},",
          "patient_id = #{patientId,jdbcType=BIGINT}",
        "where ticket_id = #{ticketId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Ticket record);
}