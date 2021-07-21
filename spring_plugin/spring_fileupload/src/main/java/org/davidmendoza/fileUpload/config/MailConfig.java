/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.davidmendoza.fileUpload.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Import(PropertyPlaceholderConfig.class)
public class MailConfig {

    @Value("${mail.smtp.host}")
    private String mailHost;
    @Value("${mail.smtp.password}")
    private String mailPassword;
    @Value("${mail.smtp.user}")
    private String mailUser;
    @Value("${mail.smtp.port}")
    private String mailPort;
    @Value("${mail.smtp.auth}")
    private String mailAuth;
    @Value("${mail.smtp.starttls.enable}")
    private String mailStarttls;
    @Value("${mail.smtp.socketFactory.class}")
    private String mailSocketFactory;
    @Value("${mail.debug}")
    private String mailDebug;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(mailHost);
        mailSender.setPassword(mailPassword);
        mailSender.setUsername(mailUser);
        mailSender.setPort(new Integer(mailPort));
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", mailAuth);
        properties.put("mail.smtp.starttls.enable", mailStarttls);
        properties.put("mail.smtp.socketFactory.class", mailSocketFactory);
        properties.put("mail.debug", mailDebug);
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}