package com.kancy.emailplus.spring.boot.service;

import com.kancy.emailplus.core.exception.EmailException;
import com.kancy.emailplus.spring.boot.message.EmailMessage;

import java.io.File;
import java.util.Map;

/**
 * EmailplusService
 *
 * @author kancy
 * @date 2020/2/20 23:27
 */
public interface EmailplusService {

    /**
     * 发送邮件
     * @param emailKey
     * @throws EmailException
     */
    void sendSimpleEmail(String emailKey);
    /**
     * 发送邮件
     * @param emailKey
     * @param content
     * @throws EmailException
     */
    void sendSimpleEmail(String emailKey, String content);
    /**
     * 发送邮件
     * @param emailKey
     * @param content
     * @param files
     * @throws EmailException
     */
    void sendFileEmail(String emailKey, String content, Map<String, File> files);
    /**
     * 发送邮件
     * @param emailKey
     * @throws EmailException
     */
    void sendTemplateEmail(String emailKey);
    /**
     * 发送邮件
     * @param emailKey
     * @param templateData 模板数据
     * @throws EmailException
     */
    void sendTemplateEmail(String emailKey, Map<String, Object> templateData);

}
