package com.gxf.his.enmu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@AllArgsConstructor
@Getter
public enum ServerResponseEnum {
    /**
     * 请求处理各种情况的枚举
     */
    SUCCESS(0, "成功"),
    BUSINESS_EXCEPTION(8,"业务异常"),
    PARAMETER_ERROR(9,"参数为空"),
    ERROR(10, "失败"),
    ACCOUNT_NOT_EXIST(11, "账号不存在"),
    DUPLICATE_ACCOUNT(12, "账号重复"),
    ACCOUNT_IS_DISABLED(13, "账号被禁用"),
    INCORRECT_CREDENTIALS(14, "账号或密码错误"),
    NOT_LOGIN_IN(15, "账号未登录"),
    UNAUTHORIZED(16, "没有权限"),
    REGISTERED_FAIL(17, "注册失败"),
    USER_SAVE_FAIL(18,"用户保存失败"),
    USER_SELECT_FAIL(19,"用户查询失败"),
    PATIENT_SAVE_FAIL(40,"病人保存失败"),
    DOCTOR_SAVE_FAIL(50,"医生保存失败");
    Integer code;
    String message;
}
