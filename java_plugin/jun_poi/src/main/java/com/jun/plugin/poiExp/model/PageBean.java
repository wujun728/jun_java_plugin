package com.jun.plugin.poiExp.model;

public class PageBean {

	private int page; // �ڼ�ҳ
	private int rows; // ÿҳ�ļ�¼��
	private int start; // ��ʼҳ
	
	public PageBean(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public int getStart() {
		return (page-1)*rows;
	}
	
	
	
}
