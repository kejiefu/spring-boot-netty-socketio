package com.project.chat.dao;


import com.project.chat.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by summer on 2017/5/5.
 */
public interface BaseDao<T extends BaseEntity> {

    /**
     * 根据Id查询实体
     */
    T findEntityById(String id);

    /**
     * 新增实体
     */
    void saveEntity(T entity);

    /**
     * 更新实体
     */
    T updateEntityById(String id, T entity);

    /**
     * 根据Id删除实体
     */
    long deleteEntityById(String id);

    /**
     * 查询全部
     */
    List<T> selectAll();


}
