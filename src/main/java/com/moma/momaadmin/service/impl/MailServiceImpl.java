package com.moma.momaadmin.service.impl;

import com.moma.momaadmin.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private Configuration configuration;

    @Override
    public void sendCodeByMail(String to, String subject, String username, String code) throws MessagingException, IOException, TemplateException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setBcc(from);
        helper.setTo(to);
        helper.setSubject(subject);
        Map<String, Object> map = new HashMap<>();
        map.put("title", "Hip0ker邮件验证码");
        map.put("username", username);
        map.put("code", code);
        String process = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("code-mail.ftl"), map);
        helper.setText(process, true);
        mailSender.send(mimeMessage);
    }
}
