package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/20 09:37
 * word文档和html互相转化的异常类
 */
public class WordToHtmlException extends BaseBusinessException {
    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public WordToHtmlException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
