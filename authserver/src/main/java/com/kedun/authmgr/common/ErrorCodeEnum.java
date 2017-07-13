package com.kedun.authmgr.common;

public enum ErrorCodeEnum {

    AuTsError("503","通讯鉴权码错误"),
    NoAuTs("502","请求头无通讯鉴权码"),
    NoToken("501","请先登录"),
    TokenInvalid("500","Token已失效"),


    LoginNameExist("014","登录名已存在"),
    NoValidCode("013","请输入验证码"),
    DepartmentNoExist("012","部门不存在"),
    VerifyCodeError("011","生成验证码失败"),
    ParamsError("010","参数错误"),
    GroupNoExist("009","用户组不存在"),
    ActionNoExist("008","权限不存在"),
    RoleNoExist("007","角色不存在"),
    UserNoExist("006","用户不存在"),
    ParentDepartmentNoExist("005","直属单位编号不存在"),
    UserNameOrPwdError("004","用户名或密码不正确"),
    LoginPwdIsNull("003","密码不能为空"),
    ValidCodeError("002","验证码不正确"),
    UserCodeExist("001","用户编号已存在");

    private String errorCode;

    private String errorMsg;

    ErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static ErrorCodeEnum stateOf(String index){
        for(ErrorCodeEnum errorCodeEnum:values()){
            if(errorCodeEnum.getErrorCode().equals(index)){
                return errorCodeEnum;
            }
        }
        return null;
    }


}
