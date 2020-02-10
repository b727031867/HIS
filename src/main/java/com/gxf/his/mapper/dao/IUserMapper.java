package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.Batch;
import com.gxf.his.mapper.generate.UserMapper;
import com.gxf.his.po.generate.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 用户模块的DAO接口
 */
public interface IUserMapper extends UserMapper {

    /**
     * 根据用户名查找用户
     * @param userName 用户名
     * @return 用户
     */
    @Select({
            "select",
            "user_id, user_name, user_salt, user_password, user_status, user_create_date,app_id ",
            "from entity_user",
            "where user_name = #{userName,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_salt", property = "userSalt", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_password", property = "userPassword", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_status", property = "userStatus", jdbcType = JdbcType.TINYINT),
            @Result(column = "user_create_date", property = "userCreateDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "app_id", property = "appId", jdbcType = JdbcType.BIGINT)
    })
    User selectByUserName(String userName);


    /**
     * 添加一位用户，并且插入后注入自增ID到record中
     * @param record 用户
     * @return 影响的行数
     */
    @Insert({
            "insert into entity_user (user_id, user_name, ",
            "user_salt, user_password, ",
            "user_status, user_create_date,app_id)",
            "values (#{userId,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
            "#{userSalt,jdbcType=VARCHAR}, #{userPassword,jdbcType=VARCHAR}, ",
            "#{userStatus,jdbcType=TINYINT},#{userCreateDate,jdbcType=TIMESTAMP},#{appId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insertAndInjectThePrimaryKey(User record);

    /**
     * 批量删除用户
     * @param users 医生列表
     * @return 影响的行数
     */
    @DeleteProvider(type = Batch.class, method = "batchUserDelete")
    Integer batchUserDelete(List<User> users);

}