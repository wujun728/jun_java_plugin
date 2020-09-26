package com.kancy.emailplus.spring.boot.message;

import com.kancy.emailplus.core.AbstractEmail;
import com.kancy.emailplus.core.exception.EmailException;
import com.kancy.emailplus.spring.boot.config.ApplicationContextHolder;
import com.kancy.emailplus.spring.boot.properties.EmailDefinition;
import com.kancy.emailplus.spring.boot.properties.EmailplusProperties;
import com.kancy.emailplus.spring.boot.service.TemplateService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * EmailMessage
 *
 * @author kancy
 * @date 2020/2/20 23:33
 */
public class EmailMessage extends AbstractEmail {

    private String content;

    private transient Map<String, Object> templateData;

    private String emailKey;

    public EmailMessage(String emailKey) {
        this.emailKey = emailKey;
    }

    /**
     * 异步发送
     * @return
     */
    public boolean isAsync() {
        return getEmailDefinition().isAsync();
    }

    /**
     * 收件人
     *
     * @return
     */
    @Override
    public String[] getTo() {
        return getEmailDefinition().getTo();
    }

    /**
     * 抄送
     *
     * @return
     */
    @Override
    public String[] getCc() {
        return getEmailDefinition().getCc();
    }

    /**
     * 主题
     *
     * @return
     */
    @Override
    public String getSubject() {
        return getEmailDefinition().getSubject();
    }

    public String getEmailKey() {
        return emailKey;
    }

    /**
     * 邮件内容
     */
    @Override
    public String getContent() {
        EmailDefinition emailDefinition = getEmailDefinition();
        if (StringUtils.isEmpty(content) && !StringUtils.isEmpty(emailDefinition.getTemplate())){
            try {
                Map<String, Object> data = new HashMap<>();
                if (!CollectionUtils.isEmpty(templateData)){
                    data.putAll(templateData);
                }
                if (!CollectionUtils.isEmpty(emailDefinition.getData())){
                    data.putAll(emailDefinition.getData());
                }
                Optional<String> render = ApplicationContextHolder.getApplicationContext().getBean(TemplateService.class)
                        .render(emailDefinition.getTemplate(), data);
                if (render.isPresent()){
                    content = render.get();
                }
            } catch (Exception e) {
                throw new EmailException(String.format("render email template [%s] fail", emailDefinition.getTemplate()), e);
            }
        }

        if (StringUtils.isEmpty(content)){
            content = getEmailDefinition().getContent();
        }

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
    }

    public void putTemplateData(String key, Object value) {
        if (Objects.isNull(templateData)){
            templateData = new HashMap<>();
        }
        templateData.put(key, value);
    }

    public static EmailMessage create(String emailKey){
        return new EmailMessage(emailKey);
    }

    /**
     * 获取emailkey对应的EmailDefinition
     * @return
     */
    private EmailDefinition getEmailDefinition() {
        return  ApplicationContextHolder.getApplicationContext()
                .getBean(EmailplusProperties.class)
                .getEmailDefinitions().getOrDefault(emailKey, new EmailDefinition());
    }
}
