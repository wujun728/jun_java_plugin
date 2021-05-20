package com.chentongwei.email.strategy;

import com.chentongwei.email.entity.Mail;

/**
 * @Description: 发送邮件策略接口
 *
 * @author Wujun
 * @Project common-boot-email
 */
public interface MailStrategy {

    /**
     * 发送邮件
     *
     * @param from：谁发送
     * @param mail：邮件信息
     */
    void sendMail(String from, Mail mail);

}
