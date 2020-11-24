package com.project.chat.service.impl;


import com.project.chat.dao.BaseDao;
import com.project.chat.entity.base.BaseEntity;
import com.project.chat.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseSeriviceImpl<T extends BaseEntity> implements BaseService<T> {

    @Qualifier("userDaoImpl")
    @Autowired
    BaseDao<T> baseDao;

    protected Class<T> entityClass;

    public BaseSeriviceImpl() {
        // 使用反射技术得到T的真实类型
        // 获取当前new的对象的 泛型的父类 类型
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        // 获取第一个类型参数的真实类型
        this.entityClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public T getEntityById(String id) {
        return baseDao.findEntityById(id);
    }

    @Override
    public void saveEntity(T entity) {
        baseDao.saveEntity(entity);
    }

    @Override
    public T updateEntityById(String id, T entity) {
        return baseDao.updateEntityById(id, entity);
    }

    @Override
    public long deleteEntityById(String id) {
        return baseDao.deleteEntityById(id);
    }

    @Override
    public List<T> selectAll() {
        return baseDao.selectAll();
    }
}
