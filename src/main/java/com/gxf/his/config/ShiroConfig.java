package com.gxf.his.config;


import com.gxf.his.Const;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Configuration
public class ShiroConfig {

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法, 与注册时使用的散列算法相同
        hashedCredentialsMatcher.setHashAlgorithmName(Const.ENCRYPTION);
        // 散列次数, 与注册时使用的散列册数相同
        hashedCredentialsMatcher.setHashIterations(Const.ENCRYPTION_NUM);
        // 是否生成16进制, 与注册时的生成格式相同
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(Const.DECIMAL);
        return hashedCredentialsMatcher;
    }

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    @Bean("defaultSecurityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 路径过滤规则
     * @param securityManager 安全管理器
     * @return 过滤器工厂
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/user/login");
        shiroFilterFactoryBean.setSuccessUrl("");
        // 有先后顺序
        Map<String, String> map = new LinkedHashMap<>();
        // 允许匿名访问
        map.put("/user/login", "anon");
        // 允许通过的测试接口
        map.put("/user/save", "anon");
        map.put("/user/savePatient", "anon");
        map.put("/user/saveDoctor", "anon");

        // 允许访问Druid的后台管理系统
        map.put("/druid/**", "anon");
        // 进行身份认证后才能访问
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro注解模式，可以在Controller中的方法上添加注解
     * @param securityManager 安全管理器
     * @return 注解属性处理器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("defaultSecurityManager") DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
