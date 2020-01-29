package com.gxf.his.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Shiro配置类
 *
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 配置核心安全事务管理器
     *
     * @param shiroRealm shiro域：负责安全信息的DAO
     * @return SecurityManager 安全事务管理器
     */
    @Bean(name = "MySecurityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm shiroRealm, RedisTemplate<String, Object> template) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(shiroRealm);
        //配置记住我
        //securityManager.setRememberMeManager(rememberMeManager());
        //配置 redis缓存管理器
        //securityManager.setCacheManager(getEhCacheManager());
        //配置自定义session管理，使用redis
        //securityManager.setSessionManager(sessionManager());
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 设置自定义的Shiro Cache
        shiroRealm.setCacheManager(new CustomCacheManager(template));
        return securityManager;
    }

    /**
     * 不需要在此处配置权限页面,因为的ShiroFilterFactoryBean已经配置过,
     * 但是此处必须存在,因为shiro-spring-boot-web-starter会查找此Bean,没有会报错
     *
     * @return DefaultShiroFilterChainDefinition
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        return new DefaultShiroFilterChainDefinition();
    }

//------------------------------------------注解支持开始------------------------------------------------

    /**
     * 扫描上下文，寻找所有的Advisor(通知器）
     * 将这些Advisor应用到所有符合切入点的Bean中。
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * Shiro生命周期处理器,管理Shiro中一些bean的生命周期
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 匹配所有加了 Shiro 认证注解的方法
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    //------------------------------------------注解支持结束------------------------------------------------

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截
     *
     * @param securityManager 安全事务管理器
     * @return ShiroFilterFactoryBean 过滤器工厂
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier(
            "MySecurityManager") DefaultWebSecurityManager securityManager,
                                              @Qualifier("RedisClient") RedisClient redisClient) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //这里的/login是后台的接口名,非页面，如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        //这里的/index是后台的接口名,非页面,登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("");
        //未授权界面,该配置无效，并不会进行页面跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        Map<String, Filter> filterMap = new HashMap<>(16);
        // 添加自定义的ShiroFilter过滤器且命名为jwt
        Map<String, String> filterRuleMap = new HashMap<>();
        //添加把JWT过滤器添加到Shiro过滤器,这里单独new一个是为了防止注入的JwtFilter覆盖ShiroFilter
        filterMap.put("jwt", new JwtFilter(redisClient));
        //设置过滤器
        shiroFilterFactoryBean.setFilters(filterMap);
        //必须设置 SecurityManager,Shiro的核心安全接口
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置认证失败的路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");
        //排除401路径，ShiroFilter将不做过滤的操作
        // 允许通过的接口
        filterRuleMap.put("/user/login", "anon");
        filterRuleMap.put("/user/checkUsername", "anon");
        filterRuleMap.put("/user/save", "anon");
        filterRuleMap.put("/user/registerPatient", "anon");
        filterRuleMap.put("/druid/*", "anon");
        filterRuleMap.put("/401", "anon");
        // 所有的请求通过ShiroFilter执行处理
        filterRuleMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 此bean的注入必须在shiroFilterFactoryBean之后，否则会导致：
     * invalid application configuration
     */
    @Bean("jwtFilter")
    public JwtFilter jwtFilterBean() {
        return new JwtFilter();
    }


//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        // 散列算法, 与注册时使用的散列算法相同
//        hashedCredentialsMatcher.setHashAlgorithmName(Const.ENCRYPTION);
//        // 散列次数, 与注册时使用的散列册数相同
//        hashedCredentialsMatcher.setHashIterations(Const.ENCRYPTION_NUM);
//        // 是否生成16进制, 与注册时的生成格式相同
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(Const.DECIMAL);
//        return hashedCredentialsMatcher;
//    }
//
//    @Bean
//    public UserRealm userRealm() {
//        UserRealm userRealm = new UserRealm();
//        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        return userRealm;
//    }
//
//    @Bean("defaultSecurityManager")
//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(userRealm());
//        return securityManager;
//    }
//
//    /**
//     * 路径过滤规则
//     * @param securityManager 安全管理器
//     * @return 过滤器工厂
//     */
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//        shiroFilterFactoryBean.setLoginUrl("/user/login");
//        shiroFilterFactoryBean.setSuccessUrl("");
//        // 有先后顺序
//        Map<String, String> map = new LinkedHashMap<>();
//        // 允许匿名访问
//        map.put("/user/login", "anon");
//        // 允许通过的测试接口
//        map.put("/user/save", "anon");
//        map.put("/user/savePatient", "anon");
//        map.put("/user/saveDoctor", "anon");
//
//        // 允许访问Druid的后台管理系统
//        map.put("/druid/**", "anon");
//        // 进行身份认证后才能访问
//        map.put("/**", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//        return shiroFilterFactoryBean;
//    }
//
//    /**
//     * 开启Shiro注解模式，可以在Controller中的方法上添加注解
//     * @param securityManager 安全管理器
//     * @return 注解属性处理器
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("defaultSecurityManager") DefaultSecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }

}
