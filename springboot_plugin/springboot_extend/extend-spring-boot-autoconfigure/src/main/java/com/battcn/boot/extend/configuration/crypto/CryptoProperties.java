package com.battcn.boot.extend.configuration.crypto;

import com.battcn.boot.extend.configuration.crypto.encrypt.CryptoType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.CRYPTO;

/**
 * @author Levin
 * @since 2018/12/27 0027
 */
@Data
@ConfigurationProperties(CRYPTO)
public class CryptoProperties {

    private Boolean enabled;
    private Encrypt encrypt;
    private Decrypt decrypt;
    private String encoding = "UTF-8";

    @Data
    public static class Encrypt {
        @NotNull
        private String key;
        @NotNull
        private CryptoType type;
    }

    @Data
    public static class Decrypt {

        @NotNull
        private String key;
        @NotNull
        private CryptoType type;
    }


}
