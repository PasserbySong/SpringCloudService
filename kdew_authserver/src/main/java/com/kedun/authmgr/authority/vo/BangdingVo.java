package com.kedun.authmgr.authority.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class BangdingVo {

    @ApiModelProperty(value = "绑定类型：primary-sub,类型组合有：U-R,R-U,U-G,G-U,R-A,A-R,G-A,A-G共八种",allowableValues = "U-R")
    private String type;
    @ApiModelProperty(value = "主ID",allowableValues = "1")
    private Integer primaryId;
    @ApiModelProperty(value = "从ID数组")
    private List<Integer> subIds;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public List<Integer> getSubIds() {
        return subIds;
    }

    public void setSubIds(List<Integer> subIds) {
        this.subIds = subIds;
    }
}
