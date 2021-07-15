package com.jun.plugin.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;

import com.jun.plugin.datasource.DataSourceUtil;
import com.jun.plugin.jdbc.JdbcUtil;
import com.jun.plugin.reflect.BeanFactory;
import com.jun.plugin.utils.BeanUtil;

// http://localhost:8080/jun_biz_erp2/bizServlet?method=getDatagrid
@WebServlet(name = "BizServlet", value = { "/bizServlet" })
public class BizServlet extends com.jun.plugin.base.BaseServlet {
	
	private BizService service = new BizService();

	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(BizServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println(this.getClass());
	}

	public void getDatagrid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map map=BeanUtil.getAllParameters(request);
		outJson(response, BeanFactory.getInstance(BizService.class).getDatagrid(map));
	}

	
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map map=BeanUtil.getAllParameters(request);
		outJson(response, BeanFactory.getInstance(BizService.class).delete(map));
	}
	
	public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map map=BeanUtil.getAllParameters(request);
		outJson(response, BeanFactory.getInstance(BizService.class).delete(map));
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Map param = WebUtil.getAllParameters(request);
//		WebUtil.copyBean3(param, bug);
//		OutputJson(getMessage(bugService.delBug(bug.getBugId())), response);
	}
	
	
	public void getList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map map=BeanUtil.getAllParameters(request);
		outJson(response, BeanFactory.getInstance(BizService.class).getDatagrid(map));
	}
	
	public void getString(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map map=BeanUtil.getAllParameters(request);
		outJson(response, BeanFactory.getInstance(BizService.class).getDatagrid(map));
	}
	
	public void getFlag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map map=BeanUtil.getAllParameters(request);
		outJson(response, BeanFactory.getInstance(BizService.class).getDatagrid(map));
	}

	//#############################################################################################################
	//#############################################################################################################
	//#############################################################################################################
	//#############################################################################################################
	//#############################################################################################################
	//#############################################################################################################
	//#############################################################################################################
	

	public void ShowServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		List<Image> list = service.queryAll();
//		request.setAttribute("imgs", list);
		getServletContext().getRequestDispatcher("/jsps/show.jsp").forward(request, response);
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ip = request.getRemoteAddr();
		if ("127.0.0.1".equals(ip)) {
			String username = request.getParameter("username");
			byte[] buf = username.getBytes("ISO8859-1");
			username = new String(buf, "UTF-8");
			Map<String, HttpSession> map = (Map<String, HttpSession>) this.getServletContext().getAttribute("map");
			HttpSession session = map.get(username);
			map.remove(username);
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("userid");
		}
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/login.html");
	}

	public void gotoAndShowInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, HttpSession> onLogin = (Map<String, HttpSession>) getServletContext().getAttribute("onLogin");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (onLogin != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Entry<String, HttpSession> en : onLogin.entrySet()) {
				Map<String, Object> one = new HashMap<String, Object>();
				one.put("id", en.getKey());
				one.put("name", en.getValue().getAttribute("user"));
				one.put("cTime", sdf.format(new Date(en.getValue().getCreationTime())));
				one.put("lTime", sdf.format(new Date(en.getValue().getLastAccessedTime())));
				one.put("ip", en.getValue().getAttribute("ip"));
				list.add(one);
			}
			request.setAttribute("list", list);
		}
		request.getRequestDispatcher("/jsps/show.jsp").forward(request, response);
	}

	public void refreshURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String title = request.getParameter("title");
		// dosomething
		RequestDispatcher dispatcher = request.getRequestDispatcher("/page/tip.jsp");
		String refreshURL = request.getContextPath() + "/findAllBook";
		PrintWriter out = response.getWriter();
		if ("" == null) {
			out.print("鏈慨鏀规垚鍔燂紝璇风◢鍚庨噸璇�<br>");
		} else {
			out.print("淇敼鎴愬姛<br/>");
		}
		out.print("3绉掑悗鑷姩璺宠浆鍒版煡璇㈤〉闈� <a href='" + refreshURL + "'>鎵嬪姩璺宠浆</a>");
		dispatcher.include(request, response);
		response.setHeader("refresh", "3;url=" + refreshURL);
	}

	public void getForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 灏嗚〃鍗曚功鍙戦�佺粰娴忚鍣�
		// * 纭畾鍙戦�佹暟鎹殑缂栫爜
		response.setContentType("text/html;charset=UTF-8");
		// * 鑾峰緱瀛楃娴�
		PrintWriter out = response.getWriter();
		// * 鍙戦�佹暟鎹�
		out.println("<form action=\"#\" method=\"post\">");
		out.println("鍚嶇О锛�<input type='text' name='username' value='鍑ゅ'> <br/>");
		out.println("瀵嗙爜锛�<input type='password' name='userpwd'> <br/>");
		out.println("<input type='submit' value='鎻愪氦' />");
		out.println(" </form>");
	}

	public void doTableOperator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getSession().setAttribute("username", "myname");
		boolean createResult = false;
		boolean insertResult = false;
		boolean dropResult = false;
		// operator.createTable();
		createResult = true;
		if (createResult) {
			try {
				// operator.insert();
				insertResult = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				// operator.tearDown();
				dropResult = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		out.println("{'createResult':" + createResult + ",'insertResult':" + insertResult + ",'dropResult':" + dropResult + "}");
		out.flush();
		out.close();
	}


	public void reLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// JSESSIONID=DFE75A1E9CCAA1591F900FA3B1AEE9F3 tomcat鑷姩娣诲姞cookie锛屼細璇濈骇
		// 鑾峰緱session
		HttpSession session = request.getSession(); // 娌℃湁鍒涘缓锛屾湁杩斿洖
		System.out.println(session.isNew());
		// 鎸佷箙鍖�
		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		// 璁剧疆鏈夋晥鏃堕棿
		cookie.setMaxAge(60 * 30);
		// 閫氱煡娴忚鍣�
		response.addCookie(cookie);
	}

	public void RequestDispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String re = request.getHeader("referer");
		if (re == null | !re.startsWith("http://localhost:8080")) {// startsWith锟斤拷锟皆达拷锟街凤拷锟角凤拷锟斤拷指锟斤拷锟斤拷前缀锟斤拷始锟斤拷
			response.sendRedirect("/servletDemo/index.jsp");// 使锟斤拷指锟斤拷路锟斤拷锟斤拷突锟斤拷朔锟斤拷锟斤拷锟接︼拷锟�
			return;

		}
		response.setContentType("text/html;charset=UTF-8");
		// response.setHeader("refresh", "1");// 每1锟斤拷刷锟斤拷一锟轿ｏ拷
		// String message = "<meta http-equiv='refresh' content='2;
		// url=/servletDemo/index.jsp'>2锟斤拷锟斤拷锟阶�";
		// this.getServletContext().setAttribute("message", message);
		// this.getServletContext().getRequestDispatcher("/response_4.jsp").forward(request,
		// response);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/testRequestServlet");
		PrintWriter out = response.getWriter();
		System.out.println("杞彂鍓�");
		out.print("椤甸潰杞彂鍓�<br/>"); // 娌℃湁鍙戦�佸埌娴忚鍣�
		request.setAttribute("root", "123456");
		System.out.println(request);
		dispatcher.forward(request, response); // 鏄剧ず鏈�鍚庝竴涓緭鍑虹殑鍐呭
		// dispatcher.include(request, response); //鍚堝苟褰撳墠鎵�鏈夌殑鎵ц椤甸潰鐨勮緭鍑哄唴瀹�
		out.print("椤甸潰杞彂鍚�<br/>"); // 娌℃湁鍙戦�佸埌娴忚鍣�
		System.out.println("杞彂鍚�");
	}

	public void encoding(HttpServletResponse response) throws IOException {
		// 閫氱煡娴忚鍣紝鏈嶅姟鍣ㄥ彂閫佺殑鏁版嵁鏃跺帇缂╃殑锛屽苟涓旀寚瀹氬帇缂╃殑鏍煎紡
		response.setHeader("content-encoding", "gzip");

		// 灏嗗ぇ鏁版嵁鍘嬬缉鍚庯紝鍙戦�佺粰娴忚鍣�
		// 鍑嗗澶ф暟鎹�
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 80000; i++) {
			builder.append("abcd");
		}
		String data = builder.toString();

		// 纭畾鍘嬬缉鐨勪綅缃�
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 鍘嬬缉 gzip
		GZIPOutputStream gzip = new GZIPOutputStream(baos); // 鍘嬬缉鐨勪綅缃�
		// 鍘嬬缉鏁版嵁
		gzip.write(data.getBytes());
		gzip.close();

		// 鑾峰緱鍘嬬缉鍚庣殑瀛楄妭鏁扮粍
		byte[] endData = baos.toByteArray();

		// 灏嗗帇缂╃殑鏁版嵁鍙戦�佺粰娴忚鍣� --
		response.getOutputStream().write(endData);
	}

	public void GzipServlet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// 澹版槑鍑嗗琚帇缂╃殑鏁版嵁
		String str = "Hello浣犲ソHello浣犲ソ鍦ㄥ唴瀛樹腑澹版槑涓�Hello浣犲ソ鍦�" + "鍐呭瓨涓０鏄庝竴涓狧ello浣犲ソ鍦ㄥ唴瀛樹腑澹版槑涓�涓狧ello浣�" + "濂藉湪鍐呭瓨涓０鏄庝竴涓�<br/>瀹瑰櫒澹版槑鍑嗗琚帇缂╄幏鍙栧噯澶囪鍘嬬缉" + "鐨勬暟鎹殑瀛楄妭鐮佺殑鏁版嵁瀹瑰櫒澹版槑鍑嗗琚帇缂╄幏鍙栧噯澶囪鍘嬬缉鐨勬暟" + "鎹殑瀛楄妭鐮佺殑鏁版嵁瀹瑰櫒澹版槑鍑嗗琚帇缂╄幏鍙栧噯澶囪鍘嬬缉鐨勬暟鎹殑"
				+ "瀛楄妭鐮佺殑鏁版嵁涓鍣ㄥ０鏄庡噯澶囪鍘嬬缉鑾峰彇鍑嗗琚帇缂╃殑鏁版嵁鐨勫瓧鑺傜爜鐨�" + "鏁版嵁鍦ㄥ唴瀛樹腑澹版槑涓�涓鍣ㄥ０鏄庡噯澶囪鍘嬬缉鑾峰彇鍑嗗琚帇缂╃殑鏁版嵁" + "鐨勫瓧鑺傜爜鐨勬暟鎹�";
		// 2锛氳幏鍙栧噯澶囪鍘嬬缉鐨勬暟鎹殑瀛楄妭鐮�
		byte[] src = str.getBytes("UTF-8");
		// 3:鍦ㄥ唴瀛樹腑澹版槑涓�涓鍣�
		ByteArrayOutputStream destByte = new ByteArrayOutputStream();
		// 锛旓細澹版槑鍘嬬缉鐨勫伐鍏锋祦锛屽苟璁剧疆鍘嬬缉鐨勭洰鐨勫湴涓篸estByte
		GZIPOutputStream zip = new GZIPOutputStream(destByte);
		// 5:鍐欏叆鏁版嵁
		zip.write(src);
		// 6:鍏抽棴鍘嬬缉宸ュ叿娴�
		zip.close();
		System.err.println("鍘嬬缉涔嬪墠瀛楄妭鐮佸ぇ灏忥細" + src.length);
		// 锛楋細鑾峰彇鍘嬬缉浠ュ悗鏁版嵁
		byte[] dest = destByte.toByteArray();
		System.err.println("鍘嬬缉浠ュ悗鐨勫瓧鑺傜爜澶у皬锛�" + dest.length);

		// 8:蹇呴』瑕佽緭鍑哄帇缂╀互鍚庡瓧鑺傛暟缁�
		resp.setContentType("text/html;charset=UTF-8");
		// 9:蹇呴』瑕佷娇鐢ㄥ瓧鑺傛祦鏉ヨ緭鍑轰俊鎭�
		OutputStream out = resp.getOutputStream();
		// 10:閫氱煡娴忚鍣ㄣ�傝繖鏄帇缂╃殑鏁版嵁锛岃姹傛祻瑙堝櫒瑙ｅ帇
		resp.setHeader("Content-encoding", "gzip");
		// 11:閫氱煡娴忚鍣ㄥ帇缂╂暟鎹殑闀垮害
		resp.setContentLength(dest.length);
		// 10:杈撳嚭
		out.write(dest);
	}

	public void ActiveServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String code = request.getParameter("id");
		try {
			String sql = "delete from active where code=?";
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			int effect = run.update(sql, code);
			if (effect == 0) {
				response.getWriter().print("Active Failed...");
			} else {
				response.getWriter().print("Active Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
 

	public void PageServlet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		// 绗竴姝ワ細瀹氫箟姣忛〉鏄剧ず澶氬皯琛�
		int pageSize = 15;
		// 鎺ユ敹鐢ㄦ埛璇锋眰绗嚑椤�
		String page = req.getParameter("page");
		int currentPage = 0;
		if (page == null || page.trim().equals("")) {
			currentPage = 1;
		} else {
			try {
				currentPage = Integer.parseInt(page);
			} catch (Exception e) {
			}
		}
		req.setAttribute("currentPage", currentPage);
		try {
			// 绗簩姝ワ細鑾峰彇鏁版嵁琛ㄤ腑鏈夊灏戣
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			String sql = "select count(*) from users";
			Object o = run.query(sql, new ScalarHandler());
			int rows = Integer.parseInt(o.toString());
			// 绗笁姝ワ細璁＄畻涓�鍏卞垎澶氬皯椤�
			int pageCount = rows / pageSize + (rows % pageSize == 0 ? 0 : 1);
			if (currentPage < 1) {
				currentPage = 1;
			}
			if (currentPage > pageCount) {
				currentPage = pageCount;
			}
			// 灏嗛〉鏁版斁鍒皉eq
			req.setAttribute("pageCount", pageCount);
			// 绗洓姝ワ細鏌ヨ鎸囧畾鐨勯〉闈㈢殑鏁版嵁
			int start = (currentPage - 1) * pageSize;
			sql = "select * from users limit " + start + "," + pageSize;
			System.err.println(sql);
			List<Map<String, Object>> datas = run.query(sql, new MapListHandler());
			// 灏嗘暟鎹皝瑁呭埌req
			req.setAttribute("datas", datas);
			// 瀵瑰垎椤电殑椤电爜鍐嶅垎椤�
			// 瀹氫箟姣忎釜椤甸潰鏄剧ず澶氬皯涓〉鐮�
			int pageNum = 15;
			int startNo = 0;
			int endNo = 0;
			if (pageNum > pageCount) {// 濡傛灉杩樹笉瓒筹紤锛愰〉锛屽垯娌℃湁蹇呰鍒嗙爜
				startNo = 1;
				endNo = pageCount;
			} else {
				if (currentPage <= pageNum / 2) {
					startNo = 1;
					endNo = pageNum;
				} else {
					startNo = currentPage - (pageNum / 2 - 1);
					endNo = startNo + (pageNum - 1);
				}
				if (endNo >= pageCount) {
					endNo = pageCount;// endNo=11;
					startNo = endNo - (pageNum - 1);
				}
			}
			System.err.println("startno:" + startNo);
			req.setAttribute("startNo", startNo);
			req.setAttribute("endNo", endNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 杞彂鍒�
		req.getRequestDispatcher("/jsps/show.jsp").forward(req, response);
	}

	public void sendMail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 锟秸硷拷锟�?锟斤拷锟斤拷
		final String username = request.getParameter("username");
		final String email = request.getParameter("email");

		// new Thread().run();锟斤拷锟斤拷
		// 一锟斤拷锟竭程凤拷锟绞硷拷
		new Thread() {
			@Override
			public void run() {
				try {
//					MailUtil.sendMail(email, username);
				} catch (Exception e) {

				}
			}
		}.start();

		// 一锟斤拷锟竭筹拷锟斤拷示锟侥硷拷
		request.setAttribute("message", "注锟斤拷晒锟�");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	public void getRequestHeader(HttpServletRequest request) {
		request.getHeader("Accept-Language");// 锟斤拷取指锟斤拷锟斤拷锟斤拷头锟斤拷值锟斤拷
		Enumeration e = request.getHeaders("Accept-Language");// 锟斤拷取指锟斤拷锟斤拷锟斤拷头锟斤拷锟斤拷锟斤拷值锟斤拷
		while (e.hasMoreElements()) {
			String value = (String) e.nextElement();
			System.out.println(value);
		}
		e = request.getHeaderNames();// 锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷头锟斤拷锟斤拷疲锟�
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String v = request.getHeader(name);
			System.out.println(name + ":  " + v);
		}
	}

}
