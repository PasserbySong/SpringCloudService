package com.kedun.authmgr.authority.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kedun.authmgr.authority.dao.RoleInfoMapper;
import com.kedun.authmgr.authority.model.RoleInfo;
import com.kedun.authmgr.authority.vo.RoleVo;
import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import com.kedun.authmgr.common.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Role Service层
 */
@Service
public class RoleService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    /**
     * 创建角色
     *
     * @param record
     * @return
     */
    public Object createRole(RoleVo record) {

        RoleInfo roleInfo = new RoleInfo();
        BeanUtils.copyProperties(record, roleInfo);

        roleInfo.setCreateDt(new Date());
        roleInfoMapper.insert(roleInfo);
        return roleInfo.getRoleId();
    }

    /**
     * 锁定角色
     *
     * @return
     */
    public Object updateRale(Integer id) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(id);
        roleInfo.setRoleStatus((byte) 1);
        roleInfo.setUpdateDt(new Date());
        return roleInfoMapper.updateByPrimaryKey(roleInfo);
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    public Object deleteRole(Integer id) {
        return roleInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 验证是否存在
     *
     * @param id
     */
    public void verify(Integer id) {
        if (roleInfoMapper.selectByPrimaryKey(id) == null) {
            throw CommonTools.createException(ErrorCodeEnum.RoleNoExist);
        }
    }

    public Object queryRoleList(String query, String fields, String sortby, String order, Integer paging, Integer limit, Integer offset) {

        String fieldStr = CommonTools.analysisFields(fields);
        String queryStr = CommonTools.analysisQuery(query);
        String orderStr = CommonTools.getOrderByStr(sortby, order);

        limit = limit > 0 ? limit : 10;
        if (paging == 1) {

            offset = offset > 0 ? offset : 1;
            if (orderStr.length() == 0) {
                PageHelper.startPage(offset, limit);
            } else {
                PageHelper.startPage(offset, limit, orderStr);
            }
            Page page = (Page) roleInfoMapper.select(fieldStr, queryStr);
            return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
        } else {
            offset = offset > 0 ? offset : 0;
            queryStr = orderStr.length() == 0 ? queryStr : (queryStr + orderStr + " limit " + offset + " " + limit);
            return roleInfoMapper.select(fieldStr, queryStr);
        }

    }
}
