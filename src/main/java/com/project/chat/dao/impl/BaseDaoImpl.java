package com.project.chat.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.project.chat.dao.BaseDao;
import com.project.chat.entity.base.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by summer on 2017/5/5.
 */
public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected Class<T> entityClass;

    public BaseDaoImpl() {
        // 使用反射技术得到T的真实类型
        // 获取当前new的对象的 泛型的父类 类型
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        // 获取第一个类型参数的真实类型
        this.entityClass = (Class<T>) pt.getActualTypeArguments()[0];
    }


    @Override
    public T findEntityById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        T t = mongoTemplate.findOne(query, entityClass);
        return t;
    }

    @Override
    public void saveEntity(T entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public T updateEntityById(String id, T entity) {

        Query query = new Query(Criteria.where("id").is(id));

        Update update = new Update();

        JSONObject jsonObject = (JSONObject) JSON.toJSON(entity);

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (!"id".equals(entry.getKey())) {
                update.set(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }
        //更新查询返回结果集的第一条
        UpdateResult result = mongoTemplate.updateFirst(query, update, entityClass);

        return entity;
    }

    @Override
    public long deleteEntityById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        DeleteResult deleteResult = mongoTemplate.remove(query, entityClass);
        return deleteResult.getDeletedCount();
    }

    @Override
    public List<T> selectAll() {
        return mongoTemplate.findAll(entityClass);
    }
}
