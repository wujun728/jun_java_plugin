package org.wf.jwtp.provider;

import java.io.Serializable;
import java.util.Date;

/**
 * Token实体类
 * Created by wangfan on 2018-12-28 上午 9:21.
 */
public class Token implements Serializable {
    /**
     * 自增主键
     */
    private Integer tokenId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * access_token
     */
    private String accessToken;
    /**
     * refresh_token
     */
    private String refreshToken;
    /**
     * 签发时间
     */
    private Date issuedAt;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * refresh_token过期时间
     */
    private Date refreshTokenExpireTime;
    /**
     * 用户角色
     */
    private String[] roles;
    /**
     * 用户权限
     */
    private String[] permissions;

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public Date getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(Date refreshTokenExpireTime) {
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }
}
