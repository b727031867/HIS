package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.GlobalExceptionHandler;
import com.gxf.his.exception.UserException;
import com.gxf.his.po.User;
import com.gxf.his.mapper.UserMapper;
import com.gxf.his.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String userName) throws UserException{
        User user;
        try {
            user = userMapper.selectByUserName(userName);
        }catch (Exception e){
            logger.error("根据用户名查询用户失败！",e);
            throw new UserException(ServerResponseEnum.USER_SELECT_FAIL);
        }
        return user;

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
}
