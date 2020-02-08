package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DepartmentException;
import com.gxf.his.exception.PatientException;
import com.gxf.his.mapper.DepartmentMapper;
import com.gxf.his.po.Department;
import com.gxf.his.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getFatherAndChildrenDepartments() {
        try {
            List<Department> departments = departmentMapper.selectFatherAndChildrenDepartments();
            if (departments.size() < 1) {
                throw new DepartmentException(ServerResponseEnum.DEPARTMENTS_NOT_EXIST);
            }
            return departments;
        } catch (DepartmentException e) {
            logger.warn("当前暂无子科室！");
        } catch (Exception e) {
            logger.warn("查询所有子科室出现异常！信息为：" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Department> getAllChildrenDepartments() {
        try {
            List<Department> departments = departmentMapper.selectAllChildrenDepartment();
            if (departments.size() < 1) {
                throw new DepartmentException(ServerResponseEnum.DEPARTMENTS_NOT_EXIST);
            }
            return departments;
        } catch (DepartmentException e) {
            logger.warn("当前暂无科室！");
        } catch (Exception e) {
            logger.warn("查询所有科室出现异常！信息为：" + e.getMessage());
        }
        return null;
    }

    @Override
    public void addDepartment(Department department) {
        try {
            departmentMapper.insert(department);
        }catch (Exception e){
            logger.error("科室保存失败！",e);
            throw new DepartmentException(ServerResponseEnum.PATIENT_SAVE_FAIL);
        }
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        try {
            departmentMapper.deleteByPrimaryKey(departmentId);
        }catch (Exception e){
            logger.error("科室删除失败！",e);
            throw new DepartmentException(ServerResponseEnum.DEPARTMENT_DELETE_FAIL);
        }
    }

    @Override
    public List<Department> getDepartmentsByVaguelyDepartmentName(String name) {
        try {
            String vaguelyName = "%"+name+"%";
            List<Department> departments = departmentMapper.getDepartmentsByVaguelyDepartmentName(vaguelyName);
            return departments;
        }catch (Exception e){
            logger.error("科室查询失败！",e);
            throw new DepartmentException(ServerResponseEnum.DEPARTMENT_LIST_FAIL);
        }
    }
}
