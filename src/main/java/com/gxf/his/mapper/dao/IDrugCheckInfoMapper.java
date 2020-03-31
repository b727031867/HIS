package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.DrugCheckInfoMapper;
import com.gxf.his.po.generate.DrugCheckInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/31 18:30
 * 药品清点信息DAO接口
 */
public interface IDrugCheckInfoMapper extends DrugCheckInfoMapper {

    /**
     * 保存一条药品入库项的清点信息
     * @param record 清点信息
     */
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
    @Options(useGeneratedKeys = true,keyProperty = "checkInfoId",keyColumn = "check_info_id")
    void insertAndInjectId(DrugCheckInfo record);
}
