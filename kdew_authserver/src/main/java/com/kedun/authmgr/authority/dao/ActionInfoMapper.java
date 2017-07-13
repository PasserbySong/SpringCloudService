package com.kedun.authmgr.authority.dao;

import com.kedun.authmgr.authority.model.ActionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActionInfoMapper {
    int deleteByPrimaryKey(Integer actionId);

    int insert(ActionInfo record);

    ActionInfo selectByPrimaryKey(Integer actionId);

    int updateByPrimaryKey(ActionInfo record);

    List<Map<String,Object>> select(@Param("fieldStr") String fieldStr, @Param("queryStr")  String queryStr);
}