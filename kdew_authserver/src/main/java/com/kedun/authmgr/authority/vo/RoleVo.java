package com.kedun.authmgr.authority.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class RoleVo {

    @ApiModelProperty(value = "子系统代码",allowableValues = "0")
    private String systemName;
    @ApiModelProperty(value = "角色名称（code）",allowableValues = "0")
    private String roleName;
    @ApiModelProperty(value = "显示名称（中）",allowableValues = "0")
    private String displayName;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
