package com.demo.weixin.utils;

import com.demo.weixin.enums.WebchatAuthScope;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class WeixinUrlBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinUrlBuilder.class);

    public static String buildWeixinWebchatAuthUrl(String apiUrl, String appId, String redirectUri,
                                                   WebchatAuthScope webchatAuthScope, String state) {
        URIBuilder uriBuilder = getURIBuilder(apiUrl);
        uriBuilder.addParameter("appid", appId);
        //URIBuilder 会urlEncod
        uriBuilder.addParameter("redirect_uri", redirectUri);
        uriBuilder.addParameter("response_type", "code");
        webchatAuthScope = webchatAuthScope == null ? WebchatAuthScope.SNSAPI_BASE : webchatAuthScope;
        uriBuilder.addParameter("scope", webchatAuthScope.getWeixinValue());
        if (StringUtils.isNotBlank(state)) {
            uriBuilder.addParameter("state", state);
        }
        uriBuilder.setFragment("wechat_redirect");
        return uriBuilder.toString();

    }

    public static String buildQYWebchatAuthUrl(String apiUrl, String appId, Integer agentId, String redirectUri, String state) {
        URIBuilder uriBuilder = getURIBuilder(apiUrl);
        uriBuilder.addParameter("appid", appId);
        uriBuilder.addParameter("agentid", agentId.toString());
        //URIBuilder 会urlEncod
        uriBuilder.addParameter("redirect_uri", redirectUri);
        uriBuilder.addParameter("state", state);
        return uriBuilder.toString();
    }

    public static String encodeUrl(String url) {
        try {
            String encodedUrl = URLEncoder.encode(url, "utf-8");
            return encodedUrl;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("url is {}", url);
            LOGGER.error("url encode error", e);
            throw new IllegalArgumentException("url encode error:" + url);
        }
    }

    public static URIBuilder getURIBuilder(String url) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            return uriBuilder;
        } catch (URISyntaxException e) {
            LOGGER.error("url is {}", url);
            LOGGER.error("url 格式错误", e);
            throw new IllegalArgumentException("url is not valid:" + url);
        }
    }
}
