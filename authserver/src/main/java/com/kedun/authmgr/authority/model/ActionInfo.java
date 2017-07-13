package com.kedun.authmgr.authority.model;

import java.util.Date;

public class ActionInfo {
    private Integer actionId;

    private String systemName;

    private String actionName;

    private String displayName;

    private String controllerName;

    private Byte actionStatus;

    private Date createDt;

    private Date updateDt;

    private String dslString;

    public String getDslString() {
        return dslString;
    }

    public void setDslString(String dslString) {
        this.dslString = dslString;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public Byte getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Byte actionStatus) {
        this.actionStatus = actionStatus;
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