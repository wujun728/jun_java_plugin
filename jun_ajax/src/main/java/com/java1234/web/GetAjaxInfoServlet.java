package com.java1234.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetAjaxInfoServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String action=request.getParameter("action");
		if("jsonObject".equals(action)){
			this.getJsonObject(request, response);
		}else if("jsonArray".equals(action)){
			this.getJsonArray(request, response);
		}else if("jsonNested".equals(action)){
			this.getJsonNested(request, response);
		}
		
	}

	
	private void getJsonObject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		// String resultJson="{\"name\":\"����\",\"age\":22}";
		JSONObject resultJson=new JSONObject();
		resultJson.put("name", "����");
		resultJson.put("age", 22);
		out.println(resultJson);
		out.flush();
		out.close();
	}
	
	private void getJsonArray(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		JSONObject resultJson=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject1=new JSONObject();
		jsonObject1.put("name", "����");
		jsonObject1.put("age", 22);
		JSONObject jsonObject2=new JSONObject();
		jsonObject2.put("name", "����");
		jsonObject2.put("age", 23);
		JSONObject jsonObject3=new JSONObject();
		jsonObject3.put("name", "����");
		jsonObject3.put("age", 24);
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		jsonArray.add(jsonObject3);
		
		resultJson.put("students", jsonArray);
		out.println(resultJson);
		out.flush();
		out.close();
	}
	
	private void getJsonNested(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		JSONObject resultJson=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject1=new JSONObject();
		jsonObject1.put("name", "����");
		jsonObject1.put("age", 22);
		
		JSONObject scoreObject1=new JSONObject();
		scoreObject1.put("chinese", 90);
		scoreObject1.put("math", 100);
		scoreObject1.put("english", 80);
		jsonObject1.put("score", scoreObject1);
		
		JSONObject jsonObject2=new JSONObject();
		jsonObject2.put("name", "����");
		jsonObject2.put("age", 23);
		
		JSONObject scoreObject2=new JSONObject();
		scoreObject2.put("chinese", 70);
		scoreObject2.put("math", 90);
		scoreObject2.put("english", 90);
		jsonObject2.put("score", scoreObject2);
		
		JSONObject jsonObject3=new JSONObject();
		jsonObject3.put("name", "����");
		jsonObject3.put("age", 24);
		
		JSONObject scoreObject3=new JSONObject();
		scoreObject3.put("chinese", 80);
		scoreObject3.put("math", 60);
		scoreObject3.put("english", 90);
		jsonObject3.put("score", scoreObject3);
		
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		jsonArray.add(jsonObject3);
		
		resultJson.put("students", jsonArray);
		out.println(resultJson);
		out.flush();
		out.close();
	}
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String action=request.getParameter("action");
		if("checkUserName".equals(action)){
			this.checkUserName(request, response);
		}else if("ejld".equals(action)){
			this.ejld(request, response);
		}
		
	}

	
	private void checkUserName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String userName=request.getParameter("userName");
		JSONObject resultJson=new JSONObject();
		if("jack".equals(userName)){
			resultJson.put("exist", true);
		}else{
			resultJson.put("exist", false);
		}
		out.println(resultJson);
		out.flush();
		out.close();
	}
	
	private void ejld(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String shengId=request.getParameter("shengId");
		JSONObject resultJson=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		JSONObject temp=null;
		switch(Integer.parseInt(shengId)){
			case 1:{
				temp=new JSONObject();temp.put("id", 1);temp.put("text", "�Ͼ�");jsonArray.add(temp);
				temp=new JSONObject();temp.put("id", 2);temp.put("text", "��ͨ");jsonArray.add(temp);
				temp=new JSONObject();temp.put("id", 3);temp.put("text", "̩��");jsonArray.add(temp);
				break;
			}
			case 2:{
				temp=new JSONObject();temp.put("id", 4);temp.put("text", "����");jsonArray.add(temp);
				temp=new JSONObject();temp.put("id", 5);temp.put("text", "��̨");jsonArray.add(temp);
				temp=new JSONObject();temp.put("id", 6);temp.put("text", "����");jsonArray.add(temp);
				break;
			}
			case 3:{
				temp=new JSONObject();temp.put("id", 7);temp.put("text", "����");jsonArray.add(temp);
				temp=new JSONObject();temp.put("id", 8);temp.put("text", "����");jsonArray.add(temp);
				temp=new JSONObject();temp.put("id", 9);temp.put("text", "����");jsonArray.add(temp);
				break;
			}
		}
		resultJson.put("rows", jsonArray);
		out.println(resultJson);
		out.flush();
		out.close();
	}
}
