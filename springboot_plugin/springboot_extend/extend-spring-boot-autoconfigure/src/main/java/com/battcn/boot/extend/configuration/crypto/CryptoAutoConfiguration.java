package com.battcn.boot.extend.configuration.crypto;

import com.battcn.boot.extend.configuration.crypto.advice.DecryptRequestBodyAdvice;
import com.battcn.boot.extend.configuration.crypto.advice.EncryptResponseBodyAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;

/**
 * @author Levin
 * @since 2018/12/28 0028
 */
@Configuration
@Import({DecryptRequestBodyAdvice.class, EncryptResponseBodyAdvice.class})
@ConditionalOnProperty(prefix = CRYPTO, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
@EnableConfigurationProperties(CryptoProperties.class)
public class CryptoAutoConfiguration {


}
