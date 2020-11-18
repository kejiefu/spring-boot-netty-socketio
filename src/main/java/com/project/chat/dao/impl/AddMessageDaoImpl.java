package com.project.chat.dao.impl;


import com.project.chat.dao.AddMessageDao;
import com.project.chat.entity.AddMessage;
import com.project.chat.entity.BaseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by summer on 2017/5/5.
 */
@Component
public class AddMessageDaoImpl<T extends BaseEntity> extends BaseDaoImpl<AddMessage> implements AddMessageDao<AddMessage> {

    /**
     * @description 查询消息盒子信息
     */
    @Override
    public List<AddMessage> findAddInfo(String userId) {
        Query query = new Query(Criteria.where("toUid").is(userId));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "time")));
        List<AddMessage> list = mongoTemplate.find(query,entityClass);
        return list;
    }


}
