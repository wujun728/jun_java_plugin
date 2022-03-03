package com.erp.jee.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.controller.BaseController;
import com.erp.jee.page2.PersonPage;
import com.erp.jee.pageModel.Json;
import com.erp.jee.service.PersonServiceI;
import com.jun.plugin.utils.ExceptionUtil;

/**   
 * @Title: Action
 * @Description: 用户
 * @author Wujun
 * @date 2013-01-17 11:41:26
 * @version V1.0   
 *
 */
//@Action(value = "personAction", results = { @Result(name = "person", location = "/sun/test/person.jsp") })
@Controller
@RequestMapping("/chartsAction.do")
public class PersonAction extends BaseController{

	private static final Logger logger = Logger.getLogger(PersonAction.class);

	private PersonServiceI personService;

	private PersonPage personPage = new PersonPage();

	public PersonPage getModel() {
		return personPage;
	}


//	public PersonServiceI getPersonService() {
//		return personService;
//	}
//
//	@Autowired
//	public void setPersonService(PersonServiceI personService) {
//		this.personService = personService;
//	}


	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	public String goPerson(Model model, HttpServletRequest request, HttpServletResponse response){
		return "/sun/test/person";
	}

	/**
	 * 跳转到查看desc页面
	 * 
	 * @return
	 */
	public void showDesc(Model model, HttpServletRequest request, HttpServletResponse response){
		writeJson(personService.get(personPage), response);
	}

	/**
	 * 获得pageHotel数据表格
	 */
	public void datagrid(Model model, HttpServletRequest request, HttpServletResponse response){
		writeJson(personService.datagrid(personPage), response);
	}
	
	
	/**
	 * 获得无分页的所有数据
	 */
	public void  combox(Model model, HttpServletRequest request, HttpServletResponse response){
		writeJson(personService.listAll(personPage), response);
	}

	/**
	 * 添加一个用户
	 */
	public void add(Model model, HttpServletRequest request, HttpServletResponse response){
		Json j = new Json();
		try {
			personService.add(personPage);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		writeJson(j, response);
	}

	/**
	 * 编辑用户
	 */
	public void edit(Model model, HttpServletRequest request, HttpServletResponse response){
		Json j = new Json();
		try {
			personService.update(personPage);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		writeJson(j, response);
	}

	/**
	 * 删除用户
	 */
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response){
		Json j = new Json();
		personService.delete(personPage.getIds());
		j.setSuccess(true);
		writeJson(j, response);
	}

	/**
	 * 文件上传
	 */
	/*public void upload(Model model, HttpServletRequest request, HttpServletResponse response){
		String savePath = ServletActionContext.getServletContext().getRealPath("/") + ResourceUtil.getUploadDirectory() + "/";// 文件保存目录路径
		String saveUrl = "/" + ResourceUtil.getUploadDirectory() + "/";// 文件保存目录URL

		String contentDisposition = ServletActionContext.getRequest().getHeader("Content-Disposition");// 如果是HTML5上传文件，那么这里有相应头的

		if (contentDisposition != null) {// HTML5拖拽上传文件
			Long fileSize = Long.valueOf(ServletActionContext.getRequest().getHeader("Content-Length"));// 上传的文件大小
			String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("filename=\""));// 文件名称
			fileName = fileName.substring(fileName.indexOf("\"") + 1);
			fileName = fileName.substring(0, fileName.indexOf("\""));

			ServletInputStream inputStream;
			try {
				inputStream = ServletActionContext.getRequest().getInputStream();
			} catch (IOException e) {
				uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			}

			if (inputStream == null) {
				uploadError("您没有上传任何文件！");
				return;
			}

			if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
				uploadError("上传文件超出限制大小！", fileName);
				return;
			}

			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				uploadError("上传文件扩展名是不允许的扩展名。\n只允许" + ResourceUtil.getUploadFileExts() + "格式！");
				return;
			}

			savePath += fileExt + "/";
			saveUrl += fileExt + "/";

			SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthDf = new SimpleDateFormat("MM");
			SimpleDateFormat dateDf = new SimpleDateFormat("dd");
			Date date = new Date();
			String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
			savePath += ymd;
			saveUrl += ymd;

			File uploadDir = new File(savePath);// 创建要上传文件到指定的目录
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;// 新的文件名称
			File uploadedFile = new File(savePath, newFileName);

			try {
				FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile));
			} catch (FileNotFoundException e) {
				uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			} catch (IOException e) {
				uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			}

			uploadSuccess(ServletActionContext.getRequest().getContextPath() + saveUrl + newFileName, fileName, 0);// 文件上传成功

			return;
		}

		MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) ServletActionContext.getRequest();// 由于struts2上传文件时自动使用了request封装
		File[] files = multiPartRequest.getFiles(ResourceUtil.getUploadFieldName());// 上传的文件集合
		String[] fileNames = multiPartRequest.getFileNames(ResourceUtil.getUploadFieldName());// 上传文件名称集合

		if (files == null || files.length < 1) {
			uploadError("您没有上传任何文件！");
			return;
		}

		for (int i = 0; i < files.length; i++) {// 循环所有文件
			File file = files[i];// 上传的文件(临时文件)
			String fileName = fileNames[i];// 上传文件名

			if (file.length() > ResourceUtil.getUploadFileMaxSize()) {
				uploadError("上传文件超出限制大小！", fileName);
				return;
			}

			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				uploadError("上传文件扩展名是不允许的扩展名。\n只允许" + ResourceUtil.getUploadFileExts() + "格式！");
				return;
			}

			savePath += fileExt + "/";
			saveUrl += fileExt + "/";

			SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthDf = new SimpleDateFormat("MM");
			SimpleDateFormat dateDf = new SimpleDateFormat("dd");
			Date date = new Date();
			String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
			savePath += ymd;
			saveUrl += ymd;

			File uploadDir = new File(savePath);// 创建要上传文件到指定的目录
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;// 新的文件名称
			File uploadedFile = new File(savePath, newFileName);

			try {
				FileCopyUtils.copy(file, uploadedFile);// 利用spring的文件工具上传
			} catch (Exception e) {
				uploadError("上传文件失败！", fileName);
				return;
			}

			uploadSuccess(ServletActionContext.getRequest().getContextPath() + saveUrl + newFileName, fileName, i);// 文件上传成功

		}

	}*/

	private void uploadError(String err, String msg) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", err);
		m.put("msg", msg);
		writeJson(m, null);
	}

	private void uploadError(String err) {
		uploadError(err, "");
	}

	private void uploadSuccess(String newFileName, String fileName, int id) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", "");
		Map<String, Object> nm = new HashMap<String, Object>();
		nm.put("url", newFileName);
		nm.put("localfile", fileName);
		nm.put("id", id);
		m.put("msg", nm);
		writeJson(m, null);
	}
	
	public PersonPage getPersonPage(Model model, HttpServletRequest request, HttpServletResponse response){
		return personPage;
	}


//	public void setPersonPage(PersonPage personPage) {
//		this.personPage = personPage;
//	}
}
