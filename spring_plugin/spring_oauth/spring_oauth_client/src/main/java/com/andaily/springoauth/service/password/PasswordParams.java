package com.andaily.springoauth.service.password;

import java.io.Serializable;

/**
 * 'password'  params
 *
 * @author Shengzhao Li
 */
public class PasswordParams implements Serializable {

    //Fixed  value
    public static final String GRANT_TYPE = "password";


    private String accessTokenUri;

    private String clientId;
    private String clientSecret;

    private String username;
    private String password;


    //Scope, default 'read,write'
    private String scope = "read,write";

    /**
     * Construct by ''accessTokenUri
     *
     * @param accessTokenUri Access Token Uri
     */
    public PasswordParams(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public PasswordParams setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public PasswordParams setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public PasswordParams setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public PasswordParams setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PasswordParams setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getGrantType() {
        return GRANT_TYPE;
    }


    public String getScope() {
        return scope;
    }

    public PasswordParams setScope(String scope) {
        this.scope = scope;
        return this;
    }

    /*
    * http://localhost:8080/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read,write&username=mobile&password=mobile
    * */
    public String getFullUri() {
        return String.format("%s?client_id=%s&client_secret=%s&grant_type=%s&scope=%s&username=%s&password=%s",
                accessTokenUri, clientId, clientSecret, getGrantType(), scope, username, password);
    }
}