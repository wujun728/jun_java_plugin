package com.kancy.emailplus.spring.boot.properties;

/**
 * Configuration
 *
 * @author Wujun
 * @date 2020/2/21 3:40
 */
public class Configuration {
    /**
     * 项目名称，作为命名空间
     */
    private String project = "default";

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
