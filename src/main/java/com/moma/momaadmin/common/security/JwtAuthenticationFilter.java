package com.moma.momaadmin.common.security;

import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.util.CheckResult;
import com.moma.momaadmin.util.JwtUtil;
import com.moma.momaadmin.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private SysUserService userService;

    private static final String URL_WHITELIST[] = {
            "/register",
            "/checkRegistered"
    };

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader("token");
        System.out.println("request.getRequestURI()="+request.getRequestURI());
        if (StringUtil.isEmpty(tokenHeader)||new ArrayList<String>(Arrays.asList(URL_WHITELIST)).contains(request.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }
        CheckResult checkResult=JwtUtil.validateJWT(tokenHeader);
        if (!checkResult.isSuccess()){
            switch (checkResult.getErrCode()){
                case 4000:
                    throw new JwtException("Token不存在");
                case 4001:
                    throw new JwtException("Token过期");
                case 4002:
                    throw new JwtException("Token验证未通过");
                case 4003:
                    throw new JwtException("加密算法异常");
                case 4004:
                    throw new JwtException("token解密异常");
            }
        }

        Claims claims=JwtUtil.parseJWT(tokenHeader);
        String username=claims.getSubject();
        SysUser sysUser=userService.getUserByName(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,null,myUserDetailsService.getUserAuthority(sysUser.getId()));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);
    }
}
