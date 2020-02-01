package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.Department;
import com.gxf.his.service.DepartmentService;
import com.gxf.his.vo.DepartmentVo;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Resource
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
        List<DepartmentVo> departmentVos = new ArrayList<>(16);
        List<Department> childrenDepts = new ArrayList<>(16);
        // 获取子科室，即Id不是-1或者-2的科室
        for(Department department : departments){
            if(department.getDepartmentParentId() != -1L && department.getDepartmentParentId() != -2L){
                childrenDepts.add(department);
            }
        }
        // -1为顶级科室
        long top = -1L;
        for(Department department : departments){
            //如果是顶级科室则设置导航名称,添加子项
            if(top == department.getDepartmentParentId()){
                DepartmentVo currentDepartmentVo = new DepartmentVo();
                List<DepartmentVo.ChildrenBean>  childrenBeans = new ArrayList<>(16);
                //获取子导航项
                for(Department childrenDept:childrenDepts){
                    if(childrenDept.getDepartmentParentId().equals(department.getDepartmentId())){
                        DepartmentVo.ChildrenBean childrenBean = new DepartmentVo.ChildrenBean();
                        childrenBean.setId(childrenDept.getDepartmentCode());
                        childrenBean.setText(childrenDept.getDepartmentName());

                        childrenBeans.add(childrenBean);
                    }

                }
                currentDepartmentVo.setText(department.getDepartmentName());
                currentDepartmentVo.setChildren(childrenBeans);
                departmentVos.add(currentDepartmentVo);
            }
        }
        return ServerResponseVO.success(departmentVos);
    }


}
