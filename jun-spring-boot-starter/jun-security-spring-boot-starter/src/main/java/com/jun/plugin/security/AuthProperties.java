package com.jun.plugin.security;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 *  映射配置类--映射yml里面的配置
 * @since 2023-01-06-9:33
 **/
@ConfigurationProperties(prefix = "jun-security")
public class AuthProperties {

    private String storeType = "redis";

    private String tokenName = "token";

    private Integer timeout = 3600;

    private String tokenStyle = "uuid";

    private String tokenPrefix;

    private String tableName = "s_auth_token";

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getTokenStyle() {
        return tokenStyle;
    }

    public void setTokenStyle(String tokenStyle) {
        this.tokenStyle = tokenStyle;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
