package com.kedun.authmgr.aop;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kedun.authmgr.common.CommonTools;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
public class DataResult<T> {

    private String Code;
    private String Msg;
    private T Data;

    public DataResult() {
    }

    public DataResult(T Data) {
        this.Data = Data;
        this.Code = "R"+ CommonTools.systemCode+"000";
        this.Msg = "操作成功";
    }

    public DataResult(String Code, String Msg) {
        this.Code = Code;
        this.Msg = Msg;
    }

    public DataResult(String Code) {
        this.Code = Code;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @Override
    public String toString() {
        return "DataResult{" +
                "Code='" + Code + '\'' +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                '}';
    }
}