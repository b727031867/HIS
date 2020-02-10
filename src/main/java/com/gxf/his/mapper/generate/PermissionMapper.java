package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.Permission;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PermissionMapper {
    @Delete({
        "delete from entity_permission",
        "where permission_id = #{permissionId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long permissionId);

    @Insert({
        "insert into entity_permission (permission_id, permission_name, ",
        "permission)",
        "values (#{permissionId,jdbcType=BIGINT}, #{permissionName,jdbcType=VARCHAR}, ",
        "#{permission,jdbcType=VARCHAR})"
    })
    int insert(Permission record);

    @Select({
        "select",
        "permission_id, permission_name, permission",
        "from entity_permission",
        "where permission_id = #{permissionId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="permission_name", property="permissionName", jdbcType=JdbcType.VARCHAR),
        @Result(column="permission", property="permission", jdbcType=JdbcType.VARCHAR)
    })
    Permission selectByPrimaryKey(Long permissionId);

    @Select({
        "select",
        "permission_id, permission_name, permission",
        "from entity_permission"
    })
    @Results({
        @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="permission_name", property="permissionName", jdbcType=JdbcType.VARCHAR),
        @Result(column="permission", property="permission", jdbcType=JdbcType.VARCHAR)
    })
    List<Permission> selectAll();

    @Update({
        "update entity_permission",
        "set permission_name = #{permissionName,jdbcType=VARCHAR},",
          "permission = #{permission,jdbcType=VARCHAR}",
        "where permission_id = #{permissionId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Permission record);
}