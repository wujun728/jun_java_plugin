package org.typroject.tyboot.core.foundation.utils;


import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.http.HttpHeaders;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * 生成和校验图形验证码
 */
public class CaptchaUtil {


    private static CaptchaUtil captchaUtil = null;

    private static Producer producer;


    private CaptchaUtil(int characterNum)
    {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", ""+characterNum);
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        producer = defaultKaptcha;
    }


    /**
     * 获取实例
     * @param characterNum 要生成的验证码的字符个数
     * @return
     */
    public static CaptchaUtil getInstance(int characterNum)
    {
        if(ValidationUtil.isEmpty(captchaUtil))
            captchaUtil = new CaptchaUtil(characterNum);
        return captchaUtil;
    }


    public  String genCode()
    {
       return  producer.createText();
    }


    public BufferedImage genImage(String text)
    {
        return producer.createImage(text);
    }
}
