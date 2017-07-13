package com.kedun.authmgr.authority.dao;


import com.kedun.authmgr.authority.model.UserGroup;

import java.util.List;
import java.util.Map;

public interface UserGroupMapper {

    int insert(UserGroup record);

    int delete(UserGroup record);

    List<Map<String,Object>> selectUserByGroup(Integer groupId);

    List<Map<String,Object>> selectActionListByUser(Integer userId,String queryStr);

    List<Map<String,Object>> selectGroupListByUser(Integer userId);

    List<Map<String,Object>> selectActionList(Integer userId);

}