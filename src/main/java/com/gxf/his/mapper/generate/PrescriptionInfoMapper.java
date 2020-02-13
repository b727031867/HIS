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
        "drug_id, `status`, package_nunber, ",
        "min_number, item_total_price)",
        "values (#{prescriptionInfoId,jdbcType=BIGINT}, #{prescriptionId,jdbcType=BIGINT}, ",
        "#{drugId,jdbcType=BIGINT}, #{status,jdbcType=INTEGER}, #{packageNunber,jdbcType=INTEGER}, ",
        "#{minNumber,jdbcType=INTEGER}, #{itemTotalPrice,jdbcType=DECIMAL})"
    })
    int insert(PrescriptionInfo record);

    @Select({
        "select",
        "prescription_info_id, prescription_id, drug_id, `status`, package_nunber, min_number, ",
        "item_total_price",
        "from entity_prescription_info",
        "where prescription_info_id = #{prescriptionInfoId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="prescription_info_id", property="prescriptionInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="package_nunber", property="packageNunber", jdbcType=JdbcType.INTEGER),
        @Result(column="min_number", property="minNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="item_total_price", property="itemTotalPrice", jdbcType=JdbcType.DECIMAL)
    })
    PrescriptionInfo selectByPrimaryKey(Long prescriptionInfoId);

    @Select({
        "select",
        "prescription_info_id, prescription_id, drug_id, `status`, package_nunber, min_number, ",
        "item_total_price",
        "from entity_prescription_info"
    })
    @Results({
        @Result(column="prescription_info_id", property="prescriptionInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="package_nunber", property="packageNunber", jdbcType=JdbcType.INTEGER),
        @Result(column="min_number", property="minNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="item_total_price", property="itemTotalPrice", jdbcType=JdbcType.DECIMAL)
    })
    List<PrescriptionInfo> selectAll();

    @Update({
        "update entity_prescription_info",
        "set prescription_id = #{prescriptionId,jdbcType=BIGINT},",
          "drug_id = #{drugId,jdbcType=BIGINT},",
          "`status` = #{status,jdbcType=INTEGER},",
          "package_nunber = #{packageNunber,jdbcType=INTEGER},",
          "min_number = #{minNumber,jdbcType=INTEGER},",
          "item_total_price = #{itemTotalPrice,jdbcType=DECIMAL}",
        "where prescription_info_id = #{prescriptionInfoId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PrescriptionInfo record);
}