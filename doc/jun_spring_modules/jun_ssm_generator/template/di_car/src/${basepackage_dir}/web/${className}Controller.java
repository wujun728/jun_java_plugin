<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  
<#assign classNameLowerCase = className?lower_case>
<#assign from = basepackage?last_index_of(".")>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>
<#assign pkJavaType = table.idColumn.javaType>  
<#assign targetpackage = targetpackage>
package  ${basepackage}.web;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${basepackage}.entity.${className};
import ${basepackage}.service.I${className}Service;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.property.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
<#assign myParentDir="web">


<#include "/copyright_class.include" >
@Controller
@RequestMapping(value="/${targetpackage}/${classNameLowerCase}")
public class ${className}Controller  extends BaseController {
	@Resource
	private I${className}Service ${classNameLower}Service;
	
	private String listurl="/${rootPagefloder}/${classNameLowerCase}/${classNameLowerCase}List";
	
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param ${classNameLower}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,${className} ${classNameLower}) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, ${classNameLower});
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param ${classNameLower}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody   
	public  ReturnDatas listjson(HttpServletRequest request, Model model,${className} ${classNameLower}) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<${className}> datas=${classNameLower}Service.findListDataByFinder(null,page,${className}.class,${classNameLower});
			returnObject.setQueryBean(${classNameLower});
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,${className} ${classNameLower}) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = ${classNameLower}Service.findDataExportExcel(null,listurl, page,${className}.class,${classNameLower});
		String fileName="${classNameLower}"+GlobalStatic.excelext;
		downFile(response, file, fileName,true);
		return;
	}
	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/${rootPagefloder}/${classNameLowerCase}/${classNameLowerCase}Look";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody      
	public ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
	     <#if pkJavaType=="java.lang.String">
		${pkJavaType} id=request.getParameter("${table.pkColumn.columnNameFirstLower}");
		if(StringUtils.isNotBlank(id)){
		<#else>
		  String  strId=request.getParameter("${table.pkColumn.columnNameFirstLower}");
		  ${pkJavaType} id=null;
		  if(StringUtils.isNotBlank(strId)){
			 id= ${pkJavaType}.valueOf(strId.trim());
		</#if>
		  ${className} ${classNameLower} = ${classNameLower}Service.find${className}ById(id);
		   returnObject.setData(${classNameLower});
		}else{
		returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
		
	}
	
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	@ResponseBody      
	public ReturnDatas saveorupdate(Model model,${className} ${classNameLower},HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
		
			<#if pkJavaType=="java.lang.String">
			${pkJavaType} ${table.pkColumn.columnNameFirstLower} =${classNameLower}.get${table.pkColumn.columnName}();
			if(StringUtils.isBlank(${table.pkColumn.columnNameFirstLower})){
			  ${classNameLower}.set${table.pkColumn.columnName}(null);
			}
			</#if>
		
			${classNameLower}Service.saveorupdate(${classNameLower});
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	
	}
	
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/${rootPagefloder}/${classNameLowerCase}/${classNameLowerCase}Cru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	@ResponseBody      
	public  ReturnDatas delete(HttpServletRequest request) throws Exception {

			// 执行删除
		try {
			 <#if pkJavaType=="java.lang.String">
		${pkJavaType} id=request.getParameter("${table.pkColumn.columnNameFirstLower}");
		if(StringUtils.isNotBlank(id)){
		<#else>
		  String  strId=request.getParameter("${table.pkColumn.columnNameFirstLower}");
		  ${pkJavaType} id=null;
		  if(StringUtils.isNotBlank(strId)){
			 id= ${pkJavaType}.valueOf(strId.trim());
		</#if>
				${classNameLower}Service.deleteById(id,${className}.class);
				return new ReturnDatas(ReturnDatas.SUCCESS,MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
	}
	
	/**
	 * 删除多条记录
	 * 
	 */
	@RequestMapping("/delete/more")
	@ResponseBody      
	public ReturnDatas deleteMore(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if(StringUtils.isBlank(records)){
			 return new ReturnDatas(ReturnDatas.ERROR,MessageUtils.DELETE_ALL_FAIL);
		}
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR,MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			${classNameLower}Service.deleteByIds(ids,${className}.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnDatas(ReturnDatas.ERROR,MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,MessageUtils.DELETE_ALL_SUCCESS);
		
		
	}

}
