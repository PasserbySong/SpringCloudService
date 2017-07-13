package com.kedun.authmgr.authority.dao;

import com.kedun.authmgr.authority.model.RoleAction;

import java.util.List;
import java.util.Map;

public interface RoleActionMapper {

    int insert(RoleAction record);

    int delete(RoleAction record);

    List<Map<String,Object>> selectActionByRole(Integer roleId);
}