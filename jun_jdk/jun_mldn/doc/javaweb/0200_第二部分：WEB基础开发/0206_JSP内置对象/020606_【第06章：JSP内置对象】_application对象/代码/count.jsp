<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%!
	BigInteger count = null ;
%>
<%!	// 为了开发简便，将所有的操作定义在方法之中，所有的异常直接加入完整的try...catch处理
	public BigInteger load(File file){
		BigInteger count = null ;	// 接收数据
		try{
			if(file.exists()){
				Scanner scan = new Scanner(new FileInputStream(file)) ;
				if(scan.hasNext()){
					count = new BigInteger(scan.next()) ;
				}
				scan.close() ;
			} else {	// 应该保存一个新的，从0开始
				count = new BigInteger("0") ;
				save(file,count) ;	// 保存一个新的文件
			}
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return count ;
	}
	public void save(File file,BigInteger count){
		try{
			PrintStream ps = null ;
			ps = new PrintStream(new FileOutputStream(file)) ;
			ps.println(count) ;
			ps.close() ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
%>
<%
	String fileName = this.getServletContext().getRealPath("/") + "count.txt";	// 这里面保存所有的计数的结果
	File file = new File(fileName) ;
	if(session.isNew()){
		synchronized(this){
			count = load(file) ;	// 读取
			count = count.add(new BigInteger("1")) ;	// 再原本的基础上增加1。
			save(file,count) ;
		}
	}
%>
<h2>您是第<%=count==null?0:count%>位访客！</h2>
</body>
</html>