


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.jun.base.datasource.DataSourceUtil;
import com.jun.base.jdbc2.JdbcUtil;
import com.jun.base.jdbc2.test.MyThreadMap;

public class ThreadDemo {
	@Test
	public  void TestJdbc() throws Exception {
		for(int i=0;i<3000;i++){
			new MyThread().start();
		}
	}
	
	@Test
	public void testThreadLocal() throws Exception{
		Object o1 = MyThreadMap.getObj();//main线程调用getobject,
		Object o2 = MyThreadMap.getObj();//main线程又去调用getObject
		System.err.println("o1:"+o1+",o2:"+o2);//一样
		
		Thread th = new Thread(){
			public void run() {
				Object o4 = MyThreadMap.getObj();
				Object o5 = MyThreadMap.getObj();
				System.err.println("o4 is:"+o4+","+o5);
				aa("Thread-1");
			};
		};
		th.setName("main");
		th.start();
		Thread.sleep(1000);
		aa("main");//main线程
	}
	
	
	public void aa(String name){
		Object o3 = MyThreadMap.getObj();//mmain
		System.err.println(name+" o3 is:"+o3);
	}
	
	
	public static void main(String[] args) {
		//声明Map<Object key,Object value>
		//Object是值，key是当前线程的引用＝Thread.currentThread();
		ThreadLocal<Object> tl = new ThreadLocal<Object>();
		//保存数据
		tl.set("Helllo");
		//获取数据
		Object val = tl.get();
		System.err.println(val);
	}
	

	public static void main1(String[] args) throws Exception {
		for(int i=0;i<3000;i++){
			new MyThread22().start();
		}
	}
}
class MyThread22 extends Thread{
	@Override
	public void run() {
		Connection con = null;
		try{
			con = DataSourceUtil.getConn();
			System.err.println(this.getName()+","+con);
			con.setAutoCommit(false);//设置事务的开始
			String sql = "insert into users values('"+this.getName()+"','Tom','44')";
			Statement st = con.createStatement();
			st.execute(sql);
			con.commit();
			System.err.println(this.getName()+"子线程执行完成。。。。。");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}


class MyThread extends Thread{
	@Override
	public void run() {
		Connection con = null;
		try{
//			con = ConnUtils.getConn();
			con = JdbcUtil.getConnection();
			System.err.println(this.getName()+","+con);
			con.setAutoCommit(false);//设置事务的开始
			String sql = "insert into users values('"+this.getName()+"','Tom','44')";
			Statement st = con.createStatement();
			st.execute(sql);
			con.commit();
			System.err.println(this.getName()+"子线程执行完成。。。。。");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
