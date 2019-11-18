package com.gxf.his.service;

import com.gxf.his.po.Role;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
public interface RoleService {
    List<Role> findRolesByUserId(Long userId);


}
