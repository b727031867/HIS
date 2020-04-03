package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.User;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper {
    @Delete({
        "delete from entity_user",
        "where user_id = #{userId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long userId);

    @Insert({
        "insert into entity_user (user_id, user_name, ",
        "user_salt, user_password, ",
        "user_type, user_status, ",
        "user_create_date, app_id)",
        "values (#{userId,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
        "#{userSalt,jdbcType=VARCHAR}, #{userPassword,jdbcType=VARCHAR}, ",
        "#{userType,jdbcType=VARCHAR}, #{userStatus,jdbcType=TINYINT}, ",
        "#{userCreateDate,jdbcType=TIMESTAMP}, #{appId,jdbcType=BIGINT})"
    })
    int insert(User record);

    @Select({
        "select",
        "user_id, user_name, user_salt, user_password, user_type, user_status, user_create_date, ",
        "app_id",
        "from entity_user",
        "where user_id = #{userId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_salt", property="userSalt", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_password", property="userPassword", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_type", property="userType", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_status", property="userStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="user_create_date", property="userCreateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="app_id", property="appId", jdbcType=JdbcType.BIGINT)
    })
    User selectByPrimaryKey(Long userId);

    @Select({
        "select",
        "user_id, user_name, user_salt, user_password, user_type, user_status, user_create_date, ",
        "app_id",
        "from entity_user"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_salt", property="userSalt", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_password", property="userPassword", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_type", property="userType", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_status", property="userStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="user_create_date", property="userCreateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="app_id", property="appId", jdbcType=JdbcType.BIGINT)
    })
    List<User> selectAll();

    @Update({
        "update entity_user",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "user_salt = #{userSalt,jdbcType=VARCHAR},",
          "user_password = #{userPassword,jdbcType=VARCHAR},",
          "user_type = #{userType,jdbcType=VARCHAR},",
          "user_status = #{userStatus,jdbcType=TINYINT},",
          "user_create_date = #{userCreateDate,jdbcType=TIMESTAMP},",
          "app_id = #{appId,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(User record);
}