package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.PrescriptionExtraCost;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PrescriptionExtraCostMapper {
    @Delete({
        "delete from entity_prescription_extra_cost",
        "where prescription_extra_cost_id = #{prescriptionExtraCostId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long prescriptionExtraCostId);

    @Insert({
        "insert into entity_prescription_extra_cost (prescription_extra_cost_id, prescription_id, ",
        "operate_id, amount, ",
        "remark, create_time)",
        "values (#{prescriptionExtraCostId,jdbcType=BIGINT}, #{prescriptionId,jdbcType=BIGINT}, ",
        "#{operateId,jdbcType=BIGINT}, #{amount,jdbcType=DECIMAL}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(PrescriptionExtraCost record);

    @Select({
        "select",
        "prescription_extra_cost_id, prescription_id, operate_id, amount, remark, create_time",
        "from entity_prescription_extra_cost",
        "where prescription_extra_cost_id = #{prescriptionExtraCostId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="prescription_extra_cost_id", property="prescriptionExtraCostId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    PrescriptionExtraCost selectByPrimaryKey(Long prescriptionExtraCostId);

    @Select({
        "select",
        "prescription_extra_cost_id, prescription_id, operate_id, amount, remark, create_time",
        "from entity_prescription_extra_cost"
    })
    @Results({
        @Result(column="prescription_extra_cost_id", property="prescriptionExtraCostId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PrescriptionExtraCost> selectAll();

    @Update({
        "update entity_prescription_extra_cost",
        "set prescription_id = #{prescriptionId,jdbcType=BIGINT},",
          "operate_id = #{operateId,jdbcType=BIGINT},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where prescription_extra_cost_id = #{prescriptionExtraCostId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PrescriptionExtraCost record);
}