package com.jun.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jun.plugin.base.BaseServlet;

//http://localhost:18080/jun_plugin_base/ajax?method=getMsg&username=abc
@SuppressWarnings("serial")
@WebServlet(name = "AjaxServlet", value = { "/ajax" }, asyncSupported = true)
public class AjaxServlet extends BaseServlet {

	StringBuilder xml=new StringBuilder();
	public void xmlInit() {
		xml.delete(0, xml.length());
	}
	
	public void getXML(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AjaxServlet::doPost()");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("�û���:" + username);
		System.out.println("����:" + password);
		String tip = null;
		if ("jack".equals(username) && "123456".equals(password)) {
			tip = "success";
		} else {
			tip = "fail";
		}
		xmlInit();
		xml.append("<root>");
		xml.append("<res>");
		xml.append(tip);
		xml.append("</res>");
		xml.append("</root>");
		outXml(response, xml);
	}

	StringBuilder json=new StringBuilder();
	protected void jsonInit() {
		json.delete(0, json.length());
	}
	public void getMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		byte[] buf = username.getBytes("ISO8859-1");
		username = new String(buf, "UTF-8");
		String msg = null;
		if (username.equals("abc")) {
			msg = "abc";
		} else {
			msg = "images/MsgSent.gif";
		}
		jsonInit();
		outJson(response, json.append("<font color='red'>" + msg + "</font>"));
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void getJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Province p1 = new Province(1, "吉林省");
//		Province p2 = new Province(2, "辽宁省");
//		Province p3 = new Province(3, "山东省");
//		List<Province> list = new ArrayList<Province>();
//		list.add(p1);
//		list.add(p2);
//		list.add(p3);
//		Map map = new HashMap();
//		map.put("total", 3);
//		map.put("rows", list);
//		outJson(response, map);
//	}
}
