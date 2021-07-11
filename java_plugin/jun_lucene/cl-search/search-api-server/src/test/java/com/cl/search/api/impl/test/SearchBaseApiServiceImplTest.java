package com.cl.search.api.impl.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cl.search.api.impl.SearchBaseApiServiceImpl;
import com.cl.search.model.Commodity;
import com.cl.search.model.FacetItem;
import com.cl.search.model.SearchModel;
import com.cl.search.model.SearchResult;

public class SearchBaseApiServiceImplTest {

	private static SearchBaseApiServiceImpl service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-test.xml");  
		service = (SearchBaseApiServiceImpl)ctx.getBean("searchBaseApiServiceImpl");
		ctx.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getWebSearchResult() {		
		SearchModel searchModel = new SearchModel();
		//searchModel.setKeyword("*");
		//searchModel.setPageNo(1);
		SearchResult sr = service.getWebSearchResult(searchModel);
		if(sr !=null)
		{
			List<Commodity> commodityList = sr.getCommodityList();
			for(Commodity c : commodityList)
			{
				System.out.println(c.getNo() + "\t" + c.getName());
			}
			List<FacetItem> facetItemList = sr.getFacetItemList();
			for(FacetItem fi : facetItemList)
			{
				System.out.println(fi.getId() + "\t" + fi.getValueCount());
			}
		} else {
			System.out.println("null");
		}
	}

}
