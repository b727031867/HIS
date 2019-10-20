package com.gxf.his.exception;

import com.gxf.his.enmu.ServerResponseEnum;

/**
 * 认证异常处理类
 * @author 龚秀峰
 * @date 2019-10-13
 */
public class UserException extends BaseBusinessException {

    public UserException(ServerResponseEnum responseCodeEnum) {
        super(responseCodeEnum);
    }


}
