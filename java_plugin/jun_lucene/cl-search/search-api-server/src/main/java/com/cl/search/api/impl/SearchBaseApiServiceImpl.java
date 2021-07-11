package com.cl.search.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.search.api.ISearchBaseApiService;
import com.cl.search.model.Commodity;
import com.cl.search.model.FacetItem;
import com.cl.search.model.FacetValue;
import com.cl.search.model.ResultType;
import com.cl.search.model.SearchModel;
import com.cl.search.model.SearchResult;
import com.cl.search.utils.ConstantUtil;
import com.cl.search.utils.PinyinUtil;
import com.cl.search.utils.SolrUtil;

@Service
public class SearchBaseApiServiceImpl implements ISearchBaseApiService {

	@Autowired
	private SolrUtil solrUtil;
	
	@Override
	public SearchResult getWebSearchResult(SearchModel searchModel) {

		initSearchModel(searchModel);
		
		SearchResult sr = null;
		
		SolrQuery solrQuery = new SolrQuery();
		setF1(solrQuery);	//设置返回列
		setPage(solrQuery,searchModel);	//设置分页
		setFacet(solrQuery);	//设置Facet，如果页面不需要Facet，则不查询，这个查询会更耗时
		setOrderBy(solrQuery);	//设置结果排序
		
		solrQuery.setQuery(searchModel.getKeyword());
		setFilter(solrQuery,searchModel);

		sr = search(solrQuery,ResultType.NORMAL);
		if(sr!=null)	return sr;
		
		//如果没有结果，按照拼音查询一次
		solrQuery.setQuery(PinyinUtil.getPingYin(searchModel.getKeyword()));
		sr = search(solrQuery,ResultType.PINYIN);
		if(sr!=null)	return sr;
		
		//如果还没有结果，按照分词后的查询一次
		
		//如果还没有结果，返回null		
		return null;
	}

	private void initSearchModel(SearchModel searchModel)
	{
		if(StringUtils.isEmpty(searchModel.getKeyword())) searchModel.setKeyword("*");
		if(searchModel.getPageNo()==null) searchModel.setPageNo(1);
	}
	private void setF1(SolrQuery solrQuery)
	{
		solrQuery.setParam("fl", "no,name,style_no,sale_price,brand_id");
	}
	private void setPage(SolrQuery solrQuery,SearchModel searchModel)
	{		
		solrQuery.setStart((searchModel.getPageNo()-1)*ConstantUtil.PageSize);
		solrQuery.setRows(ConstantUtil.PageSize);
	}
	private void setFacet(SolrQuery solrQuery)
	{
		String[] ff = new String[2];
		ff[0] = "style_no";
		ff[1] = "brand_id";
		
		solrQuery.setFacet(true);
		solrQuery.addFacetField(ff);
		solrQuery.setFacetLimit(-1);
		solrQuery.setFacetSort(FacetParams.FACET_SORT_COUNT );
		solrQuery.setFacetMinCount(1);
	}
	private void setFilter(SolrQuery solrQuery,SearchModel searchModel)
	{
		solrQuery.addFilterQuery("brand_id:" + "(1)");
	}
	private void setOrderBy(SolrQuery solrQuery)
	{
		solrQuery.setParam("sort", "no desc");
	}
	private List<Commodity> getCommodityList(SolrDocumentList sdList)
	{
		List<Commodity> commodityList = new ArrayList<Commodity>();
		for(SolrDocument sd : sdList)
		{
			Commodity c = new Commodity();
			c.setName(sd.get("name").toString());
			c.setNo(sd.get("no").toString());
			c.setSalePrice(Integer.parseInt(sd.get("sale_price").toString()));
			c.setStyleNo(sd.get("style_no").toString());
			c.setSalePrice(Integer.parseInt(sd.get("brand_id").toString()));
			commodityList.add(c);
		}
		return commodityList;
	}
	private List<FacetItem> getFacetList(List<FacetField> ffList)
	{
		List<FacetItem> facetItemList = new ArrayList<FacetItem>();
		for(FacetField f: ffList)
		{
			FacetItem fi = new FacetItem();
			fi.setId(f.getName());	
			fi.setName("null");	//关于Filter显示的选项名称，需要计算
			fi.setValueCount(f.getValueCount());
			
			List<FacetValue> fvList = new ArrayList<FacetValue>();
			
			for(Count c:f.getValues())
			{
				FacetValue fv = new FacetValue();
				fv.setId(c.getName());
				fv.setName("null");	//关于Filter显示的选项名称，需要计算
				fv.setCount((int) c.getCount());
				fvList.add(fv);
			}
			
			fi.setValueList(fvList);
			
			facetItemList.add(fi);
		}
		return facetItemList;
	}
	private SearchResult search(SolrQuery solrQuery,ResultType resultType)
	{
		HttpSolrServer hss = solrUtil.getSolrHttpConnect();
		
		SearchResult sr = null;
		SolrDocumentList sdList = null;
		QueryResponse qr = null;

		try {
			qr = hss.query(solrQuery);
			sdList = qr.getResults();
		} catch (SolrServerException e) {
			e.printStackTrace();	//吃掉，下一个
			return null;
		}
		
		if(sdList.getNumFound() > 0){
			sr = new SearchResult();
			sr.setResultType(resultType);
			List<Commodity> commodityList = getCommodityList(sdList);
			sr.setCommodityList(commodityList);
			
			List<FacetField> ffList = qr.getFacetFields();
			if(ffList.size() > 0)
			{
				List<FacetItem> facetItemList = getFacetList(ffList);
				sr.setFacetItemList(facetItemList);
			}
			return sr;
		}
		
		return null;
	}
}
