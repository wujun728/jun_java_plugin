/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.page.SimplePaginStyle.java
 * Class:			SimplePaginStyle
 * Date:			2012-12-5
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.common.page;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-5 下午3:29:25 
 */

public class SimplePaginStyle implements PaginStyle {
	/**
	 * 默认显示条目
	 */
	public static final int DEF_ITEM_COUNT = 10;
	
	/**
	 * 显示条目
	 */
	private int showItemCount = DEF_ITEM_COUNT;

	/**
	 * 分页的url
	 */
	private String url = "";
	
	public SimplePaginStyle() {
		
	}

	/**  
	 * 构造函数
	 * @param url  
	 */ 
	public SimplePaginStyle(String url) {
		super();
		this.url = url;
	}

	/**  
	 * 构造函数
	 * @param showItemCount
	 * @param url  
	 */ 
	public SimplePaginStyle(int showItemCount, String url) {
		super();
		this.showItemCount = showItemCount;
		this.url = url;
	}

	/**
	 * 
	 * @param paginable
	 * @return  
	 * @see com.ketayao.common.page.PaginStyle#getStyle(com.ketayao.common.page.Paginable)
	 */
	public String getStyle(Paginable paginable) {
		// 检查showItemCount
		//if (showItemCount < 0) {
		//	showItemCount = DEF_ITEM_COUNT;
		//}
		
		StringBuilder html = new StringBuilder("");
		
		// 首页
		if (paginable.getPageNo() != 1) {
			html.append("<a href=\"" + url + paginable.getPrePage() + "\">" + "&lt;&lt;"
					+ "</a>");
		}

		// 当前页>=显示条目
		if (paginable.getPageNo() >= showItemCount) {
			// 前一页
			html.append("<a href=\"" + url + 1 + "\">" + "&lt;Prev" + "</a>");

			int middle = showItemCount/2;
			
			// 前半部
			for (int i = middle; i > 0; i--) {
				html.append("<a href=\"" + url + (paginable.getPageNo() - i) + "\">"
						+ (paginable.getPageNo() - i) + "</a>");
			}

			// 当前页
			html.append("<span class=\"current\">" + paginable.getPageNo() + "</span>");
			int remain = showItemCount - middle - 1;

			// 后半部
			for (int i = 1; i <= remain && (paginable.getPageNo() + i) < paginable.getTotalPage(); i++) {
				html.append("<a href=\"" + url + (paginable.getPageNo() + i) + "\">"
						+ (paginable.getPageNo() + i) + "</a>");
			}

			// 下一页
			if (paginable.getPageNo() + remain < paginable.getTotalPage()) {
				html.append("<a href=\"" + url + paginable.getTotalPage() + "\">" + "Next&gt;"
						+ "</a>");
			}
		} else {
			// 平铺显示
			for (int i = 1; i <= paginable.getTotalPage() && i <= showItemCount; i++) {
				if (i == paginable.getPageNo()) {
					html.append("<span class=\"current\">" + paginable.getPageNo()
							+ "</span>");
				} else {
					html.append("<a href=\"" + url + i + "\">" + i + "</a>");
				}
			}
			// 下一页
			if (paginable.getTotalPage() > showItemCount) {
				html.append("<a href=\"" + url + paginable.getTotalPage() + "\">" + "Next&gt;"
						+ "</a>");	
			}
		}
		
		// 最后一页
		if (paginable.getPageNo() != paginable.getTotalPage()) {
			html.append("<a href=\"" + url + paginable.getNextPage() + "\">" + "&gt;&gt;"
					+ "</a>");
		}

		return html.toString();
	}

	/**  
	 * 返回 showItemCount 的值   
	 * @return showItemCount  
	 */
	public int getShowItemCount() {
		return showItemCount;
	}

	/**  
	 * 设置 showItemCount 的值  
	 * @param showItemCount
	 */
	public void setShowItemCount(int showItemCount) {
		this.showItemCount = showItemCount;
	}

	/**  
	 * 返回 url 的值   
	 * @return url  
	 */
	public String getUrl() {
		return url;
	}

	/**  
	 * 设置 url 的值  
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
