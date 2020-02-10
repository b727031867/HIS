package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.Patient;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PatientMapper {
    @Delete({
        "delete from entity_patient",
        "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long patientId);

    @Insert({
        "insert into entity_patient (patient_id, patient_age, ",
        "patient_name, patient_is_marriage, ",
        "patient_card, patient_phone, ",
        "patient_sex, patient_medicare_card, ",
        "user_id)",
        "values (#{patientId,jdbcType=BIGINT}, #{patientAge,jdbcType=INTEGER}, ",
        "#{patientName,jdbcType=VARCHAR}, #{patientIsMarriage,jdbcType=TINYINT}, ",
        "#{patientCard,jdbcType=VARCHAR}, #{patientPhone,jdbcType=VARCHAR}, ",
        "#{patientSex,jdbcType=TINYINT}, #{patientMedicareCard,jdbcType=VARCHAR}, ",
        "#{userId,jdbcType=BIGINT})"
    })
    int insert(Patient record);

    @Select({
        "select",
        "patient_id, patient_age, patient_name, patient_is_marriage, patient_card, patient_phone, ",
        "patient_sex, patient_medicare_card, user_id",
        "from entity_patient",
        "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="patient_age", property="patientAge", jdbcType=JdbcType.INTEGER),
        @Result(column="patient_name", property="patientName", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_is_marriage", property="patientIsMarriage", jdbcType=JdbcType.TINYINT),
        @Result(column="patient_card", property="patientCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_phone", property="patientPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_sex", property="patientSex", jdbcType=JdbcType.TINYINT),
        @Result(column="patient_medicare_card", property="patientMedicareCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT)
    })
    Patient selectByPrimaryKey(Long patientId);

    @Select({
        "select",
        "patient_id, patient_age, patient_name, patient_is_marriage, patient_card, patient_phone, ",
        "patient_sex, patient_medicare_card, user_id",
        "from entity_patient"
    })
    @Results({
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="patient_age", property="patientAge", jdbcType=JdbcType.INTEGER),
        @Result(column="patient_name", property="patientName", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_is_marriage", property="patientIsMarriage", jdbcType=JdbcType.TINYINT),
        @Result(column="patient_card", property="patientCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_phone", property="patientPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_sex", property="patientSex", jdbcType=JdbcType.TINYINT),
        @Result(column="patient_medicare_card", property="patientMedicareCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT)
    })
    List<Patient> selectAll();

    @Update({
        "update entity_patient",
        "set patient_age = #{patientAge,jdbcType=INTEGER},",
          "patient_name = #{patientName,jdbcType=VARCHAR},",
          "patient_is_marriage = #{patientIsMarriage,jdbcType=TINYINT},",
          "patient_card = #{patientCard,jdbcType=VARCHAR},",
          "patient_phone = #{patientPhone,jdbcType=VARCHAR},",
          "patient_sex = #{patientSex,jdbcType=TINYINT},",
          "patient_medicare_card = #{patientMedicareCard,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=BIGINT}",
        "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Patient record);
}