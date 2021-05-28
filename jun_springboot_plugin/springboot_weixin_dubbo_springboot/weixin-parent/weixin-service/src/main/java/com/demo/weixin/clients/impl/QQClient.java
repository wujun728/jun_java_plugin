package com.demo.weixin.clients.impl;

import com.alibaba.fastjson.JSON;
import com.demo.weixin.clients.IQQClient;
import com.demo.weixin.entity.QQUser;
import com.qq.connect.QQConnectException;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.http.HttpClient;
import com.qq.connect.utils.http.PostParameter;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @description
 * @date 2017/7/27
 * @since 1.0
 */
@Component
public class QQClient implements IQQClient {

    @Override
    public QQUser getQQUser(String token, String openId) throws QQConnectException {
        HttpClient client = this.getHttpClient(token, openId);
        String response = client.get(QQConnectConfig.getValue("getUserInfoURL"),
                new PostParameter[]{new PostParameter("openid", openId),
                        new PostParameter("oauth_consumer_key", QQConnectConfig.getValue("app_ID")),
                        new PostParameter("access_token", client.getToken()), new PostParameter("format", "json")}).asJSONObject().toString();

        QQUser qqUser = JSON.parseObject(response, QQUser.class);
        qqUser.setOpenId(openId);
        return qqUser;
    }

    private HttpClient getHttpClient(String token, String openId) {
        HttpClient client = new HttpClient();
        client.setToken(token);
        client.setOpenID(openId);
        return client;
    }
}
