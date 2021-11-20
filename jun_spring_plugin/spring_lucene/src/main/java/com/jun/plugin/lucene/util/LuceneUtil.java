package com.jun.plugin.lucene.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;

import com.jun.plugin.lucene.bean.QueryConfig;
import com.jun.plugin.lucene.enums.SortType;

import org.apache.lucene.search.TopDocs;


public class LuceneUtil {
	

	/**
	 * 获取排序对象
	 * 排序 true表示降序,false表示升序
	 * @param sortType
	 * @return
	 */
	public static  Sort  getSort(QueryConfig<?> config){
		
		Sort sort = new Sort() ; 
		SortType sortType = config.getSortType();
		
		if(sortType == null){
			return sort;
		}
        
		int sortVal = (sortType == null) ? 0 : sortType.getSort();
        
		/**
		 * 价格筛选优先排序
		 */
		Float min = config.getMin();
		Float max = config.getMax();
		
		/**
		 * 价格降序
		 */
		if(min != null &&  max == null){
			
			sort = new Sort(new SortField("price",Type.DOUBLE,true));
		
		/**
		 * 价格升序
		 */
		}else if(min == null &&  max != null){
			
			sort = new Sort(new SortField("price",Type.DOUBLE,false));
		/**
		 * 价格升序
		 */
		}else if((min != null &&  max != null) && min <= max){
			
			sort = new Sort(new SortField("price",Type.DOUBLE,false));
		/**
		 * 价格降序
		 */
		}else if ((min != null &&  max != null) && min > max){
			sort = new Sort(new SortField("price",Type.DOUBLE,true));
		}
		/**
         * 价格升序
         */
		else if (sortVal== SortType.PRICE_ASC.getSort()) {
			
			sort = new Sort(new SortField("price",Type.DOUBLE,false));
		/**
		 * 价格降序
		 */
		}else if(sortVal == SortType.PRICE_DESC.getSort()){
			
			sort = new Sort(new SortField("price",Type.DOUBLE,true));
		/**
		 * 销量升序
		 */
		}else if(sortVal == SortType.SALES_ASC.getSort()){
			
			sort = new Sort(new SortField("sales",Type.LONG,false));
		/**
		 * 销量降序
		 */
		}else if(sortVal == SortType.SALES_DESC.getSort()){
			
			sort = new Sort(new SortField("sales",Type.LONG,true));
		/**
		 * 时间降序
		 */
		}else if(sortVal == SortType.DATE_DSEC.getSort()){
			
			sort = new Sort(new SortField("date",Type.LONG,true));
		}
		/**
		 * 默认
		 */
		return sort;
		
	}
	
	/**
	 * 获取价格筛选对象
	 * @param config
	 * @return
	 */
	public static Filter getFilter(QueryConfig<?> config){
		
		Filter filter = null ;
		
		Float min = config.getMin();
		Float max = config.getMax();
		
		if(min != null &&  max == null){
			
			 filter =NumericRangeFilter.newFloatRange("price", min, Float.MAX_VALUE, true, true);
		
		}else if(min == null &&  max != null){
			
			filter =NumericRangeFilter.newFloatRange("price", 0f, max, true, true);
		
		}else if(min != null &&  max != null){
			
			filter =NumericRangeFilter.newFloatRange("price", min, max, true, true);
		}
		
		return filter;
		
	}
	
	/**
	 * 搜索 XSS过滤
	 * @param word
	 * @return
	 */
	public static String getSearchWord(String word){
		
		if(StringUtils.isBlank(word)){
			
			return " ";
		}
		
		word = word.trim();
		 String regEx="[`~?!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？-]";  
	     Pattern   p   =   Pattern.compile(regEx);     
	     Matcher   m   =   p.matcher(word);     
	     return   m.replaceAll("").trim();     
         
	}
	
	/**
	 * 根据页码和分页大小获取上一次最后一个ScoreDoc
	 * @param pageIndex
	 * @param pageSize
	 * @param query
	 * @param indexSearcher
	 * @param sort 
	 * @return
	 * @throws IOException
	 */
	public  static ScoreDoc getLastScoreDoc(int pageIndex,int pageSize,Query query,IndexSearcher indexSearcher, Sort sort) throws IOException{
		if(pageIndex==1)return null;//如果是第一页返回空
		int num = pageSize*(pageIndex-1);//获取上一页的数量
		TopDocs tds = indexSearcher.search(query, num,sort);
		return tds.scoreDocs[num-1];
	}	
}
