package com.kedun.authmgr.department.controller;

import com.kedun.authmgr.department.service.DepartmentService;
import com.kedun.authmgr.department.vo.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
@RestController
@Api(value = "Department接口服务", description = "单位部门信息 CURD操作接口")
@RequestMapping(value = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "创建单位部门信息")
    @RequestMapping(value = "/info/Operate", method = RequestMethod.POST)
    public Object createDepartment(@RequestBody DepartmentVo record) {
        return departmentService.operateDepartment(record);
    }

    @ApiOperation(value = "获取部门信息")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "path", name = "deptId", required = true, value = "部门ID", defaultValue = "1")
    )
    @RequestMapping(value = "/info/{deptId}", method = RequestMethod.GET)
    public Object info(@PathVariable String deptId) {
        return departmentService.info(Integer.parseInt(deptId));
    }

    @ApiOperation(value = "获取部门结构树")
    @RequestMapping(value = "/treeInfo", method = RequestMethod.GET)
    public Object treeInfo() {
        return departmentService.treeInfo();
    }

    @ApiOperation(value = "删除部门信息")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "path", name = "deptId", required = true, value = "部门ID", defaultValue = "1")
    )
    @RequestMapping(value = "/info/{deptId}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Integer deptId) {
        return departmentService.delete(deptId);
    }

    @ApiOperation(value = "查询国标")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "type", required = true, value = "查询目标 0 省 1 市 2 县 3 乡镇 4 村", defaultValue = "0"),
            @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "省 id为0 其他 所属上级单位ID", defaultValue = "0")})
    @RequestMapping(value = "/GB/{type}/{id}", method = RequestMethod.GET)
    public Object queryGB(@PathVariable Integer type, @PathVariable String id) {
        return departmentService.queryGB(type, id);
    }

}
