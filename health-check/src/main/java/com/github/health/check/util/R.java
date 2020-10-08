package com.github.health.check.util;

import com.github.health.check.constant.CommonConstants;
import java.io.Serializable;


public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> R<T> success() {
        return newResult(null, CommonConstants.SUCCESS, CommonConstants.SUCCESS_MESSAGE);
    }

    public static <T> R<T> success(T data) {
        return newResult(data, CommonConstants.SUCCESS, CommonConstants.SUCCESS_MESSAGE);
    }

    public static <T> R<T> success(T data, String msg) {
        return newResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return newResult(null, CommonConstants.FAIL, CommonConstants.FAIL_MESSAGE);
    }

    public static <T> R<T> failed(String msg) {
        return newResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return newResult(data, CommonConstants.FAIL, CommonConstants.FAIL_MESSAGE);
    }

    public static <T> R<T> failed(T data, String msg) {
        return newResult(data, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> failed(T data, int code, String msg) {
        return newResult(data,code, msg);
    }

    private static <T> R<T> newResult(T data, int code, String msg) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

}
