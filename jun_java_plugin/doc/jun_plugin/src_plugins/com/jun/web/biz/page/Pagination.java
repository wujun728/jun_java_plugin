/**   
 * @Title: Pagination.java 
 * @package com.sysmanage.common.tools.page 
 * @Description: 
 * @author zhangfeng 13940488705@163.com 
 * @date 2011-8-1 下午03:51:33 
 * @version V1.0   
 */

package com.jun.web.biz.page;

import java.util.List;

/**
 * @ClassName: Pagination
 * @Description: 分页信息
 * @author zhangfeng 13940488705@163.com
 * @date 2011-8-1 下午03:51:33
 * 
 */
@SuppressWarnings("serial")
public class Pagination extends SimplePage implements java.io.Serializable,
		Paginable {
	public Pagination() {
	}

	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	@SuppressWarnings("unchecked")
	public Pagination(int pageNo, int pageSize, int totalCount, List list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/** 
	  * @Fields list : 当前页的数据 
	  */ 
	@SuppressWarnings("unchecked")
	private List list;

	@SuppressWarnings("unchecked")
	public List getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}
}
