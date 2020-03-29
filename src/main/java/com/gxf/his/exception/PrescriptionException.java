package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 23:08
 * 处方单异常类
 */
public class PrescriptionException extends BaseBusinessException {

    @Getter
    @Setter
    private ServerResponseEnum serverResponseEnum;

    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public PrescriptionException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
        this.serverResponseEnum = responseCodeEnum;
    }
}
