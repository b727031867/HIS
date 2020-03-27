package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/26 18:29
 * 药品库存信息异常
 */
public class DrugStoreException extends BaseBusinessException {
    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public DrugStoreException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
