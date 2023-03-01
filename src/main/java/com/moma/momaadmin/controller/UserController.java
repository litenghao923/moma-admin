package com.moma.momaadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.RegisterBody;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.util.RestResult;
import com.moma.momaadmin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private SysUserService userService;

    @PostMapping("register")
    public RestResult userRegister(@RequestBody RegisterBody registerBody) {
        String msg="",username=registerBody.getUsername(),password=registerBody.getPassword(),phone=registerBody.getPhone(),email=registerBody.getEmail();
        boolean isSuccess;
        if (StringUtil.isEmpty(username)){
            msg="用户名不能为空";
            return RestResult.error(msg);
        }else if (StringUtil.isEmpty(password)){
            msg="密码不能为空";
            return RestResult.error(msg);
        }else if (username.length()<4||username.length()>20){
            msg="用户名长度必须在4到20个字符之间";
            return RestResult.error(msg);
        }else if (password.length()<6||password.length()>20){
            msg="密码长度必须在6到20个字符之间";
            return RestResult.error(msg);
        }else if (StringUtil.isEmpty(email)){
            msg="邮箱地址不能为空";
            return RestResult.error(msg);
        }else if (!userService.checkUserNameUnique(username)){
            msg="用户名'"+username+"'已被占用，换个用户名吧";
            return RestResult.error(msg);
        }else {
            isSuccess=userService.userRegister(registerBody);
            if (isSuccess){
                return RestResult.ok("注册成功");
            }else {
                return RestResult.error("注册失败");
            }
        }
    }

    @PostMapping("login")
    public RestResult userLogin(@RequestBody SysUser user) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",user.getUsername()).eq("password",user.getPassword());
        long count = userService.count(wrapper);
        if (count>0){
            return RestResult.ok("登录成功");
        }else {
            return RestResult.error("用户名或密码不正确");
        }
    }

    @GetMapping("checkRegistered")
    public RestResult findUserByUserName(String type,String text){
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq(type,text);
        long count = userService.count(wrapper);
        if (count>0){
            return RestResult.error("已存在");
        }else {
            return RestResult.ok("可以注册");
        }
    }
}
