package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.DrugExpiredInfo;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DrugExpiredInfoMapper {
    @Delete({
        "delete from entity_drug_expired_info",
        "where drug_expired_id = #{drugExpiredId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long drugExpiredId);

    @Insert({
        "insert into entity_drug_expired_info (drug_expired_id, drug_id, ",
        "inventory_ref_batches_id, processing_method, ",
        "`number`, operate_id, ",
        "create_date)",
        "values (#{drugExpiredId,jdbcType=BIGINT}, #{drugId,jdbcType=BIGINT}, ",
        "#{inventoryRefBatchesId,jdbcType=BIGINT}, #{processingMethod,jdbcType=VARCHAR}, ",
        "#{number,jdbcType=INTEGER}, #{operateId,jdbcType=BIGINT}, ",
        "#{createDate,jdbcType=TIMESTAMP})"
    })
    int insert(DrugExpiredInfo record);

    @Select({
        "select",
        "drug_expired_id, drug_id, inventory_ref_batches_id, processing_method, `number`, ",
        "operate_id, create_date",
        "from entity_drug_expired_info",
        "where drug_expired_id = #{drugExpiredId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="drug_expired_id", property="drugExpiredId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_ref_batches_id", property="inventoryRefBatchesId", jdbcType=JdbcType.BIGINT),
        @Result(column="processing_method", property="processingMethod", jdbcType=JdbcType.VARCHAR),
        @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP)
    })
    DrugExpiredInfo selectByPrimaryKey(Long drugExpiredId);

    @Select({
        "select",
        "drug_expired_id, drug_id, inventory_ref_batches_id, processing_method, `number`, ",
        "operate_id, create_date",
        "from entity_drug_expired_info"
    })
    @Results({
        @Result(column="drug_expired_id", property="drugExpiredId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="inventory_ref_batches_id", property="inventoryRefBatchesId", jdbcType=JdbcType.BIGINT),
        @Result(column="processing_method", property="processingMethod", jdbcType=JdbcType.VARCHAR),
        @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP)
    })
    List<DrugExpiredInfo> selectAll();

    @Update({
        "update entity_drug_expired_info",
        "set drug_id = #{drugId,jdbcType=BIGINT},",
          "inventory_ref_batches_id = #{inventoryRefBatchesId,jdbcType=BIGINT},",
          "processing_method = #{processingMethod,jdbcType=VARCHAR},",
          "`number` = #{number,jdbcType=INTEGER},",
          "operate_id = #{operateId,jdbcType=BIGINT},",
          "create_date = #{createDate,jdbcType=TIMESTAMP}",
        "where drug_expired_id = #{drugExpiredId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DrugExpiredInfo record);
}