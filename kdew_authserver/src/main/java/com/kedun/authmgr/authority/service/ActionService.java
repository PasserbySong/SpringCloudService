package com.kedun.authmgr.authority.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kedun.authmgr.authority.dao.ActionInfoMapper;
import com.kedun.authmgr.authority.model.ActionInfo;
import com.kedun.authmgr.authority.vo.ActionVo;
import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import com.kedun.authmgr.common.PageInfo;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * actiuon Service层
 */
@Service
public class ActionService {

    @Autowired
    private ActionInfoMapper actionInfoMapper;

    private Logger lggger= Logger.getLogger(ActionService.class);

    /**
     * 创建权限
     * @param record
     * @return
     */
    public Object createAction(ActionVo record) {

        ActionInfo actionInfo=new ActionInfo();
        BeanUtils.copyProperties(record,actionInfo);
        actionInfo.setCreateDt(new Date());
        actionInfoMapper.insert(actionInfo);
        return actionInfo.getActionId();
    }

    /**
     * 禁用权限
     * @param id
     * @return
     */
    public Object updateAction(Integer id) {

        ActionInfo actionInfo=new ActionInfo();
        actionInfo.setActionId(id);
        actionInfo.setActionStatus((byte)1);
        actionInfo.setUpdateDt(new Date());
        return actionInfoMapper.updateByPrimaryKey(actionInfo);
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    public Object deleteAction(Integer id) {
        return actionInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询权限列表
     * @return
     */
    public Object queryActionList(String query, String fields, String sortby, String order,Integer paging, Integer limit, Integer offset) {

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
            Page page = (Page) actionInfoMapper.select(fieldStr, queryStr);
            return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
        } else {
            offset = offset > 0 ? offset : 0;
            queryStr = orderStr.length() == 0 ? queryStr : (queryStr + orderStr + " limit " + offset + " " + limit);
            return actionInfoMapper.select(fieldStr, queryStr);
        }
    }

    /**
     * 验证是否存在
     * @param id
     */
    public void verify(Integer id){
        if(actionInfoMapper.selectByPrimaryKey(id)==null){
            throw CommonTools.createException(ErrorCodeEnum.ActionNoExist);
        }
    }


}
