package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.Department;
import com.gxf.his.service.DepartmentService;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public ServerResponseVO addDepartment(Department department){
        if(null != department.getDepartmentCode() && StringUtils.isNotEmpty(department.getDepartmentIntroduction())
        && StringUtils.isNotEmpty(department.getDepartmentName()) && department.getDepartmentParentId() != null){
            departmentService.addDepartment(department);
        }else {
            logger.debug("请求的参数异常，参数为："+department.toString());
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        return ServerResponseVO.success();
    }

    @DeleteMapping("/delete")
    public ServerResponseVO deleteDepartment(Long departmentId){
        if(null == departmentId){
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        departmentService.deleteDepartment(departmentId);
        return ServerResponseVO.success();
    }

    @GetMapping("/top")
    public ServerResponseVO getFartherDepartment(){
        List<Department> departments = departmentService.getAllFatherDepartments();
        return ServerResponseVO.success(departments);
    }


}
