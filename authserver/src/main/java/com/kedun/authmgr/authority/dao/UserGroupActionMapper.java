package com.kedun.authmgr.authority.dao;

import com.kedun.authmgr.authority.model.UserGroupAction;

import java.util.List;
import java.util.Map;

public interface UserGroupActionMapper {

    int insert(UserGroupAction record);

    int delete(UserGroupAction record);

    List<Map<String,Object>> selectActionByGroup(Integer groupId);
}