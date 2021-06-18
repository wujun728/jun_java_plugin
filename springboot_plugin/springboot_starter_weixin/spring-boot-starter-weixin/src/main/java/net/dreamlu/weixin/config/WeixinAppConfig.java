package net.dreamlu.weixin.config;

import com.jfinal.json.JacksonFactory;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.jfinal.wxaapp.WxaConfig;
import com.jfinal.wxaapp.WxaConfigKit;
import lombok.AllArgsConstructor;
import net.dreamlu.weixin.cache.SpringAccessTokenCache;
import net.dreamlu.weixin.properties.DreamWeixinProperties;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class WeixinAppConfig implements SmartInitializingSingleton {
    private final DreamWeixinProperties weixinProperties;
    private final SpringAccessTokenCache accessTokenCache;

    @Override
    public void afterSingletonsInstantiated() {
        boolean isdev = weixinProperties.isDevMode();
        ApiConfigKit.setDevMode(isdev);
        ApiConfigKit.setAccessTokenCache(accessTokenCache);
        List<DreamWeixinProperties.ApiConfig> list = weixinProperties.getWxConfigs();
        for (DreamWeixinProperties.ApiConfig apiConfig : list) {
            ApiConfig config = new ApiConfig();
            if (StrKit.notBlank(apiConfig.getAppId())) {
                config.setAppId(apiConfig.getAppId());
            }
            if (StrKit.notBlank(apiConfig.getAppSecret())) {
                config.setAppSecret(apiConfig.getAppSecret());
            }
            if (StrKit.notBlank(apiConfig.getToken())) {
                config.setToken(apiConfig.getToken());
            }
            if (StrKit.notBlank(apiConfig.getEncodingAesKey())) {
                config.setEncodingAesKey(apiConfig.getEncodingAesKey());
            }
            config.setEncryptMessage(apiConfig.isMessageEncrypt());
            ApiConfigKit.putApiConfig(config);
        }
        DreamWeixinProperties.WxaConfig apiConfig = weixinProperties.getWxaConfig();
        WxaConfig config = new WxaConfig();
        if (StrKit.notBlank(apiConfig.getAppId())) {
            config.setAppId(apiConfig.getAppId());
        }
        if (StrKit.notBlank(apiConfig.getAppSecret())) {
            config.setAppSecret(apiConfig.getAppSecret());
        }
        if (StrKit.notBlank(apiConfig.getToken())) {
            config.setToken(apiConfig.getToken());
        }
        if (StrKit.notBlank(apiConfig.getEncodingAesKey())) {
            config.setEncodingAesKey(apiConfig.getEncodingAesKey());
        }
        config.setMessageEncrypt(apiConfig.isMessageEncrypt());
        WxaConfigKit.setDevMode(isdev);
        WxaConfigKit.setWxaConfig(config);
        if (WxaMsgParser.JSON == weixinProperties.getWxaMsgParser()) {
            WxaConfigKit.useJsonMsgParser();
        }
        if ("jackson".equals(weixinProperties.getJsonType())) {
            JsonUtils.setJsonFactory(JacksonFactory.me());
        }
    }

}
