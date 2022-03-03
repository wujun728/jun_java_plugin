package com.feri.fyw.service.impl;

import com.feri.fyw.service.intf.CaptchaService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 10:02
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Override
    public void createImg(HttpSession session,HttpServletResponse response) throws IOException {
        //基于第三方 kaptcha 实现图像验证码
        //1.准备属性
        Properties properties=new Properties();
        //设置图片的边框 是否存在
        properties.setProperty("kaptcha.border","yes");
        //设置字体颜色
        properties.setProperty("kaptcha.textproducer.font.color","red");
        properties.setProperty("kaptcha.textproducer.font.size","26");
        //设置图片的宽
        properties.setProperty("kaptcha.image.width","100");
        properties.setProperty("kaptcha.textproducer.font.names","Courier");
        //设置图片的高
        properties.setProperty("kaptcha.image.height","38");
        //2.获取验证码对象
        DefaultKaptcha kaptcha=new DefaultKaptcha();
        kaptcha.setConfig(new Config(properties));
        //3.获取验证码的图片
        Random random=new Random();
        BufferedImage image=kaptcha.createImage(create(session));
        //4.返回 图片
        ImageIO.write(image,"png",response.getOutputStream());
    }

    //随机生成一个+—X/的表达式 并返回
    private String create(HttpSession session){
        int result=0;//记录表达式的结果
        Random random=new Random();
        //随机生成第一位数字 2位以内
        int num1=random.nextInt(99)+1;
        //随机生成第一位数字 2位以内
        int num2=random.nextInt(99)+1;
        //随机生成 运算符 0+ 1- 2X
        int y=random.nextInt(3);
        StringBuffer buffer=new StringBuffer();
        buffer.append(num1);
        switch (y){
            case 0:{buffer.append(" + ");
                result=num1+num2;
            }break;
            case 1:{buffer.append(" - ");
                result=num1-num2;
            }break;
            default:{buffer.append(" * ");
                result=num1*num2;
            }break;
        }
        buffer.append(num2+" = ");
        session.setAttribute("code",result);
        System.err.println(result);
        return buffer.toString();
    }
}
