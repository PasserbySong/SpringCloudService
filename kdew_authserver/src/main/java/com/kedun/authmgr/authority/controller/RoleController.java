package com.kedun.authmgr.authority.controller;

import com.kedun.authmgr.authority.vo.RoleVo;
import com.kedun.authmgr.authority.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色 controller
 */
@RestController
@Api(value = "角色接口服务", description = "角色信息 CURD操作接口")
@RequestMapping(value = "/auth")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "添加角色")
    @RequestMapping(value = "/CreateRole",method = RequestMethod.POST)
    public Object createRole(@RequestBody RoleVo record) {
        return roleService.createRole(record);
    }

    @ApiOperation(value = "锁定角色")
    @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "角色ID", defaultValue = "1")
    @RequestMapping(value = "/lockRole/{id}",method = RequestMethod.GET)
    public Object updateRale(@PathVariable Integer id) {
        return roleService.updateRale(id);
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "角色ID", defaultValue = "1")
    @RequestMapping(value = "/role/{id}",method = RequestMethod.DELETE)
    public Object deleteRole(@PathVariable Integer id) {
        return roleService.deleteRole(id);
    }

   /* @ApiOperation(value = "查询角色详情")
    public Object queryRole(){

    }*/

    @ApiOperation(value = "查询角色列表")
    @RequestMapping(value = "/roleList",method = RequestMethod.GET)
    public Object queryRoleList(@ApiParam(value = "过滤条件设置. e.g. col1:v1,col2:v2 …",defaultValue = "null") @RequestParam() String query,
                                @ApiParam(value = "排序字段设置. e.g. col1,col2 …",defaultValue = "null") @RequestParam() String fields,
                                @ApiParam(value = "排序字段设置. e.g. col1,col2 …",defaultValue = "null") @RequestParam() String sortby,
                                @ApiParam(value = "排序方式设置 e.g. desc,asc …",defaultValue = "null") @RequestParam() String order,
                                @ApiParam(value = "是否分页 e.g. 0 否 1 是 ",defaultValue = "0") @RequestParam() Integer paging,
                                @ApiParam(value = "设置返回记录数. 必须是整型值") @RequestParam() Integer limit,
                                @ApiParam(value = "页码/开始游标. 必须是整型值") @RequestParam() Integer offset) {
        return roleService.queryRoleList(query,fields,sortby,order,paging,limit,offset);
    }


}
