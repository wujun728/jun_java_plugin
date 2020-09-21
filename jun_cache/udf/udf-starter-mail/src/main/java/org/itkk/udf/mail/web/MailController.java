/**
 * MailController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.mail.web;

import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.domain.mail.MailInfo;
import org.itkk.udf.core.exception.ParameterValidException;
import org.itkk.udf.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

/**
 * 描述 : MailController
 *
 * @author Administrator
 */
@RestController
public class MailController implements IMailController {

    /**
     * 描述 : mailService
     */
    @Autowired
    private MailService mailService;

    @Override
    public RestResponse<String> send(@Valid @RequestBody MailInfo mailInfo, BindingResult result)
            throws MessagingException {
        //校验
        if (result.hasErrors()) {
            throw new ParameterValidException("校验失败",
                    result.getAllErrors());
        }
        //发送
        mailService.send(mailInfo);
        //返回
        return new RestResponse<>();
    }

}
