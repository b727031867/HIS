package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.StoreBatches;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface StoreBatchesMapper {
    @Delete({
        "delete from ref_store_batches",
        "where inventory_ref_batches_id = #{inventoryRefBatchesId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long inventoryRefBatchesId);

    @Insert({
        "insert into ref_store_batches (inventory_ref_batches_id, drug_id, ",
        "inventory_id, inventory_batches_id, ",
        "check_info_id, trade_price, ",
        "total_amount, total_number, ",
        "production_date, expired_time, ",
        "is_noticed)",
        "values (#{inventoryRefBatchesId,jdbcType=BIGINT}, #{drugId,jdbcType=BIGINT}, ",
        "#{inventoryId,jdbcType=BIGINT}, #{inventoryBatchesId,jdbcType=BIGINT}, ",
        "#{checkInfoId,jdbcType=BIGINT}, #{tradePrice,jdbcType=DECIMAL}, ",
        "#{totalAmount,jdbcType=DECIMAL}, #{totalNumber,jdbcType=INTEGER}, ",
        "#{productionDate,jdbcType=TIMESTAMP}, #{expiredTime,jdbcType=TIMESTAMP}, ",
        "#{isNoticed,jdbcType=INTEGER})"
    })
    int insert(StoreBatches record);

    @Select({
        "select",
        "inventory_ref_batches_id, drug_id, inventory_id, inventory_batches_id, check_info_id, ",
        "trade_price, total_amount, total_number, production_date, expired_time, is_noticed",
        "from ref_store_batches",
        "where inventory_ref_batches_id = #{inventoryRefBatchesId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="inventory_ref_batches_id", property="inventoryRefBatchesId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_id", property="inventoryId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType=JdbcType.BIGINT),
        @Result(column="check_info_id", property="checkInfoId", jdbcType=JdbcType.BIGINT),
        @Result(column="trade_price", property="tradePrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="total_amount", property="totalAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="total_number", property="totalNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="production_date", property="productionDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="expired_time", property="expiredTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_noticed", property="isNoticed", jdbcType=JdbcType.INTEGER)
    })
    StoreBatches selectByPrimaryKey(Long inventoryRefBatchesId);

    @Select({
        "select",
        "inventory_ref_batches_id, drug_id, inventory_id, inventory_batches_id, check_info_id, ",
        "trade_price, total_amount, total_number, production_date, expired_time, is_noticed",
        "from ref_store_batches"
    })
    @Results({
        @Result(column="inventory_ref_batches_id", property="inventoryRefBatchesId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_id", property="inventoryId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType=JdbcType.BIGINT),
        @Result(column="check_info_id", property="checkInfoId", jdbcType=JdbcType.BIGINT),
        @Result(column="trade_price", property="tradePrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="total_amount", property="totalAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="total_number", property="totalNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="production_date", property="productionDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="expired_time", property="expiredTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_noticed", property="isNoticed", jdbcType=JdbcType.INTEGER)
    })
    List<StoreBatches> selectAll();

    @Update({
        "update ref_store_batches",
        "set drug_id = #{drugId,jdbcType=BIGINT},",
          "inventory_id = #{inventoryId,jdbcType=BIGINT},",
          "inventory_batches_id = #{inventoryBatchesId,jdbcType=BIGINT},",
          "check_info_id = #{checkInfoId,jdbcType=BIGINT},",
          "trade_price = #{tradePrice,jdbcType=DECIMAL},",
          "total_amount = #{totalAmount,jdbcType=DECIMAL},",
          "total_number = #{totalNumber,jdbcType=INTEGER},",
          "production_date = #{productionDate,jdbcType=TIMESTAMP},",
          "expired_time = #{expiredTime,jdbcType=TIMESTAMP},",
          "is_noticed = #{isNoticed,jdbcType=INTEGER}",
        "where inventory_ref_batches_id = #{inventoryRefBatchesId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StoreBatches record);
}