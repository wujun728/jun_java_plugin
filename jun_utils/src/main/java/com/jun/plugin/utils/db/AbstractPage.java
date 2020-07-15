/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月11日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Description:		
 *
 * </pre>
 **/
package com.jun.plugin.utils.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年8月11日 下午4:28:39 
 */
public abstract class AbstractPage<T> implements IPage<T>, Serializable{
	/** 描述  */
	private static final long serialVersionUID = 1820419010335143732L;

	private final int pageNumber;
	private final int pageSize;
	private final List<T> content = new ArrayList<T>();
	private final long total;
	
	/**  
	 * @param pageNumber
	 * @param pageSize
	 * @param total  
	 */ 
	public AbstractPage(List<T> content, long total, int pageNumber, int pageSize) {
		if (null == content) {
			throw new IllegalArgumentException("Content must not be null!");
		}

		this.content.addAll(content);
		this.total = total;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#getNumber()  
	 */
	@Override
	public int getNumber() {
		return pageNumber;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#getSize()  
	 */
	@Override
	public int getSize() {
		return pageSize;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#getTotalPages()  
	 */
	@Override
	public int getTotalPages() {
		return getSize() == 0 ? 0 : (int) Math.ceil((double) total / (double) getSize());
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#getNumberOfElements()  
	 */
	@Override
	public int getNumberOfElements() {
		return content.size();
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#getTotalElements()  
	 */
	@Override
	public long getTotalElements() {
		return total;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#hasPreviousPage()  
	 */
	@Override
	public boolean hasPreviousPage() {
		return getNumber() > 0;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#isFirstPage()  
	 */
	@Override
	public boolean isFirstPage() {
		return !hasPreviousPage();
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#hasNextPage()  
	 */
	@Override
	public boolean hasNextPage() {
		return (getNumber() + 1) * getSize() < total;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#isLastPage()  
	 */
	@Override
	public boolean isLastPage() {
		return !hasNextPage();
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#iterator()  
	 */
	@Override
	public Iterator<T> iterator() {
		return content.iterator();
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#getContent()  
	 */
	@Override
	public List<T> getContent() {
		return content;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.page.IPage#hasContent()  
	 */
	@Override
	public boolean hasContent() {
		return !content.isEmpty();
	}
}
