package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.Department;
import com.gxf.his.po.vo.DepartmentVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.DepartmentService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @PostMapping("/add")
    public <T> ServerResponseVO<T> addDepartment(Department department) {
        if (null != department.getDepartmentCode() && StringUtils.isNotEmpty(department.getDepartmentIntroduction())
                && StringUtils.isNotEmpty(department.getDepartmentName()) && department.getDepartmentParentId() != null) {
            departmentService.addDepartment(department);
        } else {
            log.debug("请求的参数异常，参数为：" + department.toString());
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        return ServerResponseVO.success();
    }

    @DeleteMapping("/delete")
    public <T> ServerResponseVO<T> deleteDepartment(Long departmentId) {
        if (null == departmentId) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        departmentService.deleteDepartment(departmentId);
        return ServerResponseVO.success();
    }

    @GetMapping("/top")
    public <T> ServerResponseVO<T> getFartherAndChildrenDepartment() {
        List<Department> departments = departmentService.getFatherAndChildrenDepartments();
        List<DepartmentVo> departmentVos = new ArrayList<>(16);
        List<Department> childrenDepts = new ArrayList<>(16);
        // 获取子科室，即Id不是-1或者-2的科室
        for (Department department : departments) {
            if (department.getDepartmentParentId() != -1L && department.getDepartmentParentId() != -2L) {
                childrenDepts.add(department);
            }
        }
        // -1为顶级科室
        long top = -1L;
        for (Department department : departments) {
            //如果是顶级科室则设置导航名称,添加子项
            if (top == department.getDepartmentParentId()) {
                DepartmentVo currentDepartmentVo = new DepartmentVo();
                List<DepartmentVo.ChildrenBean> childrenBeans = new ArrayList<>(16);
                //获取子导航项
                for (Department childrenDept : childrenDepts) {
                    if (childrenDept.getDepartmentParentId().equals(department.getDepartmentId())) {
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
        return MyUtil.cast(ServerResponseVO.success(departmentVos));
    }


    @GetMapping("/farther")
    public <T> ServerResponseVO<T> getFartherDepartment() {
        return MyUtil.cast(ServerResponseVO.success(departmentService.getFartherDepartment()));
    }

    @GetMapping("/children")
    public <T> ServerResponseVO<T> getChildrenDepartment(Long parentId) {
        if (parentId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        return MyUtil.cast(ServerResponseVO.success(departmentService.getChildrenDepartment(parentId)));
    }

}
