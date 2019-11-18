package com.gxf.his.mapper;

import com.gxf.his.po.Role;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleMapper {
    @Delete({
        "delete from entity_role",
        "where role_id = #{roleId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long roleId);

    @Insert({
        "insert into entity_role (role_id, role_name, ",
        "create_by_name, create_by_id, ",
        "create_time, update_by_name, ",
        "update_by_id, update_time)",
        "values (#{roleId,jdbcType=BIGINT}, #{roleName,jdbcType=VARCHAR}, ",
        "#{createByName,jdbcType=VARCHAR}, #{createById,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateByName,jdbcType=VARCHAR}, ",
        "#{updateById,jdbcType=BIGINT}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Role record);

    @Select({
        "select",
        "role_id, role_name, create_by_name, create_by_id, create_time, update_by_name, ",
        "update_by_id, update_time",
        "from entity_role",
        "where role_id = #{roleId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_by_name", property="createByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_by_id", property="createById", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_by_name", property="updateByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_by_id", property="updateById", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Role selectByPrimaryKey(Long roleId);

    @Select({
        "select",
        "role_id, role_name, create_by_name, create_by_id, create_time, update_by_name, ",
        "update_by_id, update_time",
        "from entity_role"
    })
    @Results({
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_by_name", property="createByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_by_id", property="createById", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_by_name", property="updateByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_by_id", property="updateById", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Role> selectAll();

    @Update({
        "update entity_role",
        "set role_name = #{roleName,jdbcType=VARCHAR},",
          "create_by_name = #{createByName,jdbcType=VARCHAR},",
          "create_by_id = #{createById,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_by_name = #{updateByName,jdbcType=VARCHAR},",
          "update_by_id = #{updateById,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where role_id = #{roleId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Role record);

    /**
     * 获取用户的角色列表
     * @param uid 用户Id
     * @return 角色列表
     */
    @Select("SELECT * FROM entity_role WHERE role_id IN ( SELECT role_id FROM ref_user_role WHERE user_id = #{uid," +
            "jdbcType=BIGINT} ) ")
    @Results({
            @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_by_name", property="createByName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_by_id", property="createById", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_by_name", property="updateByName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_by_id", property="updateById", jdbcType=JdbcType.BIGINT),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Role> selectRolesByUserId(@Param("uid") Long uid);
}