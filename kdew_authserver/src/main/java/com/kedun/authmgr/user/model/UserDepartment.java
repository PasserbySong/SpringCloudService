package com.kedun.authmgr.user.model;

import java.util.Date;

public class UserDepartment {
    private Integer userId;

    private Integer deptId;

    private Date createDt;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public UserDepartment() {
    }

    public UserDepartment(Integer userId, Integer deptId) {
        this.userId = userId;
        this.deptId = deptId;
        this.createDt = new Date();
    }
}