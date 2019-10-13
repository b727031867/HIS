package com.gxf.his.service;

import com.gxf.his.po.User;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */

public interface UserService {

    User findByUserName(String userName);

}
