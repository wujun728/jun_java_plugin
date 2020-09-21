package com.chentongwei.controller.common;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 生成验证码
 *
 * @author TongWei.Chen 2017-07-14 17:59:26
 */
@RestController
@RequestMapping("/common/captcha")
public class AuthcodeController {

    private static Random random = new Random();

    private String getAuthcode() {
        int i = random.nextInt(9000) + 1000;
        return String.valueOf(i);
    }

    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public Map<String, Object> create(HttpServletRequest request) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String authcode = getAuthcode();

        BufferedImage image = getImage(authcode);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", out);
        byte[] b = out.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
        byte[] encode = IOUtils.toByteArray(inputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(out);
        //存到session
        HttpSession session = request.getSession();
        session.setAttribute("authCode", authcode);
        session.setAttribute("token", uuid);

        Map<String, Object> map = new HashMap<>();
        map.put("authCode", encode);
        map.put("token", uuid);
        return map;
    }

    private static Font font = new Font("Times New Roman", Font.PLAIN, 20);
    private BufferedImage getImage(String authcode) {
        int width = 60, height = 20;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        g.setFont(font);

        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        for (int i = 0; i < 4; i++) {
            String rand = authcode.substring(i, i + 1);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }
        g.dispose();

        return image;
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
