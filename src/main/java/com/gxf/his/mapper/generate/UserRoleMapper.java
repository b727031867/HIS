package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.UserRole;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface UserRoleMapper {
    @Delete({
        "delete from ref_user_role",
        "where user_role_id = #{userRoleId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long userRoleId);

    @Insert({
        "insert into ref_user_role (user_role_id, user_id, ",
        "role_id)",
        "values (#{userRoleId,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{roleId,jdbcType=BIGINT})"
    })
    int insert(UserRole record);

    @Select({
        "select",
        "user_role_id, user_id, role_id",
        "from ref_user_role",
        "where user_role_id = #{userRoleId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="user_role_id", property="userRoleId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT)
    })
    UserRole selectByPrimaryKey(Long userRoleId);

    @Select({
        "select",
        "user_role_id, user_id, role_id",
        "from ref_user_role"
    })
    @Results({
        @Result(column="user_role_id", property="userRoleId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT)
    })
    List<UserRole> selectAll();

    @Update({
        "update ref_user_role",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "role_id = #{roleId,jdbcType=BIGINT}",
        "where user_role_id = #{userRoleId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserRole record);
}