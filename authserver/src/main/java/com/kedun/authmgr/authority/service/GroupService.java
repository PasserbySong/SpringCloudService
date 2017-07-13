package com.kedun.authmgr.authority.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kedun.authmgr.authority.dao.UserGroupInfoMapper;
import com.kedun.authmgr.authority.model.UserGroupInfo;
import com.kedun.authmgr.authority.vo.GroupVo;
import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import com.kedun.authmgr.common.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Group service层
 */
@Service
public class GroupService {

    @Autowired
    private UserGroupInfoMapper userGroupInfoMapper;

    /**
     * 创建用户组
     * @param groupVo
     * @return 用户组ID
     */
    public Object creatGroup(GroupVo groupVo){
        UserGroupInfo userGroupInfo=new UserGroupInfo();
        BeanUtils.copyProperties(groupVo,userGroupInfo);

        userGroupInfo.setCreateDt(new Date());
        userGroupInfoMapper.insert(userGroupInfo);
        return userGroupInfo.getGroupId();
    }

    /**
     * 禁用用户组
     * @param id
     * @return
     */
    public Object updateGroup(Integer id){

        UserGroupInfo userGroupInfo=new UserGroupInfo();
        userGroupInfo.setGroupId(id);
        userGroupInfo.setGroupStatus((byte)1);
        userGroupInfo.setUpdateDt(new Date());
        return userGroupInfoMapper.updateByPrimaryKey(userGroupInfo);
    }

    /**
     * 删除用户组
     * @param id
     * @return
     */
    public Object deleteGroup(Integer id){
        return userGroupInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 验证是否存在
     * @param id
     */
    public void verify(Integer id){
        if(userGroupInfoMapper.selectByPrimaryKey(id)==null){
            throw CommonTools.createException(ErrorCodeEnum.GroupNoExist);
        }
    }

    public Object queryGroupList(String query, String fields, String sortby, String order,Integer paging, Integer limit, Integer offset) {

        String fieldStr=CommonTools.analysisFields(fields);
        String queryStr=CommonTools.analysisQuery(query);
        String orderStr=CommonTools.getOrderByStr(sortby, order);

        limit = limit > 0 ? limit : 10;
        if (paging == 1) {

            offset = offset > 0 ? offset : 1;
            if (orderStr.length() == 0) {
                PageHelper.startPage(offset, limit);
            } else {
                PageHelper.startPage(offset, limit, orderStr);
            }
            Page page = (Page) userGroupInfoMapper.select(fieldStr, queryStr);
            return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
        } else {
            offset = offset > 0 ? offset : 0;
            queryStr = orderStr.length() == 0 ? queryStr : (queryStr + orderStr + " limit " + offset + " " + limit);
            return userGroupInfoMapper.select(fieldStr, queryStr);
        }
    }

}
