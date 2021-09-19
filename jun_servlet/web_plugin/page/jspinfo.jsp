<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%-- <jsp:include page="${request.getContextPath()}/layout/meta.jsp"></jsp:include> --%>
<%-- <jsp:include page="${request.getContextPath()}/layout/easyui.jsp"></jsp:include> --%>
<%
	long 开始时间 = System.currentTimeMillis();

	class 探针类 {
		private String 主机IP, 主机名称, 服务器端口, 操作系统, 操作系统类型, 操作系统模式,
				服务器所在地区, 服务器语言, 服务器时区, 服务器时间;
		private String 当前用户, 用户目录, 本文件实际路径;
		private String JAVA运行环境名称, JAVA运行环境版本, JAVA运行环境说明书名称,
				JAVA运行环境说明书版本, JAVA虚拟机名称, JAVA虚拟机版本;
		private String JAVA虚拟机说明书名称, JAVA虚拟机说明书版本, JAVA虚拟机剩余内存;
		private String java_class_path, java_home, java_endorsed_dirs,
				java_library_path, java_io_tmpdir;
		public java.util.Properties 查询数组;
		private java.util.Properties 所有的环境变量, 相关属性;

		public void 探针类() {
			主机IP = "localhost";
			服务器端口 = "80";
		}
		public String 整数运算() {
			long 开始 = System.currentTimeMillis();
			int 变量 = 0;
			while (变量 < 3000000)
				变量++;
			long 结束 = System.currentTimeMillis();
			long 差值 = 结束 - 开始;

			return String.valueOf(差值);
		}
		public String 浮点运算() {
			long 开始 = System.currentTimeMillis();
			int 变量 = 0;
			double 随机数 = (double) new java.util.Random().nextInt(1000);
			while (变量 < 200000) {
				随机数 = Math.sqrt(随机数);
				变量++;
			}
			long 结束 = System.currentTimeMillis();
			long 差值 = 结束 - 开始;
			return String.valueOf(差值);
		}
		private void set主机IP(String 主机IP) {
			this.主机IP = 主机IP;
		}
		public String get主机IP() {
			return 主机IP;
		}
		private void set主机名称(String 主机名称) {
			this.主机名称 = 主机名称;
		}
		public String get主机名称() {
			return 主机名称;
		}
		private void set操作系统(String 操作系统) {
			this.操作系统 = 操作系统;
		}
		public String get操作系统() {
			return 操作系统;
		}
		private void set服务器端口(String 服务器端口) {
			this.服务器端口 = 服务器端口;
		}
		public String get服务器端口() {
			return 服务器端口;
		}
		private void set操作系统类型(String 操作系统类型) {
			this.操作系统类型 = 操作系统类型;
		}
		public String get操作系统类型() {
			return 操作系统类型;
		}
		private void set操作系统模式(String 操作系统模式) {
			this.操作系统模式 = 操作系统模式;
		}
		public String get操作系统模式() {
			return 操作系统模式;
		}
		private void set服务器所在地区(String 服务器所在地区) {
			this.服务器所在地区 = 服务器所在地区;
		}
		public String get服务器所在地区() {
			return 服务器所在地区;
		}
		private void set服务器语言(String 服务器语言) {
			this.服务器语言 = 服务器语言;
		}
		public String get服务器语言() {
			return 服务器语言;
		}
		private void set服务器时区(String 服务器时区) {
			this.服务器时区 = 服务器时区;
		}
		public String get服务器时区() {
			return 服务器时区;
		}
		public String get服务器时间() {
			return String.valueOf(new java.util.Date());
		}
		public String get服务器解译引擎() {
			return getServletContext().getServerInfo();
		}
		private void set当前用户(String 当前用户) {
			this.当前用户 = 当前用户;
		}
		public String get当前用户() {
			return 当前用户;
		}
		private void set用户目录(String 用户目录) {
			this.用户目录 = 用户目录;
		}
		public String get用户目录() {
			return 用户目录;
		}
		private void set本文件实际路径(String 本文件实际路径) {
			this.本文件实际路径 = 本文件实际路径;
		}
		public String get本文件实际路径() {
			return 本文件实际路径;
		}
		private void setJAVA运行环境名称(String JAVA运行环境名称) {
			this.JAVA运行环境名称 = JAVA运行环境名称;
		}
		public String getJAVA运行环境名称() {
			return JAVA运行环境名称;
		}
		private void setJAVA运行环境版本(String JAVA运行环境版本) {
			this.JAVA运行环境版本 = JAVA运行环境版本;
		}
		public String getJAVA运行环境版本() {
			return JAVA运行环境版本;
		}
		private void setJAVA运行环境说明书名称(String JAVA运行环境说明书名称) {
			this.JAVA运行环境说明书名称 = JAVA运行环境说明书名称;
		}
		public String getJAVA运行环境说明书名称() {
			return JAVA运行环境说明书名称;
		}
		private void setJAVA运行环境说明书版本(String JAVA运行环境说明书版本) {
			this.JAVA运行环境说明书版本 = JAVA运行环境说明书版本;
		}
		public String getJAVA运行环境说明书版本() {
			return JAVA运行环境说明书版本;
		}
		private void setJAVA虚拟机名称(String JAVA虚拟机名称) {
			this.JAVA虚拟机名称 = JAVA虚拟机名称;
		}
		public String getJAVA虚拟机名称() {
			return JAVA虚拟机名称;
		}
		public void set相关属性(ServletContext 上下文) {
			java.util.Enumeration 枚举 = 上下文.getAttributeNames();
			相关属性 = new java.util.Properties();
			String 名称 = "", 信息 = "";
			while (枚举.hasMoreElements()) {
				名称 = String.valueOf(枚举.nextElement());
				信息 = String.valueOf(上下文.getAttribute(名称));
				信息 = 信息.replaceAll("jar;", "jar;" + "<br>");
				信息 = 信息.replaceAll("classes/;", "classes/;" + "<br>");
				相关属性.put(名称, 信息);
			}
		}
		public java.util.Properties get相关属性() {
			return 相关属性;
		}
		private void setJAVA虚拟机版本(String JAVA虚拟机版本) {
			this.JAVA虚拟机版本 = JAVA虚拟机版本;
		}
		public String getJAVA虚拟机版本() {
			return JAVA虚拟机版本;
		}
		private void setJAVA虚拟机说明书名称(String JAVA虚拟机说明书名称) {
			this.JAVA虚拟机说明书名称 = JAVA虚拟机说明书名称;
		}
		public String getJAVA虚拟机说明书名称() {
			return JAVA虚拟机说明书名称;
		}
		private void setJAVA虚拟机说明书版本(String JAVA虚拟机说明书版本) {
			this.JAVA虚拟机说明书版本 = JAVA虚拟机说明书版本;
		}
		public String getJAVA虚拟机说明书版本() {
			return JAVA虚拟机说明书版本;
		}
		public float getJAVA虚拟机剩余内存() {
			return (float) Runtime.getRuntime().freeMemory() / 1024 / 1024;
		}
		public float getJAVA虚拟机分配内存() {
			return (float) Runtime.getRuntime().totalMemory() / 1024 / 1024;
		}
		public float getJAVA虚拟机剩余内存百分比() {
			return getJAVA虚拟机剩余内存() / getJAVA虚拟机分配内存() * 100;
		}
		private void setJava_class_path(String java_class_path) {
			this.java_class_path = java_class_path;
		}
		public String getJava_class_path() {
			return java_class_path;
		}
		private void setJava_home(String java_home) {
			this.java_home = java_home;
		}
		public String getJava_home() {
			return java_home;
		}
		private void setJava_endorsed_dirs(String java_endorsed_dirs) {
			this.java_endorsed_dirs = java_endorsed_dirs;
		}
		public String getJava_endorsed_dirs() {
			return java_endorsed_dirs;
		}
		private void setJava_library_path(String java_library_path) {
			this.java_library_path = java_library_path;
		}
		public String getJava_library_path() {
			return java_library_path;
		}
		private void setJava_io_tmpdir(String java_io_tmpdir) {
			this.java_io_tmpdir = java_io_tmpdir;
		}
		public String getJava_io_tmpdir() {
			return java_io_tmpdir;
		}
		public void set所有的环境变量() {
			所有的环境变量 = System.getProperties();
		}
		public void 参数查询(String 参数) {
			查询数组 = new java.util.Properties();
			java.util.Enumeration 枚举 = 所有的环境变量.propertyNames();
			String 变量 = "", 信息 = "";

			while (枚举.hasMoreElements()) {
				变量 = String.valueOf(枚举.nextElement());
				if (变量.indexOf(参数, 0) >= 0)
					查询数组.put(变量, String.valueOf(所有的环境变量.get(变量)));
			}
		}

		public void 设置(javax.servlet.http.HttpServletRequest 会话) {
			try {
				探针类();
				java.util.Properties 属性 = System.getProperties();

				set主机名称(会话.getServerName());
				set主机IP(会话.getRemoteAddr());
				set服务器端口(String.valueOf(会话.getServerPort()));
				set操作系统(String.valueOf(属性.get("os.name")) + " "
						+ String.valueOf(属性.get("os.version")));
				set操作系统类型(String.valueOf(属性.get("os.arch")));
				set操作系统模式(String.valueOf(属性.get("sun.arch.data.model"))
						+ "位");
				set服务器所在地区(String.valueOf(属性.get("user.country")));
				set服务器语言(String.valueOf(属性.get("user.language")));
				set服务器时区(String.valueOf(属性.get("user.timezone")));
				set当前用户(String.valueOf(属性.get("user.name")));
				set用户目录(String.valueOf(属性.get("user.dir")));
				set本文件实际路径(会话.getRealPath(会话.getServletPath()));
				setJAVA运行环境名称(String.valueOf(属性
						.get("java.runtime.name")));
				setJAVA运行环境版本(String.valueOf(属性
						.get("java.runtime.version")));
				setJAVA运行环境说明书名称(String.valueOf(属性
						.get("java.specification.name")));
				setJAVA运行环境说明书版本(String.valueOf(属性
						.get("java.specification.version")));
				setJAVA虚拟机名称(String.valueOf(属性.get("java.vm.name")));
				setJAVA虚拟机版本(String.valueOf(属性.get("java.vm.version")));
				setJAVA虚拟机说明书名称(String.valueOf(属性
						.get("java.vm.specification.name")));
				setJAVA虚拟机说明书版本(String.valueOf(属性
						.get("java.vm.specification.version")));
				setJava_class_path(String.valueOf(
						属性.get("java.class.path")).replaceAll(
						String.valueOf(属性.get("path.separator")),
						String.valueOf(属性.get("path.separator"))
								+ "<br>"));
				setJava_home(String.valueOf(属性.get("java.home")));
				setJava_endorsed_dirs(String.valueOf(属性
						.get("java.endorsed.dirs")));
				setJava_library_path(String.valueOf(
						属性.get("java.library.path")).replaceAll(
						String.valueOf(属性.get("path.separator")),
						String.valueOf(属性.get("path.separator"))
								+ "<br>"));
				setJava_io_tmpdir(String.valueOf(属性
						.get("java.io.tmpdir")));
			} catch (Exception 错误) {
				System.err.println("探针出现错误->" + 错误.getMessage());
			}
		}
	}

	探针类 探针Class = new 探针类();
	探针Class.设置(request);
%>
<style>
A {
	COLOR: #000000;
	TEXT-DECORATION: none
}

A:hover {
	COLOR: #f58200
}

body,td,span {
	font-size: 9pt
}

.input {
	BACKGROUND-COLOR: #E3F1D1;
	BORDER: #A9D46D 1px solid;
	FONT-SIZE: 9pt
}

.backc {
	BACKGROUND-COLOR: #E3F1D1;
	BORDER: #A9D46D 1px solid;
	FONT-SIZE: 9pt;
	color: #000000;
}

.PicBar {
	background-color: #A9D46D;
	border: 1px solid #ffffff;
	height: 12px;
}

.tableBorder {
	BORDER-RIGHT: #84BE3C 1px solid;
	BORDER-TOP: #84BE3C 1px solid;
	BORDER-LEFT: #84BE3C 1px solid;
	BORDER-BOTTOM: #84BE3C 1px solid;
	BACKGROUND-COLOR: #ffffff;
	WIDTH: 760;
}

.divcenter {
	position: absolute;
	height: 30px;
	z-index: 1000;
	left: 101px;
	top: 993px;
}

.InnerHead {
	background-color: #E3F1D1;
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #FFFFFF;
	border-left-color: #FFFFFF;
	text-align: left;
	padding-left: 10px;
	padding-top: 3px;
}

.InnerTable {
	background-color: #A9D46D;
}

.InnerMain {
	background-color: #FFFFFF;
	padding-left: 10px;
	padding-top: 3px;
}

.InnerQuery {
	background-color: #FFFFFF;
	padding-left: 10px;
	padding-top: 3px;
	padding-bottom: 3px;
}
</STYLE>
<script language="javascript">
	function showsubmenu(sid) {
		whichEl = eval("submenu" + sid);
		if (whichEl.style.display == "none") {
			eval("submenu" + sid + ".style.display=\"\";");
			eval("txt" + sid + ".innerHTML=\"<a href='#' onclick='showsubmenu(" + sid + ")' title='关闭此项'><font color=#FFFFFF>↓</font></a>\";");
		} else {
			eval("submenu" + sid + ".style.display=\"none\";");
			eval("txt" + sid + ".innerHTML=\"<a href='#' onclick='showsubmenu(" + sid + ")' title='打开此项'><font color=#FFFFFF>↑</font></a>\";");
		}
	}
</SCRIPT>
</head>
<body topmargin="0" background="<%=request.getContextPath()%>/admin/tz/images/bgcolor.gif">

	<table width="760" height="20" border="0" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td align="center">选项：<a href="#ServerInfo">服务器相关参数</a> | <a href="#JAVAInfo">JAVA相关参数</a> | <a href="#Paramter">参数查询</a> | <a href="#ServerAbility">服务器运算能力</a> | <a href="#ISpeedTest">服务器连接速度</a> | <a href="javascript:location.reload()">刷新</a> <a name="ServerInfo"></a></td>
		</tr>
	</table>
	<hr size="1px" width=760 />
	<br />
	<table width="760" border="0" align="center" cellpadding="0" cellspacing="1" class="tableBorder">
		<tr>
			<td background="<%=request.getContextPath()%>/admin/tz/images/h.gif" height="20">
				<table border=0 width=100% cellspacing=1 cellpadding=3>
					<tr>
						<td height="20" width="70%"><font color=#FFFFFF><strong>服务器相关参数</strong> </font></td>
						<td width="30%" align="right"><a href="#top" title="返回顶部"><font color=#FFFFFF>TOP</font> </a> <span id=txt0 name=txt0><a href='#' onclick="showsubmenu(0)" title='关闭此项'><font color=#FFFFFF>↓</font> </a> </span></td>
					</tr>
				</table></td>
		</tr>
		<tr id=submenu0>
			<td>
				<table border=0 width=100% cellspacing=1 cellpadding=0 class="InnerTable">
					<tr>
						<td width="141" height="20" class="InnerHead">服务器名</td>
						<td colspan="3" class="InnerMain"><%=探针Class.get主机名称()%>(<%=探针Class.get主机IP()%>)</td>
					</tr>
					<tr>
						<td width="141" height="20" class="InnerHead">服务器操作系统</td>
						<td colspan="3" class="InnerMain"><%=探针Class.get操作系统()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerHead">服务器操作系统类型</td>
						<td class="InnerMain"><%=探针Class.get操作系统类型()%></td>
						<td class="InnerHead">服务器操作系统模式</td>
						<td class="InnerMain"><%=探针Class.get操作系统模式()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerHead">服务器所在地区</td>
						<td class="InnerMain"><%=探针Class.get服务器所在地区()%></td>
						<td class="InnerHead">服务器语言</td>
						<td class="InnerMain"><%=探针Class.get服务器语言()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerHead">服务器时区</td>
						<td class="InnerMain"><%=探针Class.get服务器时区()%></td>
						<td class="InnerHead">服务器时间</td>
						<td class="InnerMain"><%=探针Class.get服务器时间()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerHead">服务器解译引擎</td>
						<td class="InnerMain"><%=探针Class.get服务器解译引擎()%></td>
						<td class="InnerHead">服务器端口</td>
						<td class="InnerMain"><%=探针Class.get服务器端口()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerHead">当前用户</td>
						<td colspan="3" class="InnerMain"><%=探针Class.get当前用户()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerHead">用户目录</td>
						<td colspan="3" class="InnerMain"><%=探针Class.get用户目录()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerHead">本文件实际路径</td>
						<td colspan="3" class="InnerMain"><%=探针Class.get本文件实际路径()%></td>
					</tr>
				</table></td>
		</tr>
	</table>
	<a name="JAVAInfo" id="JAVAInfo"></a>
	<br>
	<table width="760" border="0" align="center" cellpadding="0" cellspacing="1" class="tableBorder">
		<tr>
			<td background="<%=request.getContextPath()%>/admin/tz/images/h.gif">
				<table border=0 width=100% cellspacing=1 cellpadding=3>
					<tr>
						<td height="20" width="70%"><font color=#FFFFFF><strong>JAVA相关参数</strong> </font></td>
						<td width="30%" align="right"><a href="#top" title="返回顶部"><font color=#FFFFFF>TOP</font> </a> <span id=txt1 name=txt1><a href='#' onclick="showsubmenu(1)" title='关闭此项'><font color=#FFFFFF>↓</font> </a> </span></td>
					</tr>
				</table></td>
		</tr>
		<tr id=submenu1>
			<td>
				<table border=0 style="width:760px;" cellspacing=1 cellpadding=0 class="InnerTable">
					<tr>
						<td height="20" width="30%" class="InnerHead">名称</td>
						<td class="InnerHead" width="50%">英文名称</td>
						<td class="InnerHead" width="20%">版本</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">JAVA运行环境名称</td>
						<td class="InnerMain"><%=探针Class.getJAVA运行环境名称()%></td>
						<td class="InnerMain"><%=探针Class.getJAVA运行环境版本()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">JAVA运行环境说明书名称</td>
						<td class="InnerMain"><%=探针Class.getJAVA运行环境说明书名称()%></td>
						<td class="InnerMain"><%=探针Class.getJAVA运行环境说明书版本()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">JAVA虚拟机名称</td>
						<td class="InnerMain"><%=探针Class.getJAVA虚拟机名称()%></td>
						<td class="InnerMain"><%=探针Class.getJAVA虚拟机版本()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">JAVA虚拟机说明书名称</td>
						<td class="InnerMain"><%=探针Class.getJAVA虚拟机说明书名称()%></td>
						<td class="InnerMain"><%=探针Class.getJAVA虚拟机说明书版本()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">JAVA虚拟机剩余内存</td>
						<td colspan="2" class="InnerMain"><img align=absmiddle class=PicBar width='<%=探针Class.getJAVA虚拟机剩余内存百分比()%>%'> <%=探针Class.getJAVA虚拟机剩余内存()%>M</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">JAVA虚拟机分配内存</td>
						<td colspan="2" class="InnerMain"><img align=absmiddle class=PicBar width='85%'> <%=探针Class.getJAVA虚拟机分配内存()%>M</td>
					</tr>
					<tr>
						<td height="20" width="30%" class="InnerHead">参数名称</td>
						<td colspan="2" class="InnerHead">参数路径</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">java.class.path</td>
						<td colspan="2" class="InnerMain"><%=探针Class.getJava_class_path()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">java.home</td>
						<td colspan="2" class="InnerMain"><%=探针Class.getJava_home()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">java.endorsed.dirs</td>
						<td colspan="2" class="InnerMain"><%=探针Class.getJava_endorsed_dirs()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">java.library.path</td>
						<td colspan="2" class="InnerMain"><%=探针Class.getJava_library_path()%></td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">java.io.tmpdir</td>
						<td colspan="2" class="InnerMain"><%=探针Class.getJava_io_tmpdir()%></td>
					</tr>
					<tr>
						<td height="20" width="30%" class="InnerHead">Context attributes (相关属性)</td>
						<td colspan="2" class="InnerHead">参数路径</td>
					</tr>
					<%
						探针Class.set相关属性(getServletContext());
						java.util.Properties 相关属性 = 探针Class.get相关属性();
						java.util.Enumeration 枚举名称 = 相关属性.propertyNames();
						String 相关属性名称 = "";
						while (枚举名称.hasMoreElements()) {
							相关属性名称 = String.valueOf(枚举名称.nextElement());
					%>
					<tr>
						<td height="20" class="InnerMain"><%=相关属性名称%></td>
						<td colspan="2" class="InnerMain"><%=相关属性.get(相关属性名称)%></td>
					</tr>
					<%
						}
					%>
				</table></td>
		</tr>
	</table>
	<br />
	<a name="Paramter" id="Paramter"></a>
	<form action="?action=query" method="post" name="queryform">
		<table width="760" border="0" align="center" cellpadding="0" cellspacing="1" class="tableBorder">
			<tr>
				<td background="<%=request.getContextPath()%>/admin/tz/images/h.gif">
					<table border=0 width=100% cellspacing=1 cellpadding=3>
						<tr>
							<td height="20" width="70%"><font color=#FFFFFF><strong>参数查询</strong> </font>
							</td>
							<td width="30%" align="right"><a href="#top" title="返回顶部"><font color=#FFFFFF>TOP</font> </a> <span id=txt2 name=txt2><a href='#' onclick="showsubmenu(2)" title='关闭此项'><font color=#FFFFFF>↓</font> </a> </span></td>
						</tr>
					</table></td>
			</tr>
			<tr id=submenu2>
				<td>
					<table border=0 width=100% cellspacing=1 cellpadding=0 class="InnerTable">
						<tr>
							<td height="20" colspan="2" class="InnerHead">请查询系统的参数信息(<a href="javascript:queryform.query.value='';document.queryform.submit();">枚举所有参数信息</a>)</td>
						</tr>
						<tr>
							<td height="20" colspan="2" align="center" class="InnerQuery"><input type="text" name="query" class="input" size="70">&nbsp;&nbsp; <input type="submit" value="提 交" class="backc">&nbsp; <input type="reset" value="重 置" class="backc"></td>
						</tr>
						<%
							if (request.getParameter("action") != null && request.getParameter("action").equals("query")) {
								if (request.getParameter("query") != null) {
									探针Class.set所有的环境变量();
									String 参数查询 = request.getParameter("query");
									探针Class.参数查询(参数查询);
									if (探针Class.查询数组.size() > 0) {
						%>
						<tr>
							<td width="30%" height="20" align="center" class="InnerHead">参数名称</td>
							<td width="70%" align="center" class="InnerHead">参数信息</td>
						</tr>
						<%
							} else {
						%>
						<tr>
							<td width="30%" height="20" align="center" class="InnerHead"><font color=red>出错信息：</font>
							</td>
							<td width="70%" align="center" class="InnerHead"><font color=red>没有找到你所查询的内容，请输入所要查询的参数，如果不确认，可以进行抽象查询，输入所包含字母。</font>
							</td>
						</tr>
						<%
							}
									java.util.Enumeration 枚举 = 探针Class.查询数组.propertyNames();
									String 名称 = "", 信息 = "";
									while (枚举.hasMoreElements()) {
										名称 = String.valueOf(枚举.nextElement());
										信息 = String.valueOf(探针Class.查询数组.get(名称));
										if (名称.indexOf(".path", 0) >= 0)
											信息 = 信息.replaceAll(String.valueOf(探针Class.查询数组.get("path.separator")), String.valueOf(探针Class.查询数组.get("path.separator")) + "<br>");
						%>
						<tr>
							<td width="30%" height="20" align="left" class="InnerQuery"><%=名称%></td>
							<td width="70%" align="left" class="InnerQuery"><%=信息%></td>
						</tr>
						<%
							}
								}
						%>
						<%
							}
						%>
					</table></td>
			</tr>
		</table>
	</form>
	<a name="ServerAbility" id="ServerAbility"></a>
	<table border="0" align="center" cellpadding="0" cellspacing="1" class="tableBorder">
		<tr>
			<td background="<%=request.getContextPath()%>/admin/tz/images/h.gif">
				<table border=0 width=100% cellspacing=1 cellpadding=3>
					<tr>
						<td height="20" width="85%"><font color=#FFFFFF><strong>服务器运算能力</strong> </font> →让服务器执行300万次加法(<font color="#000000">整数运算</font>)和20万次开方(浮点运算)，记录其所使用的时间。</td>
						<td width="15%" align="right"><a href="#top" title="返回顶部"><font color=#FFFFFF>TOP</font> </a> <span id=txt3 name=txt3><a href='#' onclick="showsubmenu(3)" title='关闭此项'><font color=#FFFFFF>↓</font> </a> </span></td>
					</tr>
				</table></td>
		</tr>
		<tr id=submenu3>
			<td>
				<table border=0 width=100% cellspacing=1 cellpadding=0 class="InnerTable">
					<tr>
						<td height="20" class="InnerHead">可供参考的服务器列表</td>
						<td class="InnerHead">整数运算</td>
						<td class="InnerHead">浮点运算</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">公司的电脑&nbsp;(CPU:Celeron 1G&nbsp; 内存:256M)</td>
						<td height="20" class="InnerMain">60 毫秒</td>
						<td height="20" class="InnerMain">70 毫秒</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">家里的电脑&nbsp;(CPU:Duron 1G&nbsp; 内存:384M)</td>
						<td height="20" class="InnerMain">20 毫秒</td>
						<td height="20" class="InnerMain">10 毫秒</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">中国网聚服务器&nbsp;(CPU:Intel Pentium III 1G 内存:768M)</td>
						<td height="20" class="InnerMain">20 毫秒</td>
						<td height="20" class="InnerMain">3 毫秒</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">IBM俱乐部&nbsp;(CPU:IIntel(R) Celeron(R) CPU 1.70G 内存:256M)</td>
						<td height="20" class="InnerMain">3 毫秒</td>
						<td height="20" class="InnerMain">7 毫秒</td>
					</tr>
					<tr>
						<td height="20" class="InnerQuery" align="left"><font color=red>您正在使用的这台服务器</font>&nbsp; <INPUT name="button" type="button" class=backc onclick="javascript:location.reload()" value="重新测试">
						</td>
						<td height="20" class="InnerMain"><%=探针Class.整数运算()%> 毫秒</td>
						<td height="20" class="InnerMain"><%=探针Class.浮点运算()%> 毫秒</td>
					</tr>
				</table></td>
		</tr>
	</table>
	<a name="ISpeedTest" id="ISpeedTest"></a>
	<br>
	<table border="0" align="center" cellpadding="0" cellspacing="1" class="tableBorder">
		<tr>
			<td background="<%=request.getContextPath()%>/admin/tz/images/h.gif">
				<table border=0 width=100% cellspacing=1 cellpadding=3>
					<tr>
						<td height="20" width="70%"><font color=#FFFFFF><strong>服务器连接速度</strong> </font>
						</td>
						<td width="30%" align="right"><a href="#top" title="返回顶部"><font color=#FFFFFF>TOP</font> </a> <span id=txt4 name=txt4><a href='#' onclick="showsubmenu(4)" title='关闭此项'><font color=#FFFFFF>↓</font> </a> </span></td>
					</tr>
				</table></td>
		</tr>
		<tr id=submenu4>
			<td>
				<table border=0 width=100% cellspacing=1 cellpadding=0 class="InnerTable">
					<tr>
						<td height="20" width="80" class="InnerHead">接入设备</td>
						<td width="420" class="InnerHead">连接速度(理想值)</td>
						<td width="100" class="InnerHead">下载速度(理想值)</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">56k Modem</td>
						<td class="InnerMain"><img align=absmiddle class=PicBar width='1%'> 56 Kbps</td>
						<td class="InnerMain">7.0 k/s</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">64k ISDN</td>
						<td class="InnerMain"><img align=absmiddle class=PicBar width='1%'> 64 Kbps</td>
						<td class="InnerMain">8.0 k/s</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">512k ADSL</td>
						<td class="InnerMain"><img align=absmiddle class=PicBar width='5%'> 512 Kbps</td>
						<td class="InnerMain">64.0 k/s</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">1.5M Cable</td>
						<td class="InnerMain"><img align=absmiddle class=PicBar width='15%'> 1500 Kbps</td>
						<td class="InnerMain">187.5 k/s</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">5M FTTP</td>
						<td class="InnerMain"><img align=absmiddle class=PicBar width='50%'> 5000 Kbps</td>
						<td class="InnerMain">625.0 k/s</td>
					</tr>
					<tr>
						<td height="20" class="InnerMain">当前连接速度</td>
						<%
							if (request.getParameter("action") != null && request.getParameter("action").equals("SpeedTest")) {
								out.println("<script language='JavaScript'>var tSpeedStart=new Date();</script>");
								out.println("<!--\n");
								for (int i = 0; i < 1000; i++) {
									out.println("####################################################################################################");
								}
								out.println("-->\n");
								out.println("<script language='JavaScript'>var tSpeedEnd=new Date();</script>\n");
								out.println("<script language='JavaScript'>");
								out.println("var iSpeedTime = 0; iSpeedTime = ( tSpeedEnd - tSpeedStart ) / 1000;");
								out.println("if( iSpeedTime > 0 ) iKbps = Math.round( Math.round( 100 * 8 / iSpeedTime * 10.5 ) / 10 ); else iKbps = 10000 ;");
								out.println("var iShowPer = Math.round( iKbps / 100 );");
								out.println("if( iShowPer < 1 ) iShowPer = 1;  else if( iShowPer > 82 )   iShowPer = 82;");
								out.println("</script>\n");
								out.println("<script language='JavaScript'>");
								out.println("document.write('<td class=InnerMain><img align=absmiddle class=PicBar width=\"' + iShowPer + '%\">' + iKbps + ' Kbps');");
								out.println("</script>\n");
								out.println("</td><td class=InnerMain>&nbsp;<a href='?action=SpeedTest' title=测试连接速度><u>");
								out.println("<script language='JavaScript'>");
								out.println("document.write( Math.round( iKbps / 8 * 10 ) / 10 + ' k/s' );");
								out.println("</script>\n");
								out.println("</u></a></td>");
						%>
						<%
							} else {
						%>
						<td class="InnerMain">&nbsp;</td>
						<td class="InnerMain"><a href='?action=SpeedTest' title=测试连接速度><u>开始测试</u> </a>
						</td>
						<%
							}
						%>
					</tr>
				</table></td>
		</tr>
	</table>
	<%
		long 结束时间 = System.currentTimeMillis();
	%>
	<br>
	<hr size="1px" width=760 />
	<center>
		页面执行时间：约<%=结束时间 - 开始时间%>毫秒
	</center>
	<br>
</body>
</html>