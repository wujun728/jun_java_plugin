package com.jun.web.bdy.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet(name="baiduYunServlet",urlPatterns={"/server"})
public class BaiduYunServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String q=URLEncoder.encode(req.getParameter("q"), "utf-8");; // 查询数据
		String jsoncallback=req.getParameter("jsoncallback"); 
		String start=req.getParameter("start"); // 起始页
		String urlStr="https://www.googleapis.com/customsearch/v1element?key=AIzaSyCVAXiUzRYsML1Pv6RwSG1gunmMikTzQqY&rsz=filtered_cse&num=10&hl=zh_CN&prettyPrint=false&source=gcsc&gss=.com&sig=432dd570d1a386253361f581254f9ca1&start="+start+"&cx=002888546953948157320:bahxnr-mq7i&q="+q+"&sort=&googlehost=www.google.com";
		URL url = new URL(urlStr); 
	    BufferedReader bufr = new BufferedReader(new InputStreamReader(new BufferedInputStream(url.openStream()),"utf-8"));
	    String line;
	    StringBuffer sb=new StringBuffer();
	    while((line = bufr.readLine())!=null){
	    	sb.append(line);
	    }
	    bufr.close();
	    JSONObject jsonObject=JSONObject.fromObject(sb.toString());
	    JSONArray results=jsonObject.getJSONArray("results");
	    JSONArray r=new JSONArray();
	    for(int i=0;i<results.size();i++){
	    	JSONObject j=(JSONObject) results.get(i);
	    	JSONObject o=new JSONObject();
	    	o.put("title", j.get("title"));
	    	o.put("content", j.get("content"));
	    	o.put("unescapedUrl", j.get("unescapedUrl"));
	    	r.add(o);
	    }
	    try {
	    	resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.println(jsoncallback+"("+r.toString()+")");
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
