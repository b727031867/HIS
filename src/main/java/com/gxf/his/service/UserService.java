package com.gxf.his.service;

import com.gxf.his.po.generate.User;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */

public interface UserService {

    /**
     * 通过用户名查找用户
     *
     * @param userName 用户名
     * @return 用户对象
     */
    User findByUserName(String userName);

    /**
     * 根据UID查询不同用户关联的实体ID
     * @param uid 用户ID
     * @return 关联的实体ID
     */
    Long getLoginEntityId(Long uid);

    /**
     * 根据主键获取用户
     *
     * @param uid 用户ID
     * @return 用户对象
     */
    User getUserByPrimaryKey(Long uid);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 返回本次操作影响的行数
     */
    int deleteUser(Long id);

    /**
     * 批量删除用户
     *
     * @param users 要被删除的用户列表
     * @return 返回本次操作影响的行数
     */
    Integer deleteUserBatch(List<User> users);

    /**
     * 添加一位用户
     *
     * @param user 用户对象
     * @return 返回本次操作影响的行数
     */
    int addUser(User user);

    /**
     * 客户端更新密码
     * @param user 用户
     */
    Long updateUserPassword(User user);

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return 用户的ID
     */
    Long updateUser(User user);

}
