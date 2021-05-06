package com.jun.web.listener;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppAsyncListener implements AsyncListener {
	@Override
	public void onComplete(AsyncEvent asyncEvent) throws IOException {
		System.out.println("AppAsyncListener onComplete");
		// �����������һЩ��Դ���?��
	}

	@Override
	public void onError(AsyncEvent asyncEvent) throws IOException {
		System.out.println("AppAsyncListener onError");
		// ��������׳�������Ϣ
	}

	@Override
	public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
		System.out.println("AppAsyncListener onStartAsync");
		// ���Լ�¼�����־
	}

	@Override
	public void onTimeout(AsyncEvent asyncEvent) throws IOException {
		System.out.println("AppAsyncListener onTimeout");
		ServletResponse response = asyncEvent.getAsyncContext().getResponse();
		PrintWriter out = response.getWriter();
		out.write("TimeOut Error in Processing");
	}
}