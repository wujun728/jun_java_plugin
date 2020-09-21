/*   
 * Project: OSMP
 * FileName: MessageService.java
 * version: V1.0
 */
package com.osmp.intf.define.service;

/**
 * 
 * Description:消息服务
 * @author: wangkaiping
 * @date: 2016年8月8日 上午11:43:02上午10:51:30
 */
public interface MessageService {
    
    /**
     * 邮件发送
     * @param title 邮件主题
     * @param content 邮件内容
     * @param addrs 收件地址
     */
    boolean sendEmail(String title,String content,String[] addrs);
    /**
     * 短信发送
     * @param content 短信内容
     * @param tels 收信手机
     */
    boolean sendMms(String content,String[] tels);
    /**
     * 邮件报警
     * @param title 邮件主题
     * @param content 邮件内容
     */
    boolean emailWarn(String title,String content);
    /**
     * 短信报警
     * @param content 短信内容
     */
    boolean mmsWarn(String content);
}
