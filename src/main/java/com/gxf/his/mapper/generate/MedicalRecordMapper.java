package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.MedicalRecord;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface MedicalRecordMapper {
    @Delete({
        "delete from entity_medical_record",
        "where medical_record_id = #{medicalRecordId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long medicalRecordId);

    @Insert({
        "insert into entity_medical_record (medical_record_id, narrator, ",
        "chief_complaint, past_history, ",
        "current_medical_history, auxiliary_inspection, ",
        "diagnosis, doctor_id, ",
        "patient_id, create_datetime)",
        "values (#{medicalRecordId,jdbcType=BIGINT}, #{narrator,jdbcType=VARCHAR}, ",
        "#{chiefComplaint,jdbcType=VARCHAR}, #{pastHistory,jdbcType=VARCHAR}, ",
        "#{currentMedicalHistory,jdbcType=VARCHAR}, #{auxiliaryInspection,jdbcType=VARCHAR}, ",
        "#{diagnosis,jdbcType=VARCHAR}, #{doctorId,jdbcType=BIGINT}, ",
        "#{patientId,jdbcType=BIGINT}, #{createDatetime,jdbcType=TIMESTAMP})"
    })
    int insert(MedicalRecord record);

    @Select({
        "select",
        "medical_record_id, narrator, chief_complaint, past_history, current_medical_history, ",
        "auxiliary_inspection, diagnosis, doctor_id, patient_id, create_datetime",
        "from entity_medical_record",
        "where medical_record_id = #{medicalRecordId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="medical_record_id", property="medicalRecordId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="narrator", property="narrator", jdbcType=JdbcType.VARCHAR),
        @Result(column="chief_complaint", property="chiefComplaint", jdbcType=JdbcType.VARCHAR),
        @Result(column="past_history", property="pastHistory", jdbcType=JdbcType.VARCHAR),
        @Result(column="current_medical_history", property="currentMedicalHistory", jdbcType=JdbcType.VARCHAR),
        @Result(column="auxiliary_inspection", property="auxiliaryInspection", jdbcType=JdbcType.VARCHAR),
        @Result(column="diagnosis", property="diagnosis", jdbcType=JdbcType.VARCHAR),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP)
    })
    MedicalRecord selectByPrimaryKey(Long medicalRecordId);

    @Select({
        "select",
        "medical_record_id, narrator, chief_complaint, past_history, current_medical_history, ",
        "auxiliary_inspection, diagnosis, doctor_id, patient_id, create_datetime",
        "from entity_medical_record"
    })
    @Results({
        @Result(column="medical_record_id", property="medicalRecordId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="narrator", property="narrator", jdbcType=JdbcType.VARCHAR),
        @Result(column="chief_complaint", property="chiefComplaint", jdbcType=JdbcType.VARCHAR),
        @Result(column="past_history", property="pastHistory", jdbcType=JdbcType.VARCHAR),
        @Result(column="current_medical_history", property="currentMedicalHistory", jdbcType=JdbcType.VARCHAR),
        @Result(column="auxiliary_inspection", property="auxiliaryInspection", jdbcType=JdbcType.VARCHAR),
        @Result(column="diagnosis", property="diagnosis", jdbcType=JdbcType.VARCHAR),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<MedicalRecord> selectAll();

    @Update({
        "update entity_medical_record",
        "set narrator = #{narrator,jdbcType=VARCHAR},",
          "chief_complaint = #{chiefComplaint,jdbcType=VARCHAR},",
          "past_history = #{pastHistory,jdbcType=VARCHAR},",
          "current_medical_history = #{currentMedicalHistory,jdbcType=VARCHAR},",
          "auxiliary_inspection = #{auxiliaryInspection,jdbcType=VARCHAR},",
          "diagnosis = #{diagnosis,jdbcType=VARCHAR},",
          "doctor_id = #{doctorId,jdbcType=BIGINT},",
          "patient_id = #{patientId,jdbcType=BIGINT},",
          "create_datetime = #{createDatetime,jdbcType=TIMESTAMP}",
        "where medical_record_id = #{medicalRecordId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MedicalRecord record);
}