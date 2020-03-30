package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.Batch;
import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.DrugStoreMapper;
import com.gxf.his.po.generate.DrugStore;
import com.gxf.his.po.vo.DrugStoreVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 药品库存的DAO接口
 */
public interface IDrugStoreMapper extends DrugStoreMapper {

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
            @Result(column="drug_id", property="drug", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_DRUG)),
            @Result(column="inventory_num", property="inventoryNum", jdbcType=JdbcType.INTEGER),
            @Result(column="inventory_unit", property="inventoryUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="packaging_specifications", property="packagingSpecifications", jdbcType=JdbcType.INTEGER),
            @Result(column="smallest_unit", property="smallestUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="min_price", property="minPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="prescription_price", property="prescriptionPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    DrugStoreVo selectDrugStoreByDrugId(Long drugId);

    /**
     * 查询所有药品库存，关联查询药品信息
     * @return 药品库存列表
     */
    @Select({
            "select",
            "*",
            "from entity_drug_store"
    })
    @Results({
            @Result(column="inventory_id", property="inventoryId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
            @Result(column="drug_id", property="drug", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_DRUG)),
            @Result(column="inventory_num", property="inventoryNum", jdbcType=JdbcType.INTEGER),
            @Result(column="inventory_unit", property="inventoryUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="packaging_specifications", property="packagingSpecifications", jdbcType=JdbcType.INTEGER),
            @Result(column="smallest_unit", property="smallestUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="min_price", property="minPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="prescription_price", property="prescriptionPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="last_user_id", property="lastUserId", jdbcType=JdbcType.BIGINT)
    })
    List<DrugStoreVo> selectAllDrugStoreAndDrug();

    /**
     * 根据属性查询药品库存，并且关联查询药品信息
     * @param drugStoreVo 库存信息业务实体类
     * @return 药品库存信息列表
     */
    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_drug_store "
            + "<where>"
            + "<if test='attribute == \"inventoryId\" and isAccurate == \"true\"'>"
            + "AND inventory_id = #{value,jdbcType=BIGINT}"
            + "</if>"
            + "<if test='attribute == \"inventoryId\" and isAccurate == \"false\"'>"
            + "AND inventory_id like CONCAT('%',#{value,jdbcType=BIGINT},'%')"
            + "</if>"
            + "<if test='attribute == \"drugId\" and isAccurate == \"true\"'>"
            + "AND drug_id = #{value,jdbcType=BIGINT}"
            + "</if>"
            + "<if test='attribute == \"drugId\" and isAccurate == \"false\"'>"
            + "AND drug_id like CONCAT('%',#{value,jdbcType=BIGINT},'%')"
            + "</if>"
            +"</where>"
            + "</script>")
    @Results({
            @Result(column="inventory_id", property="inventoryId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
            @Result(column="drug_id", property="drug", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_DRUG)),
            @Result(column="inventory_num", property="inventoryNum", jdbcType=JdbcType.INTEGER),
            @Result(column="inventory_unit", property="inventoryUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="packaging_specifications", property="packagingSpecifications", jdbcType=JdbcType.INTEGER),
            @Result(column="smallest_unit", property="smallestUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="min_price", property="minPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="prescription_price", property="prescriptionPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="last_user_id", property="lastUserId", jdbcType=JdbcType.BIGINT)
    })
    List<DrugStoreVo> selectDrugStoresByAttribute(DrugStoreVo drugStoreVo);

    @Insert({
            "insert into entity_drug_store (inventory_id, drug_id, ",
            "inventory_num, inventory_unit, ",
            "packaging_specifications, smallest_unit, ",
            "min_price, prescription_price, ",
            "update_time, last_user_id)",
            "values (#{inventoryId,jdbcType=BIGINT}, #{drugId,jdbcType=BIGINT}, ",
            "#{inventoryNum,jdbcType=INTEGER}, #{inventoryUnit,jdbcType=VARCHAR}, ",
            "#{packagingSpecifications,jdbcType=INTEGER}, #{smallestUnit,jdbcType=VARCHAR}, ",
            "#{minPrice,jdbcType=DECIMAL}, #{prescriptionPrice,jdbcType=DECIMAL}, ",
            "#{updateTime,jdbcType=TIMESTAMP}, #{lastUserId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys = true,keyColumn = "inventory_id",keyProperty = "inventoryId")
    int insertAndInjectID(DrugStore record);

    /**
     * 批量删除药品库存信息
     *
     * @param drugStoreVos 药品库存信息列表
     */
    @DeleteProvider(type = Batch.class, method = "batchDrugStoreDelete")
    void batchDrugStoreDelete(List<DrugStoreVo> drugStoreVos);
}