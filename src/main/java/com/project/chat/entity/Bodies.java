package com.project.chat.entity;

import com.project.chat.entity.base.BaseEntity;
import com.project.chat.enums.BodyTypeEnum;
import lombok.Data;

/**
 * @author kejiefu
 */
@Data
public class Bodies extends BaseEntity {

    private BodyTypeEnum type;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * imageUrl
     */
    private String imgUrl;

    private String imageName;

    /**
     * 缩略图
     */
    private String thumbnailRemotePath;

    /**
     * 原始路径
     */
    private String originImagePath;

    /**
     * 位置
     */
    private double latitude;

    private double longitude;

    private String locationName;

    private String detailLocationName;

    private Byte[] fileData;

    private String fileName;

    private String fileRemotePath;

    private int duration;


}
