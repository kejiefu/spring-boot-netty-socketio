package com.project.chat.controller;


import com.project.chat.entity.MessageEntity;
import com.project.chat.enums.ResultTypeEnum;
import com.project.chat.service.ChatSerivice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping(value = "/chat")
public class ChatController extends BaseController<MessageEntity> {

    @Resource
    ChatSerivice chatSerivice;

    @ResponseBody
    @RequestMapping(value = "/t")
    public String test() {
        return retResultData(ResultTypeEnum.SUCCESS, "ks");
    }

    @ResponseBody
    @RequestMapping(value = "/t4")
    public String tests() {
        chatSerivice.sendApnData();
        return retResultData(ResultTypeEnum.SUCCESS);
    }


}
