package com.gxf.his.controller;

import com.gxf.his.Const;
import com.gxf.his.config.RedisClient;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.Doctor;
import com.gxf.his.po.Patient;
import com.gxf.his.po.User;
import com.gxf.his.service.DoctorService;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.UserService;
import com.gxf.his.uitls.JwtUtil;
import com.gxf.his.vo.DoctorUserVo;
import com.gxf.his.vo.PatientUserVo;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService service, PatientService pService,
                          RedisClient redis) {
        this.redis = redis;
        userService = service;
        patientService = pService;
    }

    private RedisClient redis;

    private UserService userService;

    private PatientService patientService;

    /**
     * 用户每隔多少时间需要重新登陆一次，单位：秒
     **/
    @Value("${config.refreshToken-expireTime}")
    private String refreshTokenExpireTime;

    @GetMapping("/checkUsername")
    public ServerResponseVO checkUsernameIsExists(@RequestParam(value = "userName") String userName) {
        if (StringUtils.isEmpty(userName.trim())) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if (userService.findByUserName(userName) != null) {
            return ServerResponseVO.error(ServerResponseEnum.USER_REPEAT_ERROR);
        }
        return ServerResponseVO.success(ServerResponseEnum.SUCCESS);
    }

    @PostMapping("/save")
    public ServerResponseVO save(User user) {
        if (StringUtils.isEmpty(user.getUserName().trim()) || StringUtils.isEmpty(user.getUserPassword().trim())) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if (userService.findByUserName(user.getUserName()) != null) {
            return ServerResponseVO.error(ServerResponseEnum.USER_REPEAT_ERROR);
        }
        user = doHashedCredentials(user.getUserName(), user.getUserPassword());
        if (user != null) {
            Long i = userService.addUser(user);
            if (i < 1) {
                logger.error("注册时，数据插入异常");
                return ServerResponseVO.error(ServerResponseEnum.REGISTERED_FAIL);
            }
        }

        return ServerResponseVO.success(ServerResponseEnum.SUCCESS);
    }

    @PostMapping("/registerPatient")
    public ServerResponseVO registerPatient(@RequestParam(value = "userName") String userName,
                                            @RequestParam(value = "userPassword") String password,
                                            @RequestParam(value = "patientName") String patientName) {
        if (StringUtils.isEmpty(userName.trim()) || StringUtils.isEmpty(password.trim())) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if (userService.findByUserName(password) != null) {
            return ServerResponseVO.error(ServerResponseEnum.USER_REPEAT_ERROR);
        }
        User user = doHashedCredentials(userName, password);
        Long id = userService.addUser(user);
        //病人关联用户
        Patient patient = new Patient();
        patient.setUserId(id);
        patient.setPatientName(patientName);
        patientService.addPatient(patient);
        return ServerResponseVO.success(ServerResponseEnum.SUCCESS);
    }

    @PostMapping("/login")
    public ServerResponseVO login(@RequestParam(value = "userName") String userName,
                                  @RequestParam(value = "userPassword") String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        ServerResponseVO msg = new ServerResponseVO();
        try {
            // 清除可能存在的shiro权限信息缓存
            if (redis.hasKey(Const.REDIS_CONSTANT_SHIRO_CACHE_PREFIX + userName)) {
                redis.del(Const.REDIS_CONSTANT_SHIRO_CACHE_PREFIX + userName);
            }
            //根据用户名查询用户
            User user =
                    userService.findByUserName(userName);
            if (user == null) {
                logger.info("用户名不存在：" + userName);
                throw new UnknownAccountException("用户名或密码错误！");
            }
            //账户是否有效,1有效，0无效
            if (user.getUserStatus() == 0) {
                logger.info("账户被锁定，账户的状态为：" + user.getUserStatus());
                throw new LockedAccountException("账号已被锁定,请联系管理员！");
            }
            //当前账户的盐
            logger.debug("查询到的用户的盐为：" + user.getUserSalt());
            //加密次数
            int hashIterations = Const.ENCRYPTION_NUM;
            //使用盐加密登陆密码 剩余
            SimpleHash sh = new SimpleHash(Const.ENCRYPTION, password,
                    user.getUserSalt(), hashIterations);
            //如果加密后和数据库中的密码一致，说明密码正确，签发token
            if (sh.toHex().equals(user.getUserPassword())) {
                HashMap<String, String> hashMap =
                        new HashMap<>(16);
                //设置RefreshToken，时间戳为当前时间戳，保存在Redis中
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                redis.set(Const.REDIS_CONSTANT_REFRESH_TOKEN_PREFIX + userName,
                        currentTimeMillis,
                        Integer.parseInt(refreshTokenExpireTime));
                //签发accessToken，时间戳为当前时间戳
                String token = JwtUtil.sign(userName, currentTimeMillis);
                hashMap.put("token", token);
                hashMap.put("id", user.getUserId().toString());
                msg.setData(hashMap);
                msg.setMessage("登录成功");
                msg.setCode(200);
            } else {
                logger.info("密码错误，登陆输入的密码加密后为：" + sh.toHex() + "   数据库中的密码:" + user.getUserPassword());
                throw new UnknownAccountException();
            }
            return msg;
        } catch (UnknownAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_NOT_EXIST);
        } catch (DisabledAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_IS_DISABLED);
        } catch (IncorrectCredentialsException e) {
            return ServerResponseVO.error(ServerResponseEnum.INCORRECT_CREDENTIALS);
        } catch (AuthenticationException e) {
            logger.error("认证异常！异常信息为：" + e.getMessage());
            msg.setMessage("用户名或密码错误");

        } catch (Throwable e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.ERROR);
        }
        return msg;
    }


    @GetMapping("/logout")
    public ServerResponseVO logout(HttpServletRequest request) {
        ServerResponseVO msg = new ServerResponseVO();
        try {
            String token = "";
            // 获取头部信息
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = request.getHeader(key);
                if ("Authorization".equalsIgnoreCase(key)) {
                    token = value;
                }
            }
            logger.debug("调用注销接口，进行注销的token是：" + token);
            // 校验token是否为空
            if (StringUtils.isBlank(token)) {
                logger.info("注销时，token为空");
                msg.setData("注销：Token为空");
            } else {
                String userName = JwtUtil.getUsername(token);
                logger.debug("注销时，token中获取的用户名为：" + userName);
                if (StringUtils.isBlank(userName)) {
                    msg.setData("token失效或不正确.");
                } else {
                    // 清除shiro权限信息缓存
                    if (redis.hasKey(Const.REDIS_CONSTANT_SHIRO_CACHE_PREFIX + userName)) {
                        redis.del(Const.REDIS_CONSTANT_SHIRO_CACHE_PREFIX + userName);
                    }
                    // 清除RefreshToken
                    redis.del(Const.REDIS_CONSTANT_REFRESH_TOKEN_PREFIX + userName);
                }
            }
            msg.setCode(200);
            msg.setMessage("注销成功");
        } catch (Exception e) {
            msg.setCode(500);
            e.printStackTrace();
            logger.error("注销异常： 原因为：" + e.getCause() + "信息为：" + e.getMessage());
        }
        return msg;
    }


    public static User doHashedCredentials(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        //默认注册的用户状态是正常状态
        user.setUserStatus(new Byte("1"));
        //使用密码和用户名和时间戳混合加密，生成盐
        ByteSource salt =
                ByteSource.Util.bytes(password + System.currentTimeMillis() + userName);
        String saltDeCode;
        String hashDeCode;
        //设置盐和密文的编码方式，并且使用盐加密密码
        if (Const.DECIMAL) {
            saltDeCode = salt.toHex();
            SimpleHash sh = new SimpleHash(Const.ENCRYPTION, password,
                    saltDeCode, Const.ENCRYPTION_NUM);
            hashDeCode = sh.toHex();
        } else {
            saltDeCode = salt.toBase64();
            SimpleHash sh = new SimpleHash(Const.ENCRYPTION, password,
                    saltDeCode, Const.ENCRYPTION_NUM);
            hashDeCode = sh.toBase64();
        }
        user.setUserSalt(saltDeCode);
        user.setUserPassword(hashDeCode);
        //设置当前注册时间
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ft.format(now);
        user.setUserCreateDate(now);
        user.setAppId(Const.APP_ID);
        return user;
    }

}
