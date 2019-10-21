package com.gxf.his.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gxf.his.Const;
import com.gxf.his.uitls.JwtUtil;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>Title: 实现自定义的Shiro的过滤器</p>
 * <p>Description: </p>
 * <p>Company: www.gxf727031867.top</p>
 *
 * @author 龚秀峰
 * @date 2019-8-25
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    /**
     * 判断Token头是否为空
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getHeader("Authorization") != null;
    }

    /**
     * 在过滤器中无法直接注入bean，此时bean还未实例化，因此在ShiroFilterBeanFactory中进行构造注入
     */
    private RedisClient redis;

    public JwtFilter() {
    }

    /**
     * 构造注入工具类
     * @param redisClient Redis客户端
     */
    public JwtFilter(RedisClient redisClient) {
        this.redis = redisClient;
    }

    /**
     * 首先调用的一个方法，在该方法内进行主要的认证逻辑处理，如判断Token头是否为空，解密Token等
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断用户是否想要登陆
        if (isLoginAttempt(request, response)) {
            String msg;
            try {
                this.executeLogin(request, response);
                return true;
            } catch (ExpiredCredentialsException e) {
                //凭证过期后，进行续签，如果refreshToken也过期，则需要重新登陆
                if (this.refreshToken(request, response)) {
                    return true;
                } else {
                    msg = "refreshToken已过期或accessToken过期后已经颁发了最新的accessToken，此accessToken作废！";
                }
            } catch (UnsupportedTokenException e) {
                msg = e.getMessage();
                logger.warn("JWT签名校验失败:" + e.getMessage());
            } catch (Exception e) {
                msg = e.getMessage();
            }
            this.response401(response, msg);
            return false;
        }
        logger.debug("Authorization字段为空！禁止访问");
        return false;
    }

    /**
     * 调用Realm执行登录，并返回登录认证的结果
     */
    @Override
    protected boolean executeLogin(ServletRequest request,
                                   ServletResponse response) throws AuthenticationException,
            TokenExpiredException {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        JwtToken token = new JwtToken(this.getAuthzHeader(request));
        // 提交给myRealm进行认证
        this.getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 重定向，返回HTTP 401错误
     */
    private boolean abort401(ServletResponse response) {
        try {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/401");
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 将executeLogin方法调用去除
     * 防止循环调用doGetAuthenticationInfo方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        this.sendChallenge(request, response);
        return false;
    }


    /**
     * 无需重定向，直接返回响应信息
     */
    private void response401(ServletResponse resp, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            ServerResponseVO returnMsg = new ServerResponseVO<>();
            returnMsg.setCode(401);
            returnMsg.setMessage(msg);
            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithoutExposeAnnotation();
            Gson gson = builder.create();
            String data = gson.toJson(returnMsg);
            out.append(data);
        } catch (IOException e) {
            logger.error("返回响应信息出现IOException异常" + e.getMessage());
        }
    }

    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        // 获取当前Token的用户名
        String username = JwtUtil.getUsername(token);
        // 获取当前AccessToken的时间戳
        String assessTokenMillis =
                JwtUtil.getSignTime(token);
        if (assessTokenMillis == null || username == null || token == null) {
            logger.debug("token或用户名或时间戳为空");
            return false;
        }
        try {
            // 判断Redis中RefreshToken是否存在
            if (redis.hasKey(Const.REDIS_CONSTANT_REFRESH_TOKEN_PREFIX + username)) {
                // Redis中RefreshToken还存在，获取RefreshToken的时间戳
                String currentTimeMillisRedis =
                        redis.get(Const.REDIS_CONSTANT_REFRESH_TOKEN_PREFIX + username).toString();
                if (currentTimeMillisRedis == null) {
                    logger.debug("获取RefreshToken的key检查通过，但是取值时，它刚好过期，此时应该重新登陆");
                    return false;
                }
                /*
                 * 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，刷新过期的AccessToken
                 */
                if (Long.parseLong(assessTokenMillis) == (Long.parseLong(currentTimeMillisRedis))) {
                    // 获取当前最新时间戳
                    String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                    // 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
                    redis.set(Const.REDIS_CONSTANT_REFRESH_TOKEN_PREFIX + username,
                            currentTimeMillis,
                            Integer.parseInt(redis.getRefreshTokenExpireTime()));
                    // 刷新AccessToken，设置时间戳为当前最新时间戳
                    token = JwtUtil.sign(username, currentTimeMillis);
                    // 将新刷新的AccessToken再次进行Shiro的登录
                    JwtToken jwtToken = new JwtToken(token);
                    /*
                     * 提交给myRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
                     */
                    this.getSubject(request, response).login(jwtToken);
                    // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setHeader("Authorization", token);
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                    return true;
                    //如果是过期的accessToken，并且其时间戳已经与最新的refreshToken时间戳不符，则表示此token作废，请求失效。
                } else if (Long.parseLong(assessTokenMillis) < (Long.parseLong(currentTimeMillisRedis))) {
                    logger.debug("此accessToken已经完全过期，请重新登陆获取最新的accessToken");
                    return false;
                } else {
                    logger.warn("出现accessToken的时间大于【晚于】refreshToken的时间！具体数据为：assessTokenMillis" +
                            "---" + assessTokenMillis + "   currentTimeMillisRedis---" + currentTimeMillisRedis);
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("系统异常：" + e.getMessage());
        }
        //refreshToken过期
        return false;
    }

}
