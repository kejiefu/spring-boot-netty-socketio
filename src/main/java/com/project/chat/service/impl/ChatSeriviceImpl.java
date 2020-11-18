package com.project.chat.service.impl;


import com.project.chat.entity.BaseEntity;
import com.project.chat.entity.MessageEntity;
import com.project.chat.service.ChatSerivice;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;



@Component
public class ChatSeriviceImpl<T extends BaseEntity> extends BaseSeriviceImpl<MessageEntity> implements ChatSerivice<MessageEntity> {

    @Override
    public void saveMessageData(MessageEntity entity) {
        long time = System.currentTimeMillis();
        entity.setTimestamp(time);
        saveEntity(entity);
    }

    @Override
    public void sendApnData() {
        final ApnsClient apnsClient;
        try {
            apnsClient = new ApnsClientBuilder()
                    .setApnsServer(ApnsClientBuilder.PRODUCTION_APNS_HOST)
                    .setSigningKey(ApnsSigningKey.loadFromPkcs8File(new ClassPathResource("AuthKey.p8").getFile(),
                            "5HBP8N48W6", "SXZZL6BZ83"))
                    .build();
            final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
            payloadBuilder.setAlertBody("Example!");
            payloadBuilder.setBadgeNumber(1);
            payloadBuilder.setAlertBody("sdfsdfsfsddf0");
            final String payload = payloadBuilder.buildWithDefaultMaximumLength();
            final String token = "431b9699945a0fa11e692f9a281e3be5deec70ea61c8530aef7efbcc098b7e71";

            SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, "com.example.myApp", payload);
            final Future sendNotificationFuture = apnsClient.sendNotification(pushNotification);

            sendNotificationFuture.addListener(new GenericFutureListener<Future<PushNotificationResponse>>() {

                @Override
                public void operationComplete(final Future<PushNotificationResponse> future) throws Exception {
                    final PushNotificationResponse response = future.getNow();
                }
            });
        } catch (Exception e) {

        }

    }
}
