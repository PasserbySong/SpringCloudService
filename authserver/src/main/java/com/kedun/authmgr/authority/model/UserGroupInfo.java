package com.kedun.authmgr.authority.model;

import java.util.Date;

public class UserGroupInfo {
    private Integer groupId;

    private String systemName;

    private String groupName;

    private Byte groupStatus;

    private Date createDt;

    private Date updateDt;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Byte getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Byte groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }
}