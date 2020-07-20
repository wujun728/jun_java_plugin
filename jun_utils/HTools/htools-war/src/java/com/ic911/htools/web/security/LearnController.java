package com.ic911.htools.web.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ic911.htools.commons.Constants;
import com.ic911.htools.service.security.LearnService;
/**
 * 维护知识库
 * @author caoyang
 */
@Controller
@RequestMapping("/security/learn")
public class LearnController {
	@Autowired
	private LearnService learnService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		if(request.getParameter("error")!=null){
			if(request.getParameter("error").equals("1")){
				request.setAttribute(Constants.ERROR_MESSAGE, "请不要上传大于10MB的文件!");
			}else{
				request.setAttribute(Constants.ERROR_MESSAGE, "您上传的文件内容无法解析!");
			}
		}
		return "/security/learn/index";
	}
	
	/**
	 * 上传
	 * @param file
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		if(file==null||file.getSize()<1){
			redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, "非法文件无法操作!");
			return "redirect:/security/learn/index";
		}
		try {
			byte[] data = file.getBytes();
			String values = new String(data,"GBK");
			String[] contexts = values.split("\n");
			learnService.save(contexts);
			redirectAttributes.addFlashAttribute(Constants.SUCCESS_MESSAGE, Constants.SUCCESS_INFO);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, "您上传的文件内容无法解析!");
		}
		return "redirect:/security/learn/index";
	}
	
	@ExceptionHandler(Exception.class)         
    public String handleException(Exception ex,HttpServletRequest request) {       
         if (ex instanceof MaxUploadSizeExceededException){  
        	 return "redirect:/security/learn/index?error=1";
         }else{
        	 return "redirect:/security/learn/index?error=0";
         }
    }    
	
	/**
	 * ajax预览
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/preview",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> preview(HttpServletRequest request) {
		Map<String,String> learns = learnService.findRandom();
		return learns;
	}
	
}
