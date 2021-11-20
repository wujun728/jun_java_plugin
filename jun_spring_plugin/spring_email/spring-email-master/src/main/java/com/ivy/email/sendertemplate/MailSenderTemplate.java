package com.ivy.email.sendertemplate;

import com.ivy.email.strategy.MailStrategy;
import com.ivy.email.sendertemplate.impl.MailSenderTemplateImpl;
import com.ivy.email.vo.EmailVO;

import javax.mail.MessagingException;

/**
 * Created by fangjie04 on 2016/12/3.
 */
public interface MailSenderTemplate {

    /**
     * 发送邮件
     *
     * @param vo
     */
    void sendMail(EmailVO vo) throws MessagingException;

    /**
     * 异步发送邮件
     *
     * @param vo
     * @throws MessagingException
     */
    void sendMailSync(EmailVO vo) throws MessagingException;

    /**
     * 设置邮件发送策略
     *
     * @param strategy
     * @return
     */
    MailSenderTemplateImpl setStrategy(MailStrategy strategy);
}
