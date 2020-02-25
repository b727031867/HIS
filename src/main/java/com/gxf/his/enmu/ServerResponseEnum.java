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
    USER_DELETE_FAIL(21,"用户删除失败"),
    USER_REPEAT_ERROR(22,"用户名已存在"),
    PATIENT_SAVE_FAIL(40,"病人保存失败"),
    PATIENT_DELETE_FAIL(41,"病人删除失败"),
    PATIENT_LIST_FAIL(42,"病人查询失败"),
    PATIENT_UPDATE_FAIL(43,"病人信息更新失败"),
    PATIENT_NO_EXITS(44,"病人不存在"),
    DOCTOR_SAVE_FAIL(50,"医生保存失败"),
    DOCTOR_LIST_FAIL(51,"医生查询失败"),
    DOCTOR_UPDATE_FAIL(52,"医生信息更新失败"),
    DOCTOR_DELETE_FAIL(53,"医生删除失败"),
    DOCTOR_CALL_FAIL(54,"医生叫号失败"),
    DOCTOR_GET_RANK_FAIL(55,"医生获取候诊总数和候诊排名失败!"),
    DOCTOR_GET_TOTAL_RANK_FAIL(56,"获取此医生候诊总数失败!"),
    PERMISSION_SELECT_FAIL(60,"权限查询失败"),
    DEPARTMENTS_NOT_EXIST(70,"暂无此类型的科室"),
    DEPARTMENT_SAVE_FAIL(71,"科室保存失败"),
    DEPARTMENT_DELETE_FAIL(72,"科室删除失败"),
    DEPARTMENT_LIST_FAIL(73,"科室查询失败"),
    SCHEDULING_SAVE_FAIL(80,"排班信息保存失败"),
    SCHEDULING_DELETE_FAIL(81,"排班信息删除失败"),
    SCHEDULING_UPDATE_FAIL(82,"排班信息更新失败"),
    SCHEDULING_SELECT_NONE(83,"排班信息查找结果为空"),
    SCHEDULING_SELECT_FAIL(83,"排班信息查找失败"),
    CASHIER_SAVE_FAIL(100,"收银员保存失败"),
    CASHIER_DELETE_FAIL(101,"收银员删除失败"),
    CASHIER_LIST_FAIL(102,"收银员查询失败"),
    CASHIER_UPDATE_FAIL(103,"收银员信息更新失败"),
    TICKET_RESOURCE_SAVE_FAIL(110,"票务资源保存失败"),
    TICKET_RESOURCE_DELETE_FAIL(111,"票务资源删除失败"),
    TICKET_RESOURCE_LIST_FAIL(112,"票务资源查询失败"),
    TICKET_RESOURCE_UPDATE_FAIL(113,"票务资源信息更新失败"),
    TICKET_RESOURCE_EXPIRATION_DATE_FAIL(114,"票务资源过期日期查询失败"),
    ORDER_SAVE_FAIL(120,"订单保存失败"),
    ORDER_DELETE_FAIL(121,"订单删除失败"),
    ORDER_LIST_FAIL(122,"订单查询失败"),
    ORDER_UPDATE_FAIL(123,"订单信息更新失败"),
    ORDER_REPEAT_FAIL(123,"不能重复下单"),
    ORDER_PAY_FAIL(124,"订单付款失败"),
    TICKET_SAVE_FAIL(130,"挂号信息保存失败"),
    TICKET_DELETE_FAIL(131,"挂号信息删除失败"),
    TICKET_LIST_FAIL(132,"挂号信息查询失败"),
    TICKET_UPDATE_FAIL(133,"挂号信息更新失败"),
    TICKET_QUEUE_FAIL(133,"挂号排队失败"),
    TICKET_QUEUE_NULL(134,"当前暂无挂号"),
    TICKET_CHANGE_STATUS_FAIL(135,"挂号状态修改失败！"),
    PATIENT_FILE_SAVE_FAIL(140,"病历保存失败"),
    PATIENT_FILE_DELETE_FAIL(141,"病历删除失败"),
    PATIENT_FILE_LIST_FAIL(142,"病历查询失败"),
    PATIENT_FILE_UPDATE_FAIL(143,"病历信息更新失败"),
    PATIENT_FILE_NO_EXITS(144,"病历不存在"),
    DOCTOR_MEDICAL_TEMPLATE_SAVE_FAIL(150,"模板保存失败"),
    DOCTOR_MEDICAL_TEMPLATE_DELETE_FAIL(151,"模板删除失败"),
    DOCTOR_MEDICAL_TEMPLATE_LIST_FAIL(152,"模板查询失败"),
    DOCTOR_MEDICAL_TEMPLATE_UPDATE_FAIL(153,"模板信息更新失败"),
    DOCTOR_MEDICAL_TEMPLATE_RENDER_FAIL(154,"模板渲染失败"),
    WORD_CONVERSION_EXCEPTION(300,"文档转换异常"),
    WORD_CONVERSION_IO_EXCEPTION(301,"解析【docx】文档为html时，出现IO异常");
    Integer code;
    String message;
}
