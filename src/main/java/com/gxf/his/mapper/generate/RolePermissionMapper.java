package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.RolePermission;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface RolePermissionMapper {
    @Delete({
        "delete from ref_role_permission",
        "where role_permission_id = #{rolePermissionId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long rolePermissionId);

    @Insert({
        "insert into ref_role_permission (role_permission_id, role_id, ",
        "permission_id)",
        "values (#{rolePermissionId,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
        "#{permissionId,jdbcType=BIGINT})"
    })
    int insert(RolePermission record);

    @Select({
        "select",
        "role_permission_id, role_id, permission_id",
        "from ref_role_permission",
        "where role_permission_id = #{rolePermissionId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="role_permission_id", property="rolePermissionId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT),
        @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.BIGINT)
    })
    RolePermission selectByPrimaryKey(Long rolePermissionId);

    @Select({
        "select",
        "role_permission_id, role_id, permission_id",
        "from ref_role_permission"
    })
    @Results({
        @Result(column="role_permission_id", property="rolePermissionId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT),
        @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.BIGINT)
    })
    List<RolePermission> selectAll();

    @Update({
        "update ref_role_permission",
        "set role_id = #{roleId,jdbcType=BIGINT},",
          "permission_id = #{permissionId,jdbcType=BIGINT}",
        "where role_permission_id = #{rolePermissionId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RolePermission record);
}