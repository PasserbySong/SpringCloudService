package com.kedun.authmgr.authority.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class ActionVo {

    private String systemName;
    private String actionName;
    private String displayName;
    private String controllerName;
    @ApiModelProperty(value = "dsl",allowableValues = "",required = false)
    private String dslString;

    public String getDslString() {
        return dslString;
    }

    public void setDslString(String dslString) {
        this.dslString = dslString;
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
}
