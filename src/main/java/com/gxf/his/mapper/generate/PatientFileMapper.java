package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.PatientFile;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PatientFileMapper {
    @Delete({
        "delete from entity_patient_file",
        "where patient_file_id = #{patientFileId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long patientFileId);

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
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(PatientFile record);

    @Select({
        "select",
        "patient_file_id, patient_id, emergency_contact_name, emergency_phone, emergency_relation, ",
        "right_ear_hearing, left_ear_hearing, right_vision, left_vision, height, weight, ",
        "blood_type, personal_info, family_info, create_time, update_time",
        "from entity_patient_file",
        "where patient_file_id = #{patientFileId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="patient_file_id", property="patientFileId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_phone", property="emergencyPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_relation", property="emergencyRelation", jdbcType=JdbcType.VARCHAR),
        @Result(column="right_ear_hearing", property="rightEarHearing", jdbcType=JdbcType.INTEGER),
        @Result(column="left_ear_hearing", property="leftEarHearing", jdbcType=JdbcType.INTEGER),
        @Result(column="right_vision", property="rightVision", jdbcType=JdbcType.DECIMAL),
        @Result(column="left_vision", property="leftVision", jdbcType=JdbcType.DECIMAL),
        @Result(column="height", property="height", jdbcType=JdbcType.DECIMAL),
        @Result(column="weight", property="weight", jdbcType=JdbcType.DECIMAL),
        @Result(column="blood_type", property="bloodType", jdbcType=JdbcType.VARCHAR),
        @Result(column="personal_info", property="personalInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="family_info", property="familyInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    PatientFile selectByPrimaryKey(Long patientFileId);

    @Select({
        "select",
        "patient_file_id, patient_id, emergency_contact_name, emergency_phone, emergency_relation, ",
        "right_ear_hearing, left_ear_hearing, right_vision, left_vision, height, weight, ",
        "blood_type, personal_info, family_info, create_time, update_time",
        "from entity_patient_file"
    })
    @Results({
        @Result(column="patient_file_id", property="patientFileId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_phone", property="emergencyPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_relation", property="emergencyRelation", jdbcType=JdbcType.VARCHAR),
        @Result(column="right_ear_hearing", property="rightEarHearing", jdbcType=JdbcType.INTEGER),
        @Result(column="left_ear_hearing", property="leftEarHearing", jdbcType=JdbcType.INTEGER),
        @Result(column="right_vision", property="rightVision", jdbcType=JdbcType.DECIMAL),
        @Result(column="left_vision", property="leftVision", jdbcType=JdbcType.DECIMAL),
        @Result(column="height", property="height", jdbcType=JdbcType.DECIMAL),
        @Result(column="weight", property="weight", jdbcType=JdbcType.DECIMAL),
        @Result(column="blood_type", property="bloodType", jdbcType=JdbcType.VARCHAR),
        @Result(column="personal_info", property="personalInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="family_info", property="familyInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PatientFile> selectAll();

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
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where patient_file_id = #{patientFileId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PatientFile record);
}