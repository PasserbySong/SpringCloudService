package com.kedun.authmgr.authority.dao;

import com.kedun.authmgr.authority.model.UserRole;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper {
    int insert(UserRole record);

    int delete(UserRole record);

    List<Map<String,Object>> selectRoleListByUser(Integer userId);

    List<Map<String,Object>> selectUserListByRole(Integer roleId);


}