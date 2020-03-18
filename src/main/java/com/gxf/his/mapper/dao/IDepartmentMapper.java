package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.DepartmentMapper;
import com.gxf.his.po.generate.Department;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 科室的接口类
 **/
public interface IDepartmentMapper extends DepartmentMapper {

    /**
     * 根据科室编号获取科室
     *
     * @param departmentCode 科室编号
     * @return 科室
     */
    @Select({
            "select",
            "department_id, department_code, department_name, department_introduction, department_parent_id",
            "from entity_department",
            "where department_code = #{departmentCode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "department_id", property = "departmentId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "department_code", property = "departmentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_name", property = "departmentName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_introduction", property = "departmentIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_parent_id", property = "departmentParentId", jdbcType = JdbcType.BIGINT)
    })
    Department selectByDepartmentCode(String departmentCode);

    /**
     * 根据科室名称模糊查询
     *
     * @param name 科室名称
     * @return 科室列表
     */
    @Select({
            "select",
            "department_id, department_code, department_name, department_introduction, department_parent_id",
            "from entity_department",
            "where department_name like #{name,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "department_id", property = "departmentId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "department_code", property = "departmentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_name", property = "departmentName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_introduction", property = "departmentIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_parent_id", property = "departmentParentId", jdbcType = JdbcType.BIGINT)
    })
    List<Department> getDepartmentsByVaguelyDepartmentName(String name);

    /**
     * 获取用于挂号的一（父）二（子）级列表科室
     *
     * @return 科室列表
     */
    @Select({
            "select",
            "department_id, department_code, department_name, department_introduction, department_parent_id",
            "from entity_department where department_parent_id != -2"
    })
    @Results({
            @Result(column = "department_id", property = "departmentId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "department_code", property = "departmentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_name", property = "departmentName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_introduction", property = "departmentIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_parent_id", property = "departmentParentId", jdbcType = JdbcType.BIGINT)
    })
    List<Department> selectFatherAndChildrenDepartments();

    /**
     * 获取所有可以挂号的二级（子）科室列表
     *
     * @return 科室列表
     */
    @Select({
            "select",
            "department_id, department_code, department_name, department_introduction, department_parent_id",
            "from entity_department where department_parent_id != -2 AND department_parent_id != -1"
    })
    @Results({
            @Result(column = "department_id", property = "departmentId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "department_code", property = "departmentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_name", property = "departmentName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_introduction", property = "departmentIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_parent_id", property = "departmentParentId", jdbcType = JdbcType.BIGINT)
    })
    List<Department> selectAllChildrenDepartment();

    /**
     * 获取所有医疗科室列表
     *
     * @return 医疗科室列表
     */
    @Select({
            "select",
            "*",
            "from entity_department where department_parent_id = -1"
    })
    @Results({
            @Result(column = "department_id", property = "departmentId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "department_code", property = "departmentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_name", property = "departmentName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_introduction", property = "departmentIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_parent_id", property = "departmentParentId", jdbcType = JdbcType.BIGINT)
    })
    List<Department> getFartherDepartment();

    /**
     * 获取某个父级科室的所有子科室列表
     * @param parentId 父级科室ID
     * @return 医疗科室列表
     */
    @Select({
            "select",
            "*",
            "from entity_department where department_parent_id = #{parentId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "department_id", property = "departmentId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "department_code", property = "departmentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_name", property = "departmentName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_introduction", property = "departmentIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_parent_id", property = "departmentParentId", jdbcType = JdbcType.BIGINT)
    })
    List<Department> getChildrenDepartment(Long parentId);
}