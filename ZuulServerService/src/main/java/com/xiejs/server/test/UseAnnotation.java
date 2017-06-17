package com.xiejs.server.test;

/**
 * Created by Administrator on 2017/5/27.
 */
@testA
public class UseAnnotation {

    @testA
    private String aaa;

    @testA
    public UseAnnotation(){
    }

    @testA
    private void setAaa(@testA String aaa){
        @testA
        String  bbb=aaa+1;
        this.aaa=bbb;
    }
}
