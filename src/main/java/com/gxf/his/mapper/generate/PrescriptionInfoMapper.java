package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.PrescriptionInfo;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PrescriptionInfoMapper {
    @Delete({
        "delete from entity_prescription_info",
        "where prescription_info_id = #{prescriptionInfoId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long prescriptionInfoId);

    @Insert({
        "insert into entity_prescription_info (prescription_info_id, prescription_id, ",
        "drug_id, `status`, item_total_price, ",
        "unit, num)",
        "values (#{prescriptionInfoId,jdbcType=BIGINT}, #{prescriptionId,jdbcType=BIGINT}, ",
        "#{drugId,jdbcType=BIGINT}, #{status,jdbcType=INTEGER}, #{itemTotalPrice,jdbcType=DECIMAL}, ",
        "#{unit,jdbcType=VARCHAR}, #{num,jdbcType=INTEGER})"
    })
    int insert(PrescriptionInfo record);

    @Select({
        "select",
        "prescription_info_id, prescription_id, drug_id, `status`, item_total_price, ",
        "unit, num",
        "from entity_prescription_info",
        "where prescription_info_id = #{prescriptionInfoId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="prescription_info_id", property="prescriptionInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="item_total_price", property="itemTotalPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="unit", property="unit", jdbcType=JdbcType.VARCHAR),
        @Result(column="num", property="num", jdbcType=JdbcType.INTEGER)
    })
    PrescriptionInfo selectByPrimaryKey(Long prescriptionInfoId);

    @Select({
        "select",
        "prescription_info_id, prescription_id, drug_id, `status`, item_total_price, ",
        "unit, num",
        "from entity_prescription_info"
    })
    @Results({
        @Result(column="prescription_info_id", property="prescriptionInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="item_total_price", property="itemTotalPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="unit", property="unit", jdbcType=JdbcType.VARCHAR),
        @Result(column="num", property="num", jdbcType=JdbcType.INTEGER)
    })
    List<PrescriptionInfo> selectAll();

    @Update({
        "update entity_prescription_info",
        "set prescription_id = #{prescriptionId,jdbcType=BIGINT},",
          "drug_id = #{drugId,jdbcType=BIGINT},",
          "`status` = #{status,jdbcType=INTEGER},",
          "item_total_price = #{itemTotalPrice,jdbcType=DECIMAL},",
          "unit = #{unit,jdbcType=VARCHAR},",
          "num = #{num,jdbcType=INTEGER}",
        "where prescription_info_id = #{prescriptionInfoId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PrescriptionInfo record);
}