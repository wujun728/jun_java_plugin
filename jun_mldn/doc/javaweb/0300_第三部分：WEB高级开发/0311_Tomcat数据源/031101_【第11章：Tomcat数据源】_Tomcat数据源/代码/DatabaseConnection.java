package cn.mldn.lxh.dbc ;
import java.sql.* ;
import javax.sql.* ;
import javax.naming.* ;
public class DatabaseConnection {
	private static final String DSNAME = "java:comp/env/jdbc/mldn" ;
	private Connection conn ;
	public DatabaseConnection() throws Exception {
		Context ctx = new InitialContext() ;
		DataSource ds = (DataSource) ctx.lookup(DSNAME) ;
		this.conn = ds.getConnection() ;
	}
	public Connection getConnection(){
		return this.conn ;
	}
	public void close() throws Exception {
		if(this.conn != null){
			try{
				this.conn.close() ;
			}catch(Exception e){
				throw e ;
			}
		}
	}
}