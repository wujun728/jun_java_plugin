/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.test.ModuleTreeDaoImplTest.java
 * Class:			ModuleTreeDaoImplTest
 * Date:			2012-12-25
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/


import org.junit.Test;

import com.ketayao.common.hibernate4.test.ModuleTree;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-25 上午11:31:24 
 */

public class ModuleTreeDaoImplTest {
	@Test
	public void testBasic() {
		ModuleTree moduleTree = new ModuleTree();
		
		moduleTree.setLft(1);
		moduleTree.setRgt(2);
		
		moduleTree.setName("根模块");
		moduleTree.setPriority(1);
	
		
	}
}
