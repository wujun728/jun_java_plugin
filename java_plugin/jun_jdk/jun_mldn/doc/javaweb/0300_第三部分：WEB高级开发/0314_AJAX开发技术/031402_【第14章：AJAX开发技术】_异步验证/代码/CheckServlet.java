package org.lxh.ajaxdemo ;
import java.sql.* ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class CheckServlet extends HttpServlet{
	public static final String DBDRIVER = "org.gjt.mm.mysql.Driver" ;
 	public static final String DBURL = "jdbc:mysql://localhost:3306/mldn" ;
	public static final String DBUSER = "root" ;
	public static final String DBPASS = "mysqladmin" ;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		this.doPost(request,response) ;
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("GBK") ;
		response.setContentType("text/html") ;
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		PrintWriter out = response.getWriter() ;
		String userid = request.getParameter("userid") ;
		try{
			Class.forName(DBDRIVER) ;
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
			String sql = "SELECT COUNT(userid) FROM user WHERE userid=?" ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString(1,userid) ;
			rs = pstmt.executeQuery() ;
			if(rs.next()){
				if(rs.getInt(1)>0){	// 用户ID已经存在了
					out.print("true") ;
				} else {
					out.print("false") ;
				}
			}
		}catch(Exception e){
			e.printStackTrace() ;
		}finally{
			try{
				conn.close() ;
			}catch(Exception e){}
		}
	}
}