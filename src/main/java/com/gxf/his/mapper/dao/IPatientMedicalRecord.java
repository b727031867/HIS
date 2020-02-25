package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.PatientMedicalRecordMapper;
import com.gxf.his.po.generate.PatientMedicalRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/21 20:40
 * 电子病历接口
 */
public interface IPatientMedicalRecord extends PatientMedicalRecordMapper {

    /**
     * 插入一条病历信息并且注入ID
     * @param record 病历信息
     * @return 本次操作影响的行数
     */
    @Insert({
            "insert into entity_patient_medical_record (medical_record_id, narrator, ",
            "chief_complaint, past_history, ",
            "current_medical_history, auxiliary_inspection, ",
            "tgjc, diagnosis, ",
            "zlyj, doctor_id, patient_id, ",
            "create_datetime)",
            "values (#{medicalRecordId,jdbcType=BIGINT}, #{narrator,jdbcType=VARCHAR}, ",
            "#{chiefComplaint,jdbcType=VARCHAR}, #{pastHistory,jdbcType=VARCHAR}, ",
            "#{currentMedicalHistory,jdbcType=VARCHAR}, #{auxiliaryInspection,jdbcType=VARCHAR}, ",
            "#{tgjc,jdbcType=VARCHAR}, #{diagnosis,jdbcType=VARCHAR}, ",
            "#{zlyj,jdbcType=VARCHAR}, #{doctorId,jdbcType=BIGINT}, #{patientId,jdbcType=BIGINT}, ",
            "#{createDatetime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "medicalRecordId", keyColumn = "medical_record_id")
    int insertAndInjectPrimaryKey(PatientMedicalRecord record);

    /**
     * 根据医生Id和患者Id查询某段时间内的电子病历信息
     *
     * @param doctorId  医生ID
     * @param patientId 患者ID
     * @param start     开始时间
     * @param end       结束时间
     * @return 电子病历列表
     */
    @Select({
            "select",
            "*",
            "from entity_patient_medical_record",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND patient_id = #{patientId,jdbcType=BIGINT} AND  ",
            "create_datetime < #{end,jdbcType=TIMESTAMP} AND create_datetime > #{start,jdbcType=TIMESTAMP} ORDER BY create_datetime "
    })
    @Results({
            @Result(column = "medical_record_id", property = "medicalRecordId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "narrator", property = "narrator", jdbcType = JdbcType.VARCHAR),
            @Result(column = "chief_complaint", property = "chiefComplaint", jdbcType = JdbcType.VARCHAR),
            @Result(column = "past_history", property = "pastHistory", jdbcType = JdbcType.VARCHAR),
            @Result(column = "current_medical_history", property = "currentMedicalHistory", jdbcType = JdbcType.VARCHAR),
            @Result(column = "auxiliary_inspection", property = "auxiliaryInspection", jdbcType = JdbcType.VARCHAR),
            @Result(column = "diagnosis", property = "diagnosis", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_datetime", property = "createDatetime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<PatientMedicalRecord> selectPatientMedicalRecordByDoctorIdAndPatientIdAndRange(Long doctorId, Long patientId, Date start, Date end);
}
