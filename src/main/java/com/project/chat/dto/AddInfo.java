package com.project.chat.dto;

import com.project.chat.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddInfo implements Serializable {

    private String id;

    private String uid;

    private String content;

    private String from;

    private String fromGroup;

    private String type;

    private String remark;

    private String href;

    private String read;

    private String time;

    private UserEntity user;


}
