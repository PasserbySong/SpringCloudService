package com.kedun.authmgr.authority.dao;

import com.kedun.authmgr.authority.model.RoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleInfoMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer roleId);

   /* List<Map<String,Object>> select( roleId);*/

    int updateByPrimaryKey(RoleInfo record);

    List<Map<String,Object>> select(@Param("fieldStr") String fieldStr,@Param("queryStr")  String queryStr);
}