package com.kedun.authmgr.aop;

import org.jboss.logging.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public class CustomInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = Logger.getLogger(CustomInterceptor.class);


    /**
     * 前置检查
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ip = request.getRemoteAddr();
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        HandlerMethod handlerMethod = (HandlerMethod) handler;

       /* RedisTemplate redisTemplate=SpringUtil.getBean("redisTemplate",RedisTemplate.class);

        // 获取用户token
        if(request.getHeader("token")!=null&&redisTemplate.hasKey(request.getHeader("token"))){
            String token=request.getHeader("token");
            request.setAttribute("userId",redisTemplate.opsForValue().get("token"));
        }else{
            request.setAttribute("tokenError",true);
        }*/

        return true;
    }

    // controller处理完成
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        long startTime = (Long) request.getAttribute("requestStartTime");

        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;

        // log it
        if (executeTime > 1000) {
            logger.error("[" + method.getDeclaringClass().getName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        } else {
            logger.info("[" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        }



    }
}