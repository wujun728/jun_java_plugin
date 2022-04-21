package com.jun.plugin.file.fileupload;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jun.plugin.base.BaseServlet;




@WebServlet(name = "XmlFileServlet", value = { "/xmlFileServlet" })
public class XmlFileServlet extends BaseServlet {

	@Override
	public void init() throws ServletException {
		// 获得books.xml,返回真实的路径，当前项目发布到tomcat�?在的目录webapps
		String path = this.getServletContext().getRealPath("/books.xml");
		System.out.println(path);
		// 设置路径
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void XmlFileServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<china>");
		out.println("<province name='吉林�?'>");
		out.println("<city>长春</city>");
		out.println("<city>吉林�?</city>");
		out.println("<city>四平</city>");
		out.println("<city>松原</city>");
		out.println("<city>通化</city>");
		out.println("</province>");
		out.println("<province name='辽宁�?'>");
		out.println("<city>沈阳</city>");
		out.println("<city>大连</city>");
		out.println("<city>鞍山</city>");
		out.println("<city>抚顺</city>");
		out.println("<city>铁岭</city>");
		out.println("</province>");
		out.println("<province name='山东�?'>");
		out.println("<city>济南</city>");
		out.println("<city>青岛</city>");
		out.println("<city>威海</city>");
		out.println("<city>烟台</city>");
		out.println("<city>潍坊</city>");
		out.println("</province>");
		out.println("</china>");
	}

	public void FindAllBookServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 创建Dao
//		BookDao bookDao = new BookDao();
//		List<Book> bookList = bookDao.findAllBook();
//		request.setAttribute("bookList", bookList);

		// 使用转发选择视图 jsp /page/findBooks.jsp
		// * 获得调度�? -- 确定path
		/*
		 * 当前页面�? http://localhost:8080/day09_demo/findAllBook
		 * 目标页面：http://localhost:8080/day09_demo/page/findBooks.jsp *
		 * /page/findBooks.jsp * page/findBooks.jsp
		 */
		RequestDispatcher dispatcher = request.getRequestDispatcher("/page/findBooks.jsp");
		// * 转发
		dispatcher.forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 响应的数据格式是xml格式，设置ContentType成text/xml
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();

		// xml格式的数据，并不是xml文件
		out.println("<china>");
		out.println("<province name='吉林�?'>");
		out.println("<city>长春</city>");
		out.println("<city>吉林�?</city>");
		out.println("<city>四平</city>");
		out.println("<city>松原</city>");
		out.println("<city>通化</city>");
		out.println("</province>");

		out.println("<province name='辽宁�?'>");
		out.println("<city>沈阳</city>");
		out.println("<city>大连</city>");
		out.println("<city>鞍山</city>");
		out.println("<city>抚顺</city>");
		out.println("<city>铁岭</city>");
		out.println("</province>");

		out.println("<province name='山东�?'>");
		out.println("<city>济南</city>");
		out.println("<city>青岛</city>");
		out.println("<city>威海</city>");
		out.println("<city>烟台</city>");
		out.println("<city>潍坊</city>");
		out.println("</province>");
		out.println("</china>");

	}

	public void AddBookServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 从表单中获取用户提交的数�?
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String price = request.getParameter("price");

		// 将数据存放到JavaBean�?
//		Book newBook = new Book(id, title, price);
//		BookDao bookDao = new BookDao();
//		String returnBookId = bookDao.saveBook(newBook);

		// 处理操作结果：string表示成功，null表示失败
		// 处理页面：tip.jsp，但显示内容不同，为当前servlet的内容可以显示到tip.jsp文件中，�?以使用include
		/*
		 * 当前页面：http://localhost:8080/day10_demo/addBookServlet
		 * 目标页面：http://localhost:8080/day10_demo/page/tip.jsp
		 */
		RequestDispatcher dispatcher = request.getRequestDispatcher("/page/tip.jsp");
		// 输出不同内容�?
		// 保存跳转页面url
		String refreshURL = request.getContextPath() + "/findAllBook";
		// 获得输出�?
		PrintWriter out = response.getWriter();
//		if (returnBookId == null) {
//			out.print("未保存成功，请稍后重�?<br>");
//		} else {
//			// 成功
//			out.print("保存成功<br/>");
//		}
		out.print("3秒后自动跳转到查询页�? <a href='" + refreshURL + "'>手动跳转</a>");
		// 包含
		dispatcher.include(request, response);
		// 设置3秒后浏览器自动刷�?,刷新到查询页�?
		/*
		 * 当前页面：http://localhost:8080/day10_demo/addBookServlet
		 * 目标页面：http://localhost:8080/day10_demo/findAllBook
		 */

		response.setHeader("refresh", "3;url=" + refreshURL);

	}

	public void DeleteBookServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 获得�?要删除的书籍的编�?
		String bookId = request.getParameter("bookId");
		// 通知dao，删除书�?
//		BookDao bookDao = new BookDao();
//		String returnBookId = bookDao.deleteBookById(bookId);

		// 处理结果
//		if (returnBookId == null) {
//
//		} else {
//			response.sendRedirect(request.getContextPath() + "/findAllBook");
//		}

	}

	public void PreUpdateBookServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 获得参数bookid
		String bookId = request.getParameter("bookId");

		// 通知dao，查询数�?
//		BookDao bookDao = new BookDao();
//		Book book = bookDao.findBookById(bookId);
//		if (book == null) {
//		} else {
//			request.setAttribute("book", book);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/page/updateBook.jsp");
//			dispatcher.forward(request, response);
//		}

	}
	
	

	public void UpDescServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//可以获取中文的文件名
		String path = getServletContext().getRealPath("/up");
		DiskFileItemFactory disk = 
				new DiskFileItemFactory();
		disk.setRepository(new File("d:/a"));
		try{
			ServletFileUpload up = 
					new ServletFileUpload(disk);
			List<FileItem> list = up.parseRequest(request);
			
			//声明�?个map用于封装信息
			Map<String,Object> img = new HashMap<String, Object>();
			for(FileItem file:list){
				//第一步：判断是否是普通的表单�?
				if(file.isFormField()){
					String fileName = file.getFieldName();//<input type="text" name="desc">=desc
					String value = file.getString("UTF-8");//默认以ISO方式读取数据
					System.err.println(fileName+"="+value);
					//放入图片的说�?
					img.put("desc",value);
				}else{//说明是一个文�?
					String fileName = file.getName();//以前的名�?
					//处理文件�?
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					img.put("oldName",fileName);
					//修改名称
					String extName = fileName.substring(fileName.lastIndexOf("."));
					String newName = 
							UUID.randomUUID().toString().replace("-", "")+extName;
					//保存新的名称
					img.put("newName",newName);
					file.write(new File(path+"/"+newName));
					System.err.println("文件名是:"+fileName);
					System.err.println("文件大小是："+file.getSize());
					img.put("size",file.getSize());
					file.delete();
				}
			}
			//将img=map放到req
			request.setAttribute("img",img);
			//转发
			request.getRequestDispatcher("/jsps/desc.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	public void UpdateBookServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//修改功能
		
		//获得数据，保存Book对象
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String price = request.getParameter("price");
		//创建book对象
//		Book book = new Book(id, title, price);
		
		
		//通知dao，修改操�?
//		BookDao bookDao = new BookDao();
//		String returnBookId = bookDao.updateBook(book);
		
		
		//处理修改结果
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/page/tip.jsp");
		//输出不同内容�?
		//保存跳转页面url
		String refreshURL = request.getContextPath() + "/findAllBook";
		//获得输出�?
		PrintWriter out = response.getWriter();
//		if(returnBookId == null){
//			out.print("未修改成功，请稍后重�?<br>");
//		} else {
//			out.print("修改成功<br/>");
//		}
		out.print("3秒后自动跳转到查询页�? <a href='" + refreshURL + "'>手动跳转</a>");
		//包含
		dispatcher.include(request, response);
		//设置3秒后浏览器自动刷�?,刷新到查询页�?
		/*
		 * 当前页面：http://localhost:8080/day10_demo/addBookServlet
		 * 目标页面：http://localhost:8080/day10_demo/findAllBook
		 */
		
		response.setHeader("refresh", "3;url=" + refreshURL);
		
		

	}

}
