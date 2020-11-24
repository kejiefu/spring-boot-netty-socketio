package com.project.chat.controller;

import com.alibaba.fastjson.JSON;
import com.project.chat.entity.base.BaseEntity;
import com.project.chat.entity.UserEntity;
import com.project.chat.enums.ResultTypeEnum;
import com.project.chat.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

@Slf4j
public class BaseController<T extends BaseEntity> {

    @Autowired
    HttpSession session;

    public UserEntity getSessionUser(){
        return (UserEntity) session.getAttribute("username");
    }

    protected String retResultData(ResultTypeEnum type) {
        return JSON.toJSONString(new Result(type));
    }

    protected String retResultData(ResultTypeEnum type, Object data) {
        return JSON.toJSONString(new Result(type, data));
    }

    protected String retResultData(Integer code, String message) {
        return JSON.toJSONString(new Result(code, message));
    }

    protected String retResultData(Integer code, String message, Object data) {
        return JSON.toJSONString(new Result(code, message, data));
    }





}
