package com.jun.plugin.bizservice.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jun.plugin.bizservice.dao.GradeDao;
import com.jun.plugin.bizservice.model.Grade;
import com.jun.plugin.bizservice.model.PageBean;
import com.jun.plugin.bizservice.util.DbUtil;
import com.jun.plugin.bizservice.util.JsonUtil;
import com.jun.plugin.bizservice.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet(name="GradeComboListServlet",urlPatterns="/gradeComboList", asyncSupported = true)
@WebInitParam(name="test", value="123") 
public class GradeComboListServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONArray jsonArray=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("gradeName", "��ѡ��...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, null,null)));
			ResponseUtil.write(response, jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
}
