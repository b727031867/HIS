package com.gxf.his.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * <p>Title: 自定义Token类，实现getPrincipal()用于获得主体的标识属性，可以是用户名等；getCredentials()方法用于获得证明/凭证，如密码、证书等。</p>
 * <p>Description: </p>
 * <p>Company: www.gxf727031867.top</p>
 *
 * @author 龚秀峰
 * @date 2019-8-25
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
