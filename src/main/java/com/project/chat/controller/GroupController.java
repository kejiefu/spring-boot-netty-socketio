package com.project.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.chat.entity.GroupEntity;
import com.project.chat.entity.GroupUser;
import com.project.chat.entity.UserEntity;
import com.project.chat.service.GroupSerivice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/group")
public class GroupController extends BaseController<UserEntity> {

    @Resource
    GroupSerivice groupSerivice;


    /**
     * 创建群组
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/create")
    public String creatGroup(String name, String avatar) {
        GroupEntity groupEntity = groupSerivice.creatGroup(name, avatar, getSessionUser());
        return retResultData(0, "", groupEntity);
    }


    /**
     * 根据 名字 查询对应的群
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/findGroupsByName")
    public String findGroupsByName(String page, String name) {
        List<GroupEntity> list = groupSerivice.findGroupsByGroupName(name);
        return retResultData(0, "", list);
    }


    /**
     * 查询指定群下面的 群成员
     * id 群id
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/findGroupUsers")
    public String findGroupUsers(String id) {
        List<GroupUser> list = groupSerivice.findUsersByGroupId(id);
        for (GroupUser user : list) {
            user.setId(user.getUserId());
            user.setUserId("");
        }
        JSONObject obj = new JSONObject();
        obj.put("list", list);
        return retResultData(0, "", obj);
    }


}
