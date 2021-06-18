package com.jun.plugin.kaptcha;
/*
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import com.google.code.kaptcha.Constants;

@Controller
@RequestMapping(value = "/demo", method = {RequestMethod.GET})
public class KcaptchaController {

    public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

    //--kapcha验证码。
    private Properties props = new Properties();
    private Producer kaptchaProducer = null;
    private String sessionKeyValue = null;
    private String sessionKeyDateValue = null;

    public KcaptchaController() {
        ImageIO.setUseCache(false);

        //设置宽和高。
        this.props.put(Constants.KAPTCHA_IMAGE_WIDTH, "200");
        this.props.put(Constants.KAPTCHA_IMAGE_HEIGHT, "60");
        //kaptcha.border：是否显示边框。
        this.props.put(Constants.KAPTCHA_BORDER, "no");
        //kaptcha.textproducer.font.color：字体颜色
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        //kaptcha.textproducer.char.space：字符间距
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
        //设置字体。
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "40");
        //this.props.put(Constants.KAPTCHA_NOISE_COLOR, "");
        //更多的属性设置可以在com.google.code.kaptcha.Constants类中找到。


        Config config1 = new Config(this.props);
        this.kaptchaProducer = config1.getProducerImpl();
        this.sessionKeyValue = config1.getSessionKey();
        this.sessionKeyDateValue = config1.getSessionDate();
    }


    @RequestMapping(value = "/kcaptcha.jpg", method = {RequestMethod.GET})
    @ResponseBody
    public void kaptcha(@RequestParam(value = "code", defaultValue = "0") String code,
                        HttpServletResponse response, HttpServletRequest request,
                        Model view) throws IOException {
        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

        String capText = this.kaptchaProducer.createText();
        BufferedImage bi = this.kaptchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        try {
            request.getSession().setAttribute(this.sessionKeyValue, capText);
            request.getSession().setAttribute(this.sessionKeyDateValue, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageIO.write(bi, CAPTCHA_IMAGE_FORMAT, out);

    }
}*/