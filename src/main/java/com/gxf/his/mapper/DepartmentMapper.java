package com.gxf.his.mapper;

import com.gxf.his.po.Department;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author GXF
 * @date 2019-10-13 19:06:21
 **/
public interface DepartmentMapper {
    @Delete({
        "delete from entity_department",
        "where department_id = #{departmentId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long departmentId);

    @Insert({
        "insert into entity_department (department_id, department_code, ",
        "department_name, department_introduction, ",
        "department_parent_id)",
        "values (#{departmentId,jdbcType=BIGINT}, #{departmentCode,jdbcType=INTEGER}, ",
        "#{departmentName,jdbcType=VARCHAR}, #{departmentIntroduction,jdbcType=VARCHAR}, ",
        "#{departmentParentId,jdbcType=BIGINT})"
    })
    int insert(Department record);

    @Select({
        "select",
        "department_id, department_code, department_name, department_introduction, department_parent_id",
        "from entity_department",
        "where department_id = #{departmentId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="department_id", property="departmentId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.INTEGER),
        @Result(column="department_name", property="departmentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_introduction", property="departmentIntroduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_parent_id", property="departmentParentId", jdbcType=JdbcType.BIGINT)
    })
    Department selectByPrimaryKey(Long departmentId);

    @Select({
        "select",
        "department_id, department_code, department_name, department_introduction, department_parent_id",
        "from entity_department"
    })
    @Results({
        @Result(column="department_id", property="departmentId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.INTEGER),
        @Result(column="department_name", property="departmentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_introduction", property="departmentIntroduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_parent_id", property="departmentParentId", jdbcType=JdbcType.BIGINT)
    })
    List<Department> selectAll();

    @Select({
            "select",
            "department_id, department_code, department_name, department_introduction, department_parent_id",
            "from entity_department where department_parent_id = -1"
    })
    @Results({
            @Result(column="department_id", property="departmentId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.INTEGER),
            @Result(column="department_name", property="departmentName", jdbcType=JdbcType.VARCHAR),
            @Result(column="department_introduction", property="departmentIntroduction", jdbcType=JdbcType.VARCHAR),
            @Result(column="department_parent_id", property="departmentParentId", jdbcType=JdbcType.BIGINT)
    })
    List<Department> selectAllFatherDepartment();

    @Update({
        "update entity_department",
        "set department_code = #{departmentCode,jdbcType=INTEGER},",
          "department_name = #{departmentName,jdbcType=VARCHAR},",
          "department_introduction = #{departmentIntroduction,jdbcType=VARCHAR},",
          "department_parent_id = #{departmentParentId,jdbcType=BIGINT}",
        "where department_id = #{departmentId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Department record);
}