package com.java1234.action;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;

import com.java1234.dao.UserDao;
import com.java1234.model.PageBean;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExcelUtil;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;
import com.jun.plugin.servlet.BaseServlet;
import com.opensymphony.xwork2.ActionSupport;



@WebServlet(name="UserServlet",urlPatterns={"/user!list"})
public class UserServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String page;
	private String rows;
	private String id;
	private User user;
	private String delId;
	
	private File userUploadFile;
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	
	public String getDelId() {
		return delId;
	}
	public void setDelId(String delId) {
		this.delId = delId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	


	public File getUserUploadFile() {
		return userUploadFile;
	}
	public void setUserUploadFile(File userUploadFile) {
		this.userUploadFile = userUploadFile;
	}




	DbUtil dbUtil=new DbUtil();
	UserDao userDao=new UserDao();
	
	public String list()throws Exception{
		Connection con=null;
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(userDao.userList(con, pageBean));
			int total=userDao.userCount(con);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(),result);
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
		return null;
	}
	
	public String save()throws Exception{
		if(StringUtil.isNotEmpty(id)){
			user.setId(Integer.parseInt(id));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				saveNums=userDao.userModify(con, user);
			}else{
				saveNums=userDao.userAdd(con, user);
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "����ʧ��");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
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
		return null;
	}
	
	public String delete()throws Exception{
		Connection con=null;
		try {
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=userDao.userDelete(con, delId);
			if(delNums==1){
				result.put("success", "true");
			}else{
				result.put("errorMsg", "ɾ��ʧ��");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public String export()throws Exception{
		Connection con=null;
		try {
			con=dbUtil.getCon();
			Workbook wb=new HSSFWorkbook();
			String headers[]={"���","����","�绰","Email","QQ"};
			ResultSet rs=userDao.userList(con, null);
			ExcelUtil.fillExcelData(rs, wb, headers);
			ResponseUtil.export(ServletActionContext.getResponse(), wb, "����excel.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String export2()throws Exception{
		Connection con=null;
		try {
			con=dbUtil.getCon();
			ResultSet rs=userDao.userList(con, null);
			Workbook wb=ExcelUtil.fillExcelDataWithTemplate(userDao.userList(con, null), "userExporTemplate.xls");
			ResponseUtil.export(ServletActionContext.getResponse(), wb, "����ģ�浼��excel.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	

	public String upload()throws Exception{
		POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(userUploadFile));
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFSheet hssfSheet=wb.getSheetAt(0);  // ��ȡ��һ��Sheetҳ
		if(hssfSheet!=null){
			for(int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++){
				HSSFRow hssfRow=hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
				User user=new User();
				user.setName(ExcelUtil.formatCell(hssfRow.getCell(0)));
				user.setPhone(ExcelUtil.formatCell(hssfRow.getCell(1)));
				user.setEmail(ExcelUtil.formatCell(hssfRow.getCell(2)));
				user.setQq(ExcelUtil.formatCell(hssfRow.getCell(3)));
				Connection con=null;
				try{
					con=dbUtil.getCon();
					userDao.userAdd(con, user);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					dbUtil.closeCon(con);
				}
			}
		}
		JSONObject result=new JSONObject();
		result.put("success", "true");
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	
	
}
