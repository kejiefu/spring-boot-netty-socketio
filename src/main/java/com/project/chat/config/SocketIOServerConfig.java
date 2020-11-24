package com.project.chat.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.project.chat.entity.UserEntity;
import com.project.chat.service.UserSerivice;
import com.project.chat.utils.SessionUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author kejiefu
 * @Description TODO
 * @Date 2020/11/24 16:12
 * @Created by kejiefu
 */
@Configuration
public class SocketIOServerConfig {

    @Value("${im.server.host}")
    private String host;

    @Value("${im.server.port}")
    private Integer port;

    @Resource
    UserSerivice userSerivice;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setPingInterval(5000);
        config.setPingTimeout(3000);
        config.setWorkerThreads(100);

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);

        //设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
        config.setMaxFramePayloadLength(1024 * 1024);
        //设置http交互最大内容长度
        config.setMaxHttpContentLength(1024 * 1024);

        config.setAuthorizationListener(hd -> {

            String auth_token = hd.getSingleUrlParam("auth_token");

            if (StringUtil.isNullOrEmpty(auth_token)) {
                return false;
            }

            UserEntity userEntity = userSerivice.findUserByToken(auth_token);
            //同一个账号登录多次登录 关闭之前的连接
            if (userEntity != null && SessionUtils.USER_ID_SOCKET_MAP.containsKey(userEntity.getId())) {
                SocketIOClient socketIOClient = SessionUtils.USER_ID_SOCKET_MAP.get(userEntity.getId());
                socketIOClient.sendEvent("otherLogin");
                return false;
            }
            // 移动设备不能同时登录 (android ios) 待处理
            //是否拦截 socket.io 连接
            return userEntity == null ? false : true;
        });
        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }


}
