/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.AbstractHibernateTree.java
 * Class:			AbstractHibernateTree
 * Date:			2012-12-25
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.common.hibernate4;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @param <T>
 * @since   2012-12-25 下午2:54:57 
 */

public abstract class AbstractHibernateTree<T extends Number> implements HibernateTree<T> {

	/**   
	 * @return  
	 * @see com.ketayao.common.hibernate4.HibernateTree#getLftName()  
	 */
	public String getLftName() {
		return HibernateTree.DEF_LEFT_NAME;
	}

	/**   
	 * @return  
	 * @see com.ketayao.common.hibernate4.HibernateTree#getRgtName()  
	 */
	public String getRgtName() {
		return HibernateTree.DEF_RIGHT_NAME;
	}

	/**   
	 * @return  
	 * @see com.ketayao.common.hibernate4.HibernateTree#getParentName()  
	 */
	public String getParentName() {
		return HibernateTree.DEF_PARENT_NAME;
	}

}
