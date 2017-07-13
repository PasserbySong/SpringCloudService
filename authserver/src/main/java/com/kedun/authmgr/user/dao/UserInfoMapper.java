package com.kedun.authmgr.user.dao;

import com.kedun.authmgr.user.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {
    int deleteByCode(String code);

    int insert(UserInfo record);

    UserInfo selectByCode(String code);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKey(UserInfo record);

    UserInfo userLogin(@Param("loginName") String loginName,@Param("loginPwd") String loginPwd);

    List<Map<String,Object>> select(@Param("fieldStr") String fieldStr, @Param("queryStr")  String queryStr);
}