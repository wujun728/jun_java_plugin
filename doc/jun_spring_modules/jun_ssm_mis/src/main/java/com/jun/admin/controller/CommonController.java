package com.jun.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jun.admin.service.ICommonService;
import com.jun.admin.util.Configuration;
import com.jun.admin.util.JsonUtil;
import com.jun.admin.util.LogFactory;
import com.jun.admin.util.MD5Util;
import com.jun.admin.util.RequestUtil;
import com.jun.admin.util.StringUtil;

@Controller
@RequestMapping("/commonController.do")
public class CommonController {

	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired(required = false)
	@Qualifier("commonServiceImpl")
	protected ICommonService commonServiceImpl;

	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(params = "gotoPage")
	public String gotoPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		Map map = RequestUtil.getAllParameters(request);
		String path = StringUtil.decodeToUtf(request.getParameter("path"));
		String status = StringUtil.decodeToUtf(request.getParameter("status"));
		model.addAttribute("path", path);
		model.addAttribute("status", status);
		return Configuration.getInstance().getConfiguration(path);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(params = "findAll")
	public String findAll(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = RequestUtil.getAllParameters(request);
		if(null == map.get("tableName")){
			return null ;
		}
		List list=this.commonServiceImpl.findAll(map);
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
	@RequestMapping(params = "getColumnValues")
	public String getColumnValues(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = RequestUtil.getAllParameters(request);
		if(null == map.get("tableName")){
			return null ;
		}
		List list=this.commonServiceImpl.getColumnValues(map);
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
		Map map = RequestUtil.getAllParameters(request);
		if(null == map.get("tableName") || null == map.get("id")){
			return null ;
		}
		int flag =  this.commonServiceImpl.doRemove(map);
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
		Map map = RequestUtil.getAllParameters(request);
		map.put("page", page);
		map.put("rows", rows);
		List list=this.commonServiceImpl.findByID(map);
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
		Map map = RequestUtil.getAllParameters(request);
		map.put("page", page);
		map.put("rows", rows);
		int count=this.commonServiceImpl.getCount(map);
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
		Map map = RequestUtil.getAllParameters(request);
		String ID = StringUtil.decodeToUtf(request.getParameter("ID"));
		String TABLENAME = StringUtil.decodeToUtf(request.getParameter("TABLENAME"));
		int flag = 0;
		if("".equals(TABLENAME)){
			return null;
		}
		if ("".equals(ID)) {
			flag=this.commonServiceImpl.doCreate(map);
		}else{
			flag=this.commonServiceImpl.doUpdate(map);
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
}
