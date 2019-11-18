package com.gxf.his.service;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
public interface PermissionService {
    /**
     * 获取一组角色的权限字符串
     * @param roleIds
     * @return 权限字符串，用，分隔
     */
    List<String> findPermissionsByRoleId(List<Long> roleIds);
}
