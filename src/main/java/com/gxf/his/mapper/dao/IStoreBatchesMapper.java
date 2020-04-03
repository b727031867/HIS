package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.StoreBatchesMapper;
import com.gxf.his.po.vo.StoreBatchesVo;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
     *
     * @return 采购项列表
     */
    @Select({
            "select",
            "*",
            "from ref_store_batches",
            "where is_noticed = 0 AND batches_total > 0 AND expired_time >= (DATE_SUB(CURDATE(), INTERVAL 3 MONTH))"
    })
    @Results({
            @Result(column = "inventory_ref_batches_id", property = "inventoryRefBatchesId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT),
            @Result(column = "inventory_id", property = "inventoryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "inventory_batches_id", property = "inventoryBatchesId", jdbcType = JdbcType.BIGINT),
            @Result(column = "trade_price", property = "tradePrice", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_amount", property = "totalAmount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_number", property = "totalNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "batches_total", property = "batchesTotal", jdbcType = JdbcType.INTEGER),
            @Result(column = "production_date", property = "productionDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "expired_time", property = "expiredTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "isNoticed", property = "is_noticed", jdbcType = JdbcType.INTEGER)
    })
    List<StoreBatchesVo> getExpiredStoreBatchesDrugs();

    /**
     * 查询某批次的采购项，并且关联查询药品与库存信息
     *
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
            @Result(column = "inventory_ref_batches_id", property = "inventoryRefBatchesId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT),
            @Result(column = "drug_id", property = "drugVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_DRUG_VO)),
            @Result(column = "inventory_id", property = "inventoryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "inventory_batches_id", property = "inventoryBatchesId", jdbcType = JdbcType.BIGINT),
            @Result(column = "trade_price", property = "tradePrice", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_amount", property = "totalAmount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_number", property = "totalNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "production_date", property = "productionDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "expired_time", property = "expiredTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "isNoticed", property = "is_noticed", jdbcType = JdbcType.INTEGER)
    })
    List<StoreBatchesVo> selectStoreBatchesVosRelatedByInventoryBatchesId(Long inventoryBatchesId);

    /**
     * 获取要过期处理的
     * @return 需要进行过期处理的药品采购项列表
     */
    @Select({
            "select",
            "*",
            "from ref_store_batches",
            "where is_noticed = 1 AND drug_expired_id IS NULL"
    })
    @Results({
            @Result(column = "inventory_ref_batches_id", property = "inventoryRefBatchesId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT),
            @Result(column = "drug_id", property = "drugVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_DRUG_VO)),
            @Result(column = "inventory_id", property = "drugStore", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_DRUG_STORE)),
            @Result(column = "inventory_id", property = "inventoryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "inventory_batches_id", property = "inventoryBatchesId", jdbcType = JdbcType.BIGINT),
            @Result(column = "inventory_batches_id", property = "inventoryBatches", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_STORE_BATCHES)),
            @Result(column = "check_info_id", property = "drugCheckInfo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_DRUG_CHECK_INFO)),
            @Result(column = "trade_price", property = "tradePrice", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_amount", property = "totalAmount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_number", property = "totalNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "batches_total", property = "batchesTotal", jdbcType = JdbcType.INTEGER),
            @Result(column = "production_date", property = "productionDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "expired_time", property = "expiredTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "is_noticed", property = "isNoticed", jdbcType = JdbcType.INTEGER)
    })
    List<StoreBatchesVo> getExpiringMedicines();

    /**
     * 获取某药品剩余保质期最短的采购批次项
     * @param drugId 药品信息ID
     * @return 需要进行过期处理的药品采购项列表
     */
    @Select({
            "select",
            "*",
            "from ref_store_batches",
            "where drug_id = #{drugId,jdbcType=BIGINT} AND batches_total > 0 order by expired_time limit 1"
    })
    @Results({
            @Result(column = "inventory_ref_batches_id", property = "inventoryRefBatchesId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT),
            @Result(column = "inventory_id", property = "inventoryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "inventory_batches_id", property = "inventoryBatchesId", jdbcType = JdbcType.BIGINT),
            @Result(column = "check_info_id", property = "checkInfoId", jdbcType = JdbcType.BIGINT),
            @Result(column = "trade_price", property = "tradePrice", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_amount", property = "totalAmount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_number", property = "totalNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "batches_total", property = "batchesTotal", jdbcType = JdbcType.INTEGER),
            @Result(column = "production_date", property = "productionDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "expired_time", property = "expiredTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "isNoticed", property = "is_noticed", jdbcType = JdbcType.INTEGER)
    })
    StoreBatchesVo getDrugsThatWillExpireSoon(Long drugId);
}
