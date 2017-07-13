package com.kedun.authmgr.authority.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kedun.authmgr.authority.dao.*;
import com.kedun.authmgr.authority.model.RoleAction;
import com.kedun.authmgr.authority.model.UserGroup;
import com.kedun.authmgr.authority.model.UserGroupAction;
import com.kedun.authmgr.authority.model.UserRole;
import com.kedun.authmgr.authority.vo.BangdingVo;
import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import com.kedun.authmgr.common.PageInfo;
import com.kedun.authmgr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * auth Service
 */
@Service
public class AuthorityService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private RoleActionMapper roleActionMapper;
    @Autowired
    private UserGroupActionMapper userGroupActionMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ActionService actionService;


    /**
     * 权限绑定方法
     *
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Object bangding(BangdingVo record) {

        Date cDate = new Date();
        switch (record.getType()) {
            case "U-R"://用户绑定多角色
                //验证
                userService.verify(record.getPrimaryId());

                for (Integer subId : record.getSubIds()) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(subId);
                    userRole.setUserId(record.getPrimaryId());
                    userRole.setCreateDt(cDate);

                    roleService.verify(subId);

                    userRoleMapper.insert(userRole);
                }
                break;
            case "R-U"://角色绑定多用户
                //验证
                roleService.verify(record.getPrimaryId());

                for (Integer subId : record.getSubIds()) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(record.getPrimaryId());
                    userRole.setUserId(subId);
                    userRole.setCreateDt(cDate);

                    userService.verify(subId);

                    userRoleMapper.insert(userRole);
                }
                break;
            case "U-G"://用户绑定多用户组
                //验证
                userService.verify(record.getPrimaryId());

                for (Integer subId : record.getSubIds()) {
                    UserGroup userGroup = new UserGroup();
                    userGroup.setUserId(record.getPrimaryId());
                    userGroup.setGroupId(subId);
                    userGroup.setCreateDt(cDate);

                    groupService.verify(subId);

                    userGroupMapper.insert(userGroup);
                }
                break;
            case "G-U"://用户组绑定多用户
                //验证
                groupService.verify(record.getPrimaryId());

                for (Integer subId : record.getSubIds()) {
                    UserGroup userGroup = new UserGroup();
                    userGroup.setGroupId(record.getPrimaryId());
                    userGroup.setUserId(subId);
                    userGroup.setCreateDt(cDate);

                    userService.verify(subId);

                    userGroupMapper.insert(userGroup);
                }
                break;
            case "R-A"://角色绑定多权限
                //验证
                roleService.verify(record.getPrimaryId());

                for (Integer subId : record.getSubIds()) {
                    RoleAction roleAction = new RoleAction();
                    roleAction.setRoleId(record.getPrimaryId());
                    roleAction.setActionId(subId);
                    roleAction.setCreateDt(cDate);

                    actionService.verify(subId);

                    roleActionMapper.insert(roleAction);
                }
                break;
            case "A-R"://权限绑定多角色
                //验证
                actionService.verify(record.getPrimaryId());

                for (Integer subId : record.getSubIds()) {
                    RoleAction roleAction = new RoleAction();
                    roleAction.setActionId(record.getPrimaryId());
                    roleAction.setRoleId(subId);
                    roleAction.setCreateDt(cDate);

                    roleService.verify(subId);
                    roleActionMapper.insert(roleAction);
                }
                break;
            case "G-A"://用户组绑定多权限
                //验证
                groupService.verify(record.getPrimaryId());
                for (Integer subId : record.getSubIds()) {
                    UserGroupAction uga = new UserGroupAction();
                    uga.setGroupId(record.getPrimaryId());
                    uga.setActionId(subId);
                    uga.setCreateDt(cDate);

                    actionService.verify(subId);

                    userGroupActionMapper.insert(uga);
                }
                break;
            case "A-G"://权限绑定多用户组
                //验证
                actionService.verify(record.getPrimaryId());

                for (Integer subId : record.getSubIds()) {
                    UserGroupAction uga = new UserGroupAction();
                    uga.setActionId(record.getPrimaryId());
                    uga.setGroupId(subId);
                    uga.setCreateDt(cDate);

                    groupService.verify(subId);

                    userGroupActionMapper.insert(uga);
                }
                break;
            default:
                throw CommonTools.createException(ErrorCodeEnum.ParamsError, "type", "");

        }

        return "success";

    }

    /**
     * 权限绑定方法
     *
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Object unbind(BangdingVo record) {

        switch (record.getType()) {
            case "U-R"://用户绑定多角色

                for (Integer subId : record.getSubIds()) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(subId);
                    userRole.setUserId(record.getPrimaryId());

                    userRoleMapper.delete(userRole);
                }
                break;
            case "R-U"://角色绑定多用户

                for (Integer subId : record.getSubIds()) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(record.getPrimaryId());
                    userRole.setUserId(subId);

                    userRoleMapper.delete(userRole);
                }
                break;
            case "U-G"://用户绑定多用户组

                for (Integer subId : record.getSubIds()) {
                    UserGroup userGroup = new UserGroup();
                    userGroup.setUserId(record.getPrimaryId());
                    userGroup.setGroupId(subId);

                    userGroupMapper.delete(userGroup);
                }
                break;
            case "G-U"://用户组绑定多用户

                for (Integer subId : record.getSubIds()) {
                    UserGroup userGroup = new UserGroup();
                    userGroup.setGroupId(record.getPrimaryId());
                    userGroup.setUserId(subId);

                    userGroupMapper.delete(userGroup);
                }
                break;
            case "R-A"://角色绑定多权限

                for (Integer subId : record.getSubIds()) {
                    RoleAction roleAction = new RoleAction();
                    roleAction.setRoleId(record.getPrimaryId());
                    roleAction.setActionId(subId);

                    roleActionMapper.delete(roleAction);
                }
                break;
            case "A-R"://权限绑定多角色

                for (Integer subId : record.getSubIds()) {
                    RoleAction roleAction = new RoleAction();
                    roleAction.setActionId(record.getPrimaryId());
                    roleAction.setRoleId(subId);

                    roleActionMapper.delete(roleAction);
                }
                break;
            case "G-A"://用户组绑定多权限

                for (Integer subId : record.getSubIds()) {
                    UserGroupAction uga = new UserGroupAction();
                    uga.setGroupId(record.getPrimaryId());
                    uga.setActionId(subId);

                    userGroupActionMapper.delete(uga);
                }
                break;
            case "A-G"://权限绑定多用户组

                for (Integer subId : record.getSubIds()) {
                    UserGroupAction uga = new UserGroupAction();
                    uga.setActionId(record.getPrimaryId());
                    uga.setGroupId(subId);

                    userGroupActionMapper.delete(uga);
                }
                break;
            default:
                throw CommonTools.createException(ErrorCodeEnum.ParamsError, "type", "");
        }

        return "success";

    }

    /**
     * 指定用户的角色列表
     * @param userId
     * @param sortby
     * @param order
     * @param limit
     * @param offset
     * @return
     */
    public Object userRoleList(Integer userId,String sortby, String order, Integer limit, Integer offset) {

        if(sortby==null||sortby.equals("null")){
            PageHelper.startPage(offset, limit);
        }else{
            PageHelper.startPage(offset, limit, CommonTools.getOrderByStr(sortby, order));
        }

        Page page = (Page) userRoleMapper.selectRoleListByUser(userId);

        return new PageInfo(page.getResult(), page.getPages(), page.getTotal());

    }

    /**
     * 指定用户组的权限列表
     * @param groupId
     * @param sortby
     * @param order
     * @param limit
     * @param offset
     * @return
     */
    public Object groupActionList(Integer groupId, String sortby, String order, Integer limit, Integer offset) {

        if(sortby==null||sortby.equals("null")){
            PageHelper.startPage(offset, limit);
        }else{
            PageHelper.startPage(offset, limit, CommonTools.getOrderByStr(sortby, order));
        }

        Page page = (Page) userGroupActionMapper.selectActionByGroup(groupId);

        return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
    }

    /**
     * 指定用户组的用户列表
     * @param groupId
     * @param sortby
     * @param order
     * @param limit
     * @param offset
     * @return
     */
    public Object groupUserList(Integer groupId, String sortby, String order, Integer limit, Integer offset) {
        if(sortby==null||sortby.equals("null")){
            PageHelper.startPage(offset, limit);
        }else{
            PageHelper.startPage(offset, limit, CommonTools.getOrderByStr(sortby, order));
        }

        Page page = (Page) userGroupMapper.selectUserByGroup(groupId);

        return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
    }

    /**
     * 指定角色的权限列表
     * @param roleId
     * @param sortby
     * @param order
     * @param limit
     * @param offset
     * @return
     */
    public Object roleActionList(Integer roleId, String sortby, String order, Integer limit, Integer offset) {
        if(sortby==null||sortby.equals("null")){
            PageHelper.startPage(offset, limit);
        }else{
            PageHelper.startPage(offset, limit, CommonTools.getOrderByStr(sortby, order));
        }

        Page page = (Page) roleActionMapper.selectActionByRole(roleId);

        return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
    }

    /**
     * 指定角色的用户列表
     * @param roleId
     * @param sortby
     * @param order
     * @param limit
     * @param offset
     * @return
     */
    public Object roleUserList(Integer roleId, String sortby, String order, Integer limit, Integer offset) {
        if(sortby==null||sortby.equals("null")){
            PageHelper.startPage(offset, limit);
        }else{
            PageHelper.startPage(offset, limit, CommonTools.getOrderByStr(sortby, order));
        }

        Page page = (Page) userRoleMapper.selectUserListByRole(roleId);

        return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
    }

    /**
     *指定用户的权限列表
     * @param userId
     * @param sortby
     * @param order
     * @param limit
     * @param offset
     * @return
     */
    public Object userActionList(Integer userId, String sortby, String order,Integer paging,Integer limit, Integer offset) {

        String orderStr=CommonTools.getOrderByStr(sortby, order);
        String queryStr=CommonTools.analysisQuery(null);

        limit = limit > 0 ? limit : 10;
        if (paging == 1) {

            offset = offset > 0 ? offset : 1;
            if (orderStr.length() == 0) {
                PageHelper.startPage(offset, limit);
            } else {
                PageHelper.startPage(offset, limit, orderStr);
            }
            Page page = (Page) userGroupMapper.selectActionListByUser(userId,queryStr);
            return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
        } else {
            offset = offset > 0 ? offset : 0;
            queryStr = orderStr.length() == 0 ? queryStr : (queryStr + orderStr + " limit " + offset + " " + limit);
            return userGroupMapper.selectActionListByUser(userId,"123");
        }
    }

    /**
     * 指定用户的用户组列表
     * @param userId
     * @param sortby
     * @param order
     * @param limit
     * @param offset
     * @return
     */
    public Object userGroupList(Integer userId, String sortby, String order, Integer limit, Integer offset) {
        if(sortby==null||sortby.equals("null")){
            PageHelper.startPage(offset, limit);
        }else{
            PageHelper.startPage(offset, limit, CommonTools.getOrderByStr(sortby, order));
        }

        Page page = (Page) userGroupMapper.selectGroupListByUser(userId);

        return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
    }
}
