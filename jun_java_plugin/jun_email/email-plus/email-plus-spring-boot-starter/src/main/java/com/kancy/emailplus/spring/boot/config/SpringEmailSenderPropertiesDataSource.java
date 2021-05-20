package com.kancy.emailplus.spring.boot.config;

import com.kancy.emailplus.core.EmailSenderProperties;
import com.kancy.emailplus.core.EmailSenderPropertiesDataSource;
import com.kancy.emailplus.core.annotation.Order;
import com.kancy.emailplus.spring.boot.properties.EmailplusProperties;
import org.springframework.context.ApplicationContext;

import java.util.*;

/**
 * SpringMailPropertiesDataSource
 *
 * @author Wujun
 * @date 2020/2/21 1:16
 */
@Order(Integer.MIN_VALUE + 10)
public class SpringEmailSenderPropertiesDataSource implements EmailSenderPropertiesDataSource {
    /**
     * 加载属性
     *
     * @return
     */
    @Override
    public List<EmailSenderProperties> load() {
        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
        if (Objects.nonNull(applicationContext)){
            EmailplusProperties emailplusProperties = applicationContext.getBean(EmailplusProperties.class);
            Map<String, EmailSenderProperties> senderProperties = emailplusProperties.getSender();
            if (Objects.nonNull(senderProperties)){
                senderProperties.forEach((k,v) ->{
                    if (Objects.isNull(v.getName())){
                        v.setName(k);
                    }
                });
                return new ArrayList<>(senderProperties.values());
            }
        }
        return Collections.emptyList();
    }
}
