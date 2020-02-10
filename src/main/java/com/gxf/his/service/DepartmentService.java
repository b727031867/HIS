package com.gxf.his.service;


import com.gxf.his.po.generate.Department;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
public interface DepartmentService {
    /**
     * 获取所有医疗科室，包括顶级科室和子科室，不包括财务部门等
     *
     * @return 科室列表
     */
    List<Department> getFatherAndChildrenDepartments();

    /**
     * 获取所有子医疗科室，不包括财务部门等
     *
     * @return 科室列表
     */
    List<Department> getAllChildrenDepartments();

    /**
     * 添加一种科室
     *
     * @param department 某个科室
     */
    void addDepartment(Department department);

    /**
     * 删除一种科室，不递归删除子科室
     *
     * @param departmentId 被删除科室的ID
     */
    void deleteDepartment(Long departmentId);

    /**
     * 根据科室名称模糊查询相关部门
     *
     * @param name 科室的模糊名称
     * @return 科室列表
     */
    List<Department> getDepartmentsByVaguelyDepartmentName(String name);

}
