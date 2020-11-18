package com.project.chat.service;


import com.project.chat.entity.BaseEntity;
import com.project.chat.entity.MessageEntity;


public interface ChatSerivice<T extends BaseEntity> extends BaseSerivice<MessageEntity> {

    void saveMessageData(T entity);

    void sendApnData();
}
