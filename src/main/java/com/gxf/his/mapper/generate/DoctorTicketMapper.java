package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.DoctorTicket;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DoctorTicketMapper {
    @Delete({
        "delete from entity_doctor_ticket",
        "where ticket_id = #{ticketId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long ticketId);

    @Insert({
        "insert into entity_doctor_ticket (ticket_id, ticket_number, ",
        "ticket_type, ticket_time_type, ",
        "ticket_create_time, active_time, ",
        "ticket_validity_start, ticket_validity_end, ",
        "doctor_id, patient_id, ",
        "registered_resource_id, order_id, ",
        "`status`)",
        "values (#{ticketId,jdbcType=BIGINT}, #{ticketNumber,jdbcType=INTEGER}, ",
        "#{ticketType,jdbcType=VARCHAR}, #{ticketTimeType,jdbcType=VARCHAR}, ",
        "#{ticketCreateTime,jdbcType=TIMESTAMP}, #{activeTime,jdbcType=TIMESTAMP}, ",
        "#{ticketValidityStart,jdbcType=TIMESTAMP}, #{ticketValidityEnd,jdbcType=TIMESTAMP}, ",
        "#{doctorId,jdbcType=BIGINT}, #{patientId,jdbcType=BIGINT}, ",
        "#{registeredResourceId,jdbcType=BIGINT}, #{orderId,jdbcType=BIGINT}, ",
        "#{status,jdbcType=INTEGER})"
    })
    int insert(DoctorTicket record);

    @Select({
        "select",
        "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, ",
        "active_time, ticket_validity_start, ticket_validity_end, doctor_id, patient_id, ",
        "registered_resource_id, order_id, `status`",
        "from entity_doctor_ticket",
        "where ticket_id = #{ticketId,jdbcType=BIGINT}"
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
        @Result(column="registered_resource_id", property="registeredResourceId", jdbcType=JdbcType.BIGINT),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    DoctorTicket selectByPrimaryKey(Long ticketId);

    @Select({
        "select",
        "ticket_id, ticket_number, ticket_type, ticket_time_type, ticket_create_time, ",
        "active_time, ticket_validity_start, ticket_validity_end, doctor_id, patient_id, ",
        "registered_resource_id, order_id, `status`",
        "from entity_doctor_ticket"
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
        @Result(column="registered_resource_id", property="registeredResourceId", jdbcType=JdbcType.BIGINT),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    List<DoctorTicket> selectAll();

    @Update({
        "update entity_doctor_ticket",
        "set ticket_number = #{ticketNumber,jdbcType=INTEGER},",
          "ticket_type = #{ticketType,jdbcType=VARCHAR},",
          "ticket_time_type = #{ticketTimeType,jdbcType=VARCHAR},",
          "ticket_create_time = #{ticketCreateTime,jdbcType=TIMESTAMP},",
          "active_time = #{activeTime,jdbcType=TIMESTAMP},",
          "ticket_validity_start = #{ticketValidityStart,jdbcType=TIMESTAMP},",
          "ticket_validity_end = #{ticketValidityEnd,jdbcType=TIMESTAMP},",
          "doctor_id = #{doctorId,jdbcType=BIGINT},",
          "patient_id = #{patientId,jdbcType=BIGINT},",
          "registered_resource_id = #{registeredResourceId,jdbcType=BIGINT},",
          "order_id = #{orderId,jdbcType=BIGINT},",
          "`status` = #{status,jdbcType=INTEGER}",
        "where ticket_id = #{ticketId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DoctorTicket record);
}