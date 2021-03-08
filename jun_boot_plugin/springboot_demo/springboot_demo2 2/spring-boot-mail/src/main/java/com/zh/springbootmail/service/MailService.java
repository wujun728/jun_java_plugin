package com.zh.springbootmail.service;

import org.springframework.core.io.InputStreamSource;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * @author Wujun
 * @date 2019/6/3
 */
public interface MailService {

    void sendEmail(String to, String subject, String text);

    void sendEmailWithFile(String to, String subject, String text, String path);

    void sendWithHtml(String to, String subject, String html);

    void sendWithImageHtml(String to, String subject, String html, Map<String,String> map);


}
