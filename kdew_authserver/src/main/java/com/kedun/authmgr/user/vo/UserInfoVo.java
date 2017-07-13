package com.kedun.authmgr.user.vo;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public class UserInfoVo {

    private String userCode;
    private String loginName;
    private String loginPwd;
    private String userName;
    private String phone;
    private Integer deptId;
    //private Integer createUser;

    /*public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {s
        this.createUser = createUser;
    }
*/
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
