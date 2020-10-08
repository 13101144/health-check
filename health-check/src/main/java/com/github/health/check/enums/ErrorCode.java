package com.github.health.check.enums;



public enum ErrorCode {

    CLIENT_INVALID_PARAM(400, "参数错误"),
    NO_PERMISSION(403, "鉴权失败"),
    NO_FOUND(403,"没有找到"),
    SERVER_ERROR(500,"服务器内部错误"),
    INVALID_USERNAME_OR_PASSWOD(20000,"无效的用户名或密码"),
    USERNAME_HAS_EXIST(20001,"用户名已存在"),
    USER_NOT_FOUND(20002,"用户没有找到"),
    PROJECT_NOT_FOUND(30001,"工程不存在");
    private int code;

    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
