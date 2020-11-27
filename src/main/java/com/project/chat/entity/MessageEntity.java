package com.project.chat.entity;


import com.project.chat.entity.base.BaseEntity;
import com.project.chat.enums.ChatTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "message")
@Data
public class MessageEntity extends BaseEntity {


    /**
     * 消息id uuid
     */
    @Id
    private String id;

    /**
     * 发送人
     */
    private String fromUser;

    private String fromUserId;

    /**
     * 接收人
     */
    private String toUser;

    /**
     * 接收人id
     */
    private String toUserId;

    /**
     * 聊天的类型 chat(单聊)    groupChat (群聊)
     */
    private ChatTypeEnum chatTypeEnum;

    /**
     * 群聊 房间id (群聊时房间id)
     */
    private String groupId;

    /**
     * 群聊 房间id (群聊时房间name)
     */
    private String groupName;

    /**
     * 扩展数据
     */
    private String ext;

    /**
     * 内容 json 文本 存储
     * 类型1-->文本类型  { "type":"txt","msg":"hello from test2"}
     * 类型2-->图片类型  { "type":"img","imgUrl":"hello from test2","imageName","图片名称"  //消息内容}
     */
    private Bodies bodies;

    /**
     * (服务器)时间
     */
    private long createTime;

}
