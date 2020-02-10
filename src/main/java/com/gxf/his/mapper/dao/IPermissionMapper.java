package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.PermissionMapper;
import com.gxf.his.po.generate.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
 * @author 龚秀峰
 * 权限模块的DAO接口
 */
public interface IPermissionMapper extends PermissionMapper {

    /**
     * 获取某位用户的所有权限
     * @param ids 角色id列表
     * @return 权限列表
     */
    @Select("SELECT * FROM entity_permission AS ep WHERE rrp.role_id IN #{ids} " +
            "AND ep.permission_id  = ref_role_permission.permission_id")
    @Results({
            @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="permission_name", property="permissionName", jdbcType=JdbcType.VARCHAR),
            @Result(column="permission", property="permission", jdbcType=JdbcType.VARCHAR)
    })
    List<Permission> selectPermissionsByRoleIds(@Param("ids") List<Long> ids);

}