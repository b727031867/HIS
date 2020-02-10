package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DepartmentException;
import com.gxf.his.mapper.dao.IDepartmentMapper;
import com.gxf.his.po.generate.Department;
import com.gxf.his.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private IDepartmentMapper iDepartmentMapper;

    @Override
    public List<Department> getFatherAndChildrenDepartments() {
        try {
            List<Department> departments = iDepartmentMapper.selectFatherAndChildrenDepartments();
            if (departments.size() < 1) {
                throw new DepartmentException(ServerResponseEnum.DEPARTMENTS_NOT_EXIST);
            }
            return departments;
        } catch (DepartmentException e) {
            log.warn("当前暂无子科室！");
        } catch (Exception e) {
            log.warn("查询所有子科室出现异常！信息为：" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Department> getAllChildrenDepartments() {
        try {
            List<Department> departments = iDepartmentMapper.selectAllChildrenDepartment();
            if (departments.size() < 1) {
                throw new DepartmentException(ServerResponseEnum.DEPARTMENTS_NOT_EXIST);
            }
            return departments;
        } catch (DepartmentException e) {
            log.warn("当前暂无科室！");
        } catch (Exception e) {
            log.warn("查询所有科室出现异常！信息为：" + e.getMessage());
        }
        return null;
    }

    @Override
    public void addDepartment(Department department) {
        try {
            iDepartmentMapper.insert(department);
        } catch (Exception e) {
            log.error("科室保存失败！", e);
            throw new DepartmentException(ServerResponseEnum.PATIENT_SAVE_FAIL);
        }
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        try {
            iDepartmentMapper.deleteByPrimaryKey(departmentId);
        } catch (Exception e) {
            log.error("科室删除失败！", e);
            throw new DepartmentException(ServerResponseEnum.DEPARTMENT_DELETE_FAIL);
        }
    }

    @Override
    public List<Department> getDepartmentsByVaguelyDepartmentName(String name) {
        try {
            String vaguelyName = "%" + name + "%";
            return iDepartmentMapper.getDepartmentsByVaguelyDepartmentName(vaguelyName);
        } catch (Exception e) {
            log.error("科室查询失败！", e);
            throw new DepartmentException(ServerResponseEnum.DEPARTMENT_LIST_FAIL);
        }
    }
}
