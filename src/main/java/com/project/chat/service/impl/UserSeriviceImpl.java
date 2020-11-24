package com.project.chat.service.impl;


import com.project.chat.dao.AddMessageDao;
import com.project.chat.dao.GroupDao;
import com.project.chat.dao.UserDao;
import com.project.chat.entity.base.BaseEntity;
import com.project.chat.entity.UserEntity;
import com.project.chat.exception.RepeatException;
import com.project.chat.service.UserSerivice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


@Component
public class UserSeriviceImpl<T extends BaseEntity> extends BaseSeriviceImpl<UserEntity> implements UserSerivice<UserEntity> {

    @Resource
    UserDao userDao;

    @Resource
    GroupDao groupDao;

    @Resource
    AddMessageDao addMessageDao;

    @Override
    public UserEntity findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public UserEntity findUserByToken(String token) {
        return userDao.findUserByToken(token);
    }

    /**
     * 注册
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    public UserEntity register(String name, String password, String avatar) {
        //用户名必须唯一
        if (userDao.findUserByName(name) != null) {
            throw new RepeatException(-1, "用户名不能重复");
        }

        UserEntity user = new UserEntity();
        user.setToken(UUID.randomUUID().toString());

        user.setUsername(name);
        user.setPassword(password);
        user.setAvatar(avatar);
        user.setSign("一路有你");
        userDao.saveEntity(user);
        return user;
    }

    /**
     * 根据用户名查询 对应的人员
     *
     * @return
     */
    @Override
    public List<UserEntity> findUsersByName(String page, String name) {
        return userDao.findUsersByName(page,name);
    }


}
