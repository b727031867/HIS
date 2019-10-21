package com.gxf.his.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.gxf.his.Const;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.PermissionException;
import com.gxf.his.po.Permission;
import com.gxf.his.po.Role;
import com.gxf.his.po.User;
import com.gxf.his.service.PermissionService;
import com.gxf.his.service.RoleService;
import com.gxf.his.service.UserService;
import com.gxf.his.uitls.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


//    /**
//     * Authorization 授权
//     *
//     * @param principalCollection 认证信息的集合
//     * @return 授权信息
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        User user = (User) principalCollection.getPrimaryPrincipal();
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        List<Role> roleList = roleService.findRolesByUserId(user.getUserId());
//        Set<String> roleSet = new HashSet<>();
//        List<Integer> roleIds = new ArrayList<>();
//        for (Role role : roleList) {
//            roleSet.add(role.getRoleName());
//            roleIds.add(role.getRoleId());
//        }
//        // 放入角色信息
//        authorizationInfo.setRoles(roleSet);
//        // 放入权限信息
//        List<String> permissionList = permissionService.findPermissionsByRoleId(roleIds);
//        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));
//        return authorizationInfo;
//    }
//
//    /**
//     * 认证
//     *
//     * @param authToken 认证信息的集合
//     * @return 授权信息
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
//        User user = userService.findByUserName(token.getUsername());
//        if (user == null) {
//            return null;
//        }
//        return new SimpleAuthenticationInfo(user, user.getUserPassword(), ByteSource.Util.bytes(user.getUserSalt()),
//                getName());
//    }


    @Override
    public String getName() {
        return "userRealm";
    }

    /**
     * 自定义日志 用于记录登录的用户和验证情况
     */
    private final static Logger log = LoggerFactory.getLogger("loginLog");

    @Autowired
    private RedisClient redis;

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        // 只支持JwtToken令牌类型
        return authenticationToken instanceof JwtToken;
    }


    /**
     * 定义如何获取用户的角色和权限的逻辑，给shiro做权限判断【授权处理】
     *
     * @param principals 凭据
     * @return 授权的权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null username are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        // 从principals中拿到Token令牌
        String username =
                JwtUtil.getUsername(principals.toString());
        User user =
                userService.findByUserName(username);
        if (user != null) {
            // 获取当前用户的所有角色
            List<Role> roles = roleService.findRolesByUserId(user.getUserId());
            // 添加用户拥有的角色
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            ArrayList<Integer> ids = new ArrayList<>();
            for (Role role : roles) {
                simpleAuthorizationInfo.addRole(role.getRoleName());
                ids.add(role.getRoleId());
            }
            try {
                List<String> permissions = permissionService.findPermissionsByRoleId(ids);
                //没有查到权限字符串
                if (permissions.size() < 1) {
                    log.warn("未授予此用户任何权限");
                    throw new AuthorizationException("该用户未拥有任何权限！");
                } else {
                    //添加角色对应的所有权限
                    for (String permission : permissions) {
                        simpleAuthorizationInfo.addStringPermission(permission);
                    }
                }
            } catch (Exception e) {
                throw new PermissionException(ServerResponseEnum.PERMISSION_SELECT_FAIL);
            }
            return simpleAuthorizationInfo;
        } else {
            log.warn("当前Token没有与之对应的用户");
            throw new AuthorizationException("非法Token！");
        }
    }

    /**
     * 定义如何获取用户信息的业务逻辑，给shiro做登录【认证处理】
     *
     * @param authenticationToken 用于认证Token
     * @return 有效的Token
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取自定义的token
        String token =
                (String) authenticationToken.getCredentials();
        //Token不能为空
        if (token == null) {
            throw new AuthenticationException(
                    "token不能为空");
        }
        // 解密Token，获取用户名
        String username = JwtUtil.getUsername(token);
        // Token解密失败，抛出异常
        if (username == null) {
            throw new AuthenticationException("Invalid token.");
        }
        return loginCheck(token, username);
    }

    private SimpleAuthenticationInfo loginCheck(String token, String username) {
        log.info("进行认证的Token是:" + token + "   当前登录的用户名是：" + username);
        try {
            // 开始认证，必须AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
            if (JwtUtil.verify(token) && redis.hasKey(Const.REDIS_CONSTANT_REFRESH_TOKEN_PREFIX + username)) {
                // 获取RefreshToken的时间戳
                String currentTimeMillisRedis = redis.get(Const.REDIS_CONSTANT_REFRESH_TOKEN_PREFIX + username).toString();
                // 获取accessToken的时间戳
                String accessTokenMillis = JwtUtil.getSignTime(token);
                if (currentTimeMillisRedis == null || accessTokenMillis == null) {
                    log.debug("【解密token中的时间戳失败】或【Redis中的缓存刚好过期，导致取值失败】");
                    throw new AuthorizationException("获取时间戳失败，请检查token合法性后重试！");
                } else {
                    // 获取AccessToken时间戳，与RefreshToken的时间戳对比
                    if (accessTokenMillis.equals(currentTimeMillisRedis)) {
                        //放行认证
                        return new SimpleAuthenticationInfo(token, token, getName());
                    } else {
                        log.debug("AccessToken和RefreshToken中的时间戳不一致");
                        throw new AuthorizationException("AccessToken和RefreshToken中的时间戳不一致");
                    }
                }
            } else {
                log.info("Redis中refreshToken的缓存过期");
                throw new AuthorizationException("会话过期，请重新登陆");
            }
        } catch (TokenExpiredException e) {
            log.info(e.getMessage());
            //凭证过期
            throw new ExpiredCredentialsException(e.getMessage());
        } catch (JWTVerificationException e) {
            log.info("accessToken其他认证异常：" + e.getMessage());
            throw new UnsupportedTokenException(e.getMessage());
        } catch (Exception e) {
            log.error("accessToken验证时系统发生的其他异常：" + e.getMessage());
        }
        //其他认证异常
        throw new AuthenticationException("未知的认证异常");
    }

}
