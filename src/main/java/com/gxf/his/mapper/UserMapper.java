package com.gxf.his.mapper;

import com.gxf.his.po.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


public interface UserMapper {
    //  ********************************查询方法********************************
    @Select({
            "select",
            "user_id, user_name, user_salt, user_password, user_status, user_create_date",
            "from entity_user",
            "where user_id = #{userId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_salt", property="userSalt", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_password", property="userPassword", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_status", property="userStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="user_create_date", property="userCreateDate", jdbcType=JdbcType.TIMESTAMP)
    })
    User selectByPrimaryKey(Long userId);

    @Select({
            "select",
            "user_id, user_name, user_salt, user_password, user_status, user_create_date",
            "from entity_user",
            "where user_name = #{userName,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_salt", property="userSalt", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_password", property="userPassword", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_status", property="userStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="user_create_date", property="userCreateDate", jdbcType=JdbcType.TIMESTAMP)
    })
    User selectByUserName(String userName);

    @Select({
            "select",
            "user_id, user_name, user_salt, user_password, user_status, user_create_date",
            "from entity_user"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_salt", property="userSalt", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_password", property="userPassword", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_status", property="userStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="user_create_date", property="userCreateDate", jdbcType=JdbcType.TIMESTAMP)
    })
    List<User> selectAll();

    //  ********************************更新方法********************************
    @Update({
            "update entity_user ",
            "set user_name = #{userName,jdbcType=VARCHAR},",
            "user_salt = #{userSalt,jdbcType=VARCHAR},",
            "user_password = #{userPassword,jdbcType=VARCHAR},",
            "user_status = #{userStatus,jdbcType=TINYINT},",
            "user_create_date = #{userCreateDate,jdbcType=TIMESTAMP}",
            "where user_id = #{userId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(User record);

    //  ********************************添加方法********************************
    @Insert({
            "insert into entity_user (user_id, user_name, ",
            "user_salt, user_password, ",
            "user_status, user_create_date)",
            "values (#{userId,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
            "#{userSalt,jdbcType=VARCHAR}, #{userPassword,jdbcType=VARCHAR}, ",
            "#{userStatus,jdbcType=TINYINT},#{userCreateDate,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insert(User record);
    //  ********************************删除方法********************************
    @Delete({
        "delete from entity_user",
        "where user_id = #{userId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long userId);

}