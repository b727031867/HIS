package com.gxf.his.mapper;

import com.gxf.his.po.Patient;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PatientMapper {
    @Delete({
        "delete from entity_patient",
        "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long patientId);

    @Insert({
        "insert into entity_patient " +
        "(patient_id, patient_name, patient_card, patient_sex, patient_medicare_card, " +
        "user_id,patient_age, patient_is_marriage,patient_phone) " +
        "values (#{patientId,jdbcType=BIGINT}, #{patientName,jdbcType=VARCHAR}, " +
        "#{patientCard,jdbcType=VARCHAR}, #{patientSex,jdbcType=TINYINT}, " +
        "#{patientMedicareCard,jdbcType=VARCHAR}, #{userId,jdbcType=BIGINT}, #{patientAge,jdbcType=INTEGER}," +
        "#{patientIsMarriage,jdbcType=TINYINT},#{patientPhone,jdbcType=VARCHAR})"
    })
    int insert(Patient record);

    @Select({
        "select * from entity_patient",
        "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="patient_name", property="patientName", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_card", property="patientCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_sex", property="patientSex", jdbcType=JdbcType.TINYINT),
        @Result(column="patient_medicare_card", property="patientMedicareCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_age", property="patientAge", jdbcType=JdbcType.INTEGER),
        @Result(column="patient_is_marriage", property="patientIsMarriage", jdbcType=JdbcType.TINYINT),
        @Result(column="patient_phone", property="patientPhone", jdbcType=JdbcType.VARCHAR)
    })
    Patient selectByPrimaryKey(Long patientId);

    @Select({
        "select * from entity_patient"
    })
    @Results({
            @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="patient_name", property="patientName", jdbcType=JdbcType.VARCHAR),
            @Result(column="patient_card", property="patientCard", jdbcType=JdbcType.VARCHAR),
            @Result(column="patient_sex", property="patientSex", jdbcType=JdbcType.TINYINT),
            @Result(column="patient_medicare_card", property="patientMedicareCard", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
            @Result(column="patient_age", property="patientAge", jdbcType=JdbcType.INTEGER),
            @Result(column="patient_is_marriage", property="patientIsMarriage", jdbcType=JdbcType.TINYINT),
            @Result(column="patient_phone", property="patientPhone", jdbcType=JdbcType.VARCHAR)
    })
    List<Patient> selectAll();

    @Update({
        "update entity_patient",
        "set patient_name = #{patientName,jdbcType=VARCHAR},",
            "patient_card = #{patientCard,jdbcType=VARCHAR},",
            "patient_sex = #{patientSex,jdbcType=TINYINT},",
            "patient_medicare_card = #{patientMedicareCard,jdbcType=VARCHAR},",
            "user_id = #{userId,jdbcType=BIGINT}",
            "patient_is_marriage = #{patientIsMarriage,jdbcType=TINYINT}",
            "patient_age = #{patientAge,jdbcType=INTEGER}",
            "patient_phone = #{patientPhone,jdbcType=VARCHAR}",
        "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Patient record);
}