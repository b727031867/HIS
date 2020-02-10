package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.DrugStoreBatches;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DrugStoreBatchesMapper {
    @Delete({
        "delete from entity_drug_store_batches",
        "where inventory_batches_id = #{inventoryBatchesId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long inventoryBatchesId);

    @Insert({
        "insert into entity_drug_store_batches (inventory_batches_id, supplier_id, ",
        "inventory_batches_number, purchasing_agent_id, ",
        "total_money, create_date, ",
        "`status`, verifier_id, ",
        "verifier_date)",
        "values (#{inventoryBatchesId,jdbcType=BIGINT}, #{supplierId,jdbcType=BIGINT}, ",
        "#{inventoryBatchesNumber,jdbcType=BIGINT}, #{purchasingAgentId,jdbcType=BIGINT}, ",
        "#{totalMoney,jdbcType=DECIMAL}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{status,jdbcType=VARCHAR}, #{verifierId,jdbcType=BIGINT}, ",
        "#{verifierDate,jdbcType=TIMESTAMP})"
    })
    int insert(DrugStoreBatches record);

    @Select({
        "select",
        "inventory_batches_id, supplier_id, inventory_batches_number, purchasing_agent_id, ",
        "total_money, create_date, `status`, verifier_id, verifier_date",
        "from entity_drug_store_batches",
        "where inventory_batches_id = #{inventoryBatchesId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="supplier_id", property="supplierId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_batches_number", property="inventoryBatchesNumber", jdbcType=JdbcType.BIGINT),
        @Result(column="purchasing_agent_id", property="purchasingAgentId", jdbcType=JdbcType.BIGINT),
        @Result(column="total_money", property="totalMoney", jdbcType=JdbcType.DECIMAL),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="verifier_id", property="verifierId", jdbcType=JdbcType.BIGINT),
        @Result(column="verifier_date", property="verifierDate", jdbcType=JdbcType.TIMESTAMP)
    })
    DrugStoreBatches selectByPrimaryKey(Long inventoryBatchesId);

    @Select({
        "select",
        "inventory_batches_id, supplier_id, inventory_batches_number, purchasing_agent_id, ",
        "total_money, create_date, `status`, verifier_id, verifier_date",
        "from entity_drug_store_batches"
    })
    @Results({
        @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="supplier_id", property="supplierId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_batches_number", property="inventoryBatchesNumber", jdbcType=JdbcType.BIGINT),
        @Result(column="purchasing_agent_id", property="purchasingAgentId", jdbcType=JdbcType.BIGINT),
        @Result(column="total_money", property="totalMoney", jdbcType=JdbcType.DECIMAL),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="verifier_id", property="verifierId", jdbcType=JdbcType.BIGINT),
        @Result(column="verifier_date", property="verifierDate", jdbcType=JdbcType.TIMESTAMP)
    })
    List<DrugStoreBatches> selectAll();

    @Update({
        "update entity_drug_store_batches",
        "set supplier_id = #{supplierId,jdbcType=BIGINT},",
          "inventory_batches_number = #{inventoryBatchesNumber,jdbcType=BIGINT},",
          "purchasing_agent_id = #{purchasingAgentId,jdbcType=BIGINT},",
          "total_money = #{totalMoney,jdbcType=DECIMAL},",
          "create_date = #{createDate,jdbcType=TIMESTAMP},",
          "`status` = #{status,jdbcType=VARCHAR},",
          "verifier_id = #{verifierId,jdbcType=BIGINT},",
          "verifier_date = #{verifierDate,jdbcType=TIMESTAMP}",
        "where inventory_batches_id = #{inventoryBatchesId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DrugStoreBatches record);
}