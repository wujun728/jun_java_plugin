package com.chentongwei.email.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description: 邮件实体
 *
 * @author Wujun
 * @Project common-boot-email
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

    /*************模板发送****************/
    /** 模板名称 */
    private String templateName;
    /** 模板变量替换 */
    private Map<String, Object> maps;
}
