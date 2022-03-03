package com.erp.controller;

//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.SystemCode;
import com.erp.service.ISystemCodeService;
import com.erp.viewModel.Json;
//import com.opensymphony.xwork2.ModelDriven;
//@Namespace("/systemCode")
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.StringUtil;
import com.jun.plugin.utils.WebUtil;

@Controller
@RequestMapping("/systemCodeController.do")
public class SystemCodeController extends BaseController {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -7594149055359363935L;
	private ISystemCodeService systemCodeService;
	private SystemCode systemCode;

	// private String permissionName;
	// private Integer id;
	// private Integer codePid;

	public SystemCode getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(SystemCode systemCode) {
		this.systemCode = systemCode;
	}


	@Autowired
	public void setSystemCodeService(ISystemCodeService systemCodeService) {
		this.systemCodeService = systemCodeService;
	}

	/**
	 * 函数功能说明 TODO:按节点查询所有词典 Administrator修改者名字 2013-6-19修改日期 修改内容
	 * 
	 * @Title: findSystemCodeList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findSystemCodeList")
	public String findSystemCodeList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String id = StringUtil.decodeToUtf(request.getParameter("id"));
		Map map = WebUtil.getAllParameters(request);
		OutputJson(systemCodeService.findSystemCodeList(String.valueOf(map.get("id"))),response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:查询所有词典 Administrator修改者名字 2013-6-19修改日期 修改内容
	 * 
	 * @Title: findAllSystemCodeList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findAllSystemCodeList")
	public String findAllSystemCodeList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(systemCodeService.findSystemCodeList(),response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:弹窗持久化systemCode Administrator修改者名字 2013-6-19修改日期 修改内容
	 * 
	 * @Title: persistenceSystemCodeDig
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=persistenceSystemCodeDig")
	public String persistenceSystemCodeDig(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = WebUtil.getAllParameters(request);
		String permissionName = StringUtil.decodeToUtf(map.get("permissionName"));
		SystemCode systemCode = new SystemCode();
		WebUtil.MapToBean(map, systemCode);
		OutputJson(getMessage(systemCodeService.persistenceSystemCodeDig(systemCode, permissionName, systemCode.getCodeId())), Constants.TEXT_TYPE_PLAIN,response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:删除词典 Administrator修改者名字 2013-6-19修改日期 修改内容
	 * 
	 * @Title: delSystemCode
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=delSystemCode")
	public String delSystemCode(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Json json = new Json();
		json.setTitle("提示");
		Map map = WebUtil.getAllParameters(request);
		SystemCode systemCode = new SystemCode();
		WebUtil.MapToBean(map, systemCode);
		if (systemCodeService.delSystemCode(systemCode.getCodeId())) {
			json.setStatus(true);
			json.setMessage("数据更新成功!");
		} else {
			json.setStatus(false);
			json.setMessage("数据更新失败，或含有子项不能删除!");
		}
		OutputJson(json, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-24修改日期 修改内容
	 * 
	 * @Title: findSystemCodeByType
	 * @Description: TODO:查询词典的公用方法
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findSystemCodeByType")
	public String findSystemCodeByType(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = WebUtil.getAllParameters(request);
		SystemCode systemCode = new SystemCode();
		WebUtil.copyBean3(systemCode, map);
		OutputJson(systemCodeService.findSystemCodeByType(systemCode.getCodeMyid()),response);
		return null;
	}
	
}
