package com.jd.ssh.sshdemo;

import java.beans.Introspector;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.jd.ssh.sshdemo.annotation.Indexable;
import com.jd.ssh.sshdemo.annotation.Searched;
import com.jd.ssh.sshdemo.bean.SpringClassTest;
import com.jd.ssh.sshdemo.entity.Admin;
import com.jd.ssh.sshdemo.search.IndexManager;
import com.jd.ssh.sshdemo.search.SearchHelper;
import com.jd.ssh.sshdemo.search.Searchable;
import com.jd.ssh.sshdemo.service.AdminService;
import com.jd.ssh.sshdemo.service.TaskService;
import com.jd.ssh.sshdemo.test.spring.SpringTransactionalTestCase;


@ContextConfiguration(locations = { "/applicationContext.xml" })
public class IndexManagerTest extends SpringTransactionalTestCase {

	@Autowired
	private AdminService adminService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private SpringClassTest springClassTest;
	@Autowired
	private IndexManager indexManager;
	@Autowired
	private SearchHelper searchHelper;
	
	private static Logger logger = LoggerFactory.getLogger(IndexManagerTest.class);
	
	@Test
	public void createSplit() throws Exception {
		
		String text = "android 刷机";
        long ct = System.currentTimeMillis();
        for(String word : SearchHelper.splitKeywords(text)){
            System.out.println(word);
        }
        System.out.printf("TIME %d\n", (System.currentTimeMillis() - ct));
	}
	
	@Test
	public void createHighlight() throws Exception {
		String text = "SQL server 是最好的 数据库 应用服务器";
        System.out.println("RESULT:" + searchHelper.highlight(text, "sql server"));
	}
	
	@Test
	public void createIndex() throws IOException{
		List<Admin> list = adminService.getAll();
		
		int size = indexManager.add((List<? extends Searchable>) list);
		System.out.println(size);
	}
	
	@Test
	public void searchIndex() throws IOException{
		List<Admin> list = adminService.getAll();
		for(Admin admin:list){
			System.out.println(admin.getId() + " " + admin.getName() + " " + admin.getUsername());
		}
		indexManager.delete(list);
		
		int size = indexManager.add((List<? extends Searchable>) list);
		System.out.println("size " +  size);
		
		Query query = searchHelper.makeQuery("name", "徐敏", 1);
		int count = indexManager.count(Admin.class, query, null);
		System.out.println(count + "-----");

		Filter filter = null;
		List<String> ids = indexManager.find(Admin.class, query, filter, null, 1, 10);
		for (String i1 : ids) {
			System.out.println("i1 " + i1);
			Admin admin = adminService.get(i1);
			System.out.println(admin.getId() + " " + admin.getName() + " " + admin.getUsername());
		}

		// 多字段查询 TermQuery
		TermQuery queryTerm = new TermQuery(new Term("id", "8a8a8bca44868a430144868a82820000"));
		queryTerm.setBoost(1);
		List<String> ids1 = indexManager.find(Admin.class, queryTerm, filter, null, 1,
				10);
		for (String i2 : ids1) {
			System.out.println("i2 " + i2);
		}
	}
	
	/**
	 * 测试自定义的注解
	 * */
	@Test
	public void getPersonAnno() throws ClassNotFoundException{
		final String CLASS_NAME = "com.jd.ssh.sshdemo.annotation.Person"; 
        Class test = Class.forName(CLASS_NAME); 
        Method[] methods = test.getMethods(); 
        
        boolean flag = test.isAnnotationPresent(Indexable.class); 
        if (flag) { 
        	Indexable des = (Indexable) test.getAnnotation(Indexable.class); 
        	//System.out.println("描述:" + des.value()); 
        } 
        Set<Method> set = new HashSet<Method>(); 
        for (int i = 0; i < methods.length; i++) { 
            boolean otherflag = methods[i].isAnnotationPresent(Searched.class); 
            if (otherflag) { 
                set.add(methods[i]); 
            } 
        } 
        for (Method method : set) { 
        	Searched name = method.getAnnotation(Searched.class); 
        	System.out.println(method.getName());
            System.out.println(name.type().name()); 
            
            String methodName = method.getName();
            // try "get"
            if (methodName.startsWith("get")) {
            	String testStdMethod = Introspector.decapitalize(methodName.substring(3));
            	String testOldMethod = methodName.substring(3);
            
            	System.out.println(testStdMethod + "  " + testOldMethod);
	            //if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
	            //	return methods[i];
	            //}
            	//使用testStdMethod作为属性名称进行保存
            	//testStdMethod
            }
            // if not "get" then try "is"
            if (methodName.startsWith("is")) {
            	String testStdMethod = Introspector.decapitalize(methodName.substring(2));
            	String testOldMethod = methodName.substring(2);
            	
            	System.out.println(testStdMethod + "  " + testOldMethod);
	            //if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
	            // return methods[i];
	            //}
            	//使用testStdMethod作为属性名称进行保存
            }
        } 
	}
}
