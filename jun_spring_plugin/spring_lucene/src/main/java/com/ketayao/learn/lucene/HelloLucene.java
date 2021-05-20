/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.lucene.HelloIndex.java
 * Class:			HelloIndex
 * Date:			2013年9月4日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年9月4日 上午10:06:33 
 */

public class HelloLucene {
	/*
	 * 建立索引
	 */
	public static void index() {

		IndexWriter writer = null;
		try {
			// 1.创建Directory，确定索引建立在硬盘或者是内存中
			// Directory directory=new RAMDirectory(); //建立在内存中
			Directory directory = FSDirectory
			  .open(new File("f:\\lucene\\index"));// 把索引存在这个位置
			// 2.创建IndexWriter，通过它来写索引到上述位置 参1：lucene的版本，参2：用的分词器
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
					new StandardAnalyzer(Version.LUCENE_44));
			writer = new IndexWriter(directory, iwc);
			// 3.创建Document对象，相当于数据库中的表 处理要建立索引的文档
			Document doc = null;
			// 4.为Document添加Field，Field相当于数据库中的字段
			File file = new File("F:\\lucene\\documents");// 要建立索引的文档所在文件夹
			// 为每篇文档添加field,索引文档的 内容/名称/路径
			for (File f : file.listFiles()) {
				doc = new Document();

				doc.add(new TextField("content", new FileReader(f)));// 分词，不存储
				doc.add(new StringField("filename", f.getName(),
						Field.Store.YES));// 不分词，存储
				doc.add(new StringField("path", f.getAbsolutePath(),
						Field.Store.YES));// 在api中看看TextField和StringField的不同
				// 5.通过IndexWriter添加文档到索引中
				writer.addDocument(doc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
				}
		}

	}

	/*** 搜索 */
	public static void seracher() {
		// 1.创建Directory
		Directory directory;
		try {
			directory = FSDirectory.open(new File("f:\\lucene\\index"));
			// 2.创建IndexReader 读取索引
			IndexReader reader = DirectoryReader.open(directory);
			// 3.根据IndexReader创建IndexSearcher
			IndexSearcher searcher = new IndexSearcher(reader);
			// 4.创建搜索的Query
			// 创建parser来确定要搜索文件的内容，第二个参数表示搜索的域
			QueryParser parser = new QueryParser(Version.LUCENE_44, "content",
					new StandardAnalyzer(Version.LUCENE_44));
			// 创建query，表示搜索域为content中包含java的文档
			Query query = parser.parse("target");
			// 5.根据Searcher 搜索并且返回TopDocs,第二个参数表示要显示的条数
			TopDocs tds = searcher.search(query, 10);
			// 6.根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs; // score就是相关度最高的评分
			for (ScoreDoc sd : sds) {
				// 7.根据Searcher和ScoreDoc对象获取具体的Document对象
				Document d = searcher.doc(sd.doc);// 根据id获取文档
				// 8.根据Document对象获取需要的值 content没有保存会输出null
				System.out.println(d.get("filename") + "[" + d.get("path")
						+ "]" + d.get("content"));
			}
			// 9.关闭reader
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		index();
		seracher();
	}
}
