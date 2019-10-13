package com.gxf.his.config;

import com.gxf.his.po.Role;
import com.gxf.his.po.User;
import com.gxf.his.service.PermissionService;
import com.gxf.his.service.RoleService;
import com.gxf.his.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

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

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用md5 算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列次数： 意为加密几次
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * Authorization 授权
     *
     * @param principalCollection 认证信息的集合
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roleList = roleService.findRolesByUserId(user.getUserId());
        Set<String> roleSet = new HashSet<>();
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleSet.add(role.getRoleName());
            roleIds.add(role.getRoleId());
        }
        // 放入角色信息
        authorizationInfo.setRoles(roleSet);
        // 放入权限信息
        List<String> permissionList = permissionService.findByRoleId(roleIds);
        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param authToken 认证信息的集合
     * @return 授权信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        User user = userService.findByUserName(token.getUsername());
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getUserPassword(), ByteSource.Util.bytes(user.getUserSalt()),
                getName());
    }

}
