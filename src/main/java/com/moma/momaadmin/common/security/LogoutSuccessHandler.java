package com.moma.momaadmin.common.security;

import com.alibaba.fastjson.JSON;
import com.moma.momaadmin.util.JwtUtil;
import com.moma.momaadmin.util.RestResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LogoutSuccessHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;

    public LogoutSuccessHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null)
        //清空当前用户缓存中的权限数据
        {
            String userName = JwtUtil.getUsernameByJwtToken(request);
            redisTemplate.delete(userName);
        }
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write(JSON.toJSONString(RestResult.ok()));
        writer.close();
    }
}
