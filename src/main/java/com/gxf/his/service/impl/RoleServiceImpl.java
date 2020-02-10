package com.gxf.his.service.impl;

import com.gxf.his.mapper.dao.IRoleMapper;
import com.gxf.his.po.generate.Role;
import com.gxf.his.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Resource
    private IRoleMapper iRoleMapper;

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        return iRoleMapper.selectRolesByUserId(userId);
    }
}
