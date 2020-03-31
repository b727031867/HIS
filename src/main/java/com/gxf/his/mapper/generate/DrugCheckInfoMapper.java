package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.DrugCheckInfo;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DrugCheckInfoMapper {
    @Delete({
        "delete from entity_drug_check_info",
        "where check_info_id = #{checkInfoId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long checkInfoId);

    @Insert({
        "insert into entity_drug_check_info (check_info_id, loss_amount, ",
        "stock_quantity, remark, ",
        "checker_id, create_time, ",
        "drug_id, inventory_batches_id, ",
        "inventory_ref_batches_id)",
        "values (#{checkInfoId,jdbcType=BIGINT}, #{lossAmount,jdbcType=INTEGER}, ",
        "#{stockQuantity,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, ",
        "#{checkerId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{drugId,jdbcType=BIGINT}, #{inventoryBatchesId,jdbcType=BIGINT}, ",
        "#{inventoryRefBatchesId,jdbcType=BIGINT})"
    })
    int insert(DrugCheckInfo record);

    @Select({
        "select",
        "check_info_id, loss_amount, stock_quantity, remark, checker_id, create_time, ",
        "drug_id, inventory_batches_id, inventory_ref_batches_id",
        "from entity_drug_check_info",
        "where check_info_id = #{checkInfoId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="check_info_id", property="checkInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="loss_amount", property="lossAmount", jdbcType=JdbcType.INTEGER),
        @Result(column="stock_quantity", property="stockQuantity", jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="checker_id", property="checkerId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_ref_batches_id", property="inventoryRefBatchesId", jdbcType=JdbcType.BIGINT)
    })
    DrugCheckInfo selectByPrimaryKey(Long checkInfoId);

    @Select({
        "select",
        "check_info_id, loss_amount, stock_quantity, remark, checker_id, create_time, ",
        "drug_id, inventory_batches_id, inventory_ref_batches_id",
        "from entity_drug_check_info"
    })
    @Results({
        @Result(column="check_info_id", property="checkInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="loss_amount", property="lossAmount", jdbcType=JdbcType.INTEGER),
        @Result(column="stock_quantity", property="stockQuantity", jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="checker_id", property="checkerId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_ref_batches_id", property="inventoryRefBatchesId", jdbcType=JdbcType.BIGINT)
    })
    List<DrugCheckInfo> selectAll();

    @Update({
        "update entity_drug_check_info",
        "set loss_amount = #{lossAmount,jdbcType=INTEGER},",
          "stock_quantity = #{stockQuantity,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "checker_id = #{checkerId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "drug_id = #{drugId,jdbcType=BIGINT},",
          "inventory_batches_id = #{inventoryBatchesId,jdbcType=BIGINT},",
          "inventory_ref_batches_id = #{inventoryRefBatchesId,jdbcType=BIGINT}",
        "where check_info_id = #{checkInfoId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DrugCheckInfo record);
}