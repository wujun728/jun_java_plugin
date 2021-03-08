package spring_mvc.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import spring_mvc.model.user;
import spring_mvc.utility.ExportExcel;
import spring_mvc.utility.InvokeUtility;
import spring_mvc.view.ViewExcel;

@Controller
public class ExeclController {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	/*@RequestMapping("/getexecl")
	public ModelAndView excel(Map<String,Object> model,HttpServletRequest request,HttpServletResponse response){
		ViewExcel viewExcel=new ViewExcel();
		List<user> users=new ArrayList<user>();
		users.add(new user("11","康力1",192.80,"1999-08-06"));
		users.add(new user("12","康力2",123.80,sdf.format(new Date())));
		users.add(new user("13","康力3",122.80,"1987-08-01"));
		users.add(new user("14","康力4",11142.80,sdf.format(new Date())));
		users.add(new user("15","康力5",162.80,"2016-10-10"));
		model.put("users",users);
		model.put("fileName","个人信息表");
		return new ModelAndView(viewExcel,model);
	}*/
	@ResponseBody
	@RequestMapping("/getexecl")
	public String getexcel(Map<String,Object> model,HttpServletRequest request,HttpServletResponse response){
		List<user> users=new ArrayList<user>();
		users.add(new user("11","康力1",192.80,"1999-08-06"));
		users.add(new user("12","康力2",123.80,sdf.format(new Date())));
		users.add(new user("13","康力3",122.80,"1987-08-01"));
		users.add(new user("14","康力4",11142.80,sdf.format(new Date())));
		users.add(new user("15","康力5",162.80,"2016-10-10"));
		request.setAttribute("filename", "个人信息表");
		ExportExcel<user>  ee=new ExportExcel<user>();
		ee.exportExcel(users, request, response);
		return "下载成功！";
	}
}
