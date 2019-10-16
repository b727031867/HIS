package com.gxf.his.service.impl;

import com.gxf.his.mapper.PermissionMapper;
import com.gxf.his.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;


    @Override
    public List<String> findPermissionsByRoleId(List<Integer> roleIds) {

        return null;
    }
}
