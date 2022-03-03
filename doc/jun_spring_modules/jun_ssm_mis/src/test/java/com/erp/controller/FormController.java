package com.erp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.service.IFormService;
import com.jun.admin.LogFactory;
import com.jun.plugin.utils.Configuration;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.DateUtil;
import com.jun.plugin.utils.JsonUtil;
import com.jun.plugin.utils.LogUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.StringUtil;

@Controller
@RequestMapping("/formController.do")
public class FormController {

	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired(required = false)
	@Qualifier("formServiceImpl")
	protected IFormService formServiceImpl;

	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(params = "gotoPage")
	public String gotoPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		Map map = WebUtil.getAllParameters(request);
		String path = StringUtil.decodeToUtf(request.getParameter("path"));
		String status = StringUtil.decodeToUtf(request.getParameter("status"));
		model.addAttribute("path", path);
		model.addAttribute("status", status);
		return Configuration.getInstance().getConfiguration(path);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "findAll")
	public String findAll(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = WebUtil.getAllParameters(request);
		if(null == map.get("tableName")){
			return null ;
		}
		List list=this.formServiceImpl.findAll(map);
		String jsonData = JsonUtil.getBasetJsonData(list);
		JsonResponse(jsonData,response);
		return null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "method=queryForJson")
	public String queryForJson(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogUtil.getLogger().info("执行路径：" + CallStack.toString());
        Date startDate = new Date();
        
        
        Map map = WebUtil.getAllParameters(request);
        map=Constants.mapConfig(map);
        if(null==Constants.mapConfig(map)){
        	return null;
        }
        List list=this.formServiceImpl.queryJsonBySQl(map);
        String jsonData = JsonUtil.getBasetJsonData(ListKeyLowerCase(list));
        JsonResponse(jsonData,response);
        
        
        Date endDate = new Date();
        LogUtil.getLogger().info("执行脚本：" + map.get("sql"));
        LogUtil.getLogger().info("运行时间：" + DateUtil.getTimeInMillis(startDate, endDate));
        
		return null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "getColumnValues")
	public String getColumnValues(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = WebUtil.getAllParameters(request);
		if(null == map.get("tableName")){
			return null ;
		}
		List list=this.formServiceImpl.getColumnValues(map);
		String jsonData = "";
		jsonData = JsonUtil.getBasetJsonData(list);
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
		return null;
	}
     

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "doRemove")
	public String doRemove(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = WebUtil.getAllParameters(request);
		if(null == map.get("tableName") || null == map.get("id")){
			return null ;
		}
		int flag =  this.formServiceImpl.doRemove(map);
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write(String.valueOf(flag));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
		return null;
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(params = "findByID")
	public String findByID(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 0;
		int rows = request.getParameter("rows") != null ? Integer.parseInt(request.getParameter("rows")) : 20;
		Map map = WebUtil.getAllParameters(request);
		map.put("page", page);
		map.put("rows", rows);
		List list=this.formServiceImpl.findByID(map);
		String jsonData = "";
		jsonData = JsonUtil.getBasetJsonData(list);
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
		return null;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(params = "getCount")
	public String getCount(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 0;
		int rows = request.getParameter("rows") != null ? Integer.parseInt(request.getParameter("rows")) : 20;
		Map map = WebUtil.getAllParameters(request);
		map.put("page", page);
		map.put("rows", rows);
		int count=this.formServiceImpl.getCount(map);
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write(String.valueOf(count));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
		return null;
	}
	
	
	

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "doSaveOrUpdate")
	public String doSaveOrUpdate(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = WebUtil.getAllParameters(request);
		String ID = StringUtil.decodeToUtf(request.getParameter("ID"));
		String TABLENAME = StringUtil.decodeToUtf(request.getParameter("TABLENAME"));
		int flag = 0;
		if("".equals(TABLENAME)){
			return null;
		}
		if ("".equals(ID)) {
			flag=this.formServiceImpl.doCreate(map);
		}else{
			flag=this.formServiceImpl.doUpdate(map);
		}
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write(String.valueOf(flag));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
		return null;
	}
	
	public static void JsonResponse(String json,HttpServletResponse response){
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write(String.valueOf(json));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List ListKeyLowerCase(List list){
		List nl=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map=(Map) list.get(i);
			HashMap nm = new HashMap();
			Set entrySet = map.entrySet();
			Iterator iter = entrySet.iterator(); 
			while(iter.hasNext()){
				Map.Entry entry = (Entry) iter.next();
//				System.out.println(entry.getKey().toString().toLowerCase() + "\t"+ entry.getValue());
				nm.put(entry.getKey().toString().toLowerCase(), entry.getValue());
				nl.add(nm);
			}
		}
		return nl;
	}
}
