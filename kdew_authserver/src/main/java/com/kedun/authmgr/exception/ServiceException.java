package com.kedun.authmgr.exception;

import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
public class ServiceException extends RuntimeException {
    private String errorCode = "002";

    private ServiceException(String message) {
        super(message);
    }

    public  ServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = "F"+ CommonTools.systemCode+errorCode;
    }

    public ServiceException(ErrorCodeEnum codeEnum){
        this(codeEnum.getErrorMsg(),codeEnum.getErrorCode());
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}