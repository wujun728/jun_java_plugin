package org.springrain.system.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.CaptchaUtils;
import org.springrain.frame.util.GlobalStatic;

@Controller
public class CommonController extends BaseController  {
	

		
		
		/**
		 * 错误页面
		 * @param model
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/errorpage/{error}")
		public String errorpage(HttpServletRequest request,@PathVariable String error) throws Exception {
			
			
			Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
			
			if(exception!=null){
				logger.error(exception.getMessage(), exception);
				request.setAttribute("exception", exception.getMessage());
			}
			
			
			return "/errorpage/"+error;
		}
		
		
		
		/**
		 * 生成验证码
		 * 
		 * @return
		 * @throws IOException 
		 */
		@RequestMapping("/getCaptcha")
		public void getCaptcha(HttpSession session,HttpServletResponse response) throws IOException {

			//HttpHeaders headers = new HttpHeaders();
			//headers.setContentType(MediaType.IMAGE_JPEG);

			StringBuilder code = new StringBuilder();
			BufferedImage image = CaptchaUtils.genRandomCodeImage(code);
			session.removeAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
			session.setAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM, code.toString());

			response.setHeader("Content-Type", "image/jpeg");
			
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", response.getOutputStream());
			return;
		}
		
}
