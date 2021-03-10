package controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import pagemodel.DataGrid;
import po.Doc;

@Controller
public class DocController {
	@RequestMapping(value="/getdoclist",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getdoclist(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount,@RequestParam("searchPhrase") String info) throws Exception{
		String url = "http://localhost:8080/solr/tika";
		SolrClient solr = new HttpSolrClient(url);
		SolrQuery query2 = new SolrQuery();
		query2.set("q", info);
		QueryResponse response = solr.query(query2);
		SolrDocumentList list = response.getResults();
		
		SolrQuery query = new SolrQuery();
		query.setStart((current-1)*rowCount);
        query.setRows(rowCount);
		query.set("q", info);
		QueryResponse response2 = solr.query(query);
		SolrDocumentList list2 = response2.getResults();
		List<Doc> doclist=new ArrayList<Doc>();
	        for (SolrDocument document : list2) {
	            String file =(String) document.get("file");
	            String content =(String) document.get("content");
	            long size =(Long) document.get("size");
	            Doc doc=new Doc(file,size,content);
	            doclist.add(doc);
	        }
	    DataGrid<Doc> grid=new DataGrid<Doc>();
	    grid.setCurrent(current);
	    grid.setRowCount(rowCount);
	    grid.setTotal(list.getNumFound());
	    grid.setRows(doclist);
		return JSON.toJSONString(grid);
	}
	
	@RequestMapping("/search")
	String search(){
		return "search";
	}
}
