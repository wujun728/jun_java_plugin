/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.test.PriorityComparatorTest.java
 * Class:			PriorityComparatorTest
 * Date:			2012-12-31
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ketayao.common.hibernate4.PriorityComparator;
import com.ketayao.common.hibernate4.PriorityInterface;
import com.ketayao.common.hibernate4.test.ModuleTree;
import com.ketayao.common.hibernate4.test.ModuleTreeDao;
import com.ketayao.common.page.Pagination;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-31 下午5:01:26 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:springContext.xml")
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class PriorityComparatorTest {
	@Autowired  
	protected ModuleTreeDao moduleTreeDao;  
	
	/**
	 * 测试看看priority是否正确
	 * 描述
	 */
	@Test
	public void testPriority() {
		Pagination<ModuleTree> pagination = moduleTreeDao.findPage(1, 5, false);
		List<ModuleTree> list = (List<ModuleTree>)pagination.getList();
		Comparator<PriorityInterface> c = new PriorityComparator();
		for (ModuleTree moduleTree : list) {
			Collections.sort(moduleTree.getChildren(), c);
			for (ModuleTree moduleTree2 : moduleTree.getChildren()) {
				System.out.println(moduleTree2.getPriority());
			}
		}
	}
}
