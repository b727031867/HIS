package com.gxf.his.mapper;

import com.gxf.his.po.Role;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {
    @Delete({
        "delete from entity_role",
        "where role_id = #{roleId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer roleId);

    @Insert({
        "insert into entity_role (role_id, role_name, ",
        "create_by_name, create_by_id, ",
        "create_time, update_by_name, ",
        "update_by_id, update_time)",
        "values (#{roleId,jdbcType=INTEGER}, #{roleName,jdbcType=VARCHAR}, ",
        "#{createByName,jdbcType=VARCHAR}, #{createById,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateByName,jdbcType=VARCHAR}, ",
        "#{updateById,jdbcType=INTEGER}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Role record);

    @Select({
        "select",
        "role_id, role_name, create_by_name, create_by_id, create_time, update_by_name, ",
        "update_by_id, update_time",
        "from entity_role",
        "where role_id = #{roleId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_by_name", property="createByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_by_id", property="createById", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_by_name", property="updateByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_by_id", property="updateById", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Role selectByPrimaryKey(Integer roleId);

    @Select({
        "select",
        "role_id, role_name, create_by_name, create_by_id, create_time, update_by_name, ",
        "update_by_id, update_time",
        "from entity_role"
    })
    @Results({
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_by_name", property="createByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_by_id", property="createById", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_by_name", property="updateByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_by_id", property="updateById", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Role> selectAll();

    @Update({
        "update entity_role",
        "set role_name = #{roleName,jdbcType=VARCHAR},",
          "create_by_name = #{createByName,jdbcType=VARCHAR},",
          "create_by_id = #{createById,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_by_name = #{updateByName,jdbcType=VARCHAR},",
          "update_by_id = #{updateById,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where role_id = #{roleId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Role record);

    /**
     * 获取用户的角色列表
     * @param uid 用户Id
     * @return 角色列表
     */
    @Select("SELECT * FROM entity_role WHERE role_id IN ( SELECT role_id FROM ref_user_role WHERE user_id = #{uid," +
            "jdbcType=INTEGER} ) ")
    @Results({
            @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_by_name", property="createByName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_by_id", property="createById", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_by_name", property="updateByName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_by_id", property="updateById", jdbcType=JdbcType.INTEGER),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Role> selectRolesByUserId(@Param("uid") Integer uid);
}