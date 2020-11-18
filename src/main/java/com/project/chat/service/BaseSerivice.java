package com.project.chat.service;



import com.project.chat.entity.BaseEntity;

import java.util.List;


public interface BaseSerivice<T extends BaseEntity> {
    /**
     * 根据Id查询实体
     */
    T getEntityById(String id);

    /**
     * 新增实体
     */
    void saveEntity(final T entity);
    /**
     * 更新实体
     */
    T updateEntityById(String id, T entity);
    /**
     * 根据Id删除实体
     */
    int deleteEntityById(String id);

    /**
     * 查询所有
     */
    List<T> selectAll();

}
