package com.project.chat.utils;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtils {

    /**
     * 保存所有的 user ：socket 连接
     */
    public static Map<String, SocketIOClient> USER_ID_SOCKET_MAP = new ConcurrentHashMap<>();

}
