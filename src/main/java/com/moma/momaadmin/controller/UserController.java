package com.moma.momaadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.util.RestResult;
import com.moma.momaadmin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private SysUserService userService;

    @PostMapping("register")
    public RestResult userRegister(String username, String password) {
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(StringUtil.encodePassword(password));
        user.setNickname(username);
        if (userService.userRegister(user)) {
            return RestResult.ok("注册成功");
        }

        return RestResult.error("注册失败");
    }

    @PostMapping("login")
    public RestResult findUserPhoneByUserName(@RequestBody SysUser user) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",user.getUsername()).eq("password",user.getPassword());
        long count = userService.count(wrapper);
        if (count>0){
            return RestResult.ok("登录成功");
        }else {
            return RestResult.error("用户名或密码不正确");
        }

    }
}
