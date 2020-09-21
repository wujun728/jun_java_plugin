/*   
 * Project: OSMP
 * FileName: Page.java
 * version: V1.0
 */
package com.osmp.jdbc.define;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Description:分页实体类
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 上午11:23:41
 */

public class Page<T extends Object> implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 默认的页面可装载的数据条目总和
	 */
	public static final int DEFAULT_DATA_SIZE_IN_ONE_PAGE = 50;

	/**
	 * 默认的分页索引号:1
	 */
	public static final int DEFAULT_PAGE_NUMBER = 1;

	/**
	 * 页面可装载的数据条目
	 */
	private int pageSize = DEFAULT_DATA_SIZE_IN_ONE_PAGE;

	/**
	 * 当前页面的索引
	 */
	private long pageNumber;

	/**
	 * 页面数据
	 */
	private List<T> rows;

	/**
	 * 所有的数据条目总数（所有的分页的数据累加）
	 */
	private long total;

	/**
     * 
     */
	public Page() {
		this(DEFAULT_PAGE_NUMBER, 0, DEFAULT_DATA_SIZE_IN_ONE_PAGE, new ArrayList<T>());
	}

	/**
	 * @param currentPageNo
	 *            当前页面的索引
	 * @param totalCount
	 *            所有的记录数
	 * @param pageSize
	 *            每页的记录数
	 * @param data
	 *            页面数据
	 */
	public Page(long currentPageNo, long totalCount, int pageSize, List<T> data) {
		Assert.isTrue(pageSize > 0, "页面的数据大小不能为0！");
		this.pageSize = pageSize;
		this.pageNumber = currentPageNo;
		this.total = totalCount;
		this.rows = data;
	}

	/**
	 * 所有页面的数据总数
	 */
	public long getTotal() {
		return this.total;
	}

	/**
	 * 页面总数
	 */
	public long getTotalPageCount() {
		if (total % pageSize == 0)
			return total / pageSize;
		else
			return total / pageSize + 1;
	}

	/**
	 * 页面数据数量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 页面数据
	 */
	public List<T> getRows() {
		return this.rows;
	}

	/**
	 * 当前页
	 */
	public long getPageNumber() {
		return pageNumber;
	}

	/**
	 * 当前页是否有下一页
	 */
	public boolean hasNextPage() {
		return this.getPageNumber() < this.getTotalPageCount() - 1;
	}

	/**
	 * 当前页是否有前一页
	 */
	public boolean hasPreviousPage() {
		return this.getPageNumber() > 0;
	}

	/**
	 * 获取页面的第一条数据在所有数据中的索引 默认每页可装载的数据最大为20，第5页的第一条数据在所有数据中的索引为100
	 */
	public static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_DATA_SIZE_IN_ONE_PAGE);
	}

	/**
	 * 获取页面的第一条数据在所有数据中的索引 例如，每页可装载的数据最大数为30，第3页的第一条数据在所有数据中的索引为90
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		Assert.isTrue(pageNo > 0, "页面索引不能小于1!");
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 返回默认的开始日期 本方法用户控制记录的默认过滤开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDefaultBeginDate(String date) {
		if (date == null) {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			String beginDate = String.valueOf(year) + "-01-01";
			return beginDate;
		} else
			return date;
	}

	public static Date getDefaultBeginDateForDate(Date date) {
		if (date == null) {
			Calendar calendar = Calendar.getInstance();
			String date_str = String.valueOf(calendar.get(Calendar.YEAR)) + "-01-01";
			DateFormat dateFormat = DateFormat.getDateInstance();
			try {
				return dateFormat.parse(date_str);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
		return date;
	}
}
