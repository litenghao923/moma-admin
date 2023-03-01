package com.moma.momaadmin.service;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {

    public void sendCodeByMail(String to, String subject, String nickname, String code) throws MessagingException, IOException, TemplateException;
}
