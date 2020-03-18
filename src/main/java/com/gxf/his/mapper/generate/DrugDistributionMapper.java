package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.DrugDistribution;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DrugDistributionMapper {
    @Delete({
        "delete from entity_drug_distribution",
        "where drug_distribution_id = #{drugDistributionId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long drugDistributionId);

    @Insert({
        "insert into entity_drug_distribution (drug_distribution_id, `number`, ",
        "is_in_bulk, is_new_one, ",
        "drug_id, prescription_id, ",
        "operate_id, create_time)",
        "values (#{drugDistributionId,jdbcType=BIGINT}, #{number,jdbcType=INTEGER}, ",
        "#{isInBulk,jdbcType=INTEGER}, #{isNewOne,jdbcType=INTEGER}, ",
        "#{drugId,jdbcType=BIGINT}, #{prescriptionId,jdbcType=BIGINT}, ",
        "#{operateId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(DrugDistribution record);

    @Select({
        "select",
        "drug_distribution_id, `number`, is_in_bulk, is_new_one, drug_id, prescription_id, ",
        "operate_id, create_time",
        "from entity_drug_distribution",
        "where drug_distribution_id = #{drugDistributionId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="drug_distribution_id", property="drugDistributionId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
        @Result(column="is_in_bulk", property="isInBulk", jdbcType=JdbcType.INTEGER),
        @Result(column="is_new_one", property="isNewOne", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    DrugDistribution selectByPrimaryKey(Long drugDistributionId);

    @Select({
        "select",
        "drug_distribution_id, `number`, is_in_bulk, is_new_one, drug_id, prescription_id, ",
        "operate_id, create_time",
        "from entity_drug_distribution"
    })
    @Results({
        @Result(column="drug_distribution_id", property="drugDistributionId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
        @Result(column="is_in_bulk", property="isInBulk", jdbcType=JdbcType.INTEGER),
        @Result(column="is_new_one", property="isNewOne", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<DrugDistribution> selectAll();

    @Update({
        "update entity_drug_distribution",
        "set `number` = #{number,jdbcType=INTEGER},",
          "is_in_bulk = #{isInBulk,jdbcType=INTEGER},",
          "is_new_one = #{isNewOne,jdbcType=INTEGER},",
          "drug_id = #{drugId,jdbcType=BIGINT},",
          "prescription_id = #{prescriptionId,jdbcType=BIGINT},",
          "operate_id = #{operateId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where drug_distribution_id = #{drugDistributionId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DrugDistribution record);
}