package com.erp.springjdbc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jun.admin.LogFactory;
import com.jun.plugin.utils.DateUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.StringUtil;

/**
 * 方法说明:
 */
@SuppressWarnings("serial")
public class Upload extends HttpServlet {

	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	public Upload() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGetAndPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGetAndPost(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

	public void doUpload() throws ServletException {
		// Put your code here
	}

	/**
	 * get及post提交方式
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void doGetAndPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		if ("doUpload".equalsIgnoreCase(status)) {
			Map map = WebUtil.getAllParameters(request);
			String ID = StringUtil.decodeToUtf(request.getParameter("ID"));
			System.out.println("ID=" + ID);
			System.out.println("ID2=" + map.get("ID"));
			LogFactory.getInstance().getLogger().info("info");
			int flag = 0;
			if ("".equals(ID.trim())) {
				LogFactory.getInstance().getLogger().info("info");
//				flag = this.firmServiceImpl.saveFirmADDInfo(map);
				LogFactory.getInstance().getLogger().info(flag + "");
			}
			if (!"".equals(ID.trim())) {
//				flag = this.firmServiceImpl.updateFirmADDInfo(map);
			}
			System.out.println("flag=" + flag);
			this.doUpload(request, response);
		}
	}

	/**
	 * get及post提交方式
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public void doUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map map = WebUtil.getAllParameters(request);
		request.setCharacterEncoding("GBK");
		// 文件存放的目录
		File tempDirPath = new File(request.getSession().getServletContext().getRealPath("/") + "\\upload\\");
		if (!tempDirPath.exists()) {
			tempDirPath.mkdirs();
		}
		// 创建磁盘文件工厂
		DiskFileItemFactory fac = new DiskFileItemFactory();
		// 创建servlet文件上传组件
		ServletFileUpload upload = new ServletFileUpload(fac);
		// 使用utf-8的编码格式处理数据
		upload.setHeaderEncoding("utf-8");
		// 文件列表
		List fileList = null;
		// 解析request从而得到前台传过来的文件
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			ex.printStackTrace();
			return;
		}
		// 保存后的文件名
		String imageName = null;
		// 便利从前台得到的文件列表
		Iterator<FileItem> it = fileList.iterator();
		while (it.hasNext()) {
			FileItem item = it.next();
			// 如果不是普通表单域，当做文件域来处理
			if (!item.isFormField()) {
				// 生成四位随机数
				Random r = new Random();
				int rannum = (int) (r.nextDouble() * (9999 - 1000 + 1)) + 1000;
				imageName = DateUtil.getNowStrDate() + rannum + item.getName();
				BufferedInputStream in = new BufferedInputStream(item.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(tempDirPath + "\\" + imageName)));
				Streams.copy(in, out, true);
			}
		}
		PrintWriter out = null;
		try {
			out = encodehead(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 这个地方不能少，否则前台得不到上传的结果
		out.write("1");
		out.close();
	}

	/**
	 * Ajax辅助方法 获取 PrintWriter
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 *             request.setCharacterEncoding("utf-8");
	 *             response.setContentType("text/html; charset=utf-8");
	 */
	private PrintWriter encodehead(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		return response.getWriter();
	}

}
