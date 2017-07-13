package com.kedun.authmgr.authority.dao;

import com.kedun.authmgr.authority.model.UserGroupInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserGroupInfoMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(UserGroupInfo record);

    UserGroupInfo selectByPrimaryKey(Integer groupId);

    int updateByPrimaryKey(UserGroupInfo record);

    List<Map<String,Object>> select(@Param("fieldStr") String fieldStr, @Param("queryStr")  String queryStr);
}