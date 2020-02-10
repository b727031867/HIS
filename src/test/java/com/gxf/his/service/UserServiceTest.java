package com.gxf.his.service;

import com.gxf.his.controller.UserController;
import com.gxf.his.po.generate.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 龚秀峰
 * @date 2020-1-5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void addUser() {
        String userName = "admin";
        String password = "admin";
        User user = UserController.doHashedCredentials(userName, password);
        userService.addUser(user);
    }
}
