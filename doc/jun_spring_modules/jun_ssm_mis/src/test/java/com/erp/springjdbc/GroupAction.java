package com.erp.springjdbc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.jee.action.BaseAction;
import com.erp.jee.page2.GroupPage;
import com.erp.jee.pageModel.Json;
import com.erp.jee.service.GroupServiceI;
import com.jun.plugin.utils.ExceptionUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;

/**   
 * @Title: Action
 * @Description: 组织机构
 * @author Wujun
 * @date 2013-01-18 12:18:08
 * @version V1.0   
 *
 */
//@Action(value = "groupAction", results = { @Result(name = "group", location = "/sun/core/group.jsp") })
@Controller
@RequestMapping("/groupAction.do")
public class GroupAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(GroupAction.class);

	private GroupServiceI groupService;

	private GroupPage groupPage = new GroupPage();
	
	public GroupAction() {
	}

//	public GroupPage getModel() {
//		return groupPage;
//	}


	public GroupServiceI getGroupService() {
		return groupService;
	}

	@Autowired
	public void setGroupService(GroupServiceI groupService) {
		this.groupService = groupService;
	}


	/**
	 * 跳转到组织机构管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "goGroup")
	public String goGroup() {
		return "group";
	}

	/**
	 * 跳转到查看desc页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "showDesc")
	public void showDesc(Model model, HttpServletRequest request, HttpServletResponse response) {
		WebUtil.copyBean3(WebUtil.getAllParameters(request), groupPage);
		writeJson(response, groupService.get(groupPage));
	}

	/**
	 * 获得pageHotel数据表格
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(Model model, HttpServletRequest request, HttpServletResponse response) {
		WebUtil.copyBean3(WebUtil.getAllParameters(request), groupPage);
		writeJson(response, groupService.datagrid(groupPage));
	}
	
	
	/**
	 * 获得无分页的所有数据
	 */
	@RequestMapping(params = "combox")
	public void  combox(Model model, HttpServletRequest request, HttpServletResponse response){
		WebUtil.copyBean3(WebUtil.getAllParameters(request), groupPage);
		writeJson(response, groupService.listAll(groupPage));
	}

	/**
	 * 添加一个组织机构
	 */
	@RequestMapping(params = "add")
	public void add(Model model, HttpServletRequest request, HttpServletResponse response) {
		WebUtil.copyBean3(WebUtil.getAllParameters(request), groupPage);
		Json j = new Json();
		try {
			groupService.add(groupPage);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		writeJson(response, j);
	}

	/**
	 * 编辑组织机构
	 */
	@RequestMapping(params = "edit")
	public void edit(Model model, HttpServletRequest request, HttpServletResponse response) {
		WebUtil.copyBean3(WebUtil.getAllParameters(request), groupPage);
		Json j = new Json();
		try {
			groupService.update(groupPage);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		writeJson(response, j);
	}

	/**
	 * 删除组织机构
	 */
	@RequestMapping(params = "delete")
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response) {
		WebUtil.copyBean3(WebUtil.getAllParameters(request), groupPage);
		Json j = new Json();
		groupService.delete(groupPage.getIds());
		j.setSuccess(true);
		writeJson(response, j);
	}

	/**
	 * 文件上传
	 */
	/*public void upload() {
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

	@RequestMapping(params = "goDruid")
	private void uploadError(HttpServletResponse response,String err, String msg) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", err);
		m.put("msg", msg);
		writeJson(response, m);
	}

	@RequestMapping(params = "goDruid")
	private void uploadError(HttpServletResponse response,String err) {
		uploadError(response, err, "");
	}

	@RequestMapping(params = "goDruid")
	private void uploadSuccess(HttpServletResponse response,String newFileName, String fileName, int id) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", "");
		Map<String, Object> nm = new HashMap<String, Object>();
		nm.put("url", newFileName);
		nm.put("localfile", fileName);
		nm.put("id", id);
		m.put("msg", nm);
		writeJson(response, m);
	}
	
//	public GroupPage getGroupPage() {
//		return groupPage;
//	}


//	public void setGroupPage(GroupPage groupPage) {
//		this.groupPage = groupPage;
//	}
}
