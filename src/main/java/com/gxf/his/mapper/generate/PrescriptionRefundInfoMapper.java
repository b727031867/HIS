package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.PrescriptionRefundInfo;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PrescriptionRefundInfoMapper {
    @Delete({
        "delete from entity_prescription_refund_info",
        "where refund_info_id = #{refundInfoId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long refundInfoId);

    @Insert({
        "insert into entity_prescription_refund_info (refund_info_id, create_time, ",
        "prescription_id, operate_id, ",
        "reason, `status`)",
        "values (#{refundInfoId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{prescriptionId,jdbcType=BIGINT}, #{operateId,jdbcType=BIGINT}, ",
        "#{reason,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER})"
    })
    int insert(PrescriptionRefundInfo record);

    @Select({
        "select",
        "refund_info_id, create_time, prescription_id, operate_id, reason, `status`",
        "from entity_prescription_refund_info",
        "where refund_info_id = #{refundInfoId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="refund_info_id", property="refundInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="reason", property="reason", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    PrescriptionRefundInfo selectByPrimaryKey(Long refundInfoId);

    @Select({
        "select",
        "refund_info_id, create_time, prescription_id, operate_id, reason, `status`",
        "from entity_prescription_refund_info"
    })
    @Results({
        @Result(column="refund_info_id", property="refundInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="reason", property="reason", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    List<PrescriptionRefundInfo> selectAll();

    @Update({
        "update entity_prescription_refund_info",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "prescription_id = #{prescriptionId,jdbcType=BIGINT},",
          "operate_id = #{operateId,jdbcType=BIGINT},",
          "reason = #{reason,jdbcType=VARCHAR},",
          "`status` = #{status,jdbcType=INTEGER}",
        "where refund_info_id = #{refundInfoId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PrescriptionRefundInfo record);
}