package com.jun.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MyResponse extends HttpServletResponseWrapper {
	private ByteArrayOutputStream srcByte;
	private PrintWriter out;
	private HttpServletResponse response;
	private ByteArrayOutputStream bout = new ByteArrayOutputStream();

	public MyResponse(HttpServletResponse response) {
		super(response);
	}

	public byte[] getBuffer() {
		return bout.toByteArray();
	}
	public HttpServletResponse getProxy() {
		return (HttpServletResponse) Proxy.newProxyInstance(MyResponse.class.getClassLoader(), response.getClass().getInterfaces(), new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if ("getOutputStream".equals(method.getName())) {
					return new MyServletOutputStream(bout);
				} else {
					return method.invoke(response, args);
				}
			}
		});
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		srcByte = new ByteArrayOutputStream();
		out = new PrintWriter(new OutputStreamWriter(srcByte, "UTF-8"));
		return out;
	}

	public PrintWriter getWriter2() throws IOException {
		System.err.println("有人想获取输出流");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("d:/a.txt"), "UTF-8"));
		return out;
	}

	// 覆盖
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		srcByte = new ByteArrayOutputStream();
		ServletOutputStream out = new ServletOutputStream() {
			// �?��IO�?��都是�?��个字节写出信�?
			@Override
			public void write(int b) throws IOException {
				System.err.println(">>>:" + b);
				srcByte.write(b);// 写到自己的缓存中�?相当于StringBuffer.append(""+b);
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setWriteListener(WriteListener writeListener) {
				// TODO Auto-generated method stub
				
			}
		};
		return out;
	}
	// 提供�?��方法获取原生 的数�?
	public byte[] getSrc() {
		if (out != null) {
			out.close();
		}
		return srcByte.toByteArray();
	}
}