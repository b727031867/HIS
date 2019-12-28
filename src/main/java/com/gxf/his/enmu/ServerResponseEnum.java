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
    EXPIRED(7,"此Token已失效"),
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
    USER_UPDATE_FAIL(20,"用户更新失败"),
    PATIENT_SAVE_FAIL(40,"病人保存失败"),
    DOCTOR_SAVE_FAIL(50,"医生保存失败"),
    DOCTOR_LIST_FAIL(51,"医生查询失败"),
    DOCTOR_UPDATE_FAIL(52,"医生信息更新失败"),
    PERMISSION_SELECT_FAIL(60,"权限查询失败"),
    DEPARTMENTS_NOT_EXIST(70,"暂无此类型的科室"),
    DEPARTMENT_SAVE_FAIL(71,"科室保存失败"),
    DEPARTMENT_DELETE_FAIL(72,"科室删除失败"),
    SCHEDULING_SAVE_FAIL(80,"排班信息保存失败"),
    SCHEDULING_DELETE_FAIL(81,"排班信息删除失败"),
    SCHEDULING_UPDATE_FAIL(82,"排班信息更新失败"),
    SCHEDULING_SELECT_NONE(83,"排班信息查找结果为空"),
    SCHEDULING_SELECT_FAIL(83,"排班信息查找失败");
    Integer code;
    String message;
}
