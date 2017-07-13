package com.kedun.authmgr.department.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class DepartmentVo {

    @ApiModelProperty(value = "部门ID 有值：修改 无值：新增",allowableValues = "0")
    private Integer deptId;
    @ApiModelProperty(value = "所属(父)单位ID 没有为O",allowableValues = "0")
    private Integer parentId;
    @ApiModelProperty(value = "部门名称",allowableValues = "0")
    private String deptName;
    @ApiModelProperty(value = "省",allowableValues = "0")
    private Long provinceId;
    @ApiModelProperty(value = "省名称",allowableValues = "0")
    private String provinceName;
    @ApiModelProperty(value = "市",allowableValues = "0")
    private Long cityId;
    @ApiModelProperty(value = "市名称",allowableValues = "0")
    private String cityName;

    private Long countyId;

    private String countyName;

    private Long townId;

    private String townName;

    private Long villageId;

    private String villageName;


    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
}
