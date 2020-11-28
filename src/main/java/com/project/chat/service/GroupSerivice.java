package com.project.chat.service;



import com.project.chat.entity.base.BaseEntity;
import com.project.chat.entity.GroupEntity;
import com.project.chat.entity.GroupUser;
import com.project.chat.entity.UserEntity;

import java.util.List;


public interface GroupSerivice<T extends BaseEntity> extends BaseService<GroupEntity> {


    /**
     * 创建群组
     *
     * @param name
     * @return
     */
    GroupEntity creatGroup(String name, String avatar, UserEntity userEntity);


    /**
     * 根据 名字 查询对应的群
     *
     * @return
     */
    List<GroupEntity> findGroupsByGroupName(String groupName);


    /**
     * 获取 我所在的 所有的群
     *
     * @return
     */
    List<GroupEntity> findMyGroupsByUserId(String id);


    /**
     * 加入群组
     * @param entity
     * @param groupId
     * @return
     */
    GroupUser joinGroup(UserEntity entity, String groupId);


    /**
     * 获取 群下面的所有成员
     *
     * @return
     */
    List<GroupUser> findUsersByGroupId(String groupId);


}
