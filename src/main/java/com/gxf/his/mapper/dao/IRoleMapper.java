package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.RoleMapper;
import com.gxf.his.po.generate.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 角色模块的DAO接口
 */
public interface IRoleMapper extends RoleMapper {

    /**
     * 获取用户的角色列表
     *
     * @param uid 用户Id
     * @return 角色列表
     */
    @Select("SELECT * FROM entity_role WHERE role_id IN ( SELECT role_id FROM ref_user_role WHERE user_id = #{uid," +
            "jdbcType=BIGINT} ) ")
    @Results({
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "role_name", property = "roleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_by_name", property = "createByName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_by_id", property = "createById", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by_name", property = "updateByName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_by_id", property = "updateById", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<Role> selectRolesByUserId(@Param("uid") Long uid);

}