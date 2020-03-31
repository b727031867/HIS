package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.DrugStoreBatchesMapper;
import com.gxf.his.po.generate.DrugStoreBatches;
import com.gxf.his.po.vo.DrugStoreBatchesVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

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

    /**
     * 关联查询库存采购计划、具体采购项、采购项中具体药品、库存
     * @return 采购计划列表
     */
    @Select({
            "select",
            "*",
            "from entity_drug_store_batches"
    })
    @Results({
            @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="supplier_name", property="supplierName", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplier_contact_user", property="supplierContactUser", jdbcType=JdbcType.VARCHAR),
            @Result(column="contact_person_name", property="contactPersonName", jdbcType=JdbcType.VARCHAR),
            @Result(column="inventory_batches_number", property="inventoryBatchesNumber", jdbcType=JdbcType.BIGINT),
            @Result(column="purchasing_agent_id", property="purchasingAgentId", jdbcType=JdbcType.BIGINT),
            @Result(column="total_money", property="totalMoney", jdbcType=JdbcType.DECIMAL),
            @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="verifier_id", property="verifierId", jdbcType=JdbcType.BIGINT),
            @Result(column="verifier_date", property="verifierDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="inventory_batches_id", property="storeBatchesList", jdbcType=JdbcType.BIGINT,many = @Many(select = MapperConst.MANY_STORE_BATCHES)),
    })
    List<DrugStoreBatchesVo> selectAllBatches();

    /**
     * 根据属性查询药品库存，并且关联查询药品信息
     * @param drugStoreBatchesVo 采购计划的业务实体类
     * @return 药品库存信息列表
     */
    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_drug_store_batches "
            + "<where>"
            + "<if test='attribute == \"inventoryBatchesNumber\" and isAccurate == \"true\"'>"
            + "AND inventory_batches_id = #{value,jdbcType=BIGINT}"
            + "</if>"
            + "<if test='attribute == \"inventoryBatchesNumber\" and isAccurate == \"false\"'>"
            + "AND inventory_batches_id like CONCAT('%',#{value,jdbcType=BIGINT},'%')"
            + "</if>"
            + "<if test='attribute == \"supplierName\" and isAccurate == \"true\"'>"
            + "AND supplier_name = #{value,jdbcType=BIGINT}"
            + "</if>"
            + "<if test='attribute == \"supplierName\" and isAccurate == \"false\"'>"
            + "AND supplier_name like CONCAT('%',#{value,jdbcType=BIGINT},'%')"
            + "</if>"
            + "<if test='attribute == \"contactPersonName\" and isAccurate == \"true\"'>"
            + "AND contact_person_name = #{value,jdbcType=BIGINT}"
            + "</if>"
            + "<if test='attribute == \"contactPersonName\" and isAccurate == \"false\"'>"
            + "AND contact_person_name like CONCAT('%',#{value,jdbcType=BIGINT},'%')"
            + "</if>"
            + "<if test='attribute == \"status\" and isAccurate == \"true\"'>"
            + "AND status = #{value,jdbcType=BIGINT}"
            + "</if>"
            + "<if test='attribute == \"status\" and isAccurate == \"false\"'>"
            + "AND status like CONCAT('%',#{value,jdbcType=BIGINT},'%')"
            + "</if>"
            +"</where>"
            + "</script>")
    @Results({
            @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="supplier_name", property="supplierName", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplier_contact_user", property="supplierContactUser", jdbcType=JdbcType.VARCHAR),
            @Result(column="contact_person_name", property="contactPersonName", jdbcType=JdbcType.VARCHAR),
            @Result(column="inventory_batches_number", property="inventoryBatchesNumber", jdbcType=JdbcType.BIGINT),
            @Result(column="purchasing_agent_id", property="purchasingAgentId", jdbcType=JdbcType.BIGINT),
            @Result(column="total_money", property="totalMoney", jdbcType=JdbcType.DECIMAL),
            @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="verifier_id", property="verifierId", jdbcType=JdbcType.BIGINT),
            @Result(column="verifier_date", property="verifierDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="inventory_batches_id", property="storeBatchesList", jdbcType=JdbcType.BIGINT,many = @Many(select = MapperConst.MANY_STORE_BATCHES)),
    })
    List<DrugStoreBatchesVo> selectDrugStoresByAttribute(DrugStoreBatchesVo drugStoreBatchesVo);

    /**
     * 获取采购中与已完成的采购单
     * 关联获取其中的采购项
     * @return 药品库存信息列表
     */
    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_drug_store_batches "
            + "<where>"
            +" status = 3 OR status = 4"
            +"</where>"
            + "</script>")
    @Results({
            @Result(column="inventory_batches_id", property="inventoryBatchesId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="supplier_name", property="supplierName", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplier_contact_user", property="supplierContactUser", jdbcType=JdbcType.VARCHAR),
            @Result(column="contact_person_name", property="contactPersonName", jdbcType=JdbcType.VARCHAR),
            @Result(column="inventory_batches_number", property="inventoryBatchesNumber", jdbcType=JdbcType.BIGINT),
            @Result(column="purchasing_agent_id", property="purchasingAgentId", jdbcType=JdbcType.BIGINT),
            @Result(column="total_money", property="totalMoney", jdbcType=JdbcType.DECIMAL),
            @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="verifier_id", property="verifierId", jdbcType=JdbcType.BIGINT),
            @Result(column="verifier_date", property="verifierDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="inventory_batches_id", property="storeBatchesList", jdbcType=JdbcType.BIGINT,many = @Many(select = MapperConst.MANY_STORE_BATCHES)),
    })
    List<DrugStoreBatchesVo> getBoughtAndFinishedBatchesList();
}
