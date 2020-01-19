package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
public class CashierException extends BaseBusinessException {
    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public CashierException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
