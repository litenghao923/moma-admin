package com.moma.momaadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.RegisterBody;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.entity.SysUserRole;
import com.moma.momaadmin.service.MailService;
import com.moma.momaadmin.service.SysUserRoleService;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.util.RestResult;
import com.moma.momaadmin.util.StringUtil;
import com.moma.momaadmin.util.ValidateCodeUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class RegisterController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private MailService mailService;

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
                SysUserRole userRole=new SysUserRole();
                userRole.setUserId(1L);
                userRole.setRoleId(2L);
                userRoleService.save(userRole);
                return RestResult.ok("注册成功");
            }else {
                return RestResult.error("注册失败");
            }
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

    @RequestMapping("sendCode")
    public RestResult sendCheckCode(String username, HttpSession session) throws MessagingException, TemplateException, IOException {

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser user = userService.getOne(wrapper);
        String email = user.getEmail();
        String nickname = user.getNickname();
        if (StringUtil.isNotEmpty(email)) {
            //邮件标题
            String subject = "摩码网络登录验证码";
            //登录验证码
            String code = ValidateCodeUtil.generateStringValidateCode(6).toString();
            //邮件主体部分
            mailService.sendCodeByMail(email, subject, nickname, code);
            return RestResult.ok("验证码发送成功，请及时查看");
        } else {
            return RestResult.error("验证码发送失败，请重新获取！");
        }
    }
}
