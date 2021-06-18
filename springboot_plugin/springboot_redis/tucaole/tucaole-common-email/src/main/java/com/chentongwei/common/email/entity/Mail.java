package com.chentongwei.common.email.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 邮件
 */
@Data
public class Mail implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 邮件标题 */
    private String title;
    /** 邮件内容 */
    private String content;
    /** 内容格式(默认html) */
    private String contentType;
    /** 接收邮件地址 */
    private String to;
}
