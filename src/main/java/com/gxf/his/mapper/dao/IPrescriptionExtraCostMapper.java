package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.PrescriptionExtraCostMapper;
import com.gxf.his.po.generate.PrescriptionExtraCost;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 14:28
 * 处方单额外收费
 */
public interface IPrescriptionExtraCostMapper extends PrescriptionExtraCostMapper {

    /**
     * 根据处方单ID查询额外收费
     * @param prescriptionId 处方单ID
     * @return 额外收费类
     */
    @Select({
            "select",
            "*",
            "from entity_prescription_extra_cost",
            "where prescription_id = #{prescriptionId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="prescription_extra_cost_id", property="prescriptionExtraCostId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
            @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
            @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    PrescriptionExtraCost selectByPrescriptionId(Long prescriptionId);

    /**
     * 插入一个额外收费项并且注入ID
     * @param record 额外收费项
     * @return 本次操作影响的行数
     */
    @Insert({
            "insert into entity_prescription_extra_cost (prescription_extra_cost_id, prescription_id, ",
            "operate_id, amount, ",
            "remark, create_time)",
            "values (#{prescriptionExtraCostId,jdbcType=BIGINT}, #{prescriptionId,jdbcType=BIGINT}, ",
            "#{operateId,jdbcType=BIGINT}, #{amount,jdbcType=DECIMAL}, ",
            "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "prescriptionExtraCostId", keyColumn = "prescription_extra_cost_id")
    int insertAndInjectId(PrescriptionExtraCost record);
}
