package com.demo.weixin.clients.impl;

import com.alibaba.fastjson.JSON;
import com.demo.weixin.clients.IWeixinClient;
import com.demo.weixin.entity.*;
import com.demo.weixin.enums.WebchatAuthScope;
import com.demo.weixin.response.handlers.BasicWeixinResponseHandler;
import com.demo.weixin.response.handlers.ImageWeixinResponseHandler;
import com.demo.weixin.utils.WeixinUrlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.demo.weixin.constants.WeixinUrlConstants.*;

/**
 * 微信公众号客户端
 * <p/>
 * 每个api的url，都有默认值同时也可以自行配置
 */
@Component
public class WeixinClient implements IWeixinClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private HttpClient httpClient;

    public WeixinClient() {
        httpClient = HttpClientBuilder.create().build();
    }

    public WeixinClient(HttpClient httpClient) {
        this.httpClient = httpClient == null ? HttpClientBuilder.create().build() : httpClient;
    }

    @Override
    public String getWeixinWebchatAuthUrl(String appId, String redirectUri, WebchatAuthScope scope, String state) {
        if (StringUtils.isEmpty(appId)) {
            throw new IllegalArgumentException("missing param appId");
        }
        if (StringUtils.isEmpty(redirectUri)) {
            throw new IllegalArgumentException("missing param redirectUri");
        }
        return WeixinUrlBuilder.buildWeixinWebchatAuthUrl(WX_AUTH_URL, appId, redirectUri, scope, state);
    }

    /**
     * 获取微信token
     *
     * @return 微信token
     * @throws URISyntaxException
     * @throws IOException
     */
    @Override
    public WeixinAccessToken getAccessToken(String appId, String appSecret) throws URISyntaxException, IOException {
        logger.debug("get access token with parameters- appId:{}, grantType:{}", appId);
        URIBuilder uriBuilder = new URIBuilder(WX_ACCESS_TOKEN);
        uriBuilder.addParameter("appid", appId);
        uriBuilder.addParameter("secret", appSecret);
        uriBuilder.addParameter("grant_type", "client_credential");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(WeixinAccessToken.class));
    }

    @Override
    public WeixinJsApiTicket getWeixinJsApiTicket(String accessToken) throws URISyntaxException, IOException {
        logger.debug("get weixin js api ticket with parameters - {}", accessToken);
        URIBuilder uriBuilder = new URIBuilder(WX_JSAPI_TICKET);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("type", "jsapi");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(WeixinJsApiTicket.class));
    }



    @Override
    public UserAccessToken getUserAccessToken(String appId, String appSecret, String code) throws URISyntaxException, IOException {
        logger.debug("get weixin user access token with parameters - {}", code);
        URIBuilder uriBuilder = new URIBuilder(WX_USER_ACCESS_TOKEN);
        uriBuilder.addParameter("appid", appId);
        uriBuilder.addParameter("secret", appSecret);
        uriBuilder.addParameter("code", code);
        uriBuilder.addParameter("grant_type", "authorization_code");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(UserAccessToken.class));
    }

    @Override
    public WeixinUser getWeixinUserInfo(UserAccessToken accessToken) throws URISyntaxException, IOException {
        logger.debug("get weixin user info with parameters - {}", accessToken);
        URIBuilder uriBuilder = new URIBuilder(WX_USER_INFO);
        uriBuilder.addParameter("access_token", accessToken.getAccessToken());
        uriBuilder.addParameter("openid", accessToken.getOpenId());
        uriBuilder.addParameter("lang", "zh_CN");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(WeixinUser.class));
    }

    @Override
    public byte[] downloadPhoto(String accessToken, String mediaId) throws URISyntaxException, IOException {
        logger.debug("download photo mediaId:{}", mediaId);
        URIBuilder uriBuilder = new URIBuilder(WX_DOWNLOAD_PHOTO);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("media_id", mediaId);
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        return httpClient.execute(httpGet, new ImageWeixinResponseHandler());
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public BaseWeixinResponse createMenu(String accessToken, List<WeixinMenu> menuList) throws URISyntaxException, IOException {
        logger.debug("create menu with parameters - {} ,- {}", accessToken, menuList);

        URIBuilder uriBuilder = new URIBuilder(WX_CREATE_MENU);
        uriBuilder.addParameter("access_token", accessToken);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        StringBuilder menu = new StringBuilder("{\"button\":");
        menu.append(JSON.toJSONString(menuList));
        menu.append("}");

        httpPost.setEntity(new StringEntity(menu.toString(), "utf-8"));
        return httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(BaseWeixinResponse.class));
    }
}
