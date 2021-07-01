package com.vacomall.lucene.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.kit.JsonKit;
import com.vacomall.lucene.bean.JGoods;
import com.vacomall.lucene.bean.Jpage;
import com.vacomall.lucene.bean.QueryConfig;
import com.vacomall.lucene.bean.QueryFilter;
import com.vacomall.lucene.bean.Sku;
import com.vacomall.lucene.config.LuceneConfig;
import com.vacomall.lucene.service.ILuceneService;
import com.vacomall.lucene.util.LuceneUtil;

/**
 * lucene服务接口
 * @author Administrator
 *
 */
@Service
public class LuceneServiceImpl implements ILuceneService{

	
	@Autowired private IndexWriter indexWriter;
	
	@Autowired private IKAnalyzer analyzer;
	
	public static Logger logger = Logger.getLogger(LuceneServiceImpl.class);
	
	/**
	 * 创建所有
	 */
	@SuppressWarnings("deprecation")
	public void createIndex(JGoods goods) throws Exception {
		// TODO Auto-generated method stub
		Document doc = new Document();
		
		doc.add(new Field("id",goods.getId(), Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("title",goods.getTitle(), Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new TextField("hots",goods.getHots(), Field.Store.YES));
		doc.add(new DoubleField("price",goods.getPrice(), Field.Store.YES));
		doc.add(new LongField("sales",goods.getSales(), Field.Store.YES));
		doc.add(new LongField("date",goods.getDate().getTime(), Field.Store.YES));
		doc.add(new TextField("img",goods.getImg(), Field.Store.YES));
		doc.add(new TextField("skus",JsonKit.toJson(goods.getSkus()), Field.Store.YES));
		
		indexWriter.addDocument(doc);
		indexWriter.commit();
		
		logger.debug("创建索引成功 : " + JsonKit.toJson(goods));
	}

	/**
	 * 更新索引
	 */
	@SuppressWarnings("deprecation")
	public void updateIndex(JGoods goods) throws Exception {
		// TODO Auto-generated method stub
		Document doc = new Document();
		
		doc.add(new Field("id",goods.getId(), Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("title",goods.getTitle(), Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new TextField("hots",goods.getHots(), Field.Store.YES));
		doc.add(new DoubleField("price",goods.getPrice(), Field.Store.YES));
		doc.add(new LongField("sales",goods.getSales(), Field.Store.YES));
		doc.add(new LongField("date",goods.getDate().getTime(), Field.Store.YES));
		doc.add(new TextField("img",goods.getImg(), Field.Store.YES));
		doc.add(new TextField("skus",JsonKit.toJson(goods.getSkus()), Field.Store.YES));
		
		indexWriter.updateDocument(new Term("id",goods.getId()), doc);
		indexWriter.commit();   
		
		logger.debug("更新索引成功,"+JsonKit.toJson(goods));
	}
	/**
	 * 删除索引
	 */
	public void deleteIndex(JGoods goods) throws Exception {
		// TODO Auto-generated method stub
		
		if(goods == null){
			throw new Exception("客户端传入的JGood对象为空");
		}
		
		if(StringUtils.isBlank(goods.getId())){
			throw new Exception("客户端传入的JGood对象为的唯一表示Id为空");
		}
		
		indexWriter.deleteDocuments(new Term("id", goods.getId()));
		indexWriter.commit();
		
		logger.debug("商品"+JsonKit.toJson(goods)+"已从索引中删除");
		
	}
	/**
	 * 搜索
	 */
	public Jpage<JGoods> search(String q, int pageNmber, int pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		
		QueryConfig<JGoods> config = new QueryConfig<JGoods>();
		config.setKw(q);
		config.setJpage(new Jpage<JGoods>(pageNmber,pageSize));
		return this.search(config);
	}
	/**
	 * 高级搜索
	 */
	public Jpage<JGoods> search(QueryConfig<JGoods> config) throws Exception {
		// TODO Auto-generated method stub
		Jpage<JGoods> jpage = config.getJpage();
		int pageNumber = jpage.getPageNumber();
		int pageSize = jpage.getPageSize();
		String kw = LuceneUtil.getSearchWord(config.getKw());
		/**
		 * 创建查询对象
		 */
		BooleanQuery booleanQuery = new BooleanQuery();
		/**
		 * 设置精确查询条件
		 */
		QueryFilter queryFilter = config.getQueryFilter();
		if(queryFilter!=null){
			booleanQuery.add(new TermQuery(new Term(queryFilter.getField(),queryFilter.getValue())), BooleanClause.Occur.MUST);
		}
		/**
		 * 价格筛选
		 */
		Float min = config.getMin();
		Float max = config.getMax();
		if(min == null && max!=null){
			min = Float.MIN_VALUE;
		}else if(min != null && max==null){
			max = Float.MAX_VALUE;
		}
		if(min != null || max!=null){
			NumericRangeQuery<Double> priceQuery = NumericRangeQuery.newDoubleRange("price", min.doubleValue(),max.doubleValue(), true, true);  
			booleanQuery.add(priceQuery, BooleanClause.Occur.MUST);   
		}
		/**
		 * 设置模糊查询字段
		 */
		QueryParser qp = new QueryParser(Version.LUCENE_48, "title",  analyzer);  
		Query query = qp.parse(StringUtils.join(kw));  
		//Query query = qp.parse(StringUtils.join(kw,"*"));  
		booleanQuery.add(query, Occur.MUST);  
        
      // booleanQuery.add(new WildcardQuery(new Term("title", StringUtils.join(kw))), BooleanClause.Occur.MUST);
        /**
         * 设置排序
         */
		Sort sort =LuceneUtil.getSort(config);
		/**
		 * 查询数据
		 */
		IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(indexWriter.getDirectory()));
		ScoreDoc lastScoreDoc = LuceneUtil.getLastScoreDoc(pageNumber, pageSize, booleanQuery, indexSearcher,sort);
		TopDocs topDocs = indexSearcher.searchAfter(lastScoreDoc,booleanQuery,pageSize,sort);

		List<JGoods> list=new ArrayList<JGoods>();
		for (ScoreDoc item : topDocs.scoreDocs) {
			Document doc = indexSearcher.doc(item.doc);
			JGoods bean= new JGoods();
			
			String title=doc.get("title");
			bean.setTitle2(doc.get("title"));
			/**
			 * 高亮
			 */
			TokenStream tokenStream=analyzer.tokenStream("title", new StringReader(title));
			SimpleHTMLFormatter simpleHtmlFormatter=new SimpleHTMLFormatter(LuceneConfig.STARTTAG,LuceneConfig.ENDTAG);
		    Highlighter highlighter=new Highlighter(simpleHtmlFormatter,new QueryScorer(booleanQuery));
			title=highlighter.getBestFragment(tokenStream, title);
			
			bean.setTitle(title==null?doc.get("title"):title);
			bean.setId(doc.get("id"));
	    	bean.setPrice(Float.valueOf(doc.get("price")));
	    	bean.setDate(new DateTime(Long.valueOf(doc.get("date"))).toDate());
	    	bean.setSales(Long.valueOf(doc.get("sales")));
	    	bean.setHots(doc.get("hots"));
	    	bean.setImg(doc.get("img"));
	    	bean.setSkus(JSONArray.parseArray(doc.get("skus"), Sku.class));
	    	
	    	list.add(bean);
        	
		}
		jpage.setTotalRow(topDocs.totalHits);
		jpage.setTotalPage(jpage.getTotalRow() % pageSize == 0 ? jpage.getTotalRow() / pageSize : (jpage.getTotalRow() / pageSize)+1);
		jpage.setList(list);
		return jpage;
		
	}
	
}
