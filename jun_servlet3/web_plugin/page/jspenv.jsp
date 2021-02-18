<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,java.io.*,javax.servlet.*,javax.servlet.http.*,java.lang.*" %>
<%
	/********************************************************************
	*	Title: JspEnv v
	*	Description : JSP环境探针
	*	CopyRight:(c)	2005 	www.soho.net.ru
	*	@author:	若影
	*	@version:	1.0
	*	@Data:	2005-1-1 12:00:00
	*********************************************************************/
	
	long timePageStart = System.currentTimeMillis();
%>
<%
class EnvServlet
{
	public long timeUse=0;
	public Hashtable htParam=new Hashtable();
	private Hashtable htShowMsg=new Hashtable();
	public void setHashtable()
	{
		Properties me=System.getProperties();
		Enumeration em=me.propertyNames();
		while(em.hasMoreElements())
		{
			String strKey=(String)em.nextElement();
			String strValue=me.getProperty(strKey);
			htParam.put(strKey,strValue);
		}
	}	
	public void getHashtable(String strQuery)
	{
		Enumeration em=htParam.keys();
		while(em.hasMoreElements())
		{
			String strKey=(String)em.nextElement();
			String strValue=new String();
			if(strKey.indexOf(strQuery,0)>=0)
			{
				strValue=(String)htParam.get(strKey);
				htShowMsg.put(strKey,strValue);
			}
		}
	}
	public String queryHashtable(String strKey)
	{
		strKey=(String)htParam.get(strKey);
		return strKey;
	}
	public long test_int()
	{
		long timeStart = System.currentTimeMillis();
		int i=0;
		while(i<3000000)i++;
		long timeEnd = System.currentTimeMillis();
		long timeUse=timeEnd-timeStart;
		return timeUse;
	}
	public long test_sqrt()
	{
		long timeStart = System.currentTimeMillis();
		int i=0;
		double db=(double)new Random().nextInt(1000);
		while(i<200000){db=Math.sqrt(db);i++;}
		long timeEnd = System.currentTimeMillis();
		long timeUse=timeEnd-timeStart;
		return timeUse;
	}
}
%>
<%
	EnvServlet env=new EnvServlet();
	env.setHashtable();
	String action=new String(" ");
	String act=new String("action");
	if(request.getQueryString()!=null&&request.getQueryString().indexOf(act,0)>=0)action=request.getParameter(act);
%>
<html>
<head>
<title>JSP 探针</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
A       { COLOR: #000000; TEXT-DECORATION: none}
A:hover { COLOR: #f58200}
body,td,span { font-size: 9pt}
.input  { BACKGROUND-COLOR: #ffffff;BORDER:#f58200 1px solid;FONT-SIZE: 9pt}
.backc  { BACKGROUND-COLOR: #f58200;BORDER:#f58200 1px solid;FONT-SIZE: 9pt;color:white}
.PicBar { background-color: #f58200; border: 1px solid #000000; height: 12px;}
.tableBorder {BORDER-RIGHT: #183789 1px solid; BORDER-TOP: #183789 1px solid; BORDER-LEFT: #183789 1px solid; BORDER-BOTTOM: #183789 1px solid; BACKGROUND-COLOR: #ffffff; WIDTH: 760;}
.divcenter {
	position:absolute;
	height:30px;
	z-index:1000;
	left: 101px;
	top: 993px;
}
</STYLE>
<script language="javascript">
function showsubmenu(sid)
{
whichEl = eval("submenu" + sid);
if (whichEl.style.display == "none")
{
eval("submenu" + sid + ".style.display=\"\";");
eval("txt" + sid + ".innerHTML=\"<a href='#' title='关闭此项'><font face='Wingdings' color=#FFFFFF>x</font></a>\";");
}
else
{
eval("submenu" + sid + ".style.display=\"none\";");
eval("txt" + sid + ".innerHTML=\"<a href='#' title='打开此项'><font face='Wingdings' color=#FFFFFF>y</font></a>\";");
}
}
</SCRIPT>
</head>
<body topmargin="0" leftmargin="0">
<CENTER>
  <table width="760" border="0" cellpadding="0" cellspacing="0">
    <tr> 
      <td width="30%"><P style="MARGIN-TOP: 0px; MARGIN-BOTTOM: -8px">&nbsp;<STRONG 
      style="FONT-SIZE: 40pt">JSP探针</STRONG></td>
      <td width="70%" align="center"><font color=red>欢迎访问<a href="http://free3.e-168.cn/cnforum/">JSP智库</a>，下载最新版程序<br>
      <a href="http://free3.e-168.cn/cnforum/jspenv.rar">直接下载最新版本</font></a></td>
    </tr>
  </table>  
  选项：<a href="#ServerInfo">服务器相关参数</a> | <a href="#JAVAInfo">JAVA相关参数</a> | <a href="#Paramter">参数查询</a>  | <a href="#ServerAbility">服务器运算能力</a> 
| <a href="#ISpeedTest">服务器连接速度</a> 
  | <a href="javascript:location.reload()">刷新</a><a name="ServerInfo"></a>
  <table border="0" cellpadding="0" cellspacing="1" class="tableBorder">
  <tr> 
    <td height="22" align="center" bgcolor="#f58200" onclick="showsubmenu(0)"><font color=#FFFFFF><strong>服务器相关参数</strong></font> 
      
<a href="#top" title="返回顶部"><font face='Webdings' color=#FFFFFF>5</font></a> <span id=txt0 name=txt0><a href='#' title='关闭此项'><font face='Wingdings' color=#FFFFFF>x</font></a></span> 
</td>
  </tr>
  <tr> 
    <td style="display" id='submenu0'><table border=0 width=100% cellspacing=1 cellpadding=3 bgcolor="#f58200">
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="130">&nbsp;服务器名</td>
            <td colspan="3" height="22">&nbsp;<%= request.getServerName() %>(<%=request.getRemoteAddr()%>)</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td>&nbsp;服务器操作系统</td>
            <td colspan="3">&nbsp;<%=env.queryHashtable("os.name")%> <%=env.queryHashtable("os.version")%> 
              <%=env.queryHashtable("sun.os.patch.level")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td>&nbsp;服务器操作系统类型</td>
            <td>&nbsp;<%=env.queryHashtable("os.arch")%></td>
            <td>&nbsp;服务器操作系统模式</td>
            <td>&nbsp;<%=env.queryHashtable("sun.arch.data.model")%>位</td>
          </tr>     
          <tr bgcolor="#FFFFFF" height="22"> 
            <td>&nbsp;服务器所在地区</td>
            <td>&nbsp;<%=env.queryHashtable("user.country")%></td>
            <td>&nbsp;服务器语言</td>
            <td>&nbsp;<%=env.queryHashtable("user.language")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td>&nbsp;服务器时区</td>
            <td>&nbsp;<%=env.queryHashtable("user.timezone")%></td>
            <td>&nbsp;服务器时间</td>
            <td>&nbsp;<%=new java.util.Date()%> </td>
          </tr>
		  <tr bgcolor="#FFFFFF" height="22"> 
            <td>&nbsp;服务器解译引擎</td>
            <td width="170">&nbsp;<%= getServletContext().getServerInfo() %></td>
            <td width="130">&nbsp;服务器端口</td>
            <td width="170">&nbsp;<%= request.getServerPort() %></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td height="22">&nbsp;当前用户</td>
            <td height="22" colspan="3">&nbsp;<%=env.queryHashtable("user.name")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td>&nbsp;用户目录</td>
            <td colspan="3">&nbsp;<%=env.queryHashtable("user.dir")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td align=left bgcolor="#FFFFFF">&nbsp;本文件实际路径</td>
            <td height="8" colspan="3" bgcolor="#FFFFFF">&nbsp;<%=request.getRealPath(request.getServletPath())%></td>
          </tr>
        </table>
    </td>
  </tr>
</table>
  <a name="JAVAInfo" id="JAVAInfo"></a><br>
<table border="0" cellpadding="0" cellspacing="1" class="tableBorder">
  <tr> 
      <td height="22" align="center" bgcolor="#f58200" onclick="showsubmenu(1)"><font color=#FFFFFF><strong>JAVA相关参数</strong></font> 
        <a href="#top" title="返回顶部"><font face='Webdings' color=#FFFFFF>5</font></a> 
        <span id=txt1 name=txt1><a href='#' title='关闭此项'><font face='Wingdings' color=#FFFFFF>x</font></a></span> 
      </td>
  </tr>
  <tr> 
    <td style="display" id='submenu1'>
		<table border=0 width=100% cellspacing=1 cellpadding=3 bgcolor="#f58200">
          <tr bgcolor="#fcc79b" height="22"> 
            <td width="30%">&nbsp;名称</td>
            <td width="50%" height="22">&nbsp;英文名称</td>
            <td width="20%" height="22">&nbsp;版本</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;JAVA运行环境名称</td>
            <td width="50%" height="22">&nbsp;<%=env.queryHashtable("java.runtime.name")%></td>
            <td width="20%" height="22">&nbsp;<%=env.queryHashtable("java.runtime.version")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;JAVA运行环境说明书名称</td>
            <td width="50%" height="22">&nbsp;<%=env.queryHashtable("java.specification.name")%></td>
            <td width="20%" height="22">&nbsp;<%=env.queryHashtable("java.specification.version")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;JAVA虚拟机名称</td>
            <td width="50%" height="22">&nbsp;<%=env.queryHashtable("java.vm.name")%></td>
            <td width="20%" height="22">&nbsp;<%=env.queryHashtable("java.vm.version")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;JAVA虚拟机说明书名称</td>
            <td width="50%" height="22">&nbsp;<%=env.queryHashtable("java.vm.specification.name")%></td>
            <td width="20%" height="22">&nbsp;<%=env.queryHashtable("java.vm.specification.version")%></td>
          </tr>
		  <%
		  	float fFreeMemory=(float)Runtime.getRuntime().freeMemory();
			float fTotalMemory=(float)Runtime.getRuntime().totalMemory();
			float fPercent=fFreeMemory/fTotalMemory*100;
		  %>
          <tr bgcolor="#FFFFFF" height="22"> 
		  	<td height="22">&nbsp;JAVA虚拟机剩余内存：</td>
            <td height="22" colspan="2"><img align=absmiddle class=PicBar width='<%=0.85*fPercent%>%'>&nbsp;<%=fFreeMemory/1024/1024%>M 
            </td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
		  	<td height="22">&nbsp;JAVA虚拟机分配内存</td>
            <td height="22" colspan="2"><img align=absmiddle class=PicBar width='85%'>&nbsp;<%=fTotalMemory/1024/1024%>M 
            </td>
          </tr>
        </table>
		<table border=0 width=100% cellspacing=1 cellpadding=3 bgcolor="#f58200">
          <tr bgcolor="#fcc79b" height="22"> 
            <td width="30%">&nbsp;参数名称</td>
            <td width="70%" height="22">&nbsp;参数路径</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;java.class.path </td>
            <td width="70%" height="22">&nbsp;<%=env.queryHashtable("java.class.path").replaceAll(env.queryHashtable("path.separator"),env.queryHashtable("path.separator")+"<br>&nbsp;")%>		
			</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;java.home</td>
            <td width="70%" height="22">&nbsp;<%=env.queryHashtable("java.home")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;java.endorsed.dirs</td>
            <td width="70%" height="22">&nbsp;<%=env.queryHashtable("java.endorsed.dirs")%></td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;java.library.path</td>
            <td width="70%" height="22">&nbsp;<%=env.queryHashtable("java.library.path").replaceAll(env.queryHashtable("path.separator"),env.queryHashtable("path.separator")+"<br>&nbsp;")%>
			</td>
          </tr>
		  <tr bgcolor="#FFFFFF" height="22"> 
            <td width="30%">&nbsp;java.io.tmpdir</td>
            <td width="70%" height="22">&nbsp;<%=env.queryHashtable("java.io.tmpdir")%></td>
          </tr>
        </table>
    </td>
  </tr>
</table>
  <a name="Paramter" id="Paramter"></a><br>
  <form action="?action=query" method="post" name="queryform">
  <table border="0" cellpadding="0" cellspacing="1" class="tableBorder">
    <tr> 
      <td height="22" align="center" bgcolor="#f58200" onclick="showsubmenu(2)"><font color=#FFFFFF><strong>参数查询</strong></font> 
        <a href="#top" title="返回顶部"><font face='Webdings' color=#FFFFFF>5</font></a> 
        <span id=txt2 name=txt2><a href='#' title='关闭此项'><font face='Wingdings' color=#FFFFFF>x</font></a></span> 
      </td>
    </tr>
    <tr> 
      <td style="display" id='submenu2'> 
	  	<table border=0 width=100% cellspacing=1 cellpadding=3 bgcolor="#f58200">
          <tr bgcolor="#fcc79b" height="22"> 
            <td>&nbsp;请查询系统的参数信息(<a href="#" onClick="document.queryform.submit()">枚举所有参数信息</a>)</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td align=center>
			<input type="text" name="query" class="input" size="70">&nbsp;&nbsp;
			<input type="submit" value="提交" class="backc">&nbsp;
			<input type="reset" value="重置" class="backc">			</td>
          </tr>
        </table>
		<table border=0 width=100% cellspacing=1 cellpadding=3 bgcolor="#f58200">		
 <%
 	if(action.equals("query"))
	{
		String query=request.getParameter("query");
		env.getHashtable(query);
		if(env.htShowMsg.size()>0)
			out.println("<tr bgcolor=\"#fcc79b\" height=\"22\">\n<td>&nbsp;参数名称</td>\n<td>&nbsp;参数信息</td>\n</tr>");
		else
			out.println("<tr bgcolor=\"#fcc79b\" height=\"22\">\n<td>&nbsp;<font color=red>出错信息：</font></td>\n<td>&nbsp;<font color=red>没有找到你所查询的内容，请输入所要查询的参数，如果不确认，可以进行抽象查询，输入所包含字母。</font></td>\n</tr>");
		Enumeration em=env.htShowMsg.keys();
		while(em.hasMoreElements())
   		{
   			String strParam=(String)em.nextElement();
			String strParamValue=(String)env.htShowMsg.get(strParam);
			if(strParam.indexOf(".path",0)>=0){strParamValue=strParamValue.replaceAll(env.queryHashtable("path.separator"),env.queryHashtable("path.separator")+"<br>&nbsp;");}
   			out.println ("<tr bgcolor=\"#FFFFFF\" height=\"22\"><td width=\"30%\">&nbsp;"+strParam+"</td><td width=\"70%\">&nbsp;"+strParamValue+"</td></tr>");
   		}
	}
 %>
	  </table>  		
	</td>
    </tr>
  </table>
  </form>
  <a name="ServerAbility" id="ServerAbility"></a> 
  <table border="0" cellpadding="0" cellspacing="1" class="tableBorder">
    <tr> 
      <td height="22" align="center" bgcolor="#f58200" onclick="showsubmenu(3)"><font color=#FFFFFF><strong>服务器运算能力</strong></font> 
        <a href="#top" title="返回顶部"><font face='Webdings' color=#FFFFFF>5</font></a> 
        <span id=txt3 name=txt3><a href='#' title='关闭此项'><font face='Wingdings' color=#FFFFFF>x</font></a></span> 
      </td>
    </tr>
    <tr> 
      <td style="display" id='submenu3'> <table border=0 width=100% cellspacing=1 cellpadding=3 bgcolor="#f58200">
          <tr bgcolor="#fcc79b" height="22"> 
            <td colspan="3">&nbsp;&nbsp;<font face='Webdings'>4</font> 让服务器执行300万次加法(<font color="#000000">整数运算</font>)和20万次开方(浮点运算)，记录其所使用的时间。 
            </td>
          </tr>
          <tr height="22"bgcolor="#FFFFFF" > 
            <td align=center bgcolor="#FFFFFF"><font color="#000000">可 供 参 考 的 
              服 务 器 列 表</font></td>
            <td align="center">整数运算</td>
            <td align="center">浮点运算</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td align=left>&nbsp;公司的电脑&nbsp;(CPU:Celeron 1G&nbsp; 内存:256M)</td>
            <td align="center">60 毫秒</td>
            <td align="center">70 毫秒</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td align=left>&nbsp;家里的电脑&nbsp;(CPU:Duron 1G&nbsp; 内存:384M) </td>
            <td align="center">20 毫秒</td>
            <td align="center">10 毫秒</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td align=left>&nbsp;<a href="http://free3.e-168.cn/cnforum/jspenv.jsp" target="_blank">中国网聚服务器&nbsp; 
              (CPU:Intel Pentium III 1G 内存:768M)</a></td>
            <td align="center">20 毫秒</td>
            <td align="center">3 毫秒</td>
          </tr>
          <tr bgcolor="#FFFFFF" height="22"> 
            <td align=left>&nbsp;<a href="http://ibmclub.hust.edu.cn/myweb/ggyy/jspenv.jsp" target="_blank">IBM俱乐部&nbsp; 
              (CPU:IIntel(R) Celeron(R) CPU 1.70G 内存:256M)</a></td>
            <td align="center">3 毫秒 </td>
            <td align="center">7 毫秒</td>
          </tr>
          <tr bgcolor="#FFFFFF" height=25> 
            <td align=left>&nbsp;<font color=red>您正在使用的这台服务器</font>&nbsp; <INPUT name="button2" type="button" class=backc onclick="javascript:location.reload()" value="重新测试"> 
            </td>
            <td align="center"><font color=red><%=env.test_int()%> 毫秒</font></td>
            <td align="center"><font color=red><%=env.test_sqrt()%> 毫秒</font></td>
          </tr>
        </table></td>
    </tr>
  </table>
  <a name="ISpeedTest" id="ISpeedTest"></a><br>
 <%
 	if(action.equals("SpeedTest"))
	{
 %>
<div id="testspeed" align="center"> 
  <table width="200" border="0" cellspacing="0" cellpadding="0" class="divcenter">
    <tr> 
      <td height="30" align=center><p><font color="#000000"><span id=txt5>网速测试中，请稍候...</span></font></p></td>
    </tr>
  </table>
</div>
<%}%>

  <table border="0" cellpadding="0" cellspacing="1" bgcolor="#f58200" class="tableBorder">
  <tr> 
    <td height="25" align="center" bgcolor="#f58200" onclick="showsubmenu(4)"><font color="#FFFFFF"><strong>服务器连接速度</strong></font> 
      
<a href="#top" title="返回顶部"><font face='Webdings' color=#FFFFFF>5</font></a> <span id=txt4 name=txt4><a href='#' title='关闭此项'><font face='Wingdings' color=#FFFFFF>x</font></a></span> 

    </td>
  </tr>
  <tr> 
    <td bgcolor="#F8F9FC" style="display" id='submenu4'> <table width="100%" border="0" cellspacing=1 cellpadding=3 bgcolor="#f58200">
        <tr bgcolor="#FFFFFF"> 
          <td width="80">接入设备</td>
          <td width="420">&nbsp;连接速度(理想值)</td>
          <td width="100">下载速度(理想值)</td>
        </tr>
<tr bgcolor="#FFFFFF"> 
          <td>56k Modem</td>
          <td><img align=absmiddle class=PicBar width='1%'> 56 Kbps</td><td>&nbsp;7.0 k/s</td>
        </tr>
        <tr bgcolor="#FFFFFF"> 
          <td>64k ISDN</td>
          <td><img align=absmiddle class=PicBar width='1%'> 64 Kbps</td><td>&nbsp;8.0 k/s</td>
        </tr>
        <tr bgcolor="#FFFFFF"> 
          <td>512k ADSL</td>
          <td><img align=absmiddle class=PicBar width='5%'> 512 Kbps</td><td>&nbsp;64.0 k/s</td>
        </tr>
        <tr bgcolor="#FFFFFF"> 
          <td height="19">1.5M Cable</td>
          <td><img align=absmiddle class=PicBar width='15%'> 1500 Kbps</td><td>&nbsp;187.5 k/s</td>
        </tr>
        <tr bgcolor="#FFFFFF"> 
          <td>5M FTTP</td>
          <td><img align=absmiddle class=PicBar width='50%'> 5000 Kbps</td><td>&nbsp;625.0 k/s</td>
        </tr>
        <tr bgcolor="#FFFFFF"> 
          <td>当前连接速度</td>
<%
if(action.equals("SpeedTest"))
{
		out.println("<script language='JavaScript'>var tSpeedStart=new Date();</script>")	;
		out.println("<!--\n");
		for(int i=0;i<1000;i++)
		{out.println("####################################################################################################");}
		out.println("-->\n");
		out.println("<script language='JavaScript'>var tSpeedEnd=new Date();</script>\n");
		out.println("<script language='JavaScript'>");
		out.println("var iSpeedTime=0;iSpeedTime=(tSpeedEnd - tSpeedStart) / 1000;");
		out.println("if(iSpeedTime>0) iKbps=Math.round(Math.round(100 * 8 / iSpeedTime * 10.5) / 10); else iKbps=10000 ;");
		out.println("var iShowPer=Math.round(iKbps / 100);");
		out.println("if(iShowPer<1) iShowPer=1;  else if(iShowPer>82)   iShowPer=82;");
		out.println("</script>\n");
		out.println("<script language='JavaScript'>") ;
		out.println("document.write('<td><img align=absmiddle class=PicBar width=\"' + iShowPer + '%\">' + iKbps + ' Kbps');");
		out.println("</script>\n");
		out.println("</td><td>&nbsp;<a href='?action=SpeedTest' title=测试连接速度><u>");
		out.println("<script language='JavaScript'>");
		out.println("document.write(Math.round(iKbps/8*10)/10+ ' k/s');");
		out.println("</script>\n") ;
		out.println("</u></a></td>");
%>
<script>
txt5.innerHTML="网速测试完毕!"
testspeed.style.visibility="hidden"
</script>
<%
}
else
{out.println("<td></td><td>&nbsp;<a href='?action=SpeedTest' title=测试连接速度><u>开始测试</u></a></td>");}
%>
        </tr>
      </table></td>
  </tr>
</table>
<%
	long timePageEnd = System.currentTimeMillis();
	long timePageUse=timePageEnd-timePageStart;
%>
<table border=0 cellpadding=0 cellspacing=1 class=tableBorder>
  <tr>
    <td height="59" align=center>Copyright ? 2005-2008 , All Rights Reserved. 
<br>
欢迎访问★<a href="http://free3.e-168.cn/cnforum/">JSP智库</a>★，下载最新版程序<br>
	页面执行时间：约<%=timePageUse%>毫秒  JspEnv V1.0</td>
  </tr>
</table>
</CENTER>
</body>
</html>