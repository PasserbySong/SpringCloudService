package com.kedun.authmgr.aop;


import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import com.kedun.authmgr.exception.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
@Aspect
@Component
public class DataResultChange {
    private Logger logger = LoggerFactory.getLogger(DataResultChange.class);

    @Autowired
    private RedisTemplate redisTemplate;

    public DataResultChange() {
    }

    @Around("execution(*  com.kedun.*..*Controller.*(..))")
    public Object logServiceAccess(ProceedingJoinPoint pjp) {
        String className = pjp.getTarget().getClass().getName();
        DataResult result = null;
        try {

            if (!className.equals("com.kedun.authmgr.user.controller.OpenController")) {
                this.authHandle();
            }

            Object result1 = pjp.proceed();
            result = new DataResult(result1);
        } catch (Throwable var5) {
            this.logger.error(var5.getMessage(), var5);
            if (var5 instanceof DataAccessException) {
                result = new DataResult("E03003401", "数据库操作异常");
            } else if (var5 instanceof ServiceException) {
                result = new DataResult(((ServiceException) var5).getErrorCode(), var5.getLocalizedMessage());
            } else if (var5 instanceof MethodArgumentNotValidException) {
                result = new DataResult("E03003400", "参数异常");
            } else {
                result = new DataResult("E03003404", "系统异常");
            }
        }

        return result;
    }

    /**
     * 权限token验证
     */
    private void authHandle() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // token验证
        /*if (request.getHeader("token") == null) {
            throw CommonTools.createException(ErrorCodeEnum.NoToken);
        } else if (redisTemplate.hasKey(request.getHeader("token"))) {
            request.setAttribute("userId", redisTemplate.opsForValue().get("token"));
        } else {
            throw CommonTools.createException(ErrorCodeEnum.TokenInvalid);
        }*/

        //鉴权验证
        if (request.getHeader("ts") == null || request.getHeader("au") == null) {
            throw CommonTools.createException(ErrorCodeEnum.NoAuTs);
        } else if (!CommonTools.verifyAu(request.getHeader("ts"), request.getHeader("au"))) {
            throw CommonTools.createException(ErrorCodeEnum.AuTsError);
        }
    }

}
