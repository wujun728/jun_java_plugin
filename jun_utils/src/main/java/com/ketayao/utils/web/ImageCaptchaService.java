package com.ketayao.utils.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.ketayao.utils.codec.CryptUtils;

/**
 * //调用方法
 * if(!ImageCaptchaService.validate(ctx.request()))
 * //提示错误信息
 * 图形验证码
 */
public class ImageCaptchaService {

	private final static String COOKIE_NAME = "_reg_key_"; 
	private static int WIDTH = 120;
	private static int HEIGHT = 40;
	private static int LENGTH = 5;
	private final static Random random = new Random();
	
	private String key;

	public ImageCaptchaService(String key) {
		this.key = key;
	}
	
	/**
	 * 生成验证码
	 * @param req
	 * @param res
	 * @throws IOException 
	 */
	public void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(RequestUtils.isRobot(request)){
			return;
		}
		ResponseUtils.closeCache(response);
        response.setContentType("image/png");
        _render(_generateRegKey(request, response), response.getOutputStream(), WIDTH, HEIGHT);
	}
	
	/**
	 * 检查验证码是否正确
	 * @param req
	 * @return
	 */
	public boolean validate(HttpServletRequest req) {
		Cookie cke = RequestUtils.getCookie(req, COOKIE_NAME);
		if (cke == null || StringUtils.isNotBlank(cke.getValue())) {
			return false;
		}
		
		String value = cke.getValue();
		String code1 = null;
		try {
			code1 = CryptUtils.decrypt(value, key);
		} catch (Exception e) {
			return false;
		}
		String code2 = req.getParameter("verifyCode");
		return StringUtils.equalsIgnoreCase(code1, code2);
	}
	
	private String _generateRegKey(HttpServletRequest request, HttpServletResponse response) {
		String REG_VALUE = null;
		String code = RandomStringUtils.randomAlphanumeric(LENGTH)
				.toUpperCase();
		code.replace('0', 'W');
		code.replace('o', 'R');
		code.replace('I', 'E');
		code.replace('1', 'T');
		
		REG_VALUE = CryptUtils.encrypt(code, key);
		
		RequestUtils.setCookie(request, response, COOKIE_NAME, REG_VALUE,
				1440, true);//保存1天
		
		return code;
	}
	
    /**
     * 画随机码图
     * @param text
     * @param out
     * @param width
     * @param height
     * @throws IOException
     */
    private static void _render(String text, OutputStream out, int width, int height) throws IOException {
	    BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);        
	    Graphics2D g = (Graphics2D)bi.getGraphics();
	    
	    g.setColor(Color.WHITE);
	    g.fillRect(0,0,width,height);
    	//g.setColor(Color.RED);
	    //g.drawRect(1,1,width-2,height-2);
	    for(int i=0;i<10;i++){
	    	g.setColor(_getRandColor(150, 250));
	    	g.drawOval(random.nextInt(110), random.nextInt(24), 5+random.nextInt(10), 5+random.nextInt(10));
	    }
	    Font mFont = new Font("Arial", Font.ITALIC, 28);
	    g.setFont(mFont);
	    g.setColor(_getRandColor(10,240));
	    g.drawString(text, 10, 30);
	    ImageIO.write(bi, "png", out);
    }
    
    private static Color _getRandColor(int fc,int bc){//给定范围获得随机颜色
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

    public static void main(String[] args) throws IOException {
		String code = RandomStringUtils.randomAlphanumeric(LENGTH).toUpperCase();
		code = code.replace('0', 'W');
		code = code.replace('o', 'R');
		code = code.replace('I', 'E');
		code = code.replace('1', 'T');
		
    	FileOutputStream out = new FileOutputStream("d:\\aa.jpg");
    	_render(code,out,120,40);
    }
    
}
