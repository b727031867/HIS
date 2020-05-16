package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.ServerResponseVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author 龚秀峰
 * @date 2019-10-18
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理其他未知异常
     *
     * @param e 异常
     * @return 统一异常响应信息
     */
    @ExceptionHandler({Exception.class})
    public ServerResponseVO globalExceptionHandler(Exception e) {
        this.logger.error("未知异常：", e);
        return ServerResponseVO.error(ServerResponseEnum.ERROR);
    }


    /**
     * 认证异常处理
     *
     * @param e 认证的异常对象
     * @return 响应的数据
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ServerResponseVO unAuthorizedExceptionHandler(UnauthorizedException e) {
        return ServerResponseVO.error(ServerResponseEnum.UNAUTHORIZED);
    }


    /**
     * 处理所有业务异常
     *
     * @param e 异常
     * @return 业务异常响应信息
     */
    @ExceptionHandler({BaseBusinessException.class})
    public ServerResponseVO businessExceptionHandler(BaseBusinessException e) {
        this.logger.error("业务异常：", e);
        return ServerResponseVO.error(ServerResponseEnum.BUSINESS_EXCEPTION);
    }

    /**
     * 处理处方单业务异常
     *
     * @param e 异常
     * @return 业务异常响应信息
     */
    @ExceptionHandler({PrescriptionException.class})
    public ServerResponseVO businessExceptionHandler(PrescriptionException e) {
        this.logger.error("处方单业务异常：", e);
        return ServerResponseVO.error(e.getServerResponseEnum());
    }

    /**
     * 处理药品业务异常
     *
     * @param e 异常
     * @return 业务异常响应信息
     */
    @ExceptionHandler({DrugException.class})
    public ServerResponseVO businessExceptionHandler(DrugException e) {
        this.logger.error("药品业务异常：", e);
        return ServerResponseVO.error(e.getServerResponseEnum());
    }

    /**
     * 处理订单业务异常
     *
     * @param e 异常
     * @return 业务异常响应信息
     */
    @ExceptionHandler({OrderException.class})
    public ServerResponseVO businessExceptionHandler(OrderException e) {
        this.logger.error("订单业务异常：", e);
        return ServerResponseVO.error(e.getServerResponseEnum());
    }


}
