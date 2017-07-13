package com.kedun.authmgr.authority.controller;

import com.kedun.authmgr.authority.service.GroupService;
import com.kedun.authmgr.authority.vo.GroupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * group controller层
 */
@RestController
@Api(value = "用户组接口服务", description = "用户组信息 CURD操作接口")
@RequestMapping(value = "/auth")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "创建用户组")
    @RequestMapping(value = "/CreateGroup",method = RequestMethod.POST)
    public Object creatGroup(@RequestBody GroupVo record){
        return groupService.creatGroup(record);
    }

    @ApiOperation(value = "禁用用户组")
    @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "用户组ID", defaultValue = "1")
    @RequestMapping(value = "/disableGroup/{id}",method = RequestMethod.GET)
    public Object updateGroup(@PathVariable Integer id){
        return groupService.updateGroup(id);
    }

    @ApiOperation(value = "删除用户组")
    @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "用户组ID", defaultValue = "1")
    @RequestMapping(value = "/group/{id}",method = RequestMethod.DELETE)
    public Object deleteGroup(@PathVariable Integer id){
        return groupService.deleteGroup(id);
    }

    @ApiOperation(value = "查询用户组列表")
    @RequestMapping(value = "/groupList",method = RequestMethod.POST)
    public Object queryGroupList(@ApiParam(value = "过滤条件设置. e.g. col1:v1,col2:v2 …",defaultValue = "null") @RequestParam() String query,
                                 @ApiParam(value = "排序字段设置. e.g. col1,col2 …",defaultValue = "null") @RequestParam() String fields,
                                 @ApiParam(value = "排序字段设置. e.g. col1,col2 …",defaultValue = "null") @RequestParam() String sortby,
                                 @ApiParam(value = "排序方式设置 e.g. desc,asc …",defaultValue = "null") @RequestParam() String order,
                                 @ApiParam(value = "是否分页 e.g. 0 否 1 是 ",defaultValue = "0") @RequestParam() Integer paging,
                                 @ApiParam(value = "设置返回记录数. 必须是整型值") @RequestParam() Integer limit,
                                 @ApiParam(value = "页码/开始游标. 必须是整型值") @RequestParam() Integer offset){
        return groupService.queryGroupList(query, fields, sortby, order,paging, limit, offset);
    }
}
