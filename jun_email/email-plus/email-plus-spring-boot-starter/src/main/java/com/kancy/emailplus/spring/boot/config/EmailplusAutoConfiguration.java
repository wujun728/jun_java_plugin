package com.kancy.emailplus.spring.boot.config;

import com.kancy.emailplus.core.EmailSender;
import com.kancy.emailplus.core.EmailSenderSelector;
import com.kancy.emailplus.core.PollingEmailSenderSelector;
import com.kancy.emailplus.core.PooledEmailSender;
import com.kancy.emailplus.core.cryptor.PasswordCryptor;
import com.kancy.emailplus.core.cryptor.SimplePasswordCryptor;
import com.kancy.emailplus.spring.boot.listener.RefreshEmailSenderEventListener;
import com.kancy.emailplus.spring.boot.listener.SendEmailEventListener;
import com.kancy.emailplus.spring.boot.properties.EmailplusProperties;
import com.kancy.emailplus.spring.boot.service.EmailplusService;
import com.kancy.emailplus.spring.boot.service.TemplateService;
import com.kancy.emailplus.spring.boot.service.impl.EmailplusServiceImpl;
import com.kancy.emailplus.spring.boot.service.impl.FreemarkerTemplateServiceImpl;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * EmailplusAutoConfiguration
 *
 * @author kancy
 * @date 2020/2/20 22:45
 */
@ImportAutoConfiguration({EmailNoticeAutoConfiguration.class, EndpointAutoConfiguration.class})
public class EmailplusAutoConfiguration {

    @Bean
    @ConfigurationProperties("emailplus")
    public EmailplusProperties emailplusProperties(){
        return new EmailplusProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public EmailSenderSelector emailSenderSelector(){
        return new PollingEmailSenderSelector();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordCryptor passwordCryptor(){
        return new SimplePasswordCryptor();
    }

    @Bean
    public EmailSender emailSender(EmailSenderSelector emailSenderSelector, PasswordCryptor passwordCryptor){
        return new PooledEmailSender(emailSenderSelector, passwordCryptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public TemplateService freemarkerTemplateService(){
        return new FreemarkerTemplateServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public EmailplusService emailplusService(){
        return new EmailplusServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public SendEmailEventListener sendEmailEventListener(){
        return new SendEmailEventListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public RefreshEmailSenderEventListener refreshSenderEventListener(){
        return new RefreshEmailSenderEventListener();
    }
}
