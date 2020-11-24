package com.project.chat.service;


import com.project.chat.entity.base.BaseEntity;
import com.project.chat.entity.MessageEntity;


public interface ChatSerivice<T extends BaseEntity> extends BaseService<MessageEntity> {

    void saveMessageData(T entity);

    void sendApnData();
}
