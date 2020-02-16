package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.Batch;
import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.PatientMapper;
import com.gxf.his.po.vo.PatientVo;
import com.gxf.his.po.generate.Patient;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 患者模块的DAO接口
 */
public interface IPatientMapper extends PatientMapper {

    /**
     * 批量删除患者
     *
     * @param patients 患者列表
     * @return 影响的行数
     */
    @DeleteProvider(type = Batch.class, method = "batchPatientDelete")
    Integer batchPatientDelete(List<Patient> patients);

    /**
     * 获取所有患者的所有信息
     *
     * @return 患者业务类列表
     */
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
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER))
    })
    List<PatientVo> selectAllPatientsInfo();

    /**
     * 根据不同的属性模糊查找患者
     *
     * @param patientVo 患者业务类
     * @return 患者业务类列表
     */
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
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER))
    })
    List<PatientVo> selectPatientByAttribute(PatientVo patientVo);

    /**
     * 根据UID获取患者信息
     * @param uid 用户ID
     * @return 患者对象
     */
    @Select({
            "select",
            "patient_id, patient_age, patient_name, patient_is_marriage, patient_card, patient_phone, ",
            "patient_sex, patient_medicare_card, user_id",
            "from entity_patient",
            "where user_id = #{uid,jdbcType=BIGINT}"
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
    Patient selectByUid(Long uid);

    /**
     * 根据UID获取患者信息
     * 关联查询病历
     * @param uid 患者用户ID
     * @return 患者业务类
     */
    @Select({
            "select",
            "patient_id, patient_age, patient_name, patient_is_marriage, patient_card, patient_phone, ",
            "patient_sex, patient_medicare_card, user_id",
            "from entity_patient",
            "where user_id = #{uid,jdbcType=BIGINT}"
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
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
            @Result(column="patient_id", property="patientFile", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_PATIENT_FILE))
    })
    PatientVo selectByUidRelated(Long uid);

    /**
     * 根据不同的属性精确查找患者
     *
     * @param patientVo 患者业务类
     * @return 患者业务类列表
     */
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
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER))
    })
    List<PatientVo> selectPatientByAccurateAttribute(PatientVo patientVo);

}