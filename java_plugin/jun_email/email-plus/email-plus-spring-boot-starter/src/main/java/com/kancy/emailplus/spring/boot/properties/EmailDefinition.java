package com.kancy.emailplus.spring.boot.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * EmailMetadata
 *
 * @author Wujun
 * @date 2020/2/20 2:23
 */
public class EmailDefinition {
    /**
     * 是否开启
     */
    private boolean enabled = true;
    /**
     * 收件人
     */
    private String[] to;
    /**
     * 抄送人
     */
    private String[] cc;
    /**
     * 默认的邮件主题
     */
    private String subject;
    /**
     * 默认的邮件内容
     */
    private String content;
    /**
     * 是否html格式
     */
    private boolean html;
    /**
     * 异步发送
     */
    private boolean async;
    /**
     * freemarker模板
     */
    private String template;
    /**
     * 模板自定义数据
     */
    private Map<String, Object> data = new HashMap();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
