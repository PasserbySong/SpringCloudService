package com.kedun.authmgr.authority.controller;

import com.kedun.authmgr.authority.service.AuthorityService;
import com.kedun.authmgr.authority.vo.BangdingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
@RestController
@Api(value = "认证接口服务", description = "认证接口服务")
@RequestMapping(value = "/auth")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;


    @ApiOperation(value = "绑定（批量）(User U,Role R,Action A,Group G)")
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    public Object binding(@RequestBody BangdingVo record) {
        return authorityService.bangding(record);
    }

    @ApiOperation(value = "解除绑定（批量）(User U,Role R,Action A,Group G)")
    @RequestMapping(value = "/unbind", method = RequestMethod.POST)
    public Object unbind(@RequestBody BangdingVo record) {
        return authorityService.unbind(record);
    }

    @ApiOperation(value = "指定用户的角色列表")
    @RequestMapping(value = "/userRoleList", method = RequestMethod.GET)
    public Object userRoleList(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) Integer userId,
                               @ApiParam(value = "排序字段设置.null不排序 e.g. col1,col2 …", required = false) @RequestParam(value = "sortby", defaultValue = "null") String sortby,
                               @ApiParam(value = "排序方式设置, 如果order参数为单个，则所有统一类型排序，否则按顺序对应. e.g. desc,asc …", required = false, defaultValue = "null") @RequestParam(value = "order") String order,
                               @ApiParam(value = "设置返回记录数. 必须是整型值", required = false) @RequestParam(value = "limit") Integer limit,
                               @ApiParam(value = "起始页. 必须是整型值", required = false) @RequestParam(value = "offset") Integer offset) {
        return authorityService.userRoleList(userId, sortby, order, limit, offset);
    }

    @ApiOperation(value = "指定用户组的权限列表")
    @RequestMapping(value = "/groupActionList", method = RequestMethod.GET)
    public Object groupActionList(@ApiParam(value = "用户组ID", required = true) @RequestParam(value = "groupId", required = true) Integer groupId,
                                  @ApiParam(value = "排序字段设置.null不排序 e.g. col1,col2 …", required = false) @RequestParam(value = "sortby", defaultValue = "null") String sortby,
                                  @ApiParam(value = "排序方式设置 e.g. desc,asc …", required = false, defaultValue = "null") @RequestParam(value = "order") String order,
                                  @ApiParam(value = "设置返回记录数. 必须是整型值", required = false) @RequestParam(value = "limit") Integer limit,
                                  @ApiParam(value = "起始页. 必须是整型值", required = false) @RequestParam(value = "offset") Integer offset) {
        return authorityService.groupActionList(groupId,sortby,order,limit,offset);
    }

    @ApiOperation(value = "指定用户组的用户列表")
    @RequestMapping(value = "/groupUserList", method = RequestMethod.GET)
    public Object groupUserList(@ApiParam(value = "用户组ID", required = true) @RequestParam(value = "groupId", required = true) Integer groupId,
                                @ApiParam(value = "排序字段设置.null不排序 e.g. col1,col2 …", required = false) @RequestParam(value = "sortby", defaultValue = "null") String sortby,
                                @ApiParam(value = "排序方式设置 e.g. desc,asc …", required = false, defaultValue = "null") @RequestParam(value = "order") String order,
                                @ApiParam(value = "设置返回记录数. 必须是整型值", required = false) @RequestParam(value = "limit") Integer limit,
                                @ApiParam(value = "起始页. 必须是整型值", required = false) @RequestParam(value = "offset") Integer offset) {
        return authorityService.groupUserList(groupId,sortby,order,limit,offset);
    }

    @ApiOperation(value = "指定角色的权限列表")
    @RequestMapping(value = "/roleActionList", method = RequestMethod.GET)
    public Object roleActionList(@ApiParam(value = "角色ID", required = true) @RequestParam(value = "roleId", required = true) Integer roleId,
                                 @ApiParam(value = "排序字段设置.null不排序 e.g. col1,col2 …", required = false) @RequestParam(value = "sortby", defaultValue = "null") String sortby,
                                 @ApiParam(value = "排序方式设置 e.g. desc,asc …", required = false, defaultValue = "null") @RequestParam(value = "order") String order,
                                 @ApiParam(value = "设置返回记录数. 必须是整型值", required = false) @RequestParam(value = "limit") Integer limit,
                                 @ApiParam(value = "起始页. 必须是整型值", required = false) @RequestParam(value = "offset") Integer offset) {
        return authorityService.roleActionList(roleId,sortby,order,limit,offset);
    }

    @ApiOperation(value = "指定角色的用户列表")
    @RequestMapping(value = "/roleUserList", method = RequestMethod.GET)
    public Object roleUserList(@ApiParam(value = "角色ID", required = true) @RequestParam(value = "roleId", required = true) Integer roleId,
                               @ApiParam(value = "排序字段设置.null不排序 e.g. col1,col2 …", required = false) @RequestParam(value = "sortby", defaultValue = "null") String sortby,
                               @ApiParam(value = "排序方式设置 e.g. desc,asc …", required = false, defaultValue = "null") @RequestParam(value = "order") String order,
                               @ApiParam(value = "设置返回记录数. 必须是整型值", required = false) @RequestParam(value = "limit") Integer limit,
                               @ApiParam(value = "起始页. 必须是整型值", required = false) @RequestParam(value = "offset") Integer offset) {

        return authorityService.roleUserList(roleId,sortby,order,limit,offset);
    }

    @ApiOperation(value = "指定用户的权限列表")
    @RequestMapping(value = "/userActionList", method = RequestMethod.GET)
    public Object userActionList(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) Integer userId,
                                 @ApiParam(value = "排序字段设置.null不排序 e.g. col1,col2 …", required = false) @RequestParam(value = "sortby", defaultValue = "null") String sortby,
                                 @ApiParam(value = "排序方式设置 e.g. desc,asc …", required = false, defaultValue = "null") @RequestParam(value = "order") String order,
                                 @ApiParam(value = "是否分页 e.g. 0 否 1 是 ",defaultValue = "0") @RequestParam() Integer paging,
                                 @ApiParam(value = "设置返回记录数. 必须是整型值", required = false) @RequestParam(value = "limit") Integer limit,
                                 @ApiParam(value = "起始页. 必须是整型值", required = false) @RequestParam(value = "offset") Integer offset) {
        return authorityService.userActionList(userId,sortby,order,paging,limit,offset);
    }

    @ApiOperation(value = "指定用户的用户组列表")
    @RequestMapping(value = "/userGroupList", method = RequestMethod.GET)
    public Object userGroupList(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) Integer userId,
                                @ApiParam(value = "排序字段设置.null不排序 e.g. col1,col2 …", required = false) @RequestParam(value = "sortby", defaultValue = "null") String sortby,
                                @ApiParam(value = "排序方式设置 e.g. desc,asc …", required = false, defaultValue = "null") @RequestParam(value = "order") String order,
                                @ApiParam(value = "设置返回记录数. 必须是整型值", required = false) @RequestParam(value = "limit") Integer limit,
                                @ApiParam(value = "起始页. 必须是整型值", required = false) @RequestParam(value = "offset") Integer offset) {
        return authorityService.userGroupList(userId,sortby,order,limit,offset);
    }

}
