package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.MedicalTemplateMapper;
import com.gxf.his.po.generate.MedicalTemplate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/20 15:00
 */
public interface IMedicalTemplate extends MedicalTemplateMapper {

    /**
     * 插入一个模板并且注入自增主键的值
     * @param record 模板
     * @return 影响的行数
     */
    @Insert({
            "insert into entity_medical_template (medical_template_id, `type`, ",
            "`status`, upload_id, ",
            "uploader_type, update_datetime, ",
            "create_datetime, content)",
            "values (#{medicalTemplateId,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, ",
            "#{status,jdbcType=INTEGER}, #{uploadId,jdbcType=BIGINT}, ",
            "#{uploaderType,jdbcType=INTEGER}, #{updateDatetime,jdbcType=TIMESTAMP}, ",
            "#{createDatetime,jdbcType=TIMESTAMP}, #{content,jdbcType=LONGVARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "medicalTemplateId", keyColumn = "medical_template_id")
    int insertAndInjectId(MedicalTemplate record);
}
