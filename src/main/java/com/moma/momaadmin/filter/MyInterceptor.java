package com.moma.momaadmin.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class MyInterceptor implements HandlerInterceptor {

    /**
     * 访问控制器方法前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(new Date()+"---preHandle:"+request.getRequestURI());
        return true;
    }

    /**
     * 访问控制器方法后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(new Date()+"---postHandle:"+request.getRequestURI());
    }

    /**
     * postHandle方法执行完成后执行，一般用于释放资源
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(new Date()+"---afterCompletion:"+request.getRequestURI());
    }
}
