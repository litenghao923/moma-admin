package com.moma.momaadmin.common.security;

import cn.hutool.json.JSONUtil;
import com.moma.momaadmin.util.RestResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String message = exception.getMessage();
        if (exception instanceof BadCredentialsException){
            message="用户名或密码错误";
        }else if (exception instanceof LockedException){
            message="用户已被冻结";
        }
        outputStream.write(JSONUtil.toJsonStr(RestResult.error(message)).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

    private List<GrantedAuthority> getUserAuthority(Long id){
        return new ArrayList<>();
    }
}
