package com.gxf.his.service.impl;

import com.gxf.his.controller.UserController;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.GlobalExceptionHandler;
import com.gxf.his.exception.UserException;
import com.gxf.his.po.User;
import com.gxf.his.mapper.UserMapper;
import com.gxf.his.service.UserService;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUserName(String userName) throws UserException{
        User user;
        try {
            user = userMapper.selectByUserName(userName);
        }catch (MyBatisSystemException e){
            logger.error("当前用户名查出多位用户："+e.getMessage());
            throw new UserException(ServerResponseEnum.USER_REPEAT_ERROR);
        }catch (Exception e){
            logger.error("根据用户名查询用户失败！",e);
            throw new UserException(ServerResponseEnum.USER_SELECT_FAIL);
        }
        return user;

    }

    @Override
    public int deleteUser(Long id) {
        try {
            return userMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            logger.error("根据ID删除用户失败！",e);
            throw new UserException(ServerResponseEnum.USER_DELETE_FAIL);
        }
    }

    @Override
    public Integer deleteUserBatch(List<User> users) {
        try {
            return userMapper.batchUserDelete(users);
        }catch (Exception e){
            throw new UserException(ServerResponseEnum.USER_DELETE_FAIL);
        }
    }

    @Override
    public Long addUser(User user) throws UserException {
        try{
            userMapper.insert(user);
        }catch (Exception e){
            logger.error("User插入失败!",e);
            throw new UserException(ServerResponseEnum.USER_SAVE_FAIL);
        }
        return user.getUserId();

    }

    @Override
    public Long updateUser(User user) {
        //重新加密密码,生成新密码盐和密钥
        String password = user.getUserPassword();
        //密码不为空才进行加密更新用户信息
        if(!"".equals(password.trim())) {
            User tempUser = UserController.doHashedCredentials(user.getUserName(), password);
            user.setUserPassword(tempUser.getUserPassword());
            user.setUserSalt(tempUser.getUserSalt());
            logger.info("当前更新的用户信息为：" + user.toString());
            try {
                userMapper.updateByPrimaryKey(user);
            }catch (Exception e){
                logger.error("User更新失败!",e);
                throw new UserException(ServerResponseEnum.USER_UPDATE_FAIL);
            }
        }else {
            logger.info("当前用户密码为空！");
        }
        return user.getUserId();
    }

}
