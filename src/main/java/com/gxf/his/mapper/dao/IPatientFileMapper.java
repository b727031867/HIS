package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.PatientFileMapper;
import com.gxf.his.po.generate.PatientFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/17 00:54
 */
public interface IPatientFileMapper extends PatientFileMapper {

    /**
     * 插入一条病历并且注入病历主键
     * @param record 病历
     * @return 本次操作影响的行数
     */
    @Insert({
            "insert into entity_patient_file (patient_file_id, patient_id, ",
            "emergency_contact_name, emergency_phone, ",
            "emergency_relation, right_ear_hearing, ",
            "left_ear_hearing, right_vision, ",
            "left_vision, height, ",
            "weight, blood_type, ",
            "personal_info, family_info, ",
            "create_time, update_time)",
            "values (#{patientFileId,jdbcType=BIGINT}, #{patientId,jdbcType=BIGINT}, ",
            "#{emergencyContactName,jdbcType=VARCHAR}, #{emergencyPhone,jdbcType=VARCHAR}, ",
            "#{emergencyRelation,jdbcType=VARCHAR}, #{rightEarHearing,jdbcType=INTEGER}, ",
            "#{leftEarHearing,jdbcType=INTEGER}, #{rightVision,jdbcType=DECIMAL}, ",
            "#{leftVision,jdbcType=DECIMAL}, #{height,jdbcType=DECIMAL}, ",
            "#{weight,jdbcType=DECIMAL}, #{bloodType,jdbcType=VARCHAR}, ",
            "#{personalInfo,jdbcType=VARCHAR}, #{familyInfo,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, NOW())"
    })
    @Options(useGeneratedKeys = true, keyProperty = "patientFileId", keyColumn = "patient_file_id")
    int insertAndInjectThePrimaryKey(PatientFile record);

    /**
     * 更新病历
     * @param record 病历
     * @return 本次操作影响的行数
     */
    @Update({
            "update entity_patient_file",
            "set patient_id = #{patientId,jdbcType=BIGINT},",
            "emergency_contact_name = #{emergencyContactName,jdbcType=VARCHAR},",
            "emergency_phone = #{emergencyPhone,jdbcType=VARCHAR},",
            "emergency_relation = #{emergencyRelation,jdbcType=VARCHAR},",
            "right_ear_hearing = #{rightEarHearing,jdbcType=INTEGER},",
            "left_ear_hearing = #{leftEarHearing,jdbcType=INTEGER},",
            "right_vision = #{rightVision,jdbcType=DECIMAL},",
            "left_vision = #{leftVision,jdbcType=DECIMAL},",
            "height = #{height,jdbcType=DECIMAL},",
            "weight = #{weight,jdbcType=DECIMAL},",
            "blood_type = #{bloodType,jdbcType=VARCHAR},",
            "personal_info = #{personalInfo,jdbcType=VARCHAR},",
            "family_info = #{familyInfo,jdbcType=VARCHAR},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_time = NOW()",
            "where patient_file_id = #{patientFileId,jdbcType=BIGINT}"
    })
    int update(PatientFile record);
}
