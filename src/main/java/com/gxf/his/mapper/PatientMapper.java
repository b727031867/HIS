package com.gxf.his.mapper;

import com.gxf.his.po.Patient;
import com.gxf.his.vo.PatientUserVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


public interface PatientMapper {
    @Delete({
        "delete from entity_patient",
        "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long patientId);

    /**
     * 批量删除患者
     * @param patients 患者列表
     * @return 影响的行数
     */
    @DeleteProvider(type = Batch.class, method = "batchPatientDelete")
    Integer batchPatientDelete(List<Patient> patients);

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

    @Select({"select", "patient_id, patient_age, patient_name, patient_is_marriage, patient_card, ",
            "patient_phone, patient_sex, patient_medicare_card, user_id", "from entity_patient"})
    @Results({
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "patient_age", property = "patientAge", jdbcType = JdbcType.INTEGER),
            @Result(column = "patient_name", property = "patientName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_is_marriage", property = "patientIsMarriage", jdbcType = JdbcType.TINYINT),
            @Result(column = "patient_card", property = "patientCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_phone", property = "patientPhone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_sex", property = "patientSex", jdbcType = JdbcType.TINYINT),
            @Result(column = "patient_medicare_card", property = "patientMedicareCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey"))
    })
    List<PatientUserVo> selectAll();

    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_patient "
            + "<where> "
            + "<if test='searchAttribute == \"patientName\" '>"
            + " patient_name like CONCAT('%',#{value},'%')  "
            + "</if> "
            + "<if test='searchAttribute == \"patientPhone\" '> "
            + " AND patient_phone like  CONCAT('%',#{value},'%') "
            + "</if>"
            + "<if test='searchAttribute == \"patientMedicareCard\" '> "
            + " AND patient_medicare_card like CONCAT('%',#{value},'%') "
            + "</if>"
            + "</where> "
            + "</script>")
    @Results({
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "patient_age", property = "patientAge", jdbcType = JdbcType.INTEGER),
            @Result(column = "patient_name", property = "patientName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_is_marriage", property = "patientIsMarriage", jdbcType = JdbcType.TINYINT),
            @Result(column = "patient_card", property = "patientCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_phone", property = "patientPhone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_sex", property = "patientSex", jdbcType = JdbcType.TINYINT),
            @Result(column = "patient_medicare_card", property = "patientMedicareCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey"))
    })
    List<PatientUserVo> selectPatientByAttribute(PatientUserVo patientUserVo);

    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_patient "
            + "<where> "
            + "<if test='searchAttribute == \"patientName\" '>"
            + " patient_name = #{value} "
            + "</if> "
            + "<if test='searchAttribute == \"patientPhone\" '> "
            + " AND patient_phone = #{value} "
            + "</if>"
            + "<if test='searchAttribute == \"patientMedicareCard\" '> "
            + " AND patient_medicare_card = #{value} "
            + "</if>"
            + "</where> "
            + "</script>")
    @Results({
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "patient_age", property = "patientAge", jdbcType = JdbcType.INTEGER),
            @Result(column = "patient_name", property = "patientName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_is_marriage", property = "patientIsMarriage", jdbcType = JdbcType.TINYINT),
            @Result(column = "patient_card", property = "patientCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_phone", property = "patientPhone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_sex", property = "patientSex", jdbcType = JdbcType.TINYINT),
            @Result(column = "patient_medicare_card", property = "patientMedicareCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey"))
    })
    List<PatientUserVo> selectPatientByAccurateAttribute(PatientUserVo patientUserVo);

    @Update({
        "update entity_patient",
        "set patient_name = #{patientName,jdbcType=VARCHAR},",
            "patient_card = #{patientCard,jdbcType=VARCHAR},",
            "patient_sex = #{patientSex,jdbcType=TINYINT},",
            "patient_medicare_card = #{patientMedicareCard,jdbcType=VARCHAR},",
            "user_id = #{userId,jdbcType=BIGINT},",
            "patient_is_marriage = #{patientIsMarriage,jdbcType=TINYINT},",
            "patient_age = #{patientAge,jdbcType=INTEGER},",
            "patient_phone = #{patientPhone,jdbcType=VARCHAR} ",
        "where patient_id = #{patientId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Patient record);
}