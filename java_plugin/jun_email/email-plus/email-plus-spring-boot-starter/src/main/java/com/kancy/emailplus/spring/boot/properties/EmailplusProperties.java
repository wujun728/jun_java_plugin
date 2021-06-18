package com.kancy.emailplus.spring.boot.properties;

import com.kancy.emailplus.core.EmailSenderProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * EmailProperties
 *
 * @author Wujun
 * @date 2020/2/20 2:26
 */
public class EmailplusProperties {
    /**
     * 是否开启
     */
    private boolean enabled = true;

    /**
     * 配置
     */
    private Configuration configuration = new Configuration();

    /**
     * key as sender name
     */
    private Map<String, EmailSenderProperties> sender = new HashMap<>();

    /**
     * 全局
     */
    private EmailDefinition global;

    /**
     * key equal ${namespace}-${topic}
     * value is EmailDefinition
     */
    private Map<String, EmailDefinition> emailDefinitions = new HashMap<>();

    /**
     * 通知
     */
    private Map<String, NoticeProperties> emailNotices = new HashMap<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public EmailDefinition getGlobal() {
        return global;
    }

    public void setGlobal(EmailDefinition global) {
        this.global = global;
    }

    public Map<String, EmailDefinition> getEmailDefinitions() {
        return emailDefinitions;
    }

    public void setEmailDefinitions(Map<String, EmailDefinition> emailDefinitions) {
        this.emailDefinitions = emailDefinitions;
    }

    public Map<String, EmailSenderProperties> getSender() {
        return sender;
    }

    public void setSender(Map<String, EmailSenderProperties> sender) {
        this.sender = sender;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Map<String, NoticeProperties> getEmailNotices() {
        return emailNotices;
    }

    public void setEmailNotices(Map<String, NoticeProperties> emailNotices) {
        this.emailNotices = emailNotices;
    }
}
