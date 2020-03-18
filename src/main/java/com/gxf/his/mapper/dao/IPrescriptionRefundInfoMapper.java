package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.PrescriptionRefundInfoMapper;
import com.gxf.his.po.generate.PrescriptionRefundInfo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/10 16:33
 */
public interface IPrescriptionRefundInfoMapper extends PrescriptionRefundInfoMapper {

    /**
     * 根据处方单ID查询处方退款信息
     * @param prescriptionId 处方单Id
     * @return 处方单退款信息
     */
    @Select({
            "select",
            "*",
            "from entity_prescription_refund_info",
            "where prescription_id = #{prescriptionId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="refund_info_id", property="refundInfoId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
            @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
            @Result(column="reason", property="reason", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    PrescriptionRefundInfo selectByPrescriptionId(Long prescriptionId);
}
