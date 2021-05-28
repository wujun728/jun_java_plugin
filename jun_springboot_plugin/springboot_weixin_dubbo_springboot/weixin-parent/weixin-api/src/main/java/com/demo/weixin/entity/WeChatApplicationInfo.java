package com.demo.weixin.entity;

import com.demo.weixin.enums.ClientType;
import com.demo.weixin.enums.ProjectType;

/**
 * 微信应用/公众号的相关信息
 */
public class WeChatApplicationInfo {
    /**
     * 微信应用编号
     */
    private String appId;

    /**
     * 微信应用密钥
     */
    private String appSecret;

    /**
     * 微信账号的项目类型
     */
    private ProjectType projectType;

    /**
     * 微信应用的类型
     */
    private ClientType clientType;

    /**
     * 授权后的回调页面
     */
    private String authCallback;

    /**
     * 在企业微信上的应用ID
     */
    private Integer qyAgentId;

    /**
     * 在企业微信上的应用密钥
     */
    private String qyAppSecret;

    /**
     * 在企业微信授权成功后的回调地址
     */
    private String qyAuthCallBack;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getAuthCallback() {
        return authCallback;
    }

    public void setAuthCallback(String authCallback) {
        this.authCallback = authCallback;
    }

    public Integer getQyAgentId() {
        return qyAgentId;
    }

    public void setQyAgentId(Integer qyAgentId) {
        this.qyAgentId = qyAgentId;
    }

    public String getQyAuthCallBack() {
        return qyAuthCallBack;
    }

    public void setQyAuthCallBack(String qyAuthCallBack) {
        this.qyAuthCallBack = qyAuthCallBack;
    }

    public String getQyAppSecret() {
        return qyAppSecret;
    }

    public void setQyAppSecret(String qyAppSecret) {
        this.qyAppSecret = qyAppSecret;
    }
}
