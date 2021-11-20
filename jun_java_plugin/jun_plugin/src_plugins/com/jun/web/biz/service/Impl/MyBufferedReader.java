package com.jun.web.biz.service.Impl;

import java.io.BufferedReader;

//��BufferedReader���бذ�İ�װ/װ��
public class MyBufferedReader {
	private BufferedReader br;
	private int no;
	public MyBufferedReader(BufferedReader br){
		this.br = br;
	}
	//��д������ķ���
	public String readLine() throws Exception{
		String line = null;
		line = br.readLine();
		if(line!=null){
			no++;
			line = no + ":" + line;
		}
		return line;
	}
	//���ʹ�ø�����ķ���
	public void close() throws Exception{
		br.close();
	} 
}
