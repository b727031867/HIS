package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.DrugStoreMapper;
import com.gxf.his.po.generate.DrugStore;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 药品库存的DAO接口
 */
public interface IDrugstoreMapper extends DrugStoreMapper {

    /**
     * 根据药品id查询药品库存
     * @param drugId 药品ID
     * @return 药品库存列表
     */
    @Select({
            "select",
            "*",
            "from entity_drug_store",
            "where drug_id = #{drugId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="inventory_id", property="inventoryId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
            @Result(column="inventory_num", property="inventoryNum", jdbcType=JdbcType.INTEGER),
            @Result(column="inventory_unit", property="inventoryUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="packaging_specifications", property="packagingSpecifications", jdbcType=JdbcType.INTEGER),
            @Result(column="smallest_unit", property="smallestUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="min_price", property="minPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="prescription_price", property="prescriptionPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    DrugStore selectDrugStoreByDrugId(Long drugId);

}