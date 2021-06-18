/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.test.TreeInterceptorTest.java
 * Class:			TreeInterceptorTest
 * Date:			2012-12-25
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ketayao.common.hibernate4.test.ModuleTree;
import com.ketayao.common.hibernate4.test.ModuleTreeDao;
import com.ketayao.common.page.PaginStyle;
import com.ketayao.common.page.Pagination;
import com.ketayao.common.page.SimplePaginStyle;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-25 上午11:16:47 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:springContext.xml")
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class TreeInterceptorTest {
	@Autowired  
	protected ModuleTreeDao moduleTreeDao;  
	
	/**
	 * 测试添加父节点
	 * 描述
	 */
	@Test
	//@Timed(millis=1000)
	//@Rollback(value=false)
	public void testAddRoot() {
		ModuleTree moduleTree = new ModuleTree();
		
		moduleTree.setName("根模块");
		moduleTree.setPriority(1);
		
		moduleTreeDao.save(moduleTree);
		Assert.assertEquals(new Integer(19), moduleTree.getLft());
		Assert.assertEquals(new Integer(20), moduleTree.getRgt());
	}
	
	/**
	 * 测试添加子节点
	 * 描述
	 */
	@Test
	//@Rollback(value=false)
	public void testAddChild() {
		ModuleTree parentModuleTree = moduleTreeDao.get(4L);
		
		ModuleTree moduleTree = new ModuleTree();
				
		moduleTree.setName("子模块1");
		moduleTree.setPriority(1);
		
		List<ModuleTree> list = new ArrayList<ModuleTree>(1);
		list.add(moduleTree);
		
		parentModuleTree.setChildren(list);
		moduleTree.setParent(parentModuleTree);
		
		moduleTreeDao.save(moduleTree);
		// 父节点, sql更新
		//Assert.assertEquals(new Integer(4), parentModuleTree.getLft());
		//Assert.assertEquals(new Integer(7), parentModuleTree.getRgt());
		
		// 子节点节点
		Assert.assertEquals(new Integer(5), moduleTree.getLft());
		Assert.assertEquals(new Integer(6), moduleTree.getRgt());
	}
	
	/**
	 * 测试删除父节点
	 * 描述
	 */
	@Test
	public void testDeleteRoot() {
		moduleTreeDao.delete(1L);
		
		List<ModuleTree> list = moduleTreeDao.findAll();
		Assert.assertEquals(0, list.size());
	}
	
	/**
	 * 测试删除子节点
	 * 描述
	 */
	@Test
	public void testDeleteChild() {
		moduleTreeDao.delete(9L);
		
		ModuleTree moduleTree = moduleTreeDao.get(7L);
		
		Assert.assertEquals(new Integer(12), moduleTree.getLft());
		Assert.assertEquals(new Integer(15), moduleTree.getRgt());
	}
	
	/**
	 * 测试根据类属性查找数据
	 * 描述
	 */
	@Test
	@Rollback(value=true)
	public void testFindByProperty() {
		List<ModuleTree> resultList = moduleTreeDao.findByName("Beef");
		Assert.assertEquals(1, resultList.size());
	}
	
	/**
	 * 测试Finder使用
	 * 描述
	 */
	@Test
	@Rollback(value=true)
	public void testFinder() {
		Pagination<ModuleTree> pagination = moduleTreeDao.findPage(1, 5, false);
		PaginStyle paginStyle = new SimplePaginStyle();
		String pageHtml = paginStyle.getStyle(pagination);
		System.out.println(pageHtml);
	}
	
	/**
	 * 测试使用改进的前序遍历树模型（The Nested Set Model），获取树模型
	 * 根据ModuleTree查找所有的子节点包括子树。
	 * 描述
	 */
	@Test
	@Rollback(value=true)
	public void testTree() {
		ModuleTree parentModuleTree = moduleTreeDao.get(1L);
		List<ModuleTree> childrenList = moduleTreeDao.findAllTreeChildren(parentModuleTree);
		//parentModuleTree.setChildren(childrenList);
		Assert.assertEquals(9, childrenList.size());
		
		for (ModuleTree moduleTree : parentModuleTree.getChildren()) {
			System.out.println(moduleTree.getName());
			for (ModuleTree m2 : moduleTree.getChildren()) {
				System.out.println("---level2---" + m2.getName());
				for (ModuleTree m3 : m2.getChildren()) {
					System.out.println("------level3------" + m3.getName());
				}
			}
		}
		
		parentModuleTree = moduleTreeDao.get(7L);
		childrenList = moduleTreeDao.findAllTreeChildren(parentModuleTree);
		//parentModuleTree.setChildren(childrenList);
		Assert.assertEquals(3, childrenList.size());
		
		for (ModuleTree moduleTree : parentModuleTree.getChildren()) {
			System.out.println(moduleTree.getName());
			for (ModuleTree m2 : moduleTree.getChildren()) {
				System.out.println("---level2---" + m2.getName());
				for (ModuleTree m3 : m2.getChildren()) {
					System.out.println("------level3------" + m3.getName());
				}
			}
		}
		
	}
	
	/**
	 * 测试使用改进的前序遍历树模型（The Nested Set Model），获取树模型
	 * 获取所有的叶子节点。
	 * 描述
	 */
	@Test
	@Rollback(value=true)
	public void testAllLeaves() {
		List<ModuleTree> list = moduleTreeDao.findAllLeaves(new ModuleTree());
		
		Assert.assertEquals(4, list.size());
	}
	
}
