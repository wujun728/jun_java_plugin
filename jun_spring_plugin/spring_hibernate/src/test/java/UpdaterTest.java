/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.test.UpdaterTest.java
 * Class:			UpdaterTest
 * Date:			2013-1-1
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ketayao.common.hibernate4.Updater;
import com.ketayao.common.hibernate4.Updater.UpdateMode;
import com.ketayao.common.hibernate4.test.ModuleTree;
import com.ketayao.common.hibernate4.test.ModuleTreeDao;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2013-1-1 上午10:26:28 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:springContext.xml")
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class UpdaterTest {
	@Autowired  
	protected ModuleTreeDao moduleTreeDao;  

	/**
	 * MAX：最大化更新模式。所有字段都更新（包括null）。exclude例外。
	 * 描述
	 */
	@Test
	public void testMAX() {
		ModuleTree moduleTree = new ModuleTree();
		moduleTree.setId(1L);
		moduleTree.setName(null);
		moduleTree.setPriority(88);
		
		Updater<ModuleTree> updater = new Updater<ModuleTree>(moduleTree, UpdateMode.MAX);
		updater.exclude("priority");
		
		moduleTree = moduleTreeDao.update(updater);
		
		Assert.assertEquals(88, moduleTree.getPriority());
		Assert.assertNull(moduleTree.getName());
	}
	
	/**
	 * MIN：最小化更新模式。所有字段都不更新。include例外。
	 * 描述
	 */
	@Test
	public void testMIN() {
		ModuleTree moduleTree = new ModuleTree();
		moduleTree.setId(1L);
		moduleTree.setName(null);
		moduleTree.setPriority(88);
		
		Updater<ModuleTree> updater = new Updater<ModuleTree>(moduleTree, UpdateMode.MIN);
		updater.include("priority");
		
		moduleTree = moduleTreeDao.update(updater);
		
		Assert.assertEquals(88, moduleTree.getPriority());
		Assert.assertEquals("Food", moduleTree.getName());
	}
	
	/**
	 * MIDDLE：默认模式。除了null外，都更新。exclude和include例外。
	 * 描述
	 */
	@Test
	public void testMIDDLE() {
		ModuleTree moduleTree = new ModuleTree();
		moduleTree.setId(1L);
		moduleTree.setName(null);
		moduleTree.setLft(2);
		moduleTree.setPriority(null);
		
		Updater<ModuleTree> updater = new Updater<ModuleTree>(moduleTree, UpdateMode.MIDDLE);
		updater.include("priority");
		updater.exclude("lft");
		
		moduleTree = moduleTreeDao.update(updater);
		
		//Assert.assertEquals(88, moduleTree.getPriority());
		Assert.assertEquals(Integer.valueOf(1), moduleTree.getLft());
		Assert.assertNull(moduleTree.getPriority());
		Assert.assertEquals("Food", moduleTree.getName());
	}
}
