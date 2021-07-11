package com.cl.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.cl.search.db.CommodityDao;
import com.cl.search.util.CommonUtil;
import com.cl.search.util.SolrUtil;

public class CreateIndex {

	private CommodityDao commodityDao = new CommodityDao();
	
	private void run()
	{
		long begin = System.currentTimeMillis();
		
		HttpSolrServer solrConn = SolrUtil.getSolrHttpConnect();
		
		HashMap<String,HashMap<String,String>> commodityMap = commodityDao.getCommodityList();
		
		List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
		SolrInputDocument doc = null;
		int docNum = 0, commitNum=0;
		
		for(Entry<String,HashMap<String,String>> commodityEntry : commodityMap.entrySet()) {
			docNum++;
			doc = new SolrInputDocument();
			HashMap<String,String> commodity = commodityEntry.getValue();

			doc.addField("no", commodity.get("no"));
			doc.addField("brand_id", commodity.get("brand_id"));
			doc.addField("name", commodity.get("name"));
			doc.addField("style_no", commodity.get("style_no"));
			doc.addField("sale_price", commodity.get("sale_price"));
			
			docList.add(doc);
			
			//10000个提交一次
			if(docNum%10000==0){
				commitNum++;
				if(commitNum==1){					
					try {
						solrConn.deleteByQuery("*:*");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					solrConn.add(docList);
					solrConn.commit();					
				} catch (Exception e) {
					e.printStackTrace();
				}
				docList.clear();
				System.out.println(CommonUtil.getCurrentDatetime() + "，Commit :\t"+commitNum);
			}
		}
		
		if(docList.size()>0){
			commitNum++;
			if(commitNum==1){					
				try {
					solrConn.deleteByQuery("*:*");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				solrConn.add(docList);
				solrConn.commit();
				solrConn.optimize();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(CommonUtil.getCurrentDatetime() + "，last commit :\t"+commitNum);
		}else{
			try {
				solrConn.optimize();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		long end = System.currentTimeMillis();
		System.out.println("索引完毕,共耗时： "+((double)(end-begin)/(1000))+"秒。");
	}
	
	
	public static void main(String[] args)
	{
		CreateIndex ci = new CreateIndex();
		ci.run();
	}
}
