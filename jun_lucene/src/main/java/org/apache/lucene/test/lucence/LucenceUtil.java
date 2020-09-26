package org.apache.lucene.test.lucence;

import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * lucence 的工具类
 * @author DF
 *
 */
public class LucenceUtil {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(LucenceUtil.class);
	private static IndexWriter indexWriter;
	private static IndexReader indexReader;
	private static IndexSearcher indexSearcher;
	/**
	 * 取得writer
	 * @return
	 */
	public static IndexWriter getIndexWriter(){
		if (null == indexWriter) {
			try {
				indexWriter = new IndexWriter(LucenceConfig.getDirectory(), LucenceConfig.getIndexWriterConfig());
			} catch (IOException e) {
				if (null != indexWriter) {
					try {
						indexWriter.close();
						indexWriter = null;
					} catch (IOException e1) {
						throw new RuntimeException(e1);
					}
				}
				throw new RuntimeException(e);
			} 
		}
		return indexWriter;
	}
	/**
	 * 取得reader
	 * @return
	 */
	public static IndexReader getIndexReader(){
		if (null == indexReader) {
			try {
				indexReader = DirectoryReader.open(LucenceConfig.getDirectory());
			} catch (IOException e) {
				if (null != indexReader) {
					try {
						indexReader.close();
						indexReader= null;
					} catch (IOException e1) {
						throw new RuntimeException(e1);
					}
				}
				throw new RuntimeException(e);
			} 
		}
		return indexReader;
	}
	public static IndexSearcher getIndexSearcher(){
		//不判断indexSearcher  新版本indexSearcher 没有close方法  这个地方应该判断reader是否变化  因为 lucence的锁是以Directory为基准的  这样查询过程中 别人修改了索引会等待 多线程情况下就不会有问题
		if (null == indexReader ) {
			indexSearcher = new IndexSearcher(LucenceUtil.getIndexReader());
		}
		return indexSearcher;
	}
	/**
	 * 关闭reader writer searcher
	 */
	public static void indexChanged(){
		if (null != indexWriter) {
			try {
				indexWriter.close();
				indexWriter = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (null != indexReader) {
			try {
				indexReader.close();
				indexReader = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	/**
	 * 添加索引
	 * @param docs 多个或者单个字典
	 */
	public static void addIndex(Document ... docs){
		if (docs.length == 0) {
			return ;
		}
		try {
			IndexWriter indexWriter = LucenceUtil.getIndexWriter();
			indexWriter.addDocuments(Arrays.asList(docs));
			LucenceUtil.indexChanged();
			logger.debug("添加索引成功");
		} catch (IOException e) {
			logger.error("添加索引失败.....");
			throw new RuntimeException(e);
		}
	}
	/**
	 * 删除索引
	 * @param terms 单个条件
	 */
	public static void deleteIndex(Term ... terms){
		try {
			IndexWriter indexWriter = LucenceUtil.getIndexWriter();
			indexWriter.deleteDocuments(terms);
			LucenceUtil.indexChanged();
			logger.debug("删除索引成功");
		} catch (IOException e) {
			logger.error("删除索引失败.....");
			throw new RuntimeException(e);
		}
	}
	/**
	 * 删除所有索引
	 */
	public static void deleteAllIndex(){
		try {
			IndexWriter indexWriter = LucenceUtil.getIndexWriter();
			indexWriter.deleteAll();
			LucenceUtil.indexChanged();
			logger.debug("删除所有索引成功");
		} catch (IOException e) {
			logger.error("删除所有索引失败.....");
			throw new RuntimeException(e);
		}
	}
	/**
	 * 更新索引  一般以id 组件term  
	 * @param term  条件
	 * @param newDoc 新字典数据
	 */
	public static void updateIndex(Term term,Document newDoc){
		try {
			IndexWriter indexWriter = LucenceUtil.getIndexWriter();
			indexWriter.updateDocument(term, newDoc);
			LucenceUtil.indexChanged();
			logger.debug("更新索引成功");
		} catch (IOException e) {
			logger.error("更新索引失败.....");
			throw new RuntimeException(e);
		}
	}
}
