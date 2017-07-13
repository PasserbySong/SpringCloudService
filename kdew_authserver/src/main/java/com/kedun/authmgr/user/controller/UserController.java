package com.kedun.authmgr.user.controller;

import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import com.kedun.authmgr.common.VerifyCodeUtils;
import com.kedun.authmgr.user.service.UserService;
import com.kedun.authmgr.user.vo.UserInfoVo;
import com.kedun.authmgr.user.vo.UserLoginVo;
import com.kedun.authmgr.user.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户Controller
 */
@RestController
@Api(value = "User接口服务", description = "User CURD操作接口")
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "创建用户接口")
    @RequestMapping(value = "/CreateUser",method = RequestMethod.POST)
    public Object createUser(@RequestBody UserInfoVo record){
        return userService.createUser(record);
    }

    @ApiOperation(value = "获取用户列表接口")
    @RequestMapping(value = "/GetUserList",method = RequestMethod.GET)
    public Object getUserList(@ApiParam(value = "过滤条件设置. e.g. col1:v1,col2:v2 …",defaultValue = "null") @RequestParam() String query,
                              @ApiParam(value = "排序字段设置. e.g. col1,col2 …",defaultValue = "null") @RequestParam() String fields,
                              @ApiParam(value = "排序字段设置. e.g. col1,col2 …",defaultValue = "null") @RequestParam() String sortby,
                              @ApiParam(value = "排序方式设置 e.g. desc,asc …",defaultValue = "null") @RequestParam() String order,
                              @ApiParam(value = "是否分页 e.g. 0 否 1 是 ",defaultValue = "0") @RequestParam() Integer paging,
                              @ApiParam(value = "设置返回记录数. 必须是整型值") @RequestParam() Integer limit,
                              @ApiParam(value = "页码/开始游标. 必须是整型值") @RequestParam() Integer offset){
        return userService.getUserList(query, fields, sortby, order, paging, limit, offset);
    }

    @ApiOperation(value = "变更用户部门接口")
    @RequestMapping(value = "/ChangeUserDepartment",method = RequestMethod.POST)
    public Object changeUserDepartment(@RequestBody @Valid UserVo record){
        return userService.changeUserDepartment(record);
    }

    @ApiOperation(value = "刷新用户权限缓存")
    @RequestMapping(value = "/refreshAuthority",method = RequestMethod.POST)
    public Object refreshAuthority( @ApiParam(value = "用户ID") @RequestParam Integer userId){
        return userService.refreshAuthority(userId);
    }
}
