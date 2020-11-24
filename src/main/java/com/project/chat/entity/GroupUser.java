package com.project.chat.entity;

import com.project.chat.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 群成员
 */
@Document(collection = "group_user")
@Data
public class GroupUser extends BaseEntity {

    @Id
    private String id;

    /**
     * 群 id
     */
    private String groupId;

    /**
     * 群成员的id
     */
    private String userId;

    /**
     * 群成员的名字
     */
    private String username;

    /**
     * 群成员的头像
     */
    private String avatar;

    /**
     * 签名
     */
    private String sign;

    /**
     * 加入时间
     */
    private String joinTime;


}