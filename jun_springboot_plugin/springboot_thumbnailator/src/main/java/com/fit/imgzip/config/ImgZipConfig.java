package com.fit.imgzip.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 图片配置参数
 */
@Component
public class ImgZipConfig {

    //图片默认上传服务器地址
    public static String imagePath;
    //图片压缩比例
    public static Double defaultScale;
    //图片上传默认项目名称
    public static String name;

    @Value("${imgzip.img.path}")
    public void setImagePath(String $imagePath) {
        this.imagePath = $imagePath;
    }
    @Value("${imgzip.img.default.scale}")
    public void setDefaultScale(Double $defaultScale) {
        this.defaultScale = $defaultScale;
    }
    @Value("${imgzip.img.name}")
    public void setName(String $name) {
        this.name = $name;
    }
}
