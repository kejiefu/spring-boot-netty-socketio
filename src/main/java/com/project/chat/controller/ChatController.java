package com.project.chat.controller;


import com.project.chat.entity.MessageEntity;
import com.project.chat.enums.EResultType;
import com.project.chat.service.ChatSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/chat")
public class ChatController extends BaseController<MessageEntity> {

    @Autowired
    ChatSerivice chatSerivice;

    @ResponseBody
    @RequestMapping(value = "/t")
    public String test() {
        return retResultData(EResultType.SUCCESS, "ks");
    }

    @ResponseBody
    @RequestMapping(value = "/t4")
    public String tests() {
        chatSerivice.sendApnData();
        return retResultData(EResultType.SUCCESS);
    }


}
