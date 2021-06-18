/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.HibernateTreeDao.java
 * Class:			HibernateTreeDao
 * Date:			2012-12-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.common.hibernate4;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-26 下午3:16:58 
 */

public abstract class HibernateTreeDao<T extends HibernateTree<?>, ID extends Serializable> extends HibernateBaseDao<T, ID> {

	/**
	 * 得到该节点所有的子节点（包括子父节点下的子节点）
	 * 描述
	 * @param tree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findAllTreeChildren(T tree) {
		String beanName = tree.getClass().getName();
		String lft = tree.getLftName();
		
		StringBuilder hqlBuilder = new StringBuilder("");
		
		hqlBuilder.append("from " + beanName + " bean ");
		hqlBuilder.append("where " + "bean." + lft + " between ? and ? ");
		if (!StringUtils.isBlank(tree.getTreeCondition())) {
			hqlBuilder.append("and (" + tree.getTreeCondition() + ")");
		}
		if (log.isDebugEnabled()) {
			log.debug(hqlBuilder.toString());
		}
		Query query = super.createQuery(hqlBuilder.toString(), tree.getLft(), tree.getRgt());
		return (List<T>)query.list();
	}
	
	/**
	 * 返回所有的叶子节点（Finding all the Leaf Nodes）
	 * SELECT title FROM nested_category WHERE rgt = lft + 1; 
	 * 描述
	 * @param tree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findAllLeaves(T tree) {
		String beanName = tree.getClass().getName();
		String lft = tree.getLftName();
		String rgt = tree.getRgtName();
		
		StringBuilder hqlBuilder = new StringBuilder("");
		
		hqlBuilder.append("from " + beanName + " bean ");
		hqlBuilder.append("where " + "bean." + rgt + "=bean." + lft + "+1");
		if (!StringUtils.isBlank(tree.getTreeCondition())) {
			hqlBuilder.append("and (" + tree.getTreeCondition() + ")");
		}
		if (log.isDebugEnabled()) {
			log.debug(hqlBuilder.toString());
		}
		Query query = super.createQuery(hqlBuilder.toString());
		return (List<T>)query.list();
	}
}
