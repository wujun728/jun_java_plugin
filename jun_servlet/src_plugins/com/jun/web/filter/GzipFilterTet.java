package com.jun.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GzipFilterTet implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		// 声明包装类对�?
		MyResponse myresp = new MyResponse(resp);
		// 放行,调用oneServlet.doGet
		chain.doFilter(request, myresp);

		// 第二步：从myresp2中读取原生的数据
		byte[] src = myresp.getSrc();

		// 第三步：�?��压缩
		ByteArrayOutputStream destBytes = new ByteArrayOutputStream();
		GZIPOutputStream zip = new GZIPOutputStream(destBytes);
		zip.write(src);
		zip.close();

		// 第三步：输出-使用原生的response
		resp.setContentType("text/html;charset=UTF-8");
		// 获压缩以后数�?
		byte[] dest = destBytes.toByteArray();
		System.err.println("压缩之前�?" + src.length);
		System.err.println("压缩以后�?" + dest.length);
		// 设置�?
		resp.setContentLength(dest.length);
		resp.setHeader("Content-Encoding", "gzip");
		// 输出
		OutputStream out = resp.getOutputStream();
		out.write(dest);

	}

	public void doFilter2(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 声明MyResponse包装�?
		MyResponse resp = new MyResponse((HttpServletResponse) response);
		// 放行时，必须要传递自己包装过的类
		chain.doFilter(request, resp);

		// 目标类调用完成了,返回 以后读取a.txt
		File file = new File("d:/a.txt");
		// 声明�?��缓存的字符串对象
		StringBuilder sb = new StringBuilder();
		// 读取文件
		InputStream in = new FileInputStream(file);
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = in.read(b)) != -1) {
			sb.append(new String(b, 0, len, "UTF-8"));
		}
		in.close();
		// 转成字节�?��压缩
		byte[] src = sb.toString().getBytes("UTF-8");
		// 声明缓存容器
		ByteArrayOutputStream destBytes = new ByteArrayOutputStream();
		// 声明压缩�?
		GZIPOutputStream gzip = new GZIPOutputStream(destBytes);
		// 压缩数据
		gzip.write(src);
		gzip.close();
		// 获取压缩以后数据
		byte[] dest = destBytes.toByteArray();
		System.err.println("压缩之前�?" + src.length);
		System.err.println("压缩以后�?" + dest.length);
		// 输出
		// 必须要使用原�?的response
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html;charset=UTf-8");
		OutputStream out = res.getOutputStream();
		res.setHeader("Content-encoding", "gzip");// 必须
		res.setContentLength(dest.length);
		out.write(dest);
	}

	public void GzipFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		MyResponse myResponse = new MyResponse(response);

		chain.doFilter(request, myResponse);

		// ȡ�û����е�����
		byte[] data = myResponse.getBuffer();
		System.out.println("ѹ��ǰ��" + data.length);

		// ����ѹ��
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout);
		gout.write(data);
		gout.flush();
		gout.close();
		data = bout.toByteArray();
		System.out.println("ѹ����" + data.length);

		// ֪ͨ����������ǵ�һ��ѹ������ݿ�ͳ���
		response.setHeader("content-encoding", "gzip");
		response.setHeader("content-length", data.length + "");

		// ���ֽڷ�ʽ��������������
		response.getOutputStream().write(data);

		// ����ѹ�����޳��ڣ����ƻ�����ѭ��
		// myResponse.getOutputStream().write(data);
	}

	public void GzipFilter2(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		MyResponse myResponse = new MyResponse(response);
		chain.doFilter(request, myResponse.getProxy());
		// ȡ�û����е����
		byte[] buf = myResponse.getBuffer();
		System.out.println("ѹ��ǰ��" + buf.length);
		// ������ѹ������
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout);
		gout.write(buf);
		gout.flush();
		gout.close();
		// ȡ��ѹ��������
		buf = bout.toByteArray();
		System.out.println("ѹ����" + buf.length);
		// �����������ѹ��������
		response.setHeader("content-encoding", "gzip");
		response.setHeader("content-length", buf.length + "");
		response.getOutputStream().write(buf);
	}

	public void destroy() {
	}

}




