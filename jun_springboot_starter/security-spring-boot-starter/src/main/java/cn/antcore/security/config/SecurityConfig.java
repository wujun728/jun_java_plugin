package cn.antcore.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Security 配置文件
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
@Component
@ConfigurationProperties(prefix = "spring.security")
public class SecurityConfig {

    /** Session失效时间 **/
    private Duration sessionTime = Duration.of(24, ChronoUnit.HOURS);
    
    /** Session再每次请求后自动续签 **/
    private boolean automaticRenewal = true;
    
    /** 用户最大同时存活数 **/
    private int maxLive = 1;

    public Duration getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Duration sessionTime) {
        this.sessionTime = sessionTime;
    }

    public boolean isAutomaticRenewal() {
        return automaticRenewal;
    }

    public void setAutomaticRenewal(boolean automaticRenewal) {
        this.automaticRenewal = automaticRenewal;
    }

    public int getMaxLive() {
        return maxLive;
    }

    public void setMaxLive(int maxLive) {
        this.maxLive = maxLive;
    }
}
