package org.typroject.tyboot.api.controller.systemctl;

import com.google.code.kaptcha.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.foundation.utils.CaptchaUtil;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.utils.ResponseModel;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;


@Controller
@TycloudResource(module = "systemctl", value = "captcha")
@RequestMapping(value = "/v1/systemctl/captcha")
@Api(tags = "systemctl-图片验证码")
public class CaptchaResource {


    @TycloudOperation(ApiLevel = UserType.ANONYMOUS, needAuth = false)
    @ApiOperation(value = "创建")
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ResponseModel getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.getId();
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);


        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // create the text for the image
        CaptchaUtil captchaUtil = CaptchaUtil.getInstance(4);
        String capText = captchaUtil.genCode();
        // 将验证码字符串存入session变量
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text
        BufferedImage bi = captchaUtil.genImage(capText);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);

        try {
            out.flush();
        } finally {
            out.close();
        }

        return null;
    }


}
