package com.jun.plugin.blog.entity;

/**
 * ��ҳModel��
 * @author Wujun
 *
 */
public class PageBean {

	private int page; // �ڼ�ҳ
	private int pageSize; // ÿҳ��¼��
	private int start;  // ��ʼҳ
	
	
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (page-1)*pageSize;
	}
	
	
}
