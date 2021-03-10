package cn.springmvc.jpa.common.utils.mail;

import java.util.List;

import cn.springmvc.jpa.common.exception.BusinessException;

/**
 * 邮件工具类，传几个必要参数可直接发送邮件
 * 
 * @author Wujun
 * 
 */
public class MailProcessor {

    /**
     * 发送邮件
     * 
     * @param toMails
     *            收件列表
     * @param subject
     *            邮件主题
     * @param body
     *            邮件内容
     */
    public static void toSend(String[] toMails, String subject, StringBuilder body) throws BusinessException {
        try {
            validateMailContent(toMails, subject);
            MailProcessorContext mail = new MailProcessorContext();
            mail.setToMails(toMails);
            mail.setSubject(subject);
            mail.setEmailBody(body);
            mail.toSend();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 发送邮件，可CC，BCC指定邮箱
     * 
     * @param toMails
     *            收件列表
     * @param cc
     *            邮件CC列表
     * @param bcc
     *            邮件BCC列表
     * 
     * @param subject
     *            邮件主题
     * 
     * @param body
     *            邮件内容
     */
    public static void toSend(String[] toMails, String[] cc, String[] bcc, String subject, StringBuilder body) throws BusinessException {
        try {
            validateMailContent(toMails, subject);
            MailProcessorContext mail = new MailProcessorContext();
            mail.setToMails(toMails);
            mail.setCc(cc);
            mail.setBcc(bcc);
            mail.setSubject(subject);
            mail.setEmailBody(body);
            mail.toSend();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 发送邮件，可带一个附件
     * 
     * @param toMails
     *            收件列表
     * @param subject
     *            邮件主题
     * @param body
     *            邮件内容
     * @param fileName
     *            邮件名称
     * @param fileBytes
     *            附件流
     */
    public static void toSend(String[] toMails, String subject, StringBuilder body, String fileName, byte[] fileBytes) throws BusinessException {
        try {
            validateMailContent(toMails, subject);
            MailProcessorContext mail = new MailProcessorContext();
            mail.setToMails(toMails);
            mail.setSubject(subject);
            mail.setEmailBody(body);
            mail.setFile(fileName, fileBytes);
            mail.toSend();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 发送邮件，可CC，BCC指定邮箱,并带一个附件
     * 
     * @param toMails
     *            收件列表
     * @param cc
     *            邮件CC列表
     * @param bcc
     *            邮件BCC列表
     * @param subject
     *            邮件主题
     * @param body
     *            邮件内容
     * @param fileName
     *            邮件名称
     * @param fileBytes
     *            附件流
     */
    public static void toSend(String[] toMails, String[] cc, String[] bcc, String subject, StringBuilder body, String fileName, byte[] fileBytes) throws BusinessException {
        try {
            validateMailContent(toMails, subject);
            MailProcessorContext mail = new MailProcessorContext();
            mail.setToMails(toMails);
            mail.setCc(cc);
            mail.setBcc(bcc);
            mail.setSubject(subject);
            mail.setEmailBody(body);
            mail.setFile(fileName, fileBytes);
            mail.toSend();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 发送邮件，可发送多个附件
     * 
     * @param toMails
     *            收件列表
     * @param subject
     *            邮件主题
     * @param body
     *            邮件内容
     * @param files
     *            附件对象集合(邮件发送多个附件）
     */
    public static void toSend(String[] toMails, String subject, StringBuilder body, List<MailFile> files) throws BusinessException {
        try {
            validateMailContent(toMails, subject);
            MailProcessorContext mail = new MailProcessorContext();
            mail.setToMails(toMails);
            mail.setSubject(subject);
            mail.setEmailBody(body);
            mail.setMailFile(files);
            mail.toSend();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 发送邮件，可CC，BCC指定邮箱，并发送多个附件
     * 
     * @param toMails
     *            收件列表
     * @param cc
     *            邮件CC列表
     * @param bcc
     *            邮件BCC列表
     * @param subject
     *            邮件主题
     * @param body
     *            邮件内容
     * @param files
     *            附件对象集合(邮件发送多个附件）
     */
    public static void toSend(String[] toMails, String[] cc, String[] bcc, String subject, StringBuilder body, List<MailFile> files) throws BusinessException {
        try {
            validateMailContent(toMails, subject);
            MailProcessorContext mail = new MailProcessorContext();
            mail.setToMails(toMails);
            mail.setCc(cc);
            mail.setBcc(bcc);
            mail.setSubject(subject);
            mail.setEmailBody(body);
            mail.setMailFile(files);
            mail.toSend();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 判断收件列表及邮件主题是否为空
     * 
     * @param toMails
     *            邮件收件列表
     * @param subject
     *            邮件主题
     * @throws BusinessException
     */
    private static void validateMailContent(String[] toMails, String subject) throws BusinessException {
        if (toMails == null || toMails.length == 0) {
            throw new BusinessException("## toMails is null!");
        }
        if (subject == null) {
            throw new BusinessException("## Send Mail failure,by subject is null!");
        }

    }
}
