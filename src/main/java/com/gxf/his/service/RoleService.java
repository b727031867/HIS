package com.gxf.his.service;

import com.gxf.his.po.generate.Role;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
public interface RoleService {

    /**
     * 查找某用户的所有角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> findRolesByUserId(Long userId);

}
