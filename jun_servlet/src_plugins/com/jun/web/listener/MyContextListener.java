package com.jun.web.listener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class MyContextListener implements ServletContextListener {
	//在启动时读取数据库之前保存的数据
	public void contextInitialized(ServletContextEvent sc) {
		//System.err.println("application被创建了:"+sc.getServletContext());
		URL url = MyContextListener.class.getClassLoader().getResource("count.txt");
		String path = url.getFile();
		//System.err.println(path);
		try {
			BufferedReader bf = new BufferedReader(new FileReader(path));
			String line = bf.readLine();
			Integer count = Integer.valueOf(line);
			sc.getServletContext().setAttribute("count",count);
			System.err.println("初始的值是:"+count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	//在销毁这个对象时保存一些数据到数据库或是文件中
	public void contextDestroyed(ServletContextEvent e) {
		System.err.println("销毁了:"+e.getServletContext());
		//保存到文件中去
		URL url = MyContextListener.class.getClassLoader().getResource("count.txt");
		String path = url.getFile();
		System.err.println(path);
		File file = new File(path);
		try {
			PrintWriter out = new PrintWriter(file);
			//获取applicat的数据
			Integer count = (Integer) e.getServletContext().getAttribute("count");
			out.print(count);
			out.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
	}
}
