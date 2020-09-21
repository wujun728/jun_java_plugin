/**
 * MailProperties.java
 * Created at 2016-11-19
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 描述 : MailProperties
 *
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "org.itkk.mail.properties")
@Data
public class MailProperties implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 发送地址
     */
    private String from;

}
