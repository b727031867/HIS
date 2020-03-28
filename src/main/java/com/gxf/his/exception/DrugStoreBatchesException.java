package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/28 09:19
 * 药品库存批次信息异常类
 */
public class DrugStoreBatchesException extends BaseBusinessException {
    /**
     * 给子类用的方法
     *
     * @param responseCodeEnum 统一响应枚举对象，包括各种情况的枚举
     */
    public DrugStoreBatchesException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
