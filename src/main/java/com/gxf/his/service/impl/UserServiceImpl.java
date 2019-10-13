package com.gxf.his.service.impl;

import com.gxf.his.po.User;
import com.gxf.his.mapper.UserMapper;
import com.gxf.his.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String userName){
        return  userMapper.selectByUserName(userName);
    }
}
