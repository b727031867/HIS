package com.gxf.his.mapper;

import com.gxf.his.po.Resource;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ResourceMapper {
    @Delete({
        "delete from entity_resource",
        "where resource_id = #{resourceId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long resourceId);

    @Insert({
        "insert into entity_resource (resource_id, resource_name, ",
        "`size`, `status`, `path`, ",
        "introduction, upload_time, ",
        "upload_by)",
        "values (#{resourceId,jdbcType=BIGINT}, #{resourceName,jdbcType=VARCHAR}, ",
        "#{size,jdbcType=DECIMAL}, #{status,jdbcType=TINYINT}, #{path,jdbcType=VARCHAR}, ",
        "#{introduction,jdbcType=VARCHAR}, #{uploadTime,jdbcType=TIMESTAMP}, ",
        "#{uploadBy,jdbcType=BIGINT})"
    })
    int insert(Resource record);

    @Select({
        "select",
        "resource_id, resource_name, `size`, `status`, `path`, introduction, upload_time, ",
        "upload_by",
        "from entity_resource",
        "where resource_id = #{resourceId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_name", property="resourceName", jdbcType=JdbcType.VARCHAR),
        @Result(column="size", property="size", jdbcType=JdbcType.DECIMAL),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="path", property="path", jdbcType=JdbcType.VARCHAR),
        @Result(column="introduction", property="introduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="upload_time", property="uploadTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="upload_by", property="uploadBy", jdbcType=JdbcType.BIGINT)
    })
    Resource selectByPrimaryKey(Long resourceId);

    @Select({
        "select",
        "resource_id, resource_name, `size`, `status`, `path`, introduction, upload_time, ",
        "upload_by",
        "from entity_resource"
    })
    @Results({
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_name", property="resourceName", jdbcType=JdbcType.VARCHAR),
        @Result(column="size", property="size", jdbcType=JdbcType.DECIMAL),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="path", property="path", jdbcType=JdbcType.VARCHAR),
        @Result(column="introduction", property="introduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="upload_time", property="uploadTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="upload_by", property="uploadBy", jdbcType=JdbcType.BIGINT)
    })
    List<Resource> selectAll();

    @Update({
        "update entity_resource",
        "set resource_name = #{resourceName,jdbcType=VARCHAR},",
          "`size` = #{size,jdbcType=DECIMAL},",
          "`status` = #{status,jdbcType=TINYINT},",
          "`path` = #{path,jdbcType=VARCHAR},",
          "introduction = #{introduction,jdbcType=VARCHAR},",
          "upload_time = #{uploadTime,jdbcType=TIMESTAMP},",
          "upload_by = #{uploadBy,jdbcType=BIGINT}",
        "where resource_id = #{resourceId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Resource record);
}