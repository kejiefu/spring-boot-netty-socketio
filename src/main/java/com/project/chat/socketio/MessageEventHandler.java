package com.project.chat.socketio;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import com.project.chat.dto.GpsData;
import com.project.chat.entity.*;
import com.project.chat.service.AddMessageSerivice;
import com.project.chat.service.ChatSerivice;
import com.project.chat.service.GroupSerivice;
import com.project.chat.service.UserSerivice;
import com.project.chat.utils.DateUtils;
import com.project.chat.utils.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MessageEventHandler {

    private final SocketIOServer server;

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    UserSerivice userSerivice;

    @Autowired
    ChatSerivice chatSerivice;

    @Autowired
    GroupSerivice groupSerivice;

    @Autowired
    AddMessageSerivice addMessageSerivice;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        this.server = server;
    }

    /**
     * 添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
     * 方便后面发送消息时查找到对应的目标client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        HandshakeData hd = client.getHandshakeData();
        String auth_token = hd.getSingleUrlParam("auth_token");
        UserEntity userEntity = userSerivice.findUserByToken(auth_token);
        String userId = userEntity.getId();
        String userName = userEntity.getUsername();
        client.set("userId", userId);
        client.set("userName", userName);
        SessionUtils.USER_ID_SOCKET_MAP.put(userId, client);

        //上线关联所在的群组
        List<GroupEntity> entityList = groupSerivice.findMyGroupsByUserId(userId);

        for (GroupEntity entity : entityList) {
            logger.info(userName + "自动关联了群 " + entity.getGroupName() + "   " + sdf.format(new Date()));
            client.joinRoom(entity.getId());
        }

        logger.info(userName + "---》上 === 线了  " + client.getSessionId() + "   " + sdf.format(new Date()));
    }

    /**
     * 添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        SessionUtils.USER_ID_SOCKET_MAP.remove(client.get("userId"));
        logger.info(client.get("userName") + "---------》下 线了 " + sdf.format(new Date()));
    }

    /**
     * 使用protobuf 测试传输的数据
     */
    @OnEvent(value = "protobufTest")
    public void onProtobufTest(SocketIOClient client, AckRequest ackRequest, byte[] data) throws InvalidProtocolBufferException, UnsupportedEncodingException {
        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(data);
        }
        GpsData.gps_data gps_data = GpsData.gps_data.parseFrom(data);
        System.out.println("after :" + gps_data.getTerminalId() + gps_data.getDataTime());
    }


    /**
     * 申请加入群组
     */
    @OnEvent(value = "addGroup")
    public void onEventJoin(SocketIOClient client, AckRequest ackRequest, AddMessage msg) {
        String id = msg.getToUid();
        //查询群下面的所有人  如果有当前群包含了 自己则说明是重复加群
        List<GroupUser> groupUsers = groupSerivice.findUsersByGroupId(msg.getGroupId());
        for (GroupUser user : groupUsers) {
            if (user.getUserId().equals(msg.getFromUid())) {
                ackRequest.sendAckData("请勿重复加群");
                logger.info("重复加群了。。。。。。兄弟");
                return;
            }
        }
        ackRequest.sendAckData("");
        addMessageSerivice.saveAddMessage(msg);
        if (!StringUtils.isEmpty(id) && SessionUtils.USER_ID_SOCKET_MAP.containsKey(id)) {
            SocketIOClient socketIOClient = SessionUtils.USER_ID_SOCKET_MAP.get(id);
            socketIOClient.sendEvent("addGroup");
        }
        logger.info(msg.toString());
    }

    /**
     * 拒绝加入群组
     */
    @OnEvent(value = "refuseAddGroup")
    public void refuseAddGroup(SocketIOClient client, AckRequest ackRequest, JSONObject object) {
        ackRequest.sendAckData("");
        String id = object.getString("toUid");
        addMessageSerivice.updateAddMessage(object.getString("messageBoxId"));
        if (!StringUtils.isEmpty(id) && SessionUtils.USER_ID_SOCKET_MAP.containsKey(id)) {
            SocketIOClient socketIOClient = SessionUtils.USER_ID_SOCKET_MAP.get(id);
            socketIOClient.sendEvent("refuseAddGroup");
        }
        logger.info(object.toString());
    }

    /**
     * 同意加入群组
     */
    @OnEvent(value = "agreeAddGroup")
    public void agreeAddGroup(SocketIOClient client, AckRequest ackRequest, JSONObject object) {
        String id = object.getString("toUid");
        String groupId = object.getString("groupId");
        UserEntity entity = (UserEntity) userSerivice.getEntityById(id);
        ackRequest.sendAckData("");
        addMessageSerivice.updateAddMessage(entity, groupId, object.getString("messageBoxId"));

        if (!StringUtils.isEmpty(id) && SessionUtils.USER_ID_SOCKET_MAP.containsKey(id)) {
            SocketIOClient socketIOClient = SessionUtils.USER_ID_SOCKET_MAP.get(id);
            socketIOClient.sendEvent("agreeAddGroup");
        }
        logger.info(object.toString());
    }


    /**
     * 离开群组(退群)
     */
    @OnEvent(value = "leave")
    public void onEventLeave(SocketIOClient client, AckRequest ackRequest, MessageEntity msg) {

    }

    /**
     * 消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
     */
    @OnEvent(value = "chat")
    public void onEvent(SocketIOClient client, AckRequest ackRequest, MessageEntity msg) {
        boolean isChat = msg.getChatTypeEnum().toString().equals("chat");
        //ack 返回数据 服务器收到发送的数据
        if (ackRequest.isAckRequested()) {
            if (msg.getFromUser().equals(msg.getToUser())) {
                ackRequest.sendAckData("请不要给自己发消息");
                return;
            }
            //将数据保存到服务器
            chatSerivice.saveMessageData(msg);

            String toName = "";
            if (isChat) {
                toName = msg.getToUser();
            } else {
                toName = "群： " + msg.getToUser();
            }
            logger.info("给 " + toName + " 发送的数据 服务器已经收到， 日期： " + sdf.format(new Date()));
            //发送ack回调数据到客户端
            ackRequest.sendAckData("");
        }
        String toUserId = msg.getToUserId(); //如果是 群聊，则对应群的id
        String toUser = msg.getToUser();
        if (isChat) {
            //单聊
            // 如果对方在线 则找到对应的client 给其发送消息
            if (SessionUtils.USER_ID_SOCKET_MAP.containsKey(toUserId)) {
                SessionUtils.USER_ID_SOCKET_MAP.get(toUserId).sendEvent("chat", new AckCallback<String>(String.class) {
                    //对方客户端接收到消息 返回给服务器端
                    @Override
                    public void onSuccess(String result) {
                        logger.info(toUser + "已收到消息 ， ack 回复 ： " + result + "    日期： " + sdf.format(new Date()));
                    }

                    //发送消息超时
                    @Override
                    public void onTimeout() {
                        System.out.println(toUser + "---------》发送消息超时 " + sdf.format(new Date()));
                    }
                }, msg);
            } else { //如果 下线 转apns 消息推送
                logger.info(toUser + "---------》需要转 apns 消息推送 " + sdf.format(new Date()));
            }
        } else {  //群聊
            logger.info("========================发送群消息==================================");
            // 房间（群内）广播
            server.getRoomOperations(toUserId).sendEvent("chat", msg, new BroadcastAckCallback<String>(String.class) {
                @Override
                protected void onClientTimeout(SocketIOClient client) {
                    logger.info("群消息: " + client.get("userName") + " 发送超时了");
                }

                @Override
                protected void onClientSuccess(SocketIOClient client, String result) {
                    logger.info("群消息: " + client.get("userName") + " 已接收到" + DateUtils.getDataTimeYMDHMSS());
                }
            });
        }

    }


}
