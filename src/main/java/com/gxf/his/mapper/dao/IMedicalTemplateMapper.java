package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.MedicalTemplateMapper;
import com.gxf.his.po.generate.MedicalTemplate;
import com.gxf.his.po.vo.TemplateVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/20 15:00
 */
public interface IMedicalTemplateMapper extends MedicalTemplateMapper {

    /**
     * 获取所有模板
     *
     * @return 模板列表
     */
    @Select({
            "select",
            "*",
            "from entity_medical_template"
    })
    @Results({
            @Result(column = "medical_template_id", property = "medicalTemplateId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "upload_id", property = "uploadId", jdbcType = JdbcType.BIGINT),
            @Result(column = "uploader_type", property = "uploaderType", jdbcType = JdbcType.INTEGER),
            @Result(column = "update_datetime", property = "updateDatetime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_datetime", property = "createDatetime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "content", property = "content", jdbcType = JdbcType.LONGVARCHAR)
    })
    List<TemplateVo> selectAllTemplates();

    /**
     * 插入一个模板并且注入自增主键的值
     *
     * @param record 模板
     * @return 影响的行数
     */
    @Insert({
            "insert into entity_medical_template (medical_template_id, `type`,title,description, ",
            "`status`, upload_id, ",
            "uploader_type, update_datetime, ",
            "create_datetime, content)",
            "values (#{medicalTemplateId,jdbcType=BIGINT}, #{type,jdbcType=INTEGER},#{title,jdbcType=VARCHAR},#{description,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=INTEGER}, #{uploadId,jdbcType=BIGINT}, ",
            "#{uploaderType,jdbcType=INTEGER}, #{updateDatetime,jdbcType=TIMESTAMP}, ",
            "#{createDatetime,jdbcType=TIMESTAMP}, #{content,jdbcType=LONGVARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "medicalTemplateId", keyColumn = "medical_template_id")
    int insertAndInjectId(MedicalTemplate record);
}
