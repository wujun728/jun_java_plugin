/**   
  * @Title: Page.java 
  * @package com.sysmanage.common.tools.page 
  * @Description: 
  * @author zhangfeng 13940488705@163.com 
  * @date 2011-8-10 上午09:28:43 
  * @version V1.0   
  */

package com.jun.web.biz.page;

import java.util.List;

//import org.apache.commons.lang.StringUtils;

/** 
 * @ClassName: Page 
 * @Description: 分页参数对象
 * @author zhangfeng 13940488705@163.com
 * @date 2011-8-10 上午09:28:43 
 *  
 */
public class Page<T> {
	
	//-- 分页参数 --//
	protected int pageNo = 1;// 页数
	protected int pageSize = 1;// 显示条数
	protected int leftCount=3;// 左边显示的条数
	protected int rigthCount=3;// 右边显示的条数
	private String forwordName;// 跳转页面
	protected String order = null;//设置排序字段，多个字段用，隔开 格式（name:desc,createDate:asc）
	protected List<T> result;//取得页内的记录列表.

	//-- 返回结果 --//
	protected long totalCount = -1;// 总条数


	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	//-- 访问查询参数函数 --//
	/**
	 * 获得当前页的页号,序号如果大于总条数，则当前页定位到总页数
	 */
	public int getPageNo() {
		if(this.getTotalPages()>-1&&this.pageNo>this.getTotalPages()){		
			this.pageNo=(int) this.getTotalPages();
		}
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
		
	}

	public Page<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}


	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {

		StringBuffer orderBy=new StringBuffer(" order by ");

		orderBy.append(" "
				//+StringUtils.replace(order, ":"," ")
				);	


		this.order = orderBy.toString();
	}

	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return false;
//		return StringUtils.isNotBlank(order);
	}



	//-- 访问查询结果函数 --//

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * @return the leftCount
	 */
	public int getLeftCount() {
		return leftCount;
	}

	/**
	 * @param leftCount the leftCount to set
	 */
	public void setLeftCount(int leftCount) {
		this.leftCount = leftCount;
	}

	/**
	 * @return the rigthCount
	 */
	public int getRigthCount() {
		return rigthCount;
	}

	/**
	 * @param rigthCount the rigthCount to set
	 */
	public void setRigthCount(int rigthCount) {
		this.rigthCount = rigthCount;
	}

	/**
	 * @return the forwordName
	 */
	public String getForwordName() {
		return forwordName;
	}

	/**
	 * @param forwordName the forwordName to set
	 */
	public void setForwordName(String forwordName) {
		this.forwordName = forwordName;
	}
	
	
}

