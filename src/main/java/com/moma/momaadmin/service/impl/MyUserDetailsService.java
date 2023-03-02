package com.moma.momaadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.service.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名查询user
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        SysUser user=userService.getOne(wrapper);
        //2.封装为UserDetails对象
        UserDetails userDetails= User.withUsername(user.getUsername()).password(user.getPassword()).authorities("admin").build();
        return userDetails;
    }
}
