package com.project.chat.service.impl;


import com.project.chat.dao.GroupDao;
import com.project.chat.entity.base.BaseEntity;
import com.project.chat.entity.GroupEntity;
import com.project.chat.entity.GroupUser;
import com.project.chat.entity.UserEntity;
import com.project.chat.exception.RepeatException;
import com.project.chat.service.GroupSerivice;
import com.project.chat.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class GroupSeriviceImpl<T extends BaseEntity> extends BaseSeriviceImpl<GroupEntity> implements GroupSerivice<GroupEntity> {

    @Autowired
    GroupDao groupDao;


    @Transactional
    @Override
    public GroupEntity creatGroup(String name, String avatar, UserEntity user) {
        //不允许 创建重复的群名字
        if (groupDao.findGroupsByGroupName(name).size() > 0) {
            throw new RepeatException(-1, "群名称不能重复");
        }
        //创建 群组
        GroupEntity entity = new GroupEntity();
        entity.setCreatTime(DateUtils.getDataTimeYMD());
        entity.setGroupname(name);
        entity.setUserId(user.getId());
        entity.setUserName(user.getUsername());
        entity.setAvatar(avatar);
        groupDao.saveEntity(entity);

        //把自己加入群组
        joinGroup(user, entity.getId());

        return entity;
    }

    /**
     * 加入群组
     *
     * @param user
     * @param groupId
     * @return
     */
    @Override
    public GroupUser joinGroup(UserEntity user, String groupId) {

        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(user.getId());
        groupUser.setUsername(user.getUsername());
        groupUser.setAvatar(user.getAvatar());
        groupUser.setSign(user.getSign());
        groupUser.setJoinTime(DateUtils.getDataTimeYMDHMS());

        groupDao.saveEntity(groupUser);
        return groupUser;
    }


    /**
     * 根据群id  获取 群下面的所有成员
     *
     * @return
     */
    @Override
    public List<GroupUser> findUsersByGroupId(String group_id) {
        return groupDao.findUsersByGroupId(group_id);
    }


    /**
     * 根据 名字 查询对应的群
     *
     * @return
     */
    @Override
    public List<GroupEntity> findGroupsByGroupName(String groupName) {
        return groupDao.findGroupsByGroupName(groupName);
    }

    /**
     * 获取 我所在的 所有的群
     *
     * @return
     */
    @Override
    public List<GroupEntity> findMyGroupsByUserId(String id) {
        return groupDao.findMyGroupsByUserId(id);
    }


}
