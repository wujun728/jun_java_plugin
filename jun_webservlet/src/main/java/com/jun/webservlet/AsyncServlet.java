package com.jun.webservlet;
import java.io.IOException;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported=true,name="asyncServlet", urlPatterns="/async")
public class AsyncServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3903580630389463919L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write("hello, async test");
		resp.getWriter().println("start："+new Date()+".<br/>"); 
		resp.getWriter().flush();
		final AsyncContext async = req.startAsync(req,resp);
		async.setTimeout(3000);
		async.start(new Runnable() {
			@Override
			public void run() {
				ServletRequest request = async.getRequest();
				try {
					Thread.sleep(2000);
					async.getResponse().getWriter().write("aync thread processing");
					async.getResponse().getWriter().flush();
					async.getResponse().getWriter().println("async end："+new Date()+".<br/>"); 
					async.getResponse().getWriter().flush();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		async.addListener(new AsyncListener() {
			
			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartAsync(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
		});
		resp.getWriter().println("end："+new Date()+".<br/>"); 
		resp.getWriter().flush();
		 
	}

}