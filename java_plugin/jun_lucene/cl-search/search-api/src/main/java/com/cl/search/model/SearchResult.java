package com.cl.search.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SearchResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private ResultType resultType;
	private List<Commodity> commodityList;	//正常查询时的结果
	private Map<String,List<Commodity>> commodityMap;	//分词查询时的结果
	private List<FacetItem> facetItemList;	//对于有多条件过滤的搜索页面需要的Facet内容
	
	public ResultType getResultType() {
		return resultType;
	}
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}
	public List<Commodity> getCommodityList() {
		return commodityList;
	}
	public void setCommodityList(List<Commodity> commodityList) {
		this.commodityList = commodityList;
	}
	public Map<String, List<Commodity>> getCommodityMap() {
		return commodityMap;
	}
	public void setCommodityMap(Map<String, List<Commodity>> commodityMap) {
		this.commodityMap = commodityMap;
	}
	public List<FacetItem> getFacetItemList() {
		return facetItemList;
	}
	public void setFacetItemList(List<FacetItem> facetItemList) {
		this.facetItemList = facetItemList;
	}
}
