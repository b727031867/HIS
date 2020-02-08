package com.gxf.his.service.impl;

import com.gxf.his.mapper.PermissionMapper;
import com.gxf.his.po.Permission;
import com.gxf.his.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Resource
    PermissionMapper permissionMapper;


    @Override
    public List<String> findPermissionsByRoleId(List<Long> roleIds) {
        List<Permission> permissions = permissionMapper.selectPermissionsByRoleIds(roleIds);
        ArrayList<String> userPermissions =new ArrayList<>();
        for (Permission permission : permissions) {
            userPermissions.add(permission.getPermission());
        }
        //可以考虑加入角色权限去重，比如同时拥有多个角色，每个角色都拥有相同的权限
        return userPermissions;
    }
}
