package com.gxf.his.service;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
public interface PermissionService {
    List<String> findByRoleId(List<Integer> roleIds);
}
