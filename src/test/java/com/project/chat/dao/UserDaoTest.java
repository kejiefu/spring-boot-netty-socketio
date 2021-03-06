package com.project.chat.dao;

import com.google.protobuf.InvalidProtocolBufferException;
import com.project.chat.ApplicationTests;
import com.project.chat.dto.GpsData;
import com.project.chat.entity.GroupEntity;
import com.project.chat.entity.UserEntity;
import com.project.chat.service.GroupSerivice;
import com.project.chat.service.UserSerivice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * @author kejiefu
 * @Description TODO
 * @Date 2020/11/27 17:16
 * @Created by kejiefu
 */
@Slf4j
public class UserDaoTest extends ApplicationTests {

    @Autowired
    private UserSerivice userSerivice;

    @Autowired
    private GroupSerivice groupSerivice;

    @Autowired
    protected HttpSession session;

    @Test
    public void testSaveUser() throws Exception {

        UserEntity user = userSerivice.register("ke", "123456", "http://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg");
        System.out.println("已生成ID：" + user.getId());
        user = userSerivice.register("jie", "123456", "http://tva4.sinaimg.cn/crop.0.1.1125.1125.180/475bb144jw8f9nwebnuhkj20v90vbwh9.jpg");
        System.out.println("已生成ID：" + user.getId());
        user = userSerivice.register("fu", "123456", "http://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg");
        System.out.println("已生成ID：" + user.getId());

        createGroup();

    }

    public void createGroup() throws Exception {

        UserEntity user = userSerivice.findUserByName("ke");
        session.setAttribute("username", user);

        GroupEntity entity = groupSerivice.creatGroup("前端群", "http://tva1.sinaimg.cn/crop.0.0.200.200.50/006q8Q6bjw8f20zsdem2mj305k05kdfw.jpg", user);
        System.out.println("已生成ID：" + entity.getId());

        entity = groupSerivice.creatGroup("后端群", "http://tva2.sinaimg.cn/crop.0.0.199.199.180/005Zseqhjw1eplix1brxxj305k05kjrf.jpg", user);
        System.out.println("已生成ID：" + entity.getId());

        entity = groupSerivice.creatGroup("UI设计", "http://tva2.sinaimg.cn/crop.0.8.751.751.180/961a9be5jw8fczq7q98i7j20kv0lcwfn.jpg", user);
        System.out.println("已生成ID：" + entity.getId());

    }


    @Test
    public void testProutobu() throws InvalidProtocolBufferException {
        //模拟将对象转成byte[]，方便传输
        GpsData.gps_data.Builder builder = GpsData.gps_data.newBuilder();
        builder.setId(1);
        builder.setDataTime("2018-07-03");
        GpsData.gps_data bd = builder.build();
        System.out.println("before :" + bd.toString());

        System.out.println("===========Person Byte==========");
        for (byte b : bd.toByteArray()) {
            System.out.print(b);
        }
        System.out.println();
        System.out.println(bd.toByteString());
        System.out.println("================================");

        //模拟接收Byte[]，反序列化成Person类
        byte[] byteArray = bd.toByteArray();
        GpsData.gps_data gps_data = GpsData.gps_data.parseFrom(byteArray);
        System.out.println("after :" + gps_data.toString());
    }


}