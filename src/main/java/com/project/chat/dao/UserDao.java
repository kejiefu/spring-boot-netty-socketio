package com.project.chat.dao;


import com.project.chat.entity.BaseEntity;
import com.project.chat.entity.UserEntity;

import java.util.List;

/**
 * Created by summer on 2017/5/5.
 */
public interface UserDao<T extends BaseEntity> extends BaseDao<UserEntity> {

    UserEntity findUserByUserName(String name);

    UserEntity findUserByToken(String accessToken);

    /**
     * 根据用户名查询 对应的人员
     */
    List<UserEntity> findUsersByName(String page, String name);
}
