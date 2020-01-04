package com.gxf.his.service;

import com.gxf.his.po.Department;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
public interface DepartmentService {
    /**
     * 获取所有顶级科室
     * @return
     */
    List<Department> getAllFatherDepartments();

    /**
     * 添加一种科室
     * @param department 某个科室
     */
    void addDepartment(Department department);

    /**
     * 删除一种科室，不递归删除子科室
     * @param departmentId 被删除科室的ID
     */
    void deleteDepartment(Long departmentId);

    /**
     * 根据科室名称模糊查询相关部门
     * @param name 科室的模糊名称
     * @return 科室列表
     */
    List<Department> getDepartmentsByVaguelyDepartmentName(String name);

}
