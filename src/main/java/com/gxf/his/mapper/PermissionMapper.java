package com.gxf.his.mapper;

import com.gxf.his.po.Permission;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
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

    /**
     * 获取某位用户的所有权限
     * @param ids 角色id列表
     * @return 权限列表
     */
    @Select("SELECT * FROM entity_permission AS ep WHERE rrp.role_id IN #{ids} " +
            "AND ep.permission_id  = ref_role_permission.permission_id")
    @Results({
            @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="permission_name", property="permissionName", jdbcType=JdbcType.VARCHAR),
            @Result(column="permission", property="permission", jdbcType=JdbcType.VARCHAR)
    })
    List<Permission> selectPermissionsByRoleIds(@Param("ids") List<Integer> ids);

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