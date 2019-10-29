package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * @author 龚秀峰
 * @date 2019-10-29
 */
public class SchedulingException extends BaseBusinessException {
    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public SchedulingException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
