package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ServerResponseVO unAuthorizedExceptionHandler(UnauthorizedException e) {
        return ServerResponseVO.error(ServerResponseEnum.UNAUTHORIZED);
    }
}
