package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.DrugDistributionMapper;
import com.gxf.his.po.generate.DrugDistribution;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/9 15:01
 * 药品分发信息
 */
public interface IDrugDistributionMapper extends DrugDistributionMapper {
    /**
     * 根据处方单ID获取药品分发信息
     * @param prescriptionId 处方单ID
     * @return 药品分发信息列表
     */
    @Select({
            "select",
            "drug_distribution_id, `number`, is_in_bulk, is_new_one, drug_id, prescription_id, ",
            "operate_id, create_time",
            "from entity_drug_distribution",
            "where prescription_id = #{prescriptionId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="drug_distribution_id", property="drugDistributionId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
            @Result(column="is_in_bulk", property="isInBulk", jdbcType=JdbcType.INTEGER),
            @Result(column="is_new_one", property="isNewOne", jdbcType=JdbcType.INTEGER),
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
            @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
            @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<DrugDistribution> getDistributionsByPrescriptionId(Long prescriptionId);
}
