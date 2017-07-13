package com.kedun.authmgr.user.controller;

import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import com.kedun.authmgr.common.VerifyCodeUtils;
import com.kedun.authmgr.user.service.UserService;
import com.kedun.authmgr.user.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
@RestController
@Api(value = "Auth开放接口服务", description = "认证中心免验证接口")
public class OpenController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取图形验证码")
    @RequestMapping(value = "/verifyCode",method = RequestMethod.GET)
    public Object outputImage(HttpServletRequest request, HttpServletResponse response){

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

        //加入session回话
        HttpSession session = request.getSession(true);
        session.setAttribute("rand", verifyCode.toLowerCase());

        session.setMaxInactiveInterval(30*60);

        //生成图片
        int w = 200, h = 80;
        try{
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        }catch (Exception e){
            throw CommonTools.createException(ErrorCodeEnum.VerifyCodeError);
        }
        return "success";
    }

    @ApiOperation(value = "用户登录接口")
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public Object userLogin(@RequestBody @Valid UserLoginVo record, HttpServletRequest request){
        return userService.userLogin(record,request);
    }

}
