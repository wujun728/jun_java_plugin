package com.erp.jee.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.jee.pageModel.Bug;
import com.erp.jee.pageModel.Json;
import com.erp.jee.service.BugServiceI;
import com.jun.plugin.utils.ExceptionUtil;

/**
 * Bug Action
 * 
 * @author Wujun
 * 
 */
//@Action(value = "bugAction", results = { @Result(name = "bug", location = "/com/jeecg/bug.jsp") })
@Controller
@RequestMapping("/bugAction.do")
public class BugAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(BugAction.class);

	private BugServiceI bugService;

	private Bug bug = new Bug();

	public Bug getModel() {
		return bug;
	}

	public BugServiceI getBugService() {
		return bugService;
	}

	@Autowired
	public void setBugService(BugServiceI bugService) {
		this.bugService = bugService;
	}

	/**
	 * 跳转到bug管理页面
	 * 
	 * @return
	 */
	public String goBug() {
		return "bug";
	}

	/**
	 * 跳转到查看desc页面
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "method=showDesc")
	public void showDesc(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, bugService.get(bug));
	}

	/**
	 * 获得bug数据表格
	 */
	@RequestMapping(params = "method=datagrid")
	public void datagrid(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, bugService.datagrid(bug));
	}

	/**
	 * 添加一个BUG
	 */
	@RequestMapping(params = "method=add")
	public void add(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			bugService.add(bug);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		writeJson(response, j);
	}

	/**
	 * 编辑BUG
	 */
	@RequestMapping(params = "method=edit")
	public void edit(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			bugService.update(bug);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		writeJson(response, j);
	}

	/**
	 * 删除BUG
	 */
	@RequestMapping(params = "method=delete")
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		bugService.delete(bug.getIds());
		j.setSuccess(true);
		writeJson(response, j);
	}

	/**
	 * 文件上传
	 */
	/*@RequestMapping(params = "method=upload")
	public void upload(Model model, HttpServletRequest request, HttpServletResponse response) {
		String savePath = request.getSession().getServletContext().getRealPath("/") + ResourceUtil.getUploadDirectory() + "/";// 文件保存目录路径
		String saveUrl = "/" + ResourceUtil.getUploadDirectory() + "/";// 文件保存目录URL

		String contentDisposition = request.getHeader("Content-Disposition");// 如果是HTML5上传文件，那么这里有相应头的

		if (contentDisposition != null) {// HTML5拖拽上传文件
			Long fileSize = Long.valueOf(request.getHeader("Content-Length"));// 上传的文件大小
			String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("filename=\""));// 文件名称
			fileName = fileName.substring(fileName.indexOf("\"") + 1);
			fileName = fileName.substring(0, fileName.indexOf("\""));

			ServletInputStream inputStream;
			try {
				inputStream = request.getInputStream();
			} catch (IOException e) {
				uploadError(response,"上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			}

			if (inputStream == null) {
				uploadError(response, "您没有上传任何文件！");
				return;
			}

			if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
				uploadError(response, "上传文件超出限制大小！", fileName);
				return;
			}

			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				uploadError(response, "上传文件扩展名是不允许的扩展名。\n只允许" + ResourceUtil.getUploadFileExts() + "格式！");
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
				uploadError(response, "上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			} catch (IOException e) {
				uploadError(response, "上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			}

			uploadSuccess(response, request.getContextPath() + saveUrl + newFileName, fileName, 0);// 文件上传成功

			return;
		}

		MultipartRequest multiPartRequest = (MultipartRequest) request;// 由于struts2上传文件时自动使用了request封装
		File[] files = multiPartRequest.getFiles(ResourceUtil.getUploadFieldName());// 上传的文件集合
		String[] fileNames = multiPartRequest.getFileNames(ResourceUtil.getUploadFieldName());// 上传文件名称集合

		if (files == null || files.length < 1) {
			uploadError(response, "您没有上传任何文件！");
			return;
		}

		for (int i = 0; i < files.length; i++) {// 循环所有文件
			File file = files[i];// 上传的文件(临时文件)
			String fileName = fileNames[i];// 上传文件名

			if (file.length() > ResourceUtil.getUploadFileMaxSize()) {
				uploadError(response, "上传文件超出限制大小！", fileName);
				return;
			}

			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				uploadError(response, "上传文件扩展名是不允许的扩展名。\n只允许" + ResourceUtil.getUploadFileExts() + "格式！");
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
				uploadError(response, "上传文件失败！", fileName);
				return;
			}

			uploadSuccess(response, request.getContextPath() + saveUrl + newFileName, fileName, i);// 文件上传成功
		}
	}*/

	private void uploadError(HttpServletResponse response,String err, String msg) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", err);
		m.put("msg", msg);
		writeJson(response, m);
	}

	private void uploadError(HttpServletResponse response,String err) {
		uploadError(response, err, "");
	}

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
}
