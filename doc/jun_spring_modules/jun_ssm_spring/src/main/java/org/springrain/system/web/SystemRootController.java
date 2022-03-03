package org.springrain.system.web;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.system.dto.FileDto;
import org.springrain.system.entity.Menu;
import org.springrain.system.service.ISystemRootService;

@Controller
@RequestMapping(value = "/system/file")
public class SystemRootController extends BaseController {

	@Resource
	private ISystemRootService systemRootService;

	private String path = "";
	private String webinfpath = "";

	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Menu menu)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String p = request.getParameter("p");
		path = request.getServletContext().getRealPath("");
		if (path.contains("\\")) {
			path = StringUtils.replace(path, "\\", "/");
		}
		if (StringUtils.isNotBlank(p)) {
			p = path + p;
		} else {
			p = path;
		}
		List<FileDto> dtos = systemRootService.findFileDtosByPath(p, path);
		returnObject.setData(dtos);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		
		// 获取上级目录
		String parent = "";
		if (!p.equals(path)) {
			File file = new File(p);
			parent = file.getParent();
			if (parent.contains("\\")) {
				parent = StringUtils.replace(parent, "\\", "/");
			}
			parent = StringUtils.replace(parent, path, "");
		}
		String curr = StringUtils.replace(p, path, "");
		model.addAttribute("parent", parent);
		model.addAttribute("curr", curr);
		return "/system/file/fileList";
	}

	@RequestMapping("/web/list")
	public String web_list(HttpServletRequest request, Model model, Menu menu)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String p = request.getParameter("p");
		webinfpath = request.getServletContext().getRealPath("WEB-INF");
		if (webinfpath.contains("\\")) {
			webinfpath = StringUtils.replace(webinfpath, "\\", "/");
		}
		if (StringUtils.isNotBlank(p)) {
			p = webinfpath + p;
		} else {
			p = webinfpath;
		}
		List<FileDto> dtos = systemRootService
				.findFileDtosByPath(p, webinfpath);
		returnObject.setData(dtos);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		model.addAttribute("t", "t");

		// 获取上级目录
		String parent = "";
		if (!p.equals(webinfpath)) {
			File file = new File(p);
			parent = file.getParent();
			if (parent.contains("\\")) {
				parent = StringUtils.replace(parent, "\\", "/");
			}
			parent = StringUtils.replace(parent, path, "");
		}
		String curr = StringUtils.replace(p, path, "");
		model.addAttribute("parent", parent);
		model.addAttribute("curr", curr);
		return "/system/file/fileList";
	}

	@RequestMapping("/uploadDic")
	@ResponseBody 
	public  ReturnDatas uploadDic(HttpServletRequest request,
			HttpServletResponse response, String t, String p, String name)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String currpath = "";
		if (StringUtils.isBlank(t)) {
			currpath = path;
		} else {
			currpath = webinfpath;
		}

		if (StringUtils.isBlank(p)) {
			p = currpath + "/" + name;
		} else {
			p = currpath + p + "/" + name;
		}
		File file = new File(p);
		if (!file.exists()) {
			file.mkdirs();
		}
		returnObject.setData(p);
		return returnObject;
	}

	@RequestMapping("/uploadFile")
	@ResponseBody 
	public  ReturnDatas uploadImage(HttpServletRequest request,
			HttpServletResponse response, MultipartFile _fileUpload, String t,
			String p) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		if (_fileUpload == null || _fileUpload.isEmpty()) {
			returnObject = ReturnDatas.getErrorReturnDatas();
			returnObject.setMessage("no file!");
			return returnObject;
		}
		String currpath = "";
		if (StringUtils.isBlank(t)) {
			currpath = path;
		} else {
			currpath = webinfpath;
		}

		if (StringUtils.isBlank(p)) {
			p = currpath;
		} else {
			p = currpath + p;
		}
		// 复制文件
		FileUtils.copyInputStreamToFile(_fileUpload.getInputStream(), new File(
				p, _fileUpload.getOriginalFilename()));

		returnObject.setData(p + _fileUpload.getOriginalFilename());
		return returnObject;
	}

	@RequestMapping("/downfile")
	public void downfile(HttpServletRequest request,
			HttpServletResponse response, Model model, Menu menu)
			throws Exception {

		String currpath = "";
		String p = request.getParameter("p");
		String t = request.getParameter("t");
		if (StringUtils.isNotBlank(t)) {
			currpath = webinfpath;
		} else {
			currpath = path;
		}

		if (StringUtils.isBlank(p)) {
			PrintWriter writer = response.getWriter();
			writer.print("File not foud,path is error!");
			writer.flush();
			writer.close();
		} else {
			File file = new File(currpath + p);
			if (file.exists()) {
				downFile(response, file, file.getName(), false);
			}
		}

	}
}
