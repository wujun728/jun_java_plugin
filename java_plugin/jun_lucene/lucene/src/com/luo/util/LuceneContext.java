package com.luo.util;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.SearcherWarmer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

/**
 * 完全实时搜索，只要数据库有变动，立即更新索引
 * 近实时搜索，当有数据变动，先把索引保存到内存中，然后在一个统一时间提交
 * 使用NRTManager(near-real-time)和SearcherManager
 * @author 罗辉, @date:Aug 5, 2013
 *
 */
public class LuceneContext {
	private static LuceneContext luceneContext=null;
	private static String INDEX_DIR="G:/lucene/solr/home/data/index";
	private static Version VERSION = Version.LUCENE_35;
	private static Directory directory;
	private static MMSegAnalyzer analyzer;
	private static IndexWriter indexWriter;
	private static NRTManager nrtManager;
	private static SearcherManager searcherManager;
	private LuceneContext() {
	}
	
	
	/**
	 * 注意：使用searcherManager的acquire()方法来获取IndexSearcher
	 * 
	 * Author:罗辉 ,Date:Aug 5, 2013
	 */
	public static IndexSearcher getSearcher() {
		return searcherManager.acquire();
	}
	public static LuceneContext getContext() {
		if(luceneContext==null){
			init();
			new LuceneContext();
		}
		return luceneContext;
	}
	
	
	private static void init() {
		try {
			directory = FSDirectory.open(new File(INDEX_DIR));
			String dicDir = LuceneContext.class.getResource("data").getPath();
			analyzer = new MMSegAnalyzer(dicDir);
			indexWriter=new IndexWriter(directory,new IndexWriterConfig(VERSION,analyzer));
			//注意，只有在lucene3.5中才有这个构造方法，如果以后运行有问题，请换成3.6
			nrtManager=new NRTManager(indexWriter,new SearcherWarmer(){
				@Override
				public void warm(IndexSearcher arg0) throws IOException {
					System.out.println("reopen index");
				}
			});
			searcherManager=nrtManager.getSearcherManager(true);//允许全部删除
			NRTManagerReopenThread thread=new NRTManagerReopenThread(nrtManager,5.0,0.025);
			thread.setDaemon(true);//设置为
			thread.setName("NRTManager Reopen Thread");
			thread.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void commitIndex() {
		try {
			indexWriter.commit();
			indexWriter.forceMerge(3);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
