package com.erp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.struts2.ServletActionContext;
//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.Bug;
import com.erp.service.IBugService;
import com.erp.viewModel.GridModel;
import com.jun.plugin.utils.ConfigUtil;
//import com.opensymphony.xwork2.ModelDriven;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//@Namespace("/bug")
//@Action(value = "bugAction")
@Controller
@RequestMapping("/bugController.do")
public class BugController extends BaseController {
	private static final long serialVersionUID = -3055754336964775139L;
	private IBugService bugService;
	private File filedata;
	private String filedataFileName;
	private String filedataContentType;

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public String getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	private Bug bug;

	public Bug getBug() {
		return bug;
	}

	public void setBug(Bug bug) {
		this.bug = bug;
	}

	@Autowired
	public void setBugService(IBugService bugService) {
		this.bugService = bugService;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-22修改日期 修改内容
	 * 
	 * @Title: findBugList
	 * @Description: TODO:查询所有bug
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "method=findBugList")
	public String findBugList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageUtil pageUtil = new PageUtil();
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.copyBean3(param, pageUtil);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		GridModel gridModel = new GridModel();
		gridModel.setRows(bugService.findBugList(map, pageUtil));
		gridModel.setTotal(bugService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-22修改日期 修改内容
	 * 
	 * @Title: addBug
	 * @Description: TODO:增加bug
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=addBug")
	public String addBug(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map param = WebUtil.getAllParameters(request);
		Bug bug = new Bug();
//		WebUtil.copyBean3(param, bug);
		WebUtil.paramToBean(request, bug);
		OutputJson(getMessage(bugService.addBug(bug)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-22修改日期 修改内容
	 * 
	 * @Title: delBug
	 * @Description: TODO:删除bug
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=delBug")
	public String delBug(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Bug bug = new Bug();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.copyBean3(param, bug);
		OutputJson(getMessage(bugService.delBug(bug.getBugId())), response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-22修改日期 修改内容
	 * 
	 * @Title: upload
	 * @Description: TODO:BUG附件上传
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=upload")
	public String upload(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// HttpServletRequest request=ServletActionContext.getRequest();
		// HttpServletResponse response=ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		String savePath = session.getServletContext().getRealPath(File.separator) + ConfigUtil.getUploadDirectory() + "/";// 文件保存目录路径
		String saveUrl = contextPath + "/" + ConfigUtil.getUploadDirectory() + "/";// 要返回给xhEditor的文件保存目录URL
		SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthDf = new SimpleDateFormat("MM");
		SimpleDateFormat dateDf = new SimpleDateFormat("dd");
		Date date = new Date();
		String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
		savePath += ymd;
		saveUrl += ymd;

		System.out.println("request.getContextPath()==>" + request.getContextPath());
		File uploadDir = new File(savePath);// 创建要上传文件到指定的目录
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String contentDisposition = request.getHeader("Content-Disposition");// 如果是HTML5上传文件，那么这里有相应头的
		if (contentDisposition != null) {// HTML5拖拽上传文件
			Long fileSize = Long.valueOf(request.getHeader("Content-Length"));// 上传的文件大小
			String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("filename=\""));// 文件名称
			fileName = fileName.substring(fileName.indexOf("\"") + 1);
			fileName = fileName.substring(0, fileName.indexOf("\""));
			fileName = URLDecoder.decode(fileName, "utf-8");
			ServletInputStream inputStream = null;
			try {
				inputStream = request.getInputStream();
			} catch (IOException e) {
				map.put("err", "上传文件出错！");
			}

			if (inputStream == null) {
				map.put("err", "您没有上传任何文件！");
			}

			if (fileSize > ConfigUtil.getUploadFileMaxSize()) {
				map.put("err", "上传文件超出限制大小！");
				map.put("msg", fileName);
			} else {
				// 检查文件扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;// 新的文件名称
				File uploadedFile = new File(savePath, newFileName);

				try {
					FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile));
				} catch (FileNotFoundException e) {
					map.put("err", "上传文件出错！");
				} catch (IOException e) {
					map.put("err", "上传文件出错！");
				}
				map.put("err", "");
				System.out.println("----" + saveUrl);
				Map<String, Object> nm = new HashMap<String, Object>();
				nm.put("url", saveUrl + newFileName);
				nm.put("localfile", fileName);
				nm.put("id", 0);
				map.put("msg", nm);
			}
		} else {// 不是HTML5拖拽上传,普通上传
			if (ServletFileUpload.isMultipartContent(request)) {// 判断表单是否存在enctype="multipart/form-data"
				Long fileSize = Long.valueOf(request.getHeader("Content-Length"));
				if (fileSize > ConfigUtil.getUploadFileMaxSize()) {
					map.put("err", "上传文件超出限制大小！");
					map.put("msg", filedataFileName);
				} else {
					String fileExt = filedataFileName.substring(filedataFileName.lastIndexOf(".") + 1).toLowerCase();
					String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
					Constants.copy(filedata, savePath + newFileName);
					Map<String, Object> nm = new HashMap<String, Object>();
					map.put("err", "");
					nm.put("url", saveUrl + newFileName);
					nm.put("localfile", filedataFileName);
					nm.put("id", 0);
					map.put("msg", nm);
				}
			} else {
				// 不是multipart/form-data表单
				map.put("err", "上传文件出错！");
			}
		}
		System.out.println(request.getHeader("Content-Length"));
		System.out.println("filedataContentType=>>" + filedataContentType);
		System.out.println("filedata=>>" + filedata);
		System.out.println("filedataFileName==>>" + filedataFileName);
		System.out.println("savePath==>>" + savePath);
		System.out.println("saveUrl==>>" + saveUrl);
		OutputJson(map, Constants.TEXT_TYPE_PLAIN, response);
		return null;

	}

	// public Bug getModel() {
	// if (bug == null) {
	// bug = new Bug();
	// }
	// return bug;
	// }
}
