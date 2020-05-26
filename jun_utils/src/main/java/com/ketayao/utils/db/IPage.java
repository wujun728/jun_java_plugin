/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月8日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 *
 * </pre>
 **/
package com.ketayao.utils.db;

import java.util.Iterator;
import java.util.List;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年8月8日 上午9:13:44 
 */
public interface IPage<T> extends Iterable<T> {

	/**
	 * Returns the number of the current page. Is always non-negative and less that {@code Page#getTotalPages()}.
	 * 
	 * @return the number of the current page
	 */
	int getNumber();

	/**
	 * Returns the size of the page.
	 * 
	 * @return the size of the page
	 */
	int getSize();

	/**
	 * Returns the number of total pages.
	 * 
	 * @return the number of toral pages
	 */
	int getTotalPages();

	/**
	 * Returns the number of elements currently on this page.
	 * 
	 * @return the number of elements currently on this page
	 */
	int getNumberOfElements();

	/**
	 * Returns the total amount of elements.
	 * 
	 * @return the total amount of elements
	 */
	long getTotalElements();

	/**
	 * Returns if there is a previous page.
	 * 
	 * @return if there is a previous page
	 */
	boolean hasPreviousPage();

	/**
	 * Returns whether the current page is the first one.
	 * 
	 * @return
	 */
	boolean isFirstPage();

	/**
	 * Returns if there is a next page.
	 * 
	 * @return if there is a next page
	 */
	boolean hasNextPage();

	/**
	 * Returns whether the current page is the last one.
	 * 
	 * @return
	 */
	boolean isLastPage();

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	Iterator<T> iterator();

	/**
	 * Returns the page content as {@link List}.
	 * 
	 * @return
	 */
	List<T> getContent();

	/**
	 * Returns whether the {@link Page} has content at all.
	 * 
	 * @return
	 */
	boolean hasContent();
	
	/**
	 * Returns page's css.            
	 * @return
	 */
	String getCss();
}
