package com.itheima.web.common;

import java.util.List;

//封装与分页有关的所有信息
public class Page {
	private List records;//要显示的分页记录
	private int currentPageNum;//当前页码：可由用户指定*
	private int pageSize=10;//每页显示的记录条数 *
	private int totalPageNum;//总页数*
	private int prePageNum;//上一页的页码*
	private int nextPageNum;//下一页的页码*
	
	private int startIndex;//数据库每页开始记录的索引*
	private int totalRecords;//总记录的条数*
	
	
	//扩展的
	private int startPage;//开始页码
	private int endPage;//结束页码
	
	
	private String url;//查询分页的请求servlet的地址
	
	//currentPageNum：用户要看的页码
	//totalRecords:总记录条数
	public Page(int currentPageNum,int totalRecords){
		this.currentPageNum = currentPageNum;
		this.totalRecords = totalRecords;
		
		//计算总页数
		totalPageNum = totalRecords%pageSize==0?totalRecords/pageSize:(totalRecords/pageSize+1);
		//计算每页开始的索引
		startIndex = (currentPageNum-1)*pageSize;
		
		//计算开始和结束页码:9个页码
		if(totalPageNum>9){
			//超过9页
			startPage = currentPageNum-4;
			endPage = currentPageNum+4;
			
			if(startPage<1){
				startPage = 1;
				endPage = 9;
			}
			if(endPage>totalPageNum){
				endPage = totalPageNum;
				startPage = endPage-8;
			}
			
			
		}else{
			//没有9页
			startPage = 1;
			endPage = totalPageNum;
		}
	}

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getPrePageNum() {
		prePageNum = currentPageNum-1;
		if(prePageNum<1){
			prePageNum = 1;
		}
		return prePageNum;
	}

	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}

	public int getNextPageNum() {
		
		nextPageNum = currentPageNum+1;
		if(nextPageNum>totalPageNum){
			nextPageNum = totalPageNum;
		}
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
