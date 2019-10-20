package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础异常类
 *
 * @author 龚秀峰
 * @date 2019-10-18
 */

public class BaseBusinessException extends RuntimeException {

    @Setter
    @Getter
    private Integer code;

    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public BaseBusinessException(ServerResponseEnum responseCodeEnum) {
        this(responseCodeEnum.getMessage(), responseCodeEnum.getCode());
    }

    private BaseBusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }


}
