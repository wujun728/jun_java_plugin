package io.github.wujun728.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 文件上传参数配置类
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Primary
@Component
@Data
@ConfigurationProperties(prefix = "file")
public class FileProperties {



    private String path;
    private String url;
    private String accessUrl;
    
    private String qiniuAccessKey;
    private String qiniuBucketName;
    private String qiniuDomain;
    private String qiniuPrefix;
    private String qiniuSecretKey;
    private String type;




    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        this.url = url;

        //set accessUrl
        if (StringUtils.isEmpty(url)) {
            this.accessUrl = null;
        }
        this.accessUrl = url.substring(url.lastIndexOf("/")) + "/**";
        System.out.println("accessUrl=" + accessUrl);
    }


}