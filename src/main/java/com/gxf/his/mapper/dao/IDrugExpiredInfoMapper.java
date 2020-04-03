package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.DrugExpiredInfoMapper;
import com.gxf.his.po.generate.DrugExpiredInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/4/2 23:22
 * 药品过期处理信息的DAO接口
 */
public interface IDrugExpiredInfoMapper extends DrugExpiredInfoMapper {

    @Insert({
            "insert into entity_drug_expired_info (drug_expired_id, drug_id, ",
            "processing_method, `number`, ",
            "operate_id, create_date)",
            "values (#{drugExpiredId,jdbcType=BIGINT}, #{drugId,jdbcType=BIGINT}, ",
            "#{processingMethod,jdbcType=VARCHAR}, #{number,jdbcType=INTEGER}, ",
            "#{operateId,jdbcType=BIGINT}, #{createDate,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "drugExpiredId",keyColumn = "drug_expired_id")
    int insertAndInjectId(DrugExpiredInfo record);
}
