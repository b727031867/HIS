package com.gxf.his.service;

import com.gxf.his.po.User;

import java.util.List;

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
     * 删除用户
     * @param id 用户ID
     * @return 受影响的行数
     */
    int deleteUser(Long id);

    /**
     * 批量删除用户
     * @param users 要被删除的用户列表
     * @return 影响的行数
     */
    Integer deleteUserBatch(List<User> users);

    /**
     * 添加一位用户
     * @param user 用户对象
     * @return 成功插入的用户ID
     */
    Long addUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return 更新影响的用户ID
     */
    Long updateUser(User user);


}
