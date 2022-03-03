package com.jun.common.page;

/**
 * @author Wujun
 * @createTime   2011-11-29 下午05:43:31
 */
public class PageModel {
	private PageData pageData;

	public PageData getPageData() {
		if(null == pageData){
			pageData = new PageData();
		}
		return pageData;
	}

	public void setPageData(PageData pageData) {
		this.pageData = pageData;
	}
	
}
