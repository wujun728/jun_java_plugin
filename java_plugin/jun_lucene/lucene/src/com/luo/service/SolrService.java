package com.luo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luo.dao.MessageDao;
import com.luo.pojo.Message;
import com.luo.util.IndexUtil;
import com.luo.util.SolrContext;
import com.luo.vo.IndexField;
import com.luo.vo.IndexModel;
import com.taiping.b2b2e.common.page.PageBean;

@Service
public class SolrService {

	@Autowired
	private MessageDao messageDao;
	
	
	public void commitRamIndex(){
		try {
			SolrContext.getServer().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 重构所有的索引
	 * 把数据库中所有的记录查出开，构建IndexField添加到索引中
	 * Author:罗辉 ,Date:Aug 2, 2013
	 */
	public void updateReconstructorIndex(){
		try {
			//1、先删除所有的索引
			SolrContext.getServer().deleteByQuery("*:*");
			
			//2、取出所有数据，构建indexField
			List<Message> messageList=messageDao.findAll();
			indexMessageList(messageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为所有的数据构建索引
	 * Author:罗辉 ,Date:Aug 2, 2013
	 */
	private void indexMessageList(List<Message> messageList) {
		for (Message message : messageList) {
			IndexField indexField=IndexUtil.message2IndexField(message);
			addIndex(indexField);
		}
	}

	/**
	 * 添加索引
	 * Author:罗辉 ,Date:Aug 2, 2013
	 */
	public void addIndex(IndexField indexField) {
		try {
			//1、添加索引到solr
			SolrServer solrServer = SolrContext.getServer();
			solrServer.addBean(indexField);
			//2、优化索引
			solrServer.optimize();
			//2、提交索引
			SolrContext.getServer().commit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检索
	 * Author:罗辉 ,Date:Aug 2, 2013
	 * @param field 
	 */
	public PageBean<IndexModel> findByIndex(String keyword, String field,Integer start,int pageSize) throws SolrServerException {
		if(keyword==null)keyword="*";
		if(field==null)field="*";
		SolrQuery solrQuery=new SolrQuery(field+":"+keyword);
		solrQuery.addSortField("last_modified", SolrQuery.ORDER.desc);
		solrQuery.setHighlight(true)//设置显示高亮
		.setHighlightSimplePre("<span class='highlighter'>")
		.setHighlightSimplePost("</span>")//设置高亮的样式
		.setParam("hl.fl", "title,content,description")//设置高亮的域
//		.setParam("hl.fl", "title,content,summary")//设置高亮的域
		.setStart(start)//设置分页
		.setRows(pageSize);
		
		QueryResponse queryResponse = SolrContext.getServer().query(solrQuery);//solrQuery是SolrParams的子类
		//第一个map的key是文档document的ID，第二个map的key'是要高亮显示的field
		Map<String,Map<String,List<String>>> map=queryResponse.getHighlighting();
		SolrDocumentList solrDocumentList=queryResponse.getResults();
		PageBean<IndexModel> page = new PageBean<IndexModel>(new Long(solrDocumentList.getNumFound()).intValue());
		page.setPageSize(pageSize);
		page.setStartRow(start);
		List<IndexModel> datas=new ArrayList<IndexModel>();
		for (SolrDocument sd : solrDocumentList) {
			String id = (String) sd.getFieldValue("id");
			String title = (String) sd.getFieldValue("title");
			String summary = (String) sd.getFieldValue("description");
			List<String> content = (List<String>) sd.getFieldValue("content");//multiValued="true"的field返回List
			
			IndexModel indexModel=new IndexModel();
			indexModel.setTitle(title);
			indexModel.setSummary(summary);
//			Map<String,List<String>> glMap=map.get(id);
//			List<String> titleList = glMap.get("title");
//			if(titleList!=null&&titleList.size()!=0){
//				indexModel.setTitle(titleList.get(0));//获取高亮
//			}
//			List<String> summaryList = glMap.get("description");
//			if(summaryList!=null&&summaryList.size()!=0){
//				indexModel.setSummary(summaryList.get(0));//获取高亮
//			}
//			List<String> content = glMap.get("content");//获取高亮
			Date addTime = (Date) sd.getFieldValue("last_modified");
			indexModel.setId(id);
			indexModel.setContent(content.get(0));
			indexModel.setAddTime(addTime);
			datas.add(indexModel);
		}
		page.setResultList(datas);
		return page;
	}

	/**
	 * 删除索引
	 * Author:罗辉 ,Date:Aug 2, 2013
	 */
	public void deleteIndex(IndexField indexField) {
		try {
			SolrContext.getServer().deleteById(indexField.getId());
			SolrContext.getServer().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void commitDBIndex() {
		try {
			SolrContext.getServer().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void deleteIndex() {
		try {
			SolrContext.getServer().deleteByQuery("*:*");
			SolrContext.getServer().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
