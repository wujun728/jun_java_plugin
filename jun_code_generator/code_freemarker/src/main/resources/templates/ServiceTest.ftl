<#import "base/date.ftl" as dt>
package ${conf.servicePackage}<#if table.prefix!="">.${table.prefix}</#if>;

<#assign beanName = table.beanName/>
<#assign beanNameUncap_first = beanName?uncap_first/>

import ${conf.entityPackage}<#if table.prefix!="">.${table.prefix}</#if>.${beanName};
import ${conf.servicePackage}<#if table.prefix!="">.${table.prefix}</#if>.${beanName}Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;

import com.github.wujun728.core.mybatis.page.Pagination;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 类说明:
 * Created by noname on ${.now}
 */
public class ${beanName}ServiceTest extends Junit4SpringTestBase {

	@Autowired
	private ${beanName}Service ${beanNameUncap_first}Service;

	static ${beanName} ${beanNameUncap_first};
	
	static Map<String, Object> params = new HashMap<String, Object>();
	static int pageSize = 5;
	static int pageNo =1;
	private static int DEFAULT_ID=1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		${beanNameUncap_first} = new ${beanName}();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testqueryInfoById(){
		${beanName} result = ${beanNameUncap_first}Service.queryInfoById(DEFAULT_ID);
		assertNotNull(result); 
	}

	@Test
	public void testGetCount(){
		int count = ${beanNameUncap_first}Service.getCount();
		assertTrue(count>=0);
	}

	@Test
	public void testQueryCount(){
		${beanName} entity = new ${beanName}();
		int count = ${beanNameUncap_first}Service.queryCount(entity);
		assertTrue(count>=0);
	}

	@Test
	public void testQuery() {
		${beanName} entity = new ${beanName}();
		List<${beanName}> resut = ${beanNameUncap_first}Service.query(entity);
		assertTrue(CollectionUtils.isNotEmpty(resut));
	}

	@Test
	public void testUpdate() {
		${beanName} entity = new ${beanName}();
		int rowId = ${beanNameUncap_first}Service.update(entity);
		assertTrue(rowId==1);
	}

	@Test
	public void testDelete() {
		int rowId = ${beanNameUncap_first}Service.delete(DEFAULT_ID);
		assertTrue(rowId==1);
	}

	@Test
	public void testAdd() {
		int rowId = ${beanNameUncap_first}Service.add(${beanNameUncap_first});
		assertTrue(rowId==1);
		assertTrue(${beanNameUncap_first}.getId().compareTo(0)>0);
	}

	@Test
	public void testAddList() {
		List<${beanName}> list = new ArrayList<${beanName}>();
		${beanName} entity = new ${beanName}();
		list.add(entity);
		int row = ${beanNameUncap_first}Service.addList(list);
		assertTrue(row >= 0);
	}

	@Test
	public void testFindPage() {
		Pagination<${beanName}> result = ${beanNameUncap_first}Service.findPage(params, pageNo, pageSize);
		assertNotNull(result);
	}
}