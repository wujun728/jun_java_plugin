package cn.kiwipeach.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create Date: 2017/11/05
 * Description: SpringBoot入门案例之作者实体类
 *
 * @author Wujun
 */
@Component
@ConfigurationProperties(prefix = "author")
public class AuthorProperties {
    private String auId;
    private String auName;
    private String email;

    public String getAuId() {
        return auId;
    }

    public void setAuId(String auId) {
        this.auId = auId;
    }

    public String getAuName() {
        return auName;
    }

    public void setAuName(String auName) {
        this.auName = auName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
