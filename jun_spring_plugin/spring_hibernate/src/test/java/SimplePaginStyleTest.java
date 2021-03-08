/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.page.test.SimplePaginStyleTest.java
 * Class:			SimplePaginStyleTest
 * Date:			2012-12-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/


import org.junit.Test;

import com.ketayao.common.page.Paginable;
import com.ketayao.common.page.SimplePage;
import com.ketayao.common.page.SimplePaginStyle;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-7 上午11:18:51 
 */

public class SimplePaginStyleTest {
	
	@Test
	public void test() {
		Paginable paginable = new SimplePage(45, 10, 1000);
		
		SimplePaginStyle paginStyle = new SimplePaginStyle();
		paginStyle.setShowItemCount(-4);
		String html = paginStyle.getStyle(paginable);
		System.out.println(html);
	}
}
