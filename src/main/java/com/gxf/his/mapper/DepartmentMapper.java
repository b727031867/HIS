package com.gxf.his.mapper;

import com.gxf.his.po.Department;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @author GXF
 * @date 2019-10-13 19:06:21
 **/
@Repository
@Mapper
public interface DepartmentMapper {
    @Delete({
        "delete from entity_department",
        "where department_id = #{departmentId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer departmentId);

    @Insert({
        "insert into entity_department (department_id, department_code, ",
        "department_name, department_introduction, ",
        "department_parent_id)",
        "values (#{departmentId,jdbcType=INTEGER}, #{departmentCode,jdbcType=INTEGER}, ",
        "#{departmentName,jdbcType=VARCHAR}, #{departmentIntroduction,jdbcType=VARCHAR}, ",
        "#{departmentParentId,jdbcType=INTEGER})"
    })
    int insert(Department record);

    @Select({
        "select",
        "department_id, department_code, department_name, department_introduction, department_parent_id",
        "from entity_department",
        "where department_id = #{departmentId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="department_id", property="departmentId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.INTEGER),
        @Result(column="department_name", property="departmentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_introduction", property="departmentIntroduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_parent_id", property="departmentParentId", jdbcType=JdbcType.INTEGER)
    })
    Department selectByPrimaryKey(Integer departmentId);

    @Select({
        "select",
        "department_id, department_code, department_name, department_introduction, department_parent_id",
        "from entity_department"
    })
    @Results({
        @Result(column="department_id", property="departmentId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.INTEGER),
        @Result(column="department_name", property="departmentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_introduction", property="departmentIntroduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_parent_id", property="departmentParentId", jdbcType=JdbcType.INTEGER)
    })
    List<Department> selectAll();

    @Select({
            "select",
            "department_id, department_code, department_name, department_introduction, department_parent_id",
            "from entity_department where department_parent_id = -1"
    })
    @Results({
            @Result(column="department_id", property="departmentId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.INTEGER),
            @Result(column="department_name", property="departmentName", jdbcType=JdbcType.VARCHAR),
            @Result(column="department_introduction", property="departmentIntroduction", jdbcType=JdbcType.VARCHAR),
            @Result(column="department_parent_id", property="departmentParentId", jdbcType=JdbcType.INTEGER)
    })
    List<Department> selectAllFatherDepartment();

    @Update({
        "update entity_department",
        "set department_code = #{departmentCode,jdbcType=INTEGER},",
          "department_name = #{departmentName,jdbcType=VARCHAR},",
          "department_introduction = #{departmentIntroduction,jdbcType=VARCHAR},",
          "department_parent_id = #{departmentParentId,jdbcType=INTEGER}",
        "where department_id = #{departmentId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Department record);
}