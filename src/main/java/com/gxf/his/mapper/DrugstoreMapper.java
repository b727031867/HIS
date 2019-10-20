package com.gxf.his.mapper;

import com.gxf.his.po.Drugstore;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DrugstoreMapper {
    @Delete({
        "delete from entity_drug_store",
        "where drug_store_id = #{drugStoreId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer drugStoreId);

    @Insert({
        "insert into entity_drug_store (drug_store_id, drug_store_name, ",
        "drug_store_number)",
        "values (#{drugStoreId,jdbcType=INTEGER}, #{drugStoreName,jdbcType=VARCHAR}, ",
        "#{drugStoreNumber,jdbcType=INTEGER})"
    })
    int insert(Drugstore record);

    @Select({
        "select",
        "drug_store_id, drug_store_name, drug_store_number",
        "from entity_drug_store",
        "where drug_store_id = #{drugStoreId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="drug_store_id", property="drugStoreId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="drug_store_name", property="drugStoreName", jdbcType=JdbcType.VARCHAR),
        @Result(column="drug_store_number", property="drugStoreNumber", jdbcType=JdbcType.INTEGER)
    })
    Drugstore selectByPrimaryKey(Integer drugStoreId);

    @Select({
        "select",
        "drug_store_id, drug_store_name, drug_store_number",
        "from entity_drug_store"
    })
    @Results({
        @Result(column="drug_store_id", property="drugStoreId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="drug_store_name", property="drugStoreName", jdbcType=JdbcType.VARCHAR),
        @Result(column="drug_store_number", property="drugStoreNumber", jdbcType=JdbcType.INTEGER)
    })
    List<Drugstore> selectAll();

    @Update({
        "update entity_drug_store",
        "set drug_store_name = #{drugStoreName,jdbcType=VARCHAR},",
          "drug_store_number = #{drugStoreNumber,jdbcType=INTEGER}",
        "where drug_store_id = #{drugStoreId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Drugstore record);
}