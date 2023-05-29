package com.jun.plugin.resources.repository.git.config;

import com.jun.plugin.resources.KeyConstants;
import com.jun.plugin.resources.config.Config;
import com.jun.plugin.resources.config.GlobalConfig;
import com.jun.plugin.resources.utils.StringUtils;

/**
 * Git 配置
 * Created By Hong on 2018/8/13
 **/
public class GitConfig {

    /**
     * 默认分支
     */
    private static String BRANCH_DEFAULT = "master";

    private static Config CONFIG = GlobalConfig.get();
    private static GitConfig GIT_CONFIG = new GitConfig();

    public static GitConfig get() {
        return GIT_CONFIG;
    }

    private String uri;

    private String username;

    private String password;

    private String branch;

    private String localDir;

    public GitConfig() {
        this.uri = CONFIG.getValue(KeyConstants.GIT_URI);
        this.username = CONFIG.getValue(KeyConstants.GIT_USERNAME);
        this.password = CONFIG.getValue(KeyConstants.GIT_PASSWORD);
        this.branch = CONFIG.getValue(KeyConstants.GIT_BRANCH);
        this.localDir = CONFIG.getValue(KeyConstants.GIT_LOCAL_DIR);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public String getBranch() {
        if (StringUtils.isEmpty(branch)) {
            return BRANCH_DEFAULT;
        }
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocalDir() {
        if (StringUtils.isEmpty(localDir)) {
            //获取系统临时路径
            return System.getProperty("java.io.tmpdir").concat("\\AntResources\\git");
        }
        return localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }
}
