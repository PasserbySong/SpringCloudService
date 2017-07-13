package com.kedun.authmgr.common;

/**
 * 加密枚举
 */
public enum Algorithm {

    HS256("HmacSHA256"), HS512("HmacSHA512");

    private Algorithm(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
