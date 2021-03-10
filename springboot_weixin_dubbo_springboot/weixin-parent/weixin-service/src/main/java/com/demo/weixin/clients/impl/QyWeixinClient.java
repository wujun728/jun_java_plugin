package com.demo.weixin.clients.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.demo.weixin.clients.IQyWeixinClient;
import com.demo.weixin.entity.*;
import com.demo.weixin.entity.message.BasicMessage;
import com.demo.weixin.exception.WeixinException;
import com.demo.weixin.request.handlers.CreateMenuRequestHandler;
import com.demo.weixin.request.handlers.CreateUserRequestHandler;
import com.demo.weixin.request.handlers.UpdateUserRequestHandler;
import com.demo.weixin.response.handlers.BasicWeixinResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.demo.weixin.constants.WeixinUrlConstants.*;

/**
 * @author Wujun
 * @description 企业微信客户端
 * @date 2017/7/28
 * @since 1.0
 */
@Component
public class QyWeixinClient implements IQyWeixinClient {

    private HttpClient httpClient;

    public QyWeixinClient() {
        httpClient = HttpClientBuilder.create().build();
    }

    public QyWeixinClient(HttpClient httpClient) {
        this.httpClient = httpClient == null ? HttpClientBuilder.create().build() : httpClient;
    }

    @Override
    public QYAccessToken getQYAccessToken(String corpId, String corpSecret) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_ACCESS_TOKEN);
        uriBuilder.addParameter("corpid", corpId);
        uriBuilder.addParameter("corpsecret", corpSecret);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(QYAccessToken.class));
    }

    @Override
    public WeixinJsApiTicket getWeixinQyJsApiTicket(String accessToken) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_JSAPI_TICKET);
        uriBuilder.addParameter("access_token", accessToken);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(WeixinJsApiTicket.class));
    }

    @Override
    public BaseWeixinResponse createDept(String accessToken, String name, String parentId) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_CREATE_DEPT);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("name", name);
        uriBuilder.addParameter("parentid", parentId);
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        return httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(BaseWeixinResponse.class));
    }

    @Override
    public QYDept getDeptList(String accessToken, Integer deptId) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_GET_DEPT);
        uriBuilder.addParameter("access_token", accessToken);
        if (deptId != null) {
            uriBuilder.addParameter("id", deptId.toString());
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(QYDept.class));
    }

    @Override
    public BaseWeixinResponse createMenu(String accessToken, CreateMenuRequestHandler createMenuRequestHandler) throws URISyntaxException, IOException, WeixinException {
        URIBuilder uriBuilder = new URIBuilder(QY_CREATE_MENU);
        uriBuilder.addParameter("access_token", accessToken);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        String jsonString = JSON.toJSONString(createMenuRequestHandler.getParamMap());
        httpPost.setEntity(new StringEntity(jsonString, "utf-8"));
        return httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(BaseWeixinResponse.class));
    }

    @Override
    public List<QYMenu> getMenu(String accessToken, Integer agentId) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_GET_MENU);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("agentid", agentId.toString());
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        JSONObject jsonObject = httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(JSONObject.class));
        String menuString = JSON.toJSONString(jsonObject.get("button"));

        List<QYMenu> qyMenuList = JSON.parseObject(menuString, new TypeReference<List<QYMenu>>() {
        });

        return qyMenuList;
    }

    @Override
    public BaseWeixinResponse delMenu(String accessToken, Integer agentId) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_DEL_MENU);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("agentid", agentId.toString());
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(BaseWeixinResponse.class));
    }


    // ##################################  消息推送 ####################################

    @Override
    public <T extends BasicMessage> QYMessage sendMessage(String accessToken, T sendMessageHandler) throws URISyntaxException, IOException, WeixinException {

        URIBuilder uriBuilder = new URIBuilder(QY_SEND_MESSAGE);
        uriBuilder.addParameter("access_token", accessToken);
        String jsonString = JSON.toJSONString(sendMessageHandler.getParamMap());

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setEntity(new StringEntity(jsonString, "utf-8"));
        return httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(QYMessage.class));
    }

    // ################################# 通讯录管理 #####################################
    @Override
    public BaseWeixinResponse createUser(String accessToken, CreateUserRequestHandler createUserRequestHandler) throws IOException, URISyntaxException, WeixinException {
        URIBuilder uriBuilder = new URIBuilder(QY_CREATE_USER);
        uriBuilder.addParameter("access_token", accessToken);
        String jsonString = JSON.toJSONString(createUserRequestHandler.getParamMap());

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setEntity(new StringEntity(jsonString, "utf-8"));
        return httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(BaseWeixinResponse.class));
    }

    @Override
    public BaseWeixinResponse updateUser(String accessToken, UpdateUserRequestHandler updateUserRequestHandler) throws IOException, URISyntaxException, WeixinException {

        URIBuilder uriBuilder = new URIBuilder(QY_UPDATE_USER);
        uriBuilder.addParameter("access_token", accessToken);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        String jsonString = JSON.toJSONString(updateUserRequestHandler.getParamMap());
        httpPost.setEntity(new StringEntity(jsonString, "utf-8"));
        return httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(BaseWeixinResponse.class));
    }

    @Override
    public BaseWeixinResponse deleteUser(String accessToken, String userId) throws URISyntaxException, IOException {

        URIBuilder uriBuilder = new URIBuilder(QY_DELETE_USER);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("userid", userId);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(BaseWeixinResponse.class));
    }

    @Override
    public BaseWeixinResponse batchDeleteUser(String accessToken, List<String> userIdList) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(QY_BATCH_DELETE_USER);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("useridlist", JSON.toJSONString(userIdList));

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        return httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(BaseWeixinResponse.class));
    }

    @Override
    public QYUserDetail getQYUserDetail(String accessToken, String userId) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_GET_USER);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("userid", userId);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(QYUserDetail.class));
    }

    @Override
    public QYUserList getDeptQYUserList(String accessToken, Integer deptId, Boolean fetchChild) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_DEPT_USER_LIST);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("department_id", deptId.toString());
        if (fetchChild != null) {
            uriBuilder.addParameter("fetch_child", fetchChild ? "1" : "0");
        }

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(QYUserList.class));
    }

    @Override
    public QYUserList getDeptUserDetailList(String accessToken, Integer deptId, Boolean fetchChild) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_DEPT_USER_DETAIL_LIST);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("department_id", deptId.toString());
        if (fetchChild != null) {
            uriBuilder.addParameter("fetch_child", fetchChild ? "1" : "0");
        }

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(QYUserList.class));
    }

    @Override
    public String userIdConvertToOpenId(String accessToken, String openId, Integer agentId) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_CONVERT_TO_OPENID);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("openid", openId);
        if (agentId != 0) {
            uriBuilder.addParameter("agentid", agentId.toString());
        }

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        JSONObject jsonObject = httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(JSONObject.class));
        return JSON.toJSONString(jsonObject.get("openid"));
    }

    @Override
    public String openIdConvertToUserId(String accessToken, String userId) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_CONVERT_TO_USERID);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("userid", userId);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        JSONObject jsonObject = httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(JSONObject.class));
        return JSON.toJSONString(jsonObject.get("userid"));
    }

    @Override
    public QYLoginUserInfo getLoginUserInfo(String accessToken, String authCode) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(QY_LOGIN_USER_INFO);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("code", authCode);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet, new BasicWeixinResponseHandler<>(QYLoginUserInfo.class));
    }

    @Override
    public QYUserDetail getLoginUserDetail(String accessToken, String userTicket) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(QY_LOGIN_USER_DETAIL);
        uriBuilder.addParameter("access_token", accessToken);
        uriBuilder.addParameter("user_ticket", userTicket);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        return httpClient.execute(httpPost, new BasicWeixinResponseHandler<>(QYUserDetail.class));
    }
}
