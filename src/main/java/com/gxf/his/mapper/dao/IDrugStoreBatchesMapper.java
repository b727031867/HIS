package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.DrugStoreBatchesMapper;
import com.gxf.his.po.generate.DrugStoreBatches;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/28 09:33
 * 药库库存批次信息DAO接口
 */
public interface IDrugStoreBatchesMapper extends DrugStoreBatchesMapper {

    /**
     * 插入一条库存批次信息，并且注入主键
     * @param record 库存批次信息
     * @return 本次操作影响的行数
     */
    @Insert({
            "insert into entity_drug_store_batches (inventory_batches_id, supplier_name, ",
            "phone, supplier_contact_user, ",
            "contact_person_name, inventory_batches_number, ",
            "purchasing_agent_id, total_money, ",
            "create_date, `status`, ",
            "verifier_id, verifier_date, ",
            "remark)",
            "values (#{inventoryBatchesId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
            "#{phone,jdbcType=VARCHAR}, #{supplierContactUser,jdbcType=VARCHAR}, ",
            "#{contactPersonName,jdbcType=VARCHAR}, #{inventoryBatchesNumber,jdbcType=BIGINT}, ",
            "#{purchasingAgentId,jdbcType=BIGINT}, #{totalMoney,jdbcType=DECIMAL}, ",
            "#{createDate,jdbcType=TIMESTAMP}, #{status,jdbcType=VARCHAR}, ",
            "#{verifierId,jdbcType=BIGINT}, #{verifierDate,jdbcType=TIMESTAMP}, ",
            "#{remark,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "inventoryBatchesId", keyColumn = "inventory_batches_id")
    int insertAndInjectId(DrugStoreBatches record);
}
