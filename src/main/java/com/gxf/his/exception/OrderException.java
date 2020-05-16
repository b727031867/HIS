package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author GXF
 * 订单异常类
 */
public class OrderException extends BaseBusinessException {

    @Getter
    @Setter
    private ServerResponseEnum serverResponseEnum;

    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public OrderException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
        this.serverResponseEnum = responseCodeEnum;
    }
}
