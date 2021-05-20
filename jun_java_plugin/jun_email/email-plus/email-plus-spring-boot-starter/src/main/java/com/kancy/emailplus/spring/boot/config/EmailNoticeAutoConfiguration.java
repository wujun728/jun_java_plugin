package com.kancy.emailplus.spring.boot.config;

import com.kancy.emailplus.spring.boot.aop.EmailNoticeAspect;
import com.kancy.emailplus.spring.boot.aop.EmailNoticeTrigger;
import com.kancy.emailplus.spring.boot.aop.PollingCountEmailNoticeTrigger;
import com.kancy.emailplus.spring.boot.aop.RamBucketEmailNoticeTrigger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * EmailNoticeAutoConfiguration
 *
 * @author Wujun
 * @date 2020/2/22 22:03
 */
@Import({RedisBucketEmailNoticeTriggerConfiguration.class})
public class EmailNoticeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EmailNoticeAspect emailNoticeAspect(){
        return new EmailNoticeAspect();
    }

    @Bean
    public EmailNoticeTrigger ramBucketEmailNoticeTrigger(){
        return new RamBucketEmailNoticeTrigger();
    }
    @Bean
    public EmailNoticeTrigger pollingCountEmailNoticeTrigger(){
        return new PollingCountEmailNoticeTrigger();
    }

}
