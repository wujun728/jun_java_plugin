package com.springboot.springbootmail.service;

public interface MailService {
    void sendSimpleEmail(String to, String subject, String content);

    void sendHTMLEmail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String fileName, String filePath);
}