package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@RestController("/user")
public class UserController {

    @PostMapping("/savePatient")
    public ServerResponseVO savePatient() {
        return null;
    }

    @PostMapping("/saveDoctor")
    public ServerResponseVO saveDoctor() {
        return null;
    }

    @PostMapping("/save")
    public ServerResponseVO save() {
        return null;
    }

    @PostMapping("/login")
    public ServerResponseVO login(@RequestParam(value = "username") String userName,
                                  @RequestParam(value = "password") String password) {
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Subject userSubject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            // 登录验证
            userSubject.login(token);
            return ServerResponseVO.success();
        } catch (UnknownAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_NOT_EXIST);
        } catch (DisabledAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_IS_DISABLED);
        } catch (IncorrectCredentialsException e) {
            return ServerResponseVO.error(ServerResponseEnum.INCORRECT_CREDENTIALS);
        } catch (Throwable e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.ERROR);
        }
    }
}
