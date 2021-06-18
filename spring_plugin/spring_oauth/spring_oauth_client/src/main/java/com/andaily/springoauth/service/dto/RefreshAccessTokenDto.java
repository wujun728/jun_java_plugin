package com.andaily.springoauth.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * http://localhost:8080/spring-oauth-server/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=b36f4978-a172-4aa8-af89-60f58abe3ba1
 *
 * @author Shengzhao Li
 */
public class RefreshAccessTokenDto implements Serializable {

    private String refreshAccessTokenUri;
    private String clientId;
    private String clientSecret;

    private String grantType = "refresh_token";

    private String refreshToken;


    public RefreshAccessTokenDto() {
    }

    public String getRefreshAccessTokenUri() {
        return refreshAccessTokenUri;
    }

    public void setRefreshAccessTokenUri(String refreshAccessTokenUri) {
        this.refreshAccessTokenUri = refreshAccessTokenUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /*
    * http://localhost:8080/spring-oauth-server/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=b36f4978-a172-4aa8-af89-60f58abe3ba1
    * */
    public Map<String, String> getRefreshTokenParams() {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", clientId);

        map.put("client_secret", clientSecret);
        map.put("grant_type", grantType);
        map.put("refresh_token", refreshToken);

        return map;
    }

}