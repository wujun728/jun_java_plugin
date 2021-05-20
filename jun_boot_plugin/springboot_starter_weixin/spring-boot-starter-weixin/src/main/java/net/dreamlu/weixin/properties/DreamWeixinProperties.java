package net.dreamlu.weixin.properties;

import lombok.Getter;
import lombok.Setter;
import net.dreamlu.weixin.config.WxaMsgParser;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties("dream.weixin")
public class DreamWeixinProperties {

    /**
     * 拦截的路由，默认：/weixin/*
     */
    private String urlPatterns = "/weixin/*";
    /**
     * 是否开发模式，默认：false
     */
    private boolean devMode = false;
    /**
     * Spring cache name，需要开启spring cache，默认：dreamWeixinCache
     */
    private String accessTokenCache = "dreamWeixinCache";
    /**
     * 多公众号url挂参，默认：appId
     */
    private String appIdKey = "appId";
    /**
     * 多公众号配置
     */
    private List<ApiConfig> wxConfigs = new ArrayList<>();
    /**
     * 小程序配置
     */
    private WxaConfig wxaConfig = new WxaConfig();
    /**
     * 小程序消息解析，默认xml，支持json和xml
     */
    private WxaMsgParser wxaMsgParser = WxaMsgParser.XML;
    /**
     * json 类型，默认为 boot 的 jackson，可配置成 jfinal，使用jfinal默认规则
     */
    private String jsonType = "jackson";

    @Getter
    @Setter
    public static class ApiConfig {
        private String token;
        private String appId;
        private String appSecret;
        private String encodingAesKey;
        private boolean messageEncrypt = false;    // 消息加密与否
    }

    @Getter
    @Setter
    public static class WxaConfig {
        private String appId;
        private String appSecret;
        private String token;
        private String encodingAesKey;
        private boolean messageEncrypt = false;    // 消息加密与否
    }
}
