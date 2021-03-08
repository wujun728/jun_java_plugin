package top.linxz.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

    public String wechatMpAuthorize;

    public String wechatOpenAuthorize;

    public String sell;
    public String openid;
}

