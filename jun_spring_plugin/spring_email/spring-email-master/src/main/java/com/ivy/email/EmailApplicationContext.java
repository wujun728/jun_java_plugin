package com.ivy.email;


import com.ivy.email.config.MailConfig;
import com.ivy.email.constant.EmailConstant;
import com.google.common.base.Charsets;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Properties;


/**
 * Created by fangjie04 on 2016/12/1.
 */

@Configuration
@ComponentScan(value = {"com.ivy.email"})
@PropertySource(value = {"classpath:properties/base.properties",
        "classpath:properties/email-env.properties", "classpath:properties/template-env.properties"})
public class EmailApplicationContext {

    @Autowired
    private MailConfig mailConfig;

    @Bean
    public JavaMailSender mailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailConfig.getMailHost());
        mailSender.setPort(mailConfig.getMailPort());
        mailSender.setUsername(mailConfig.getMailUserName());
        mailSender.setPassword(mailConfig.getMailPassWd());
        // 设置邮件编码
        mailSender.setDefaultEncoding(Charsets.UTF_8.toString());
        mailSender.setProtocol(EmailConstant.SMTP);

        // smtp服务器验证
        Properties properties = new Properties();
        // 设置需要身份认证
        properties.put("mail.smtp.auth", Boolean.TRUE);
        // 邮件传输协议
        properties.put("mail.transport.protocol", EmailConstant.SMTP);
        // 始终使用安全设置
        properties.put("mail.smtp.starttls.enable", Boolean.TRUE);
        // 邮件超时时间
        properties.put("mail.smtp.timeout", EmailConstant.MAIL_SMTP_TIMEOUT);

        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

    /**
     * velocity模版引擎
     *
     * @return
     */
    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, EmailConstant.CLASSPATH);
        engine.setProperty(EmailConstant.CLASSPATH_RESOURCE_LOADER_CLASS, ClasspathResourceLoader.class.getName());
        engine.init();
        return engine;
    }

    /**
     * 加载thymeleaf模版,从类路径下去加载
     *
     * @return
     */
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding(Charsets.UTF_8.toString());
        return resolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        // 将加载模版类注册到springTemplateEngine中，在加载Thymeleaf时使用
        springTemplateEngine.addTemplateResolver(emailTemplateResolver());
        return springTemplateEngine;
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(EmailConstant.CORE_POOL_SIZE);
        threadPoolTaskExecutor.setMaxPoolSize(EmailConstant.MAX_POOL_SIZE);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        threadPoolTaskExecutor.setKeepAliveSeconds(EmailConstant.KEEP_ALIVE_SECONDS);
        return threadPoolTaskExecutor;
    }
}
