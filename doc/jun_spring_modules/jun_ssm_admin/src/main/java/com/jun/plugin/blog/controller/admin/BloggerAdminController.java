package com.jun.plugin.blog.controller.admin;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jun.plugin.blog.entity.Blogger;
import com.jun.plugin.blog.service.BloggerService;
import com.jun.plugin.blog.util.CryptographyUtil;
import com.jun.plugin.blog.util.DateUtil;
import com.jun.plugin.blog.util.ResponseUtil;

import net.sf.json.JSONObject;

/**
 * ����Ա����Controller��
 * @author Wujun
 *
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * �޸Ĳ�����Ϣ
	 * @param file1
	 * @param blogger
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/save")
	public String save(@RequestParam("imageFile") MultipartFile imageFile,Blogger blogger,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(!imageFile.isEmpty()){
			String filePath=request.getRealPath("/");
			String imageName=DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
			imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
			blogger.setImageName(imageName);
		}
		int resultTotal=bloggerService.update(blogger);
		StringBuffer result=new StringBuffer();
		if(resultTotal>0){
			result.append("<script language='javascript'>alert('�޸ĳɹ���');</script>");
		}else{
			result.append("<script language='javascript'>alert('�޸�ʧ�ܣ�');</script>");
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * ��ѯ������Ϣ
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/find")
	public String find(HttpServletResponse response)throws Exception{
		Blogger blogger=bloggerService.find();
		JSONObject jsonObject=JSONObject.fromObject(blogger);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * �޸Ĳ�������
	 * @param newPassword
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String newPassword,HttpServletResponse response)throws Exception{
		Blogger blogger=new Blogger();
		blogger.setPassword(CryptographyUtil.md5(newPassword, "java1234"));
		int resultTotal=bloggerService.update(blogger);
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * ע��
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String  logout()throws Exception{
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
}
