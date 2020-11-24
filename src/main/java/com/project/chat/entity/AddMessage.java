package com.project.chat.entity;


import com.project.chat.entity.base.BaseEntity;
import com.project.chat.enums.AddMessageTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "add_message")
@Data
public class AddMessage extends BaseEntity {

    @Id
    private String id;

    private String fromUid;     //谁发起的请求

    private String toUid;       //发送给谁的申请,可能是群，那么就是创建该群组的用户

    private String groupId;     //创建人，群主

    private String remark;      //附言

    private String ext;     //扩展数据

    private AddMessageTypeEnum msgResult = AddMessageTypeEnum.Untreated;      //消息处理结果

    private String type;       //类型 ，可能是添加好友或群组 friend 好友， group 群组

    private String time;       //申请时间


}
