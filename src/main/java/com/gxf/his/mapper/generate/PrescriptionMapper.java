package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.Prescription;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PrescriptionMapper {
    @Delete({
        "delete from entity_prescription",
        "where prescription_id = #{prescriptionId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long prescriptionId);

    @Insert({
        "insert into entity_prescription (prescription_id, total_spend, ",
        "doctor_advice, patient_id, ",
        "ticket_id, doctor_id, ",
        "create_datetime)",
        "values (#{prescriptionId,jdbcType=BIGINT}, #{totalSpend,jdbcType=DECIMAL}, ",
        "#{doctorAdvice,jdbcType=VARCHAR}, #{patientId,jdbcType=BIGINT}, ",
        "#{ticketId,jdbcType=BIGINT}, #{doctorId,jdbcType=BIGINT}, ",
        "#{createDatetime,jdbcType=TIMESTAMP})"
    })
    int insert(Prescription record);

    @Select({
        "select",
        "prescription_id, total_spend, doctor_advice, patient_id, ticket_id, doctor_id, ",
        "create_datetime",
        "from entity_prescription",
        "where prescription_id = #{prescriptionId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="total_spend", property="totalSpend", jdbcType=JdbcType.DECIMAL),
        @Result(column="doctor_advice", property="doctorAdvice", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="ticket_id", property="ticketId", jdbcType=JdbcType.BIGINT),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP)
    })
    Prescription selectByPrimaryKey(Long prescriptionId);

    @Select({
        "select",
        "prescription_id, total_spend, doctor_advice, patient_id, ticket_id, doctor_id, ",
        "create_datetime",
        "from entity_prescription"
    })
    @Results({
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="total_spend", property="totalSpend", jdbcType=JdbcType.DECIMAL),
        @Result(column="doctor_advice", property="doctorAdvice", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="ticket_id", property="ticketId", jdbcType=JdbcType.BIGINT),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Prescription> selectAll();

    @Update({
        "update entity_prescription",
        "set total_spend = #{totalSpend,jdbcType=DECIMAL},",
          "doctor_advice = #{doctorAdvice,jdbcType=VARCHAR},",
          "patient_id = #{patientId,jdbcType=BIGINT},",
          "ticket_id = #{ticketId,jdbcType=BIGINT},",
          "doctor_id = #{doctorId,jdbcType=BIGINT},",
          "create_datetime = #{createDatetime,jdbcType=TIMESTAMP}",
        "where prescription_id = #{prescriptionId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Prescription record);
}