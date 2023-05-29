package com.jun.plugin.resources.repository.git;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.jun.plugin.resources.repository.git.config.GitConfig;
import com.jun.plugin.resources.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Git验证 构建器
 * Created By Hong on 2018/8/13
 **/
public class GitBuilder {

    /**
     * CredentialsProvider Cache
     **/
    private static Map<String, CredentialsProvider> PROVIDER_CACHE = new ConcurrentHashMap<>();

    private static GitConfig GIT_CONFIG = GitConfig.get();

    /**
     * 构建Git验证器
     *
     * @return CredentialsProvider
     */
    public static CredentialsProvider createProvider() {
        if(StringUtils.isEmpty(GIT_CONFIG.getUsername()) || StringUtils.isEmpty(GIT_CONFIG.getPassword())) {
            return null;
        }
        if (PROVIDER_CACHE.containsKey(GIT_CONFIG.getUsername().concat(GIT_CONFIG.getPassword()))) {
            return PROVIDER_CACHE.get(GIT_CONFIG.getUsername().concat(GIT_CONFIG.getPassword()));
        }
        PROVIDER_CACHE.put(GIT_CONFIG.getUsername().concat(GIT_CONFIG.getPassword()), new UsernamePasswordCredentialsProvider(GIT_CONFIG.getUsername(), GIT_CONFIG.getPassword()));
        return PROVIDER_CACHE.get(GIT_CONFIG.getUsername().concat(GIT_CONFIG.getPassword()));
    }

    /**
     * 构建Git验证器
     *
     * @param username 用户名
     * @param password 密码
     * @return UsernamePasswordCredentialsProvider
     */
    public static CredentialsProvider createProvider(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("username is empty.");
        }
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("password is empty.");
        }
        if (PROVIDER_CACHE.containsKey(username.concat(password))) {
            return PROVIDER_CACHE.get(username.concat(password));
        }
        PROVIDER_CACHE.put(username.concat(password), new UsernamePasswordCredentialsProvider(username, password));
        return PROVIDER_CACHE.get(username.concat(password));
    }

}
