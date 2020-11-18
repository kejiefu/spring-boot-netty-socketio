package com.project.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.chat.entity.UserEntity;
import com.project.chat.service.AddMessageSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/addMsg")
public class AddMessageController extends BaseController<UserEntity> {

    @Autowired
    AddMessageSerivice addMessageSerivice;

    /**
     * 询消息盒子信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/findAddInfo")
    public String findAddInfo(String page) {
        JSONObject obj = addMessageSerivice.findAddInfo(getSessionUser().getId());
        return retResultData(0, "", obj);
    }

}
