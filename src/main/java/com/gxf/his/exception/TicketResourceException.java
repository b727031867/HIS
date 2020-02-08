package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * 票务资源的异常处理类
 * @author GXF
 */
public class TicketResourceException extends BaseBusinessException {
    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public TicketResourceException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
