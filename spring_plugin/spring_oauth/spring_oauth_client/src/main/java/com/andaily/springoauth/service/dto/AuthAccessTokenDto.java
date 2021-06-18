package com.andaily.springoauth.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 15-5-18
 * <p/>
 * http://localhost:8080/oauth/token?client_id=unity-client&client_secret=unity&grant_type=authorization_code&code=zLl170&redirect_uri=http%3a%2f%2flocalhost%3a8080%2funity%2fdashboard.htm
 *
 * @author Shengzhao Li
 */
public class AuthAccessTokenDto implements Serializable {


    private String accessTokenUri;

    private String clientId;

    private String clientSecret;

    private String grantType = "authorization_code";

    private String code;

    private String redirectUri;

    private String scope;
    private String username;
    private String password;


    public AuthAccessTokenDto() {
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public AuthAccessTokenDto setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public AuthAccessTokenDto setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public AuthAccessTokenDto setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getCode() {
        return code;
    }

    public AuthAccessTokenDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public AuthAccessTokenDto setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public AuthAccessTokenDto setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
        return this;
    }

    /*
    * http://localhost:8080/oauth/token?client_id=unity-client&client_secret=unity&grant_type=authorization_code&code=zLl170&redirect_uri=http%3a%2f%2flocalhost%3a8080%2funity%2fdashboard.htm
    * */
    public Map<String, String> getAuthCodeParams() {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("grant_type", grantType);

        map.put("redirect_uri", redirectUri);
        map.put("code", code);

        return map;
    }


    /*
   * http://localhost:8080/spring-oauth-server/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read,write&username=mobile&password=mobile
   * */
    public Map<String, String> getAccessTokenParams() {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("grant_type", grantType);
        map.put("scope", scope);

        map.put("username", username);
        map.put("password", password);

        return map;
    }

    /*
     * http://localhost:8080/spring-oauth-server/oauth/token?client_id=credentials-client&client_secret=credentials-secret&grant_type=client_credentials&scope=read,write
     */
    public Map<String, String> getCredentialsParams() {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("grant_type", grantType);
        map.put("scope", scope);
        return map;
    }
}
