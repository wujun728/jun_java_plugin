/**
 * MailService.java
 * Created at 2017-05-28
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.mail.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.domain.file.FileInfo;
import org.itkk.udf.core.domain.file.FileParam;
import org.itkk.udf.core.domain.mail.AttachmentInfo;
import org.itkk.udf.core.domain.mail.InlineInfo;
import org.itkk.udf.core.domain.mail.MailInfo;
import org.itkk.udf.mail.MailException;
import org.itkk.udf.mail.MailProperties;
import org.itkk.udf.rms.Rms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 描述 : MailService
 *
 * @author Administrator
 */
@Service
public class MailService {

    /**
     * 描述 : mailSender
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 描述 : mailProperties
     */
    @Autowired
    private MailProperties mailProperties;

    /**
     * 描述 : rms
     */
    @Autowired
    private Rms rms;

    /**
     * 描述 : 发送邮件
     *
     * @param mailInfo 邮件信息
     * @throws MessagingException 异常
     */
    public void send(MailInfo mailInfo) throws MessagingException { //NOSONAR
        //创建消息
        MimeMessage message = mailSender.createMimeMessage();
        //判断是否有附件
        boolean multipart = CollectionUtils.isNotEmpty(mailInfo.getAttachments());
        //构造消息
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
        //必要字段
        helper.setFrom(mailProperties.getFrom());
        helper.setSentDate(new Date());
        helper.setTo(mailInfo.getTo());
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getText(), mailInfo.getIsHtmlText());
        //秘密抄送
        if (ArrayUtils.isNotEmpty(mailInfo.getBcc())) {
            helper.setBcc(mailInfo.getBcc());
        }
        //抄送
        if (ArrayUtils.isNotEmpty(mailInfo.getCc())) {
            helper.setCc(mailInfo.getCc());
        }
        //优先级
        if (mailInfo.getPriority() != null) {
            helper.setPriority(mailInfo.getPriority());
        }
        //邮件回执
        if (StringUtils.isNotBlank(mailInfo.getReplyTo())) {
            helper.setReplyTo(mailInfo.getReplyTo());
        }
        //处理静态资源
        if (CollectionUtils.isNotEmpty(mailInfo.getInlines())) {
            for (InlineInfo inlineInfo : mailInfo.getInlines()) {
                //判空
                if (StringUtils.isBlank(inlineInfo.getFileId())
                        || StringUtils.isBlank(inlineInfo.getName())) {
                    throw new MailException("inlineInfo fileid and name are not be null");
                }
                //获得必要参数
                String fileId = inlineInfo.getFileId();
                String name = inlineInfo.getName();
                //构造参数
                FileParam fileParam = new FileParam();
                fileParam.setId(fileId);
                //获得文件
                ResponseEntity<byte[]> file =
                        rms.call("FILE_3", fileParam, null, new ParameterizedTypeReference<byte[]>() {
                        }, null);
                //构造resource
                ByteArrayResource bar = new ByteArrayResource(file.getBody());
                //添加静态资源
                helper.addInline(name, bar);
            }
        }
        //处理附件
        if (CollectionUtils.isNotEmpty(mailInfo.getAttachments())) {
            for (AttachmentInfo attachment : mailInfo.getAttachments()) {
                if (StringUtils.isNotBlank(attachment.getFileId())) {
                    //获得必要参数
                    String fileId = attachment.getFileId();
                    String name = attachment.getName();
                    //构造参数
                    FileParam fileParam = new FileParam();
                    fileParam.setId(fileId);
                    //获得文件信息
                    ResponseEntity<RestResponse<FileInfo>> fileInfo = rms.call("FILE_4", fileParam, null,
                            new ParameterizedTypeReference<RestResponse<FileInfo>>() {
                            }, null);
                    //获得文件
                    ResponseEntity<byte[]> file =
                            rms.call("FILE_3", fileParam, null, new ParameterizedTypeReference<byte[]>() {
                            }, null);
                    //构造resource
                    ByteArrayResource bar = new ByteArrayResource(file.getBody());
                    //判断附件名称
                    name = StringUtils.isNoneBlank(name) ? name : fileInfo.getBody().getResult().getName();
                    //添加附件
                    helper.addAttachment(name, bar);
                }
            }
        }
        //发送消息
        mailSender.send(message);
    }

}
