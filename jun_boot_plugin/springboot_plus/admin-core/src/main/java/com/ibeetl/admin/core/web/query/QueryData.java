package com.ibeetl.admin.core.web.query;

import java.util.ArrayList;
import java.util.List;

public class QueryData {
	List<QueryItem>  all = new ArrayList<>();
	List<QueryItem>  defaultItems = new ArrayList<>();
	public List<QueryItem> getQueryItems(){
		return all;
	}
	
	public List<QueryItem> getDefaultItems(){
		return defaultItems;
	}
	
	public void addQueryItem(QueryItem item){
		all.add(item);
		if(item.isShow()){
			defaultItems.add(item);
		}
	}
}
