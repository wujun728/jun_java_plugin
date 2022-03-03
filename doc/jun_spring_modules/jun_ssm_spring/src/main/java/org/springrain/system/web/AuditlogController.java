package  org.springrain.system.web;
import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springrain.frame.controller.BaseController;
import org.springrain.frame.entity.AuditLog;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.system.service.IAuditlogService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author weicms.net<Auto generate>
 * @version  2013-08-02 12:14:50
 * @see org.springrain.frame.entity.springrain.web.AuditLog
 */
@Controller
@RequestMapping(value="/system/auditlog")
public class AuditlogController  extends BaseController {
	@Resource
	private IAuditlogService auditlogService;
	
	private String listurl="/system/auditlog/auditlogList";
	
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param auditlog
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, AuditLog auditlog) throws Exception {
		ReturnDatas returnObject = listjson(request, model, auditlog);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param auditlog
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody 
	public ReturnDatas listjson(HttpServletRequest request, Model model, AuditLog auditlog) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
	
		Page page = newPage(request);
		page.setOrder("operationTime");
		page.setSort("desc");
		
		List<AuditLog> datas = auditlogService.findListDataByFinder(null, page, AuditLog.class, auditlog);
		returnObject.setQueryBean(auditlog);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 导出Excle格式的数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param auditlog
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model,
			AuditLog auditlog) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);
		File file = auditlogService.findDataExportExcel(null, listurl, page, AuditLog.class, auditlog);
		String fileName = "auditlog" + GlobalStatic.excelext;
		downFile(response, file, fileName, true);
		return;
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/auditlog/auditlogLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody 
	public ReturnDatas lookjson(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			AuditLog auditlog = auditlogService.findAuditlogById(id);
			returnObject.setData(auditlog);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}

}
