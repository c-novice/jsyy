package com.lzq.jsyy.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 * @author lzq
 */
@Getter
public enum ResultCodeEnum {
    // 返回结果状态枚举
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    PARAM_ERROR(202, "参数不正确"),
    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    USER_REPEAT(205, "该用户已存在"),
    DELETE_FAIL(206, "删除失败"),
    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    CODE_ERROR(210, "验证码错误"),
    LOGIN_USER_ERROR(211, "账号或密码错误"),
    LOGIN_PHONE_ERROR(212, "该手机号未注册"),
    REGISTER_PHONE_ERROR(213, "手机号已被使用"),
    LOGIN_ERROR_MULTI(214, "最近登录失败次数过多，请稍后再试"),
    LOGIN_ACL(215, "没有权限"),
    PERMISSION_ADD_ERROR(216, "添加权限失败"),
    PERMISSION_EXIST(217, "该权限已存在"),
    FACILITY_ADD_ERROR(218, "添加设施失败"),
    FACILITY_CHANGE_ERROR(219, "修改设施失败"),
    ROOM_ADD_ERROR(220, "添加房间失败"),
    ROOM_CHANGE_ERROR(221, "修改房间失败"),
    SCHEDULE_ADD_ERROR(222, "添加排班失败"),
    SCHEDULE_CHANGE_ERROR(223, "修改排班失败");


    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
