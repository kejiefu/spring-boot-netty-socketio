package com.project.chat.entity;

import com.project.chat.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "group")
@Data
public class GroupEntity extends BaseEntity {

    /**
     * 群id
     */
    @Id
    private String id;

    /**
     * 群的名字
     */
    private String groupname;

    /**
     * 创建的人 id
     */
    private String userId;

    /**
     * 创建人的名字，群主
     */
    private String userName;

    /**
     * 创建时间
     */
    private String creatTime;

    /**
     * 群的头像
     */
    private String avatar;


}
