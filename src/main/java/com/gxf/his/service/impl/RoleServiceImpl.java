package com.gxf.his.service.impl;

import com.gxf.his.mapper.RoleMapper;
import com.gxf.his.po.Role;
import com.gxf.his.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> findRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }
}
