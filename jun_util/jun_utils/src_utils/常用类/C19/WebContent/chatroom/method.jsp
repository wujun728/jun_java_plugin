<%@ page contentType="text/html; charset=GB2312" language="java"%>
<%@ page import ="java.util.*" %>

<jsp:useBean id="msgs" class="java.util.HashMap" scope="application" />

<%	
	// 上面的msgs被设置为应用程序内有效
	// 另一种设置应用程序有效的变量的方法是使用application。它的用法跟session一样
	// application.setAttribute("msgs", new HashMap());
	// msgs = (HashMap)application.getAttribute("msgs");
	
	request.setCharacterEncoding("GBK");
	String action = request.getParameter("action");
	
	if(action.equals("login")){
	   // 用户登陆，获得用户名。然后创建两个session变量，保存用户登陆信息和聊天信息
		String username = request.getParameter("username");
		String msg="欢迎 "+username+" 光临本聊天室！<br>";
		session.setAttribute("username",username);
		msgs.put(username, msg);
		response.sendRedirect("main.html");
	}
	if(action.equals("sendMsg")){
		String newMsg = session.getAttribute("username")+ ": "
				+(String)request.getParameter("msg");
	    // 发送消息时，将聊天室所有人的消息都加上新的发言内容
		Iterator it = msgs.keySet().iterator();
		String username = null;
		String msg = null;
		while (it.hasNext()){
			username = (String)it.next();
			msg = (String)msgs.get(username);
			msg = msg+"<br>"+ newMsg;
			msgs.put(username, msg);
		}
		response.sendRedirect("inputMsg.jsp");
	}
	if(action.equals("showMsg")){
	    // 显示某个用户的消息 
		String username = (String)session.getAttribute("username");
	    String msg = (String)msgs.get(username);
		out.println("loadContent.innerHTML=\""+msg+"\";");
	}
%>