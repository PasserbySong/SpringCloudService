package com.kedun.authmgr.user.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kedun.authmgr.authority.dao.UserGroupMapper;
import com.kedun.authmgr.authority.service.AuthorityService;
import com.kedun.authmgr.common.*;
import com.kedun.authmgr.department.service.DepartmentService;
import com.kedun.authmgr.user.dao.UserDepartmentMapper;
import com.kedun.authmgr.user.dao.UserInfoMapper;
import com.kedun.authmgr.user.model.UserDepartment;
import com.kedun.authmgr.user.model.UserInfo;
import com.kedun.authmgr.user.vo.UserInfoVo;
import com.kedun.authmgr.user.vo.UserLoginVo;
import com.kedun.authmgr.user.vo.UserVo;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户Service
 */
@Service
public class UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    private static JWTSigner signer = new JWTSigner();

    private Logger logger = Logger.getLogger(UserService.class);
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserGroupMapper userGroupMapper;


    /**
     * 新建用户
     *
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Object createUser(UserInfoVo record) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //验证条件

        //验证部门是否存在
        String deptName = departmentService.verify(record.getDeptId());

        //验证user_code是否已存在
        if (userInfoMapper.selectByCode(record.getUserCode()) != null) {
            throw CommonTools.createException(ErrorCodeEnum.UserCodeExist);
        }

        //验证loginName是否存在

        if(this.getUserList("loginName:"+record.getLoginName(),null,null,null,0,0,0)!=null){
            throw CommonTools.createException(ErrorCodeEnum.LoginNameExist);
        }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(record, userInfo);

        userInfo.setLoginPwd(CommonTools.MD5(userInfo.getLoginPwd()));
        userInfo.setCreateUser((int) request.getAttribute("userId"));
        userInfo.setDeptName(deptName);
        userInfo.setCreateDt(new Date());
        userInfoMapper.insert(userInfo);

        //添加用户与部门关联
        UserDepartment ud = new UserDepartment(userInfo.getUserId(), record.getDeptId());
        userDepartmentMapper.insert(ud);

        return userInfo.getUserId();

    }

    /**
     * 查询用户列表
     *
     * @param query
     * @param fields
     * @param sortby
     * @param order
     * @param paging
     * @param limit
     * @param offset
     * @return
     */
    public Object getUserList(String query, String fields, String sortby, String order, Integer paging, Integer limit, Integer offset) {

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
            Page page = (Page) userInfoMapper.select(fieldStr, queryStr);
            return new PageInfo(page.getResult(), page.getPages(), page.getTotal());
        } else {
            offset = offset > 0 ? offset : 0;
            queryStr = orderStr.length() == 0 ? queryStr : (queryStr + orderStr + " limit " + offset + " " + limit);
            return userInfoMapper.select(fieldStr, queryStr);
        }

    }

    /**
     * 用户登录
     *
     * @param record
     * @return
     */
    public String userLogin(UserLoginVo record, HttpServletRequest request) {

        //获取serssion
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(30 * 60);
        logger.info(session.getId());
        boolean isNeed = false;
        if (session.getAttribute("isNeed") == null) {
            session.setAttribute("isNeed", isNeed);
        } else {
            isNeed = (boolean) session.getAttribute("isNeed");
        }

        if (isNeed) {
            //验证验证码
            if (record.getValidCode() == null) {
                throw CommonTools.createException(ErrorCodeEnum.NoValidCode);
            } else if (request.getAttribute("rand") == null || request.getSession().getAttribute("rand").toString().equals(record.getValidCode())) {
                throw CommonTools.createException(ErrorCodeEnum.ValidCodeError);
            }
        }

        //验证密码是否正确
        UserInfo userInfo = userInfoMapper.userLogin(record.getLoginName(), CommonTools.MD5(record.getLoginPwd()));
        if (userInfo == null) {
            //加入session回话
            session.setAttribute("isNeed", true);
            throw CommonTools.createException(ErrorCodeEnum.UserNameOrPwdError);
        }

        //生成Token 加入redis
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);

        //String token=signer.sign(map);JWT
        String token = UuidUtils.uuid();

        //失效旧token
        if (redisTemplate.hasKey(userInfo.getUserId() + "_token")) {
            String oldToken = redisTemplate.opsForValue().get(userInfo.getUserId() + "_token").toString();
            redisTemplate.delete(oldToken);
        }

        redisTemplate.opsForValue().set(userInfo.getUserId() + "_token", token, 30, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(token, userInfo.getUserId(), 30, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(userInfo.getUserId(), userInfo, 1, TimeUnit.DAYS);

        //加载权限信息
        this.refreshAuthority(userInfo.getUserId());

        session.setAttribute("isNeed", false);
        return token;
    }

    public String changeUserStatus() {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object changeUserDepartment(UserVo record) {

        //查询用户信息
        UserInfo userInfo = userInfoMapper.selectByCode(record.getUserCode());

        //验证并获取部门名称
        String deptName = departmentService.verify(record.getDeptId());

        //更新关联
        UserDepartment userDepartment = new UserDepartment(userInfo.getUserId(), record.getDeptId());
        userDepartmentMapper.updateByUser(userDepartment);

        //更新主表
        userInfo.setDeptName(deptName);
        userInfoMapper.updateByPrimaryKey(userInfo);

        return "success";
    }

    /**
     * 重新缓存用户权限
     *
     * @param userId
     * @return
     */
    public Object refreshAuthority(Integer userId) {
        //加载权限信息
        List<Map<String, Object>> sysActionList = userGroupMapper.selectActionList(userId);

        for (Map<String, Object> sysAction : sysActionList) {
            String redisKey = "actionList_" + userId + "_" + sysAction.get("systemName");
            logger.info(redisKey);
            redisTemplate.opsForValue().set(redisKey, sysAction.get("actionList"), 1, TimeUnit.DAYS);
        }
        return "success";
    }

    /**
     * 验证是否存在
     *
     * @param id
     */
    public void verify(Integer id) {
        if (userInfoMapper.selectByPrimaryKey(id) == null) {
            throw CommonTools.createException(ErrorCodeEnum.UserNoExist);
        }
    }

}
