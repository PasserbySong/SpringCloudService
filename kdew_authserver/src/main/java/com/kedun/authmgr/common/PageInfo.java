package com.kedun.authmgr.common;

import com.github.pagehelper.Page;

/**
 * Created by Administrator on 2016/9/22.
 */
public class PageInfo {

    private Object dataInfo;
    private int pages;
    private long totals;

    public Object getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(Object dataInfo) {
        this.dataInfo = dataInfo;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }

    public PageInfo() {
    }

    public PageInfo(Object dataInfo, int pages, long totals) {
        this.dataInfo = dataInfo;
        this.pages = pages;
        this.totals = totals;
    }
}
