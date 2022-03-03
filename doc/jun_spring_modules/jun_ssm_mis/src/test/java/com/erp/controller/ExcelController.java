package com.erp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.struts2.ServletActionContext;
//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.CompanyInfo;
import com.erp.service.IExcelService;
import com.jun.plugin.utils.ExcelUtil;
import com.jun.plugin.utils.FileUtil;
import com.jun.plugin.utils.WebUtil;

//@Namespace("/excel")
//@Action(value = "excelAction")
@Controller
@RequestMapping("/excelController.do")
public class ExcelController extends BaseController {
	private static final long serialVersionUID = 6711372422886609823L;
	private IExcelService excelService;

	// private String isCheckedIds;
	// public String getIsCheckedIds()
	// {
	// return isCheckedIds;
	// }
	// public void setIsCheckedIds(String isCheckedIds )
	// {
	// this.isCheckedIds = isCheckedIds;
	// }
	@Autowired
	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=CompanyInfoExcelExport")
	public String CompanyInfoExcelExport(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map param = WebUtil.getAllParameters(request);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String excelName = format.format(new Date());
		String path = "CompanyInfo-" + excelName + ".xls";
		String fegefu = File.separator;
		// HttpServletRequest request = ServletActionContext.getRequest();
		// HttpServletResponse response = ServletActionContext.getResponse();
		String severPath = request.getSession().getServletContext().getRealPath(fegefu);
		String allPath = severPath + "attachment" + fegefu + path;
		FileUtil.makeDir(severPath + "attachment" + fegefu);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(allPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<CompanyInfo> list = excelService.findExcelExportList(String.valueOf(param.get("isCheckedIds")),
				CompanyInfo.class);
		ExcelUtil<CompanyInfo> util = new ExcelUtil<CompanyInfo>(CompanyInfo.class);
		util.exportExcel(list, "Sheet", 60000, out);
		FileUtil.downFile(path, response, allPath);
		return null;
	}
}
