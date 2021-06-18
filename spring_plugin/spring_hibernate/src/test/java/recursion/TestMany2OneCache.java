/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.test.recursion.TestMany2OneCache.java
 * Class:			TestMany2OneCache
 * Date:			2012-9-10
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package recursion;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Module;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-9-10 下午5:47:09 
 */

public class TestMany2OneCache {
	
	public String tree(Module module, int split) {
		if (module.getChildren().isEmpty()) {
			return "";
		}

		StringBuffer fengexian = new StringBuffer("");
		for (int i = 0; i < split; i++) {
			fengexian.append("-");
		}

		StringBuffer buffer = new StringBuffer("");
		for (Module o : module.getChildren()) {
			buffer.append(fengexian + o.getName() + "\n");
			buffer.append(tree(o, split + 2));
		}
		return buffer.toString();
	}
	
	/**
	 * manyToOne会直接使用二级缓存的对象
	 * 描述
	 */
	@Test
	public void testRecursion() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();

		Module module = (Module) s1.get(Module.class, 1L);
		System.out.println(module.getName());
		System.out.println(tree(module, 2));

		s1.getTransaction().commit();
		
		Session s2 = HibernateUtil.getSessionFactory().getCurrentSession();
		s2.beginTransaction();

		Module module2 = (Module) s2.get(Module.class, 2L);
		System.out.println(module2.getName());
		System.out.println(module2.getParent().getName());

		s2.getTransaction().commit();


		// 如果配置文件打开了generate_statistics性能调解，可以得到二级缓存命中次数等数据
		Statistics s = HibernateUtil.getSessionFactory().getStatistics();
		System.out.println(s);
		System.out.println("put:" + s.getSecondLevelCachePutCount());
		System.out.println("hit:" + s.getSecondLevelCacheHitCount());
		System.out.println("miss:" + s.getSecondLevelCacheMissCount());
	}
}
