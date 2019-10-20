package com.gxf.his.service;

import com.gxf.his.po.User;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */

public interface UserService {

    /**
     * 通过用户名查找用户
     * @param userName 用户名
     * @return 用户对象
     */
    User findByUserName(String userName);

    /**
     * 添加一位用户
     * @param user 用户对象
     * @return 成功插入的行数
     */
    Integer addUser(User user);


}
