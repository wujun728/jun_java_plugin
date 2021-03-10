package com.demo.weixin.service.impl;

import com.demo.weixin.api.ICacheService;
import com.demo.weixin.api.IWeixinService;
import com.demo.weixin.clients.IWeixinClient;
import com.demo.weixin.constants.WeixinUrlConstants;
import com.demo.weixin.entity.UserAccessToken;
import com.demo.weixin.entity.WeChatApplicationInfo;
import com.demo.weixin.entity.WeixinJsApiTicket;
import com.demo.weixin.entity.WeixinUser;
import com.demo.weixin.enums.ClientType;
import com.demo.weixin.enums.ProjectType;
import com.demo.weixin.enums.WebchatAuthScope;
import com.demo.weixin.exception.WeixinException;
import com.demo.weixin.utils.VerifyParamsUtils;
import com.qq.connect.utils.RandomStatusGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author Wujun
 * @description 微信服务实现
 * @date 2017/7/27
 * @since 1.0
 */
public class WeixinServiceImpl implements IWeixinService {


    // 微信用户信息redis的key
    public static final String WX_USER_PREFIX = "weixin_user_%s";

    @Resource
    private RedisTemplate<String, WeixinUser> redisTemplate;

    @Value("${wx_user_expire_minutes}")
    private int weixinUserExpireMinutes;

    // 微信应用相关list
    @Resource
    private List<WeChatApplicationInfo> weChatApplicationInfoList;

    @Resource
    private IWeixinClient weixinClient;

    @Resource
    private ICacheService cacheService;


    @Override
    public String getAuthUrl(ProjectType projectType, ClientType clientType, String fromUrl, WebchatAuthScope webchatAuthScope) throws UnsupportedEncodingException, WeixinException {
        VerifyParamsUtils.notNull(projectType, "调用的项目类型不能为空");
        VerifyParamsUtils.notNull(clientType, "调用的客户端类型不能为空");
        for (WeChatApplicationInfo info : weChatApplicationInfoList) {
            // 项目类型和客户端类型都匹配不上
            if (projectType != info.getProjectType() || clientType != info.getClientType()) {
                continue;
            }
            String authUrl = null;
            String state = RandomStatusGenerator.getUniqueState();
            String callBack_url = URLEncoder.encode(info.getAuthCallback(), "UTF-8");
            if (clientType == ClientType.WEB) {
                authUrl = String.format(WeixinUrlConstants.WX_PC_AUTH_LOCATION, info.getAppId(), callBack_url, state);
            } else if (clientType == ClientType.PUBLIC) {
                // 获取微信授权用户信息的类型
                String scope = webchatAuthScope == null ? "snsapi_base" : webchatAuthScope.getWeixinValue();
                authUrl = String.format(WeixinUrlConstants.WX_M_AUTH_LOCATION, info.getAppId(), callBack_url, scope, state);
            }

            // 保存授权成功后的回调地址
            cacheService.saveAuthCallBackUrl(fromUrl, state);
            return authUrl;
        }
        return null;
    }

    @Override
    public String getAccessToken(String appId, String appSecret) throws WeixinException, IOException, URISyntaxException {
        VerifyParamsUtils.hasText(appId, "appId不能为空");
        VerifyParamsUtils.hasText(appSecret, "appSecret不能为空");
        String accessToken = cacheService.getAccessToken(appId, appSecret);
        if (StringUtils.isBlank(accessToken)) {
            accessToken = weixinClient.getAccessToken(appId, appSecret).getAccessToken();
            cacheService.saveAccessToken(accessToken, appId, appSecret);
        }
        return accessToken;
    }

    @Override
    public String getJsApiTicket(String appId, String appSecret) throws IOException, URISyntaxException, WeixinException {
        VerifyParamsUtils.hasText(appId, "appId不能为空");
        VerifyParamsUtils.hasText(appSecret, "appSecret不能为空");

        String ticket = cacheService.getJsApiTicket(appId);
        if (StringUtils.isBlank(ticket)) {
            String accessToken = this.getAccessToken(appId, appSecret);
            WeixinJsApiTicket weixinJsApiTicket = weixinClient.getWeixinJsApiTicket(accessToken);
            ticket = cacheService.saveJsApiTicket(appId, weixinJsApiTicket.getTicket());
        }
        return ticket;
    }

    @Override
    public String getWeixinUserUnionId(ProjectType projectType, ClientType clientType, String code) throws IOException, URISyntaxException, WeixinException {
        VerifyParamsUtils.notNull(projectType, "调用的项目类型不能为空");
        VerifyParamsUtils.notNull(clientType, "调用的客户端类型不能为空");
        VerifyParamsUtils.hasText(code, "授权code不能为空");
        for (WeChatApplicationInfo info : weChatApplicationInfoList) {
            // 项目类型和客户端类型都匹配不上
            if (projectType != info.getProjectType() || clientType != info.getClientType()) {
                continue;
            }
            UserAccessToken userAccessToken = weixinClient.getUserAccessToken(info.getAppId(), info.getAppSecret(), code);
            WeixinUser weixinUser = weixinClient.getWeixinUserInfo(userAccessToken);

            // 存入redis
            this.saveWeixinUser(weixinUser);
            return weixinUser.getUnionid();
        }
        return null;
    }

    @Override
    public WeixinUser getWeixinUserByUnionId(String unionId) {
        if (StringUtils.isBlank(unionId)) {
            return null;
        }
        String wxUserKey = String.format(WX_USER_PREFIX, unionId);
        return redisTemplate.opsForValue().get(wxUserKey);
    }


    /**
     * 保存微信用户信息
     *
     * @param weixinUser
     */
    private void saveWeixinUser(WeixinUser weixinUser) {
        if (weixinUser == null || StringUtils.isEmpty(weixinUser.getUnionid())) {
            return;
        }
        String wxUserKey = String.format(WX_USER_PREFIX, weixinUser.getUnionid());
        redisTemplate.delete(wxUserKey);
        redisTemplate.opsForValue().set(wxUserKey, weixinUser);
        // 设置失效时间
        redisTemplate.expire(wxUserKey, weixinUserExpireMinutes, TimeUnit.MINUTES);
    }

}
