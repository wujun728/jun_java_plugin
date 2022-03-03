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
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.system.entity.Fwlog;
import org.springrain.system.service.IFwlogService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author weicms.net<Auto generate>
 * @version  2013-07-29 11:36:44
 * @see org.springrain.springrain.web.Fwlog
 */
@Controller
@RequestMapping(value="/system/fwlog")
public class FwlogController  extends BaseController {
	@Resource
	private IFwlogService fwlogService;
	
	private String listurl="/system/fwlog/fwlogList";
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param fwlog
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Fwlog fwlog) throws Exception {
		ReturnDatas returnObject = listjson(request, model, fwlog);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param fwlog
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody 
	public ReturnDatas listjson(HttpServletRequest request, Model model, Fwlog fwlog) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
	
		Page page = newPage(request);
		page.setOrder("strDate");
		page.setSort("desc");
		List<Fwlog> datas = fwlogService.findListDataByFinder(null, page, Fwlog.class, fwlog);
		returnObject.setQueryBean(fwlog);
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
	 * @param fwlog
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model,
			Fwlog fwlog) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);
		File file = fwlogService.findDataExportExcel(null, listurl, page, Fwlog.class, fwlog);
		String fileName = "fwlog" + GlobalStatic.excelext;
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
		return "/system/fwlog/fwlogLook";
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
			Fwlog fwlog = fwlogService.findFwlogById(id);
			returnObject.setData(fwlog);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}
	

	
	/*
	@RequestMapping("/test/jsonp")
	public void testJsonp(HttpServletRequest request,HttpServletResponse response) throws Exception{
			//得到js函数名称     
			String jsonpcallback=request.getParameter("jsonpcallback");
		    
			if(StringUtils.isBlank(jsonpcallback)){
				return;
			}
			
			//对参数编码,处理异常的注入
			jsonpcallback=URLEncoder.encode(jsonpcallback);
		
			List data=new ArrayList();
			response.setHeader("content-type", "application/javascript;charset=UTF-8");
			String _json=JsonUtils.writeValueAsString(data);
		     try { 
		    	 response.getWriter().write(jsonpcallback + "("+_json+")"); //返回jsonp数据 
		     } catch (IOException e) { 
		     } 
	}
    */
	
	
	/**
	 * spring webmvc 输出纯文本文件
	 * @return
	 * @throws Exception
	 */
	
	/*
	@RequestMapping("/download/txtfile")
	@ResponseBody
	public ResponseEntity<byte[]> downloadtxtfile()throws Exception {
	  
		byte[] bb = IOUtils.toByteArray(new FileInputStream(new File("txtfile")));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<byte[]>(bb, headers, HttpStatus.OK);
	}
    */
	
	
}
