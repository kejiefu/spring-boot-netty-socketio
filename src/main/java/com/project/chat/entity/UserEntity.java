package com.project.chat.entity;

import com.project.chat.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user")
@Data
public class UserEntity extends BaseEntity {


    @Id
    private String id;

    private String username;

    private String password;

    private String token;

    private String sign;

    private String avatar;

    private String nickname;

    /**
     * 性别 0 男，1，女 2，其他
     */
    private int sex;




}
