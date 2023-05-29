/**   
 * @Title: SimplePage.java 
 * @package com.sysmanage.common.tools.page 
 * @Description: 
 * @author zhangfeng 13940488705@163.com 
 * @date 2011-8-1 下午03:41:19 
 * @version V1.0   
 */

package com.jun.web.biz.page;

/**
 * @ClassName: SimplePage
 * @Description: 简单分页
 * @author zhangfeng 13940488705@163.com
 * @date 2011-8-1 下午03:41:19
 * 
 */
public class SimplePage implements Paginable {

	public static final int DEF_COUNT = 20;

	/** 
	  * @Fields totalCount : 总页数
	  */ 
	protected int totalCount = 0;

	/** 
	  * @Fields pageSize : 一页显示的记录数
	  */ 
	protected int pageSize = 20;

	/** 
	  * @Fields pageNo : 默认页号
	  */ 
	protected int pageNo = 1;
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public SimplePage() {

	}

	public SimplePage(int pageNo, int pageSize, int totalCount) {
		if (totalCount <= 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
		if (pageSize <= 0) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
		if (pageNo <= 0) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
		if ((this.pageNo - 1) * this.pageSize >= totalCount) {
			this.pageNo = totalCount / pageSize;
		}
	}

	/** 
	  * @Title: adjustPage 
	  * @Description: 分页信息调整
	  * @param 
	  * @return void
	  * @throws 
	  */
	public void adjustPage() {
		if (totalCount <= 0) {
			totalCount = 0;
		}
		if (pageSize <= 0) {
			pageSize = DEF_COUNT;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if ((pageNo - 1) * pageSize >= totalCount) {
			pageNo = totalCount / pageSize;
		}
	}

	/*
	 * (non-Javadoc) <p>Title: getNextPage</p> <p>Description: </p> @return
	 * 
	 * @see com.sysmanage.common.tools.page.Paginable#getNextPage()
	 */

	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/*
	 * (non-Javadoc) <p>Title: getPageNo</p> <p>Description: </p> @return
	 * 
	 * @see com.sysmanage.common.tools.page.Paginable#getPageNo()
	 */

	public int getPageNo() {
		return pageNo;
	}

	/*
	 * (non-Javadoc) <p>Title: getPageSize</p> <p>Description: </p> @return
	 * 
	 * @see com.sysmanage.common.tools.page.Paginable#getPageSize()
	 */

	public int getPageSize() {
		return pageSize;
	}

	/*
	 * (non-Javadoc) <p>Title: getPrePage</p> <p>Description: </p> @return
	 * 
	 * @see com.sysmanage.common.tools.page.Paginable#getPrePage()
	 */

	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/*
	 * (non-Javadoc) <p>Title: getTotalCount</p> <p>Description: </p> @return
	 * 
	 * @see com.sysmanage.common.tools.page.Paginable#getTotalCount()
	 */

	public int getTotalCount() {
		return totalCount;
	}

	/*
	 * (non-Javadoc) <p>Title: getTotalPage</p> <p>Description: </p> @return
	 * 
	 * @see com.sysmanage.common.tools.page.Paginable#getTotalPage()
	 */

	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalCount % pageSize != 0 || totalPage == 0) {
			totalPage++;
		}
		return totalPage;
	}

	/*
	 * (non-Javadoc) <p>Title: isFirstPage</p> <p>Description: </p> @return
	 * 
	 * @see com.sysmanage.common.tools.page.Paginable#isFirstPage()
	 */

	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/*
	 * (non-Javadoc) <p>Title: isLastPage</p> <p>Description: </p> @return
	 * 
	 * @see com.sysmanage.common.tools.page.Paginable#isLastPage()
	 */

	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

}
