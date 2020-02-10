package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.DrugStore;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DrugStoreMapper {
    @Delete({
        "delete from entity_drug_store",
        "where inventory_id = #{inventoryId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long inventoryId);

    @Insert({
        "insert into entity_drug_store (inventory_id, drug_id, ",
        "inventory_num, inventory_unit, ",
        "packaging_specifications, smallest_unit, ",
        "min_price, prescription_price, ",
        "update_time)",
        "values (#{inventoryId,jdbcType=BIGINT}, #{drugId,jdbcType=BIGINT}, ",
        "#{inventoryNum,jdbcType=INTEGER}, #{inventoryUnit,jdbcType=VARCHAR}, ",
        "#{packagingSpecifications,jdbcType=INTEGER}, #{smallestUnit,jdbcType=VARCHAR}, ",
        "#{minPrice,jdbcType=DECIMAL}, #{prescriptionPrice,jdbcType=DECIMAL}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(DrugStore record);

    @Select({
        "select",
        "inventory_id, drug_id, inventory_num, inventory_unit, packaging_specifications, ",
        "smallest_unit, min_price, prescription_price, update_time",
        "from entity_drug_store",
        "where inventory_id = #{inventoryId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="inventory_id", property="inventoryId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_num", property="inventoryNum", jdbcType=JdbcType.INTEGER),
        @Result(column="inventory_unit", property="inventoryUnit", jdbcType=JdbcType.VARCHAR),
        @Result(column="packaging_specifications", property="packagingSpecifications", jdbcType=JdbcType.INTEGER),
        @Result(column="smallest_unit", property="smallestUnit", jdbcType=JdbcType.VARCHAR),
        @Result(column="min_price", property="minPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="prescription_price", property="prescriptionPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    DrugStore selectByPrimaryKey(Long inventoryId);

    @Select({
        "select",
        "inventory_id, drug_id, inventory_num, inventory_unit, packaging_specifications, ",
        "smallest_unit, min_price, prescription_price, update_time",
        "from entity_drug_store"
    })
    @Results({
        @Result(column="inventory_id", property="inventoryId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_num", property="inventoryNum", jdbcType=JdbcType.INTEGER),
        @Result(column="inventory_unit", property="inventoryUnit", jdbcType=JdbcType.VARCHAR),
        @Result(column="packaging_specifications", property="packagingSpecifications", jdbcType=JdbcType.INTEGER),
        @Result(column="smallest_unit", property="smallestUnit", jdbcType=JdbcType.VARCHAR),
        @Result(column="min_price", property="minPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="prescription_price", property="prescriptionPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<DrugStore> selectAll();

    @Update({
        "update entity_drug_store",
        "set drug_id = #{drugId,jdbcType=BIGINT},",
          "inventory_num = #{inventoryNum,jdbcType=INTEGER},",
          "inventory_unit = #{inventoryUnit,jdbcType=VARCHAR},",
          "packaging_specifications = #{packagingSpecifications,jdbcType=INTEGER},",
          "smallest_unit = #{smallestUnit,jdbcType=VARCHAR},",
          "min_price = #{minPrice,jdbcType=DECIMAL},",
          "prescription_price = #{prescriptionPrice,jdbcType=DECIMAL},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where inventory_id = #{inventoryId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DrugStore record);
}