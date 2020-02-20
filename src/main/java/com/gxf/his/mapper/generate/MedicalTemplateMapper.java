package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.MedicalTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface MedicalTemplateMapper {
    @Delete({
        "delete from entity_medical_template",
        "where medical_template_id = #{medicalTemplateId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long medicalTemplateId);

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
    int insert(MedicalTemplate record);

    @Select({
        "select",
        "medical_template_id, `type`, `status`, upload_id, uploader_type, update_datetime, ",
        "create_datetime, content",
        "from entity_medical_template",
        "where medical_template_id = #{medicalTemplateId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="medical_template_id", property="medicalTemplateId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="upload_id", property="uploadId", jdbcType=JdbcType.BIGINT),
        @Result(column="uploader_type", property="uploaderType", jdbcType=JdbcType.INTEGER),
        @Result(column="update_datetime", property="updateDatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    MedicalTemplate selectByPrimaryKey(Long medicalTemplateId);

    @Select({
        "select",
        "medical_template_id, `type`, `status`, upload_id, uploader_type, update_datetime, ",
        "create_datetime, content",
        "from entity_medical_template"
    })
    @Results({
        @Result(column="medical_template_id", property="medicalTemplateId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="upload_id", property="uploadId", jdbcType=JdbcType.BIGINT),
        @Result(column="uploader_type", property="uploaderType", jdbcType=JdbcType.INTEGER),
        @Result(column="update_datetime", property="updateDatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalTemplate> selectAll();

    @Update({
        "update entity_medical_template",
        "set `type` = #{type,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=INTEGER},",
          "upload_id = #{uploadId,jdbcType=BIGINT},",
          "uploader_type = #{uploaderType,jdbcType=INTEGER},",
          "update_datetime = #{updateDatetime,jdbcType=TIMESTAMP},",
          "create_datetime = #{createDatetime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where medical_template_id = #{medicalTemplateId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MedicalTemplate record);
}