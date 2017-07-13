package com.kedun.authmgr.user.dao;

import com.kedun.authmgr.user.model.UserDepartment;

public interface UserDepartmentMapper {

    int insert(UserDepartment record);

    int deleteByUser(Integer userId);

    int updateByUser(UserDepartment record);
}