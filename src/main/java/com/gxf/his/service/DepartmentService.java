package com.gxf.his.service;

import com.gxf.his.po.Department;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
public interface DepartmentService {
    List<Department> getAllFatherDepartments();

    void addDepartment(Department department);

    void deleteDepartment(Long departmentId);

}
