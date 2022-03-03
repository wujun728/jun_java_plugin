package org.springrain.frame.util;

import java.awt.image.BufferedImage;

import org.springrain.frame.util.patchca.service.Captcha;
import org.springrain.frame.util.patchca.service.ConfigurableCaptchaService;

/**
 * 验证码工具类
 * 
 * @author caomei
 *
 */
public class CaptchaUtils {
    private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();

    private CaptchaUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    /**
     * 生成随机图片验证码
     */
    public static BufferedImage genRandomCodeImage(StringBuilder randomCode) {
        Captcha captcha = cs.getCaptcha();
        randomCode.append(captcha.getChallenge());
        return captcha.getImage();
    }

    /*
     * public static void main(String[] args) {
     * 
     * StringBuilder code = new StringBuilder(); BufferedImage image =
     * CaptchaUtils.genRandomCodeImage(code); try { // 将内存中的图片通过流动形式输出到客户端
     * ImageIO.write(image, "png", new FileOutputStream(new File(
     * "random-code.png"))); } catch (Exception e) { System.out.println(e); }
     * 
     * }
     */

}
