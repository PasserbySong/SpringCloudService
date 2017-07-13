package com.kedun.authmgr.user.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public class UserLoginVo {

    @ApiModelProperty(value ="用户名")
    @Size(min = 1)
    private String loginName;
    @ApiModelProperty(value ="密码")
    @Size(min = 1)
    private String loginPwd;
    @ApiModelProperty(value ="验证码")
    @Size(min = 1)
    private String validCode;
    @ApiModelProperty(value ="子系统名称")
    private String system;
    @ApiModelProperty(value ="时间戳",allowableValues ="123" )
    private String ts;
    @ApiModelProperty(value = "通讯鉴权码",allowableValues = "04837ddcf1c271fa52868e84be646d11")
    private String au;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getAu() {
        return au;
    }

    public void setAu(String au) {
        this.au = au;
    }
}
