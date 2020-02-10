package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.Drug;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DrugMapper {
    @Delete({
        "delete from entity_drug",
        "where drug_id = #{drugId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long drugId);

    @Insert({
        "insert into entity_drug (drug_id, code, ",
        "drug_alias, type_name, ",
        "drug_name, drug_form, ",
        "toxicology_type)",
        "values (#{drugId,jdbcType=BIGINT}, #{code,jdbcType=VARCHAR}, ",
        "#{drugAlias,jdbcType=VARCHAR}, #{typeName,jdbcType=VARCHAR}, ",
        "#{drugName,jdbcType=VARCHAR}, #{drugForm,jdbcType=VARCHAR}, ",
        "#{toxicologyType,jdbcType=BIGINT})"
    })
    int insert(Drug record);

    @Select({
        "select",
        "drug_id, code, drug_alias, type_name, drug_name, drug_form, toxicology_type",
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
        @Result(column="toxicology_type", property="toxicologyType", jdbcType=JdbcType.BIGINT)
    })
    Drug selectByPrimaryKey(Long drugId);

    @Select({
        "select",
        "drug_id, code, drug_alias, type_name, drug_name, drug_form, toxicology_type",
        "from entity_drug"
    })
    @Results({
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="drug_alias", property="drugAlias", jdbcType=JdbcType.VARCHAR),
        @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="drug_name", property="drugName", jdbcType=JdbcType.VARCHAR),
        @Result(column="drug_form", property="drugForm", jdbcType=JdbcType.VARCHAR),
        @Result(column="toxicology_type", property="toxicologyType", jdbcType=JdbcType.BIGINT)
    })
    List<Drug> selectAll();

    @Update({
        "update entity_drug",
        "set code = #{code,jdbcType=VARCHAR},",
          "drug_alias = #{drugAlias,jdbcType=VARCHAR},",
          "type_name = #{typeName,jdbcType=VARCHAR},",
          "drug_name = #{drugName,jdbcType=VARCHAR},",
          "drug_form = #{drugForm,jdbcType=VARCHAR},",
          "toxicology_type = #{toxicologyType,jdbcType=BIGINT}",
        "where drug_id = #{drugId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Drug record);
}