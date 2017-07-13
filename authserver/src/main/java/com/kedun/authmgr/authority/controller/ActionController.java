package com.kedun.authmgr.authority.controller;

import com.kedun.authmgr.authority.service.ActionService;
import com.kedun.authmgr.authority.vo.ActionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * action Controller层.
 */
@RestController
@Api(value = "权限接口服务", description = "权限信息 CURD操作接口")
@RequestMapping(value = "/auth")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @ApiOperation(value = "创建权限")
    @RequestMapping(value = "/CreateAction",method = RequestMethod.POST)
    public Object createAction(@RequestBody ActionVo record) {
        return actionService.createAction(record);
    }

    @ApiOperation(value = "禁用权限")
    @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "权限ID", defaultValue = "1")
    @RequestMapping(value = "/disableAction/{id}",method = RequestMethod.GET)
    public Object updateAction(@PathVariable Integer id) {
        return actionService.updateAction(id);
    }

    @ApiOperation(value = "删除权限")
    @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "权限ID", defaultValue = "1")
    @RequestMapping(value = "/action/{id}",method = RequestMethod.DELETE)
    public Object deleteAction(@PathVariable Integer id) {
        return actionService.deleteAction(id);
    }

   /* @ApiOperation(value = "查询角色详情")
    public Object queryRole(){

    }*/

    @ApiOperation(value = "查询权限列表")
    @RequestMapping(value = "/actionList",method = RequestMethod.GET)
    public Object queryActionList(@ApiParam(value = "过滤条件设置. e.g. col1:v1,col2:v2 …",defaultValue = "null") @RequestParam() String query,
                                  @ApiParam(value = "排序字段设置. e.g. col1,col2 …",defaultValue = "null") @RequestParam() String fields,
                                  @ApiParam(value = "排序字段设置. e.g. col1,col2 …",defaultValue = "null") @RequestParam() String sortby,
                                  @ApiParam(value = "排序方式设置 e.g. desc,asc …",defaultValue = "null") @RequestParam() String order,
                                  @ApiParam(value = "是否分页 e.g. 0 否 1 是 ",defaultValue = "0") @RequestParam() Integer paging,
                                  @ApiParam(value = "设置返回记录数. 必须是整型值") @RequestParam() Integer limit,
                                  @ApiParam(value = "页码/开始游标. 必须是整型值") @RequestParam() Integer offset) {
        return actionService.queryActionList(query, fields, sortby, order,paging, limit, offset);
    }

}
