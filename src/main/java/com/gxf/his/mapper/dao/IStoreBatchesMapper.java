package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.StoreBatchesMapper;
import com.gxf.his.po.generate.StoreBatches;
import com.gxf.his.po.vo.StoreBatchesVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/30 11:00
 * 批次采购项DAO接口
 */
public interface IStoreBatchesMapper extends StoreBatchesMapper {

    /**
     * 查询某批次的采购项，并且关联查询药品与库存信息
     * @param inventoryBatchesId 采购批次的ID
     * @return 采购项列表
     */
    @Select({
            "select",
            "*",
            "from ref_store_batches",
            "where inventory_batches_id = #{inventoryBatchesId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="inventory_ref_batches_id", property="inventoryRefBatchesId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
            @Result(column="drug_id", property="drugVo", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_DRUG_VO)),
            @Result(column="inventory_id", property="inventoryId", jdbcType=JdbcType.BIGINT),
            @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType=JdbcType.BIGINT),
            @Result(column="trade_price", property="tradePrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="total_amount", property="totalAmount", jdbcType=JdbcType.DECIMAL),
            @Result(column="total_number", property="totalNumber", jdbcType=JdbcType.INTEGER),
            @Result(column="production_date", property="productionDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="expired_time", property="expiredTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="isNoticed", property="is_noticed", jdbcType=JdbcType.INTEGER)
    })
    List<StoreBatchesVo> selectStoreBatchesVosRelatedByInventoryBatchesId(Long inventoryBatchesId);

}
