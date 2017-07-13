package com.kedun.authmgr.department.dao;

import com.kedun.authmgr.department.model.DepartmentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DepartmentInfoMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(DepartmentInfo record);

    DepartmentInfo selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKey(DepartmentInfo record);

    List<Map<String,Object>> selectAll();

    List<Map<String,Object>> selectGB(@Param("type") Integer type,@Param("id")String id);
}