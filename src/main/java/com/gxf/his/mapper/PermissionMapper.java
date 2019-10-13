package com.gxf.his.mapper;

import com.gxf.his.po.Permission;
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
        "where permission_id = #{permissionId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer permissionId);

    @Insert({
        "insert into entity_permission (permission_id, permission_name, ",
        "permission)",
        "values (#{permissionId,jdbcType=INTEGER}, #{permissionName,jdbcType=VARCHAR}, ",
        "#{permission,jdbcType=VARCHAR})"
    })
    int insert(Permission record);

    @Select({
        "select",
        "permission_id, permission_name, permission",
        "from entity_permission",
        "where permission_id = #{permissionId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="permission_name", property="permissionName", jdbcType=JdbcType.VARCHAR),
        @Result(column="permission", property="permission", jdbcType=JdbcType.VARCHAR)
    })
    Permission selectByPrimaryKey(Integer permissionId);

    @Select({
        "select",
        "permission_id, permission_name, permission",
        "from entity_permission"
    })
    @Results({
        @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="permission_name", property="permissionName", jdbcType=JdbcType.VARCHAR),
        @Result(column="permission", property="permission", jdbcType=JdbcType.VARCHAR)
    })
    List<Permission> selectAll();

    @Update({
        "update entity_permission",
        "set permission_name = #{permissionName,jdbcType=VARCHAR},",
          "permission = #{permission,jdbcType=VARCHAR}",
        "where permission_id = #{permissionId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Permission record);
}