package com.project.chat.model;


import com.project.chat.enums.ResultTypeEnum;

public class Result<T> {

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息提示
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultTypeEnum type, T data) {
        this.code = type.getCode();
        this.msg = type.getMsg();
        this.data = data;
    }

    public Result(ResultTypeEnum type) {
        this.code = type.getCode();
        this.msg = type.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
}
