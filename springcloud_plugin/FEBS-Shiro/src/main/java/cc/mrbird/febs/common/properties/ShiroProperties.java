package cc.mrbird.febs.common.properties;

import lombok.Data;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author MrBird
 */
@Data
public class ShiroProperties {

    /**
     * session 超时时间，单位为秒
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration sessionTimeout = Duration.ofSeconds(86400);
    /**
     * rememberMe cookie有效时长，单位为秒
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration cookieTimeout = Duration.ofSeconds(604800);
    /**
     * 免认证的路径配置，如静态资源等
     */
    private String anonUrl;
    /**
     * 登录 url
     */
    private String loginUrl;
    /**
     * 首页 url
     */
    private String successUrl;
    /**
     * 登出 url
     */
    private String logoutUrl;
    /**
     * 未授权跳转 url
     */
    private String unauthorizedUrl;
}
