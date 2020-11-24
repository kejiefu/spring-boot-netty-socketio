package com.project.chat.dao.impl;

import com.project.chat.dao.UserDao;
import com.project.chat.entity.base.BaseEntity;
import com.project.chat.entity.UserEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class UserDaoImpl<T extends BaseEntity> extends BaseDaoImpl<UserEntity> implements UserDao<UserEntity> {

    /**
     * 根据用户名查询对象
     */
    @Override
    public UserEntity findUserByName(String name) {
        Query query = new Query(Criteria.where("username").is(name));
        UserEntity user = mongoTemplate.findOne(query, UserEntity.class);
        return user;
    }

    /**
     * 根据用户Token查询对象
     */
    @Override
    public UserEntity findUserByToken(String token) {
        Query query = new Query(Criteria.where("token").is(token));
        UserEntity user = mongoTemplate.findOne(query, UserEntity.class);
        return user;
    }

    /**
     * 根据用户名查询 对应的人员
     *
     * @return
     */
    @Override
    public List<UserEntity> findUsersByName(String page, String name) {
        List<UserEntity> list;
        if (StringUtils.isEmpty(name)) {
            list = mongoTemplate.findAll(UserEntity.class);
        } else {
            Query query = new Query(Criteria.where("String").regex(name));
            list = mongoTemplate.find(query, UserEntity.class);
        }
        return list;
    }

    @Override
    public List<UserEntity> selectAll() {
        List<UserEntity> userEntityList = mongoTemplate.findAll(UserEntity.class);
        return userEntityList;
    }


}
