package com.moma.momaadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.MailService;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.util.JwtUtil;
import com.moma.momaadmin.util.RestResult;
import com.moma.momaadmin.util.StringUtil;
import com.moma.momaadmin.util.ValidateCodeUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MailService mailService;

    @RequestMapping("find")
    public RestResult findAllUser(@RequestHeader(required = false) String token) {
        if (StringUtil.isNotEmpty(token)) {
            Map<String, Object> map = new HashMap<>();
            List<SysUser> list = sysUserService.list();
            map.put("userList", list);
            return RestResult.ok(map);
        } else {
            return RestResult.error(403, "无权限访问");
        }


    }

    @RequestMapping("login")
    public RestResult login() {
        String token = JwtUtil.genJwtToken("litenghao");
        return RestResult.ok().put("token", token);
    }


    @RequestMapping("sendCode")
    public RestResult sendCheckCode(String username, HttpSession session) throws MessagingException, TemplateException, IOException {

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        String email = sysUserService.getOne(wrapper).getEmail();
        if (StringUtil.isNotEmpty(email)) {
            //邮件标题
            String subject = "摩码网络登录验证码";
            //登录验证码
            String code = ValidateCodeUtil.generateStringValidateCode(6).toString();
            //邮件主体部分
            mailService.sendCodeByMail(email, subject, username, code);
            return RestResult.ok("验证码发送成功，请及时查看");
        } else {
            return RestResult.error("验证码发送失败，请重新获取！");
        }
    }
}
