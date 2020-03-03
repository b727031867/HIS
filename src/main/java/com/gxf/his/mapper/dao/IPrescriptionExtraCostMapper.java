package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.PrescriptionExtraCostMapper;
import com.gxf.his.po.generate.PrescriptionExtraCost;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
}
