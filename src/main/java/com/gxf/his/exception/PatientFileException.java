package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/17 00:56
 */
public class PatientFileException extends BaseBusinessException {

    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public PatientFileException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
