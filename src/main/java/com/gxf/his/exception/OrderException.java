package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * @author GXF
 * 订单异常类
 */
public class OrderException extends BaseBusinessException {
    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public OrderException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
