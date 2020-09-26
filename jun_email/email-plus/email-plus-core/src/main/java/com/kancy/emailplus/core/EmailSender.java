package com.kancy.emailplus.core;

import com.kancy.emailplus.core.exception.EmailException;


/**
 * EmailSender
 *
 * @author kancy
 * @date 2020/2/19 23:02
 */
public interface EmailSender {
    /**
     * 发送邮件
     * @param message
     * @throws EmailException
     */
    void send(Email message);

    /**
     * 获取EmailSender一个名词标识
     * @return
     */
    String getSenderName();

}
