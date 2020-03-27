package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.Batch;
import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.DrugMapper;
import com.gxf.his.po.generate.Doctor;
import com.gxf.his.po.vo.DrugVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 药品模块的DAO接口
 */
public interface IDrugMapper extends DrugMapper {
    /**
     * 根据药品名称模糊查询药品列表
     *
     * @param drugName 药品名称
     * @return 药品列表
     */
    @Select({
            "select",
            "*",
            "from entity_drug ",
            "where drug_name like CONCAT('%',#{drugName,jdbcType=VARCHAR},'%') "
    })
    @Results({
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_alias", property = "drugAlias", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type_name", property = "typeName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_name", property = "drugName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_form", property = "drugForm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "toxicology_type", property = "toxicologyType", jdbcType = JdbcType.BIGINT),
            @Result(column = "is_in_bulk", property = "isInBulk", jdbcType = JdbcType.INTEGER),
            @Result(column = "drug_id", property = "drugStore", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_DRUG_STORE))
    })
    List<DrugVo> selectDrugByDrugName(String drugName);



    /**
     * 查询所有药品
     *
     * @return 药品列表
     */
    @Select({
            "select",
            "*",
            "from entity_drug "
    })
    @Results({
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_alias", property = "drugAlias", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type_name", property = "typeName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_name", property = "drugName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_form", property = "drugForm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "toxicology_type", property = "toxicologyType", jdbcType = JdbcType.BIGINT),
            @Result(column = "toxicology_type", property = "drugToxicology", jdbcType = JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_DRUG_TOXICOLOGY)),
            @Result(column = "is_in_bulk", property = "isInBulk", jdbcType = JdbcType.INTEGER),
            @Result(column = "drug_id", property = "drugStore", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_DRUG_STORE))
    })
    List<DrugVo> selectAllDrug();

    /**
     * 根据药品别名模糊查询药品列表
     *
     * @param drugAlias 药品别名
     * @return 药品列表
     */
    @Select({
            "select",
            "*",
            "from entity_drug ",
            "where drug_alias like CONCAT('%',#{drugAlias,jdbcType=VARCHAR},'%') "
    })
    @Results({
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_alias", property = "drugAlias", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type_name", property = "typeName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_name", property = "drugName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_form", property = "drugForm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "toxicology_type", property = "toxicologyType", jdbcType = JdbcType.BIGINT),
            @Result(column = "is_in_bulk", property = "isInBulk", jdbcType = JdbcType.INTEGER),
            @Result(column = "drug_id", property = "drugStore", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_DRUG_STORE))
    })
    List<DrugVo> selectDrugByDrugAlias(String drugAlias);

    /**
     * 根据药品ID关联查询药品信息
     *
     * @param drugId 药品ID
     * @return 药品关联信息
     */
    @Select({
            "select",
            "*",
            "from entity_drug ",
            "where drug_id = #{drugId,jdbcType=VARCHAR} "
    })
    @Results({
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_alias", property = "drugAlias", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type_name", property = "typeName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_name", property = "drugName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_form", property = "drugForm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "toxicology_type", property = "toxicologyType", jdbcType = JdbcType.BIGINT),
            @Result(column = "is_in_bulk", property = "isInBulk", jdbcType = JdbcType.INTEGER),
            @Result(column = "drug_id", property = "drugStore", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_DRUG_STORE))
    })
    DrugVo selectDrugByDrugId(Long drugId);

    /**
     * 根据药品ID关联查询药品信息
     * @param drugId 药品ID
     * @return 药品信息
     */
    @Select({
            "select",
            "*",
            "from entity_drug",
            "where drug_id = #{drugId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_alias", property="drugAlias", jdbcType=JdbcType.VARCHAR),
            @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_name", property="drugName", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_form", property="drugForm", jdbcType=JdbcType.VARCHAR),
            @Result(column="toxicology_type", property="drugToxicology", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_DRUG_TOXICOLOGY)),
            @Result(column="is_in_bulk", property="isInBulk", jdbcType=JdbcType.INTEGER),
            @Result(column="drug_id", property="drugStore", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_DRUG_STORE)),
    })
    List<DrugVo> selectByPrimaryKeyRelated(Long drugId);

    /**
     * 根据属性模糊查询药品
     * @param attribute 字段名
     * @param value 值
     * @return 药品列表
     */
    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_drug "
            + "<where> "
            + "<if test='attribute == \"drugName\" '>"
            + " drug_name like CONCAT('%',#{value},'%')  "
            + "</if> "
            + "<if test='attribute == \"drugAlias\" '> "
            + " AND drug_alias like  CONCAT('%',#{value},'%') "
            + "</if>"
            + "</where> "
            + "</script>")
    @Results({
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_alias", property="drugAlias", jdbcType=JdbcType.VARCHAR),
            @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_name", property="drugName", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_form", property="drugForm", jdbcType=JdbcType.VARCHAR),
            @Result(column="toxicology_type", property="drugToxicology", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_DRUG_TOXICOLOGY)),
            @Result(column="is_in_bulk", property="isInBulk", jdbcType=JdbcType.INTEGER),
            @Result(column="drug_id", property="drugStore", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_DRUG_STORE)),
    })
    List<DrugVo> selectDrugByAttribute(String attribute, String value);

    /**
     * 根据属性精确查询药品
     * @param attribute 字段名
     * @param value 值
     * @return 药品列表
     */
    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_drug "
            + "<where> "
            + "<if test='attribute == \"drugName\" '>"
            + " drug_name = #{value,jdbcType=VARCHAR} "
            + "</if> "
            + "<if test='attribute == \"drugAlias\" '> "
            + " AND drug_alias = #{value,jdbcType=VARCHAR} "
            + "</if>"
            + "<if test='attribute == \"code\" '> "
            + " AND code = #{value,jdbcType=VARCHAR} "
            + "</if>"
            + "</where> "
            + "</script>")
    @Results({
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_alias", property="drugAlias", jdbcType=JdbcType.VARCHAR),
            @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_name", property="drugName", jdbcType=JdbcType.VARCHAR),
            @Result(column="drug_form", property="drugForm", jdbcType=JdbcType.VARCHAR),
            @Result(column="toxicology_type", property="drugToxicology", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_DRUG_TOXICOLOGY)),
            @Result(column="toxicology_type", property="toxicologyType", jdbcType=JdbcType.BIGINT),
            @Result(column="is_in_bulk", property="isInBulk", jdbcType=JdbcType.INTEGER),
            @Result(column="drug_id", property="drugStore", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_DRUG_STORE)),
    })
    List<DrugVo> selectDrugsByAccurateAttribute(String attribute, String value);

    /**
     * 批量删除药品信息
     *
     * @param drugVos 药品列表
     */
    @DeleteProvider(type = Batch.class, method = "batchDrugDelete")
    void batchDrugDelete(List<DrugVo> drugVos);

    /**
     * 根据药品编号查询药品信息
     *
     * @param code 国家药品编号
     * @return 药品信息
     */
    @Select({
            "select",
            "*",
            "from entity_drug ",
            "where code = #{code,jdbcType=VARCHAR} "
    })
    @Results({
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_alias", property = "drugAlias", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type_name", property = "typeName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_name", property = "drugName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "drug_form", property = "drugForm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "toxicology_type", property = "toxicologyType", jdbcType = JdbcType.BIGINT),
            @Result(column = "is_in_bulk", property = "isInBulk", jdbcType = JdbcType.INTEGER),
            @Result(column = "drug_id", property = "drugStore", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_DRUG_STORE))
    })
    List<DrugVo> selectDrugByCode(String code);
}