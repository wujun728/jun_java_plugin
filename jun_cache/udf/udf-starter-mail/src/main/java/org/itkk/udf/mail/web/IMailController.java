/**
 * IMailController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.mail.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.domain.mail.MailInfo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.validation.Valid;

/**
 * 描述 : IMailController
 *
 * @author Administrator
 */
@Api(value = "邮件服务", consumes = "application/json", produces = "application/json",
        protocols = "http")
@RequestMapping(value = "/service/mail")
public interface IMailController { //NOSONAR

    /**
     * 描述 : 发送邮件
     *
     * @param mailInfo 邮件信息
     * @param result   校验结果
     * @return 发送结果
     * @throws MessagingException 异常
     */
    @ApiOperation(value = "MAIL_1", notes = "发送邮件<br />注意:请填写正常内容,不要有test,helloword之类的词,否则会被设定为垃圾邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称",
                    required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true,
                    dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号",
                    required = true, dataType = "string")})
    @RequestMapping(method = RequestMethod.POST)
    RestResponse<String> send(@Valid @RequestBody MailInfo mailInfo, BindingResult result)
            throws MessagingException;

}
