package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.PrescriptionInfoMapper;
import com.gxf.his.po.generate.PrescriptionInfo;
import com.gxf.his.po.vo.PrescriptionInfoVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 13:32
 * 处方单详情
 */
public interface IPrescriptionInfoMapper extends PrescriptionInfoMapper {

    /**
     * 根据处方单ID查询处方项列表
     * @param prescriptionId 处方单ID
     * @return 处方项列表
     */
    @Select({
            "select",
            "*",
            "from entity_prescription_info",
            "where prescription_id = #{prescriptionId,jdbcType=BIGINT} "
    })
    @Results({
            @Result(column="prescription_info_id", property="prescriptionInfoId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
            @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
            @Result(column="drug_id", property="drugVo", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_DRUG_VO)),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="item_total_price", property="itemTotalPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="unit", property="unit", jdbcType=JdbcType.VARCHAR),
            @Result(column="num", property="num", jdbcType=JdbcType.INTEGER)
    })
    List<PrescriptionInfoVo> selectPrescriptionInfosByPrescriptionId(Long prescriptionId);

    /**
     * 插入一个处方项，并且注入ID
     * @param record 处方项
     * @return 本次操作影响的行数
     */
    @Insert({
            "insert into entity_prescription_info (prescription_info_id, prescription_id, ",
            "drug_id, `status`, item_total_price, ",
            "unit, num)",
            "values (#{prescriptionInfoId,jdbcType=BIGINT}, #{prescriptionId,jdbcType=BIGINT}, ",
            "#{drugId,jdbcType=BIGINT}, #{status,jdbcType=INTEGER}, #{itemTotalPrice,jdbcType=DECIMAL}, ",
            "#{unit,jdbcType=VARCHAR}, #{num,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "prescriptionInfoId", keyColumn = "prescription_info_id")
    int insertAndInjectId(PrescriptionInfo record);

}
