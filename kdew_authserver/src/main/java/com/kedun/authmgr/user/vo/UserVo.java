package com.kedun.authmgr.user.vo;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public class UserVo {

    private Integer userStatus;
    @Size(min = 1)
    private String  userCode;
    private Integer deptId;

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
