package org.apache.lucene.test.lucence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基本搜索
 * @author DF
 *
 */
public class LucenceSearch {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(LucenceSearch.class);
	/**
	 * 分页查询
	 * @param page
	 * @param query
	 * @param convert
	 * @return
	 */
	public static <T> LucencePage<T> findPage(LucencePage<T> page , Query query,LucenceConvert<T> convert){
		IndexSearcher indexSearcher = LucenceUtil.getIndexSearcher();
		int n = page.getFirstResult() + page.getPageSize();//查询前n条
		try {
			TopDocs topDocs = indexSearcher.search(query, n);
			int total = topDocs.totalHits; //实际总数
			page.setTotal(total);
			//会延迟加载
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;//指定前n条 实际得到的数量
			//分页
			int endIndex = Math.min(n, scoreDocs.length);
			List<T> list = new ArrayList<T>();
			for (int i = page.getFirstResult() ; i < endIndex ;i ++) {
				Document doc = indexSearcher.doc(scoreDocs[i].doc);
				T t = convert.docToObject(doc);
				list.add(t);
			}
			page.setList(list);
			logger.debug("一共匹配到：{},实际取：{}",total,scoreDocs.length);
		} catch (IOException e) {
			logger.error("索引匹配失败");
			throw new RuntimeException(e);
		}
		return page;
	}
	/**
	 * 查询所有
	 * @param query
	 * @param convert
	 * @return
	 */
	public static <T> List<T> findAll(Query query,LucenceConvert<T> convert){
		LucencePage<T> page = new LucencePage<T>(1, Integer.MAX_VALUE);
		return findPage(page, query, convert).getList();
	}
}
