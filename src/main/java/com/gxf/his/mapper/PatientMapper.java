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
        "where patient_id = #{patientId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer patientId);

    @Insert({
        "insert into entity_patient (patient_id, patient_name, ",
        "patient_card, patient_sex, ",
        "patient_medicare_card, user_id)",
        "values (#{patientId,jdbcType=INTEGER}, #{patientName,jdbcType=VARCHAR}, ",
        "#{patientCard,jdbcType=VARCHAR}, #{patientSex,jdbcType=TINYINT}, ",
        "#{patientMedicareCard,jdbcType=VARCHAR}, #{userId,jdbcType=INTEGER})"
    })
    int insert(Patient record);

    @Select({
        "select",
        "patient_id, patient_name, patient_card, patient_sex, patient_medicare_card, ",
        "user_id",
        "from entity_patient",
        "where patient_id = #{patientId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="patient_name", property="patientName", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_card", property="patientCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_sex", property="patientSex", jdbcType=JdbcType.TINYINT),
        @Result(column="patient_medicare_card", property="patientMedicareCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER)
    })
    Patient selectByPrimaryKey(Integer patientId);

    @Select({
        "select",
        "patient_id, patient_name, patient_card, patient_sex, patient_medicare_card, ",
        "user_id",
        "from entity_patient"
    })
    @Results({
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="patient_name", property="patientName", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_card", property="patientCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="patient_sex", property="patientSex", jdbcType=JdbcType.TINYINT),
        @Result(column="patient_medicare_card", property="patientMedicareCard", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER)
    })
    List<Patient> selectAll();

    @Update({
        "update entity_patient",
        "set patient_name = #{patientName,jdbcType=VARCHAR},",
          "patient_card = #{patientCard,jdbcType=VARCHAR},",
          "patient_sex = #{patientSex,jdbcType=TINYINT},",
          "patient_medicare_card = #{patientMedicareCard,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=INTEGER}",
        "where patient_id = #{patientId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Patient record);
}