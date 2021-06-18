package com.jd.ssh.sshdemo.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchDao {
	/**
	 * 抽象的父类文件夹
	 * */
	public static Directory directory;

	/**
	 * 返回IndexWriter
	 * */
	public static IndexWriter getWriter() throws Exception {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);// 设置标准分词器
																	// ,默认是一元分词
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,
				analyzer);// 设置IndexWriterConfig
		// iwc.setRAMBufferSizeMB(3);//设置缓冲区大小
		return new IndexWriter(directory, iwc);
	}

	/***
	 * @param indexPath
	 *            查询的路径
	 * @param field
	 *            查询的字段类型
	 * @param searchText
	 *            搜索的文本
	 * 
	 * 
	 * **/
	public void searchTermQuery(String indexPath, String field,
			String searchText) {
		try {
			directory = FSDirectory.open(new File(indexPath));// 打开索引库
			IndexReader reader = DirectoryReader.open(directory);// 流读取
			IndexSearcher search = new IndexSearcher(reader);// 搜索
			// Query q=new PhraseQuery();//查询实例
			Query q = new TermQuery(new Term(field, searchText));
			// q.add();
			TopDocs td = search.search(q, 1000);// 获取最高得分命中
			for (ScoreDoc doc : td.scoreDocs) {
				Document d = search.doc(doc.doc);
				System.out.println("id:" + d.get("id"));
				System.out.println("name:" + d.get("name"));
				System.out.println("content:" + d.get("content"));
			}
			reader.close();// 关闭读取流
			directory.close();// 文件夹
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加的方法
	 * */
	public void add(String indexWriterPath) {
		IndexWriter writer = null;
		try {
			directory = FSDirectory.open(new File(indexWriterPath));// 打开存放索引的路径
			writer = getWriter();
			Document doc = new Document();
			doc.add(new StringField("id", "5", Store.YES));// ID类型不分词存储
			doc.add(new TextField("name", "秋去春来,几多愁", Store.YES));// name使用默认一元分词
			doc.add(new TextField("content", "命运总是颠沛流离,命运总是崎岖厉害", Store.YES));// 存储
			// doc.add(new StringField("id", "1", Store.YES));//存储
			// doc.add(new StringField("name", "张飞", Store.YES));//存储
			// doc.add(new StringField("content", "也许放弃,才能靠近你！",
			// Store.YES));//存储
			writer.addDocument(doc);// 添加进写入流里
			writer.forceMerge(1);// 优化压缩段,大规模添加数据的时候建议,少使用本方法,会影响性能
			writer.commit();// 提交数据
			System.out.println("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();// 关闭流
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/***
	 * 简单查询的方法
	 * 
	 * @param indexReadPath
	 *            读取的索引路径
	 * @param filed
	 *            查询的字段类型
	 * @param searchText查询的文本
	 * */
	public void simpleSearch1(String indexReadPath, String field,
			String searchText) {
		try {
			directory = FSDirectory.open(new File(indexReadPath));// 打开索引文件夹
			IndexReader reader = DirectoryReader.open(directory);// 读取目录
			IndexSearcher search = new IndexSearcher(reader);// 初始化查询组件
			// Query query=new TermQuery(new Term(field, searchText));//查询
			QueryParser parser = new QueryParser(Version.LUCENE_43, field,
					new StandardAnalyzer(Version.LUCENE_43));// 标准分析器查询时候一元分词效果
			Query query = parser.parse(searchText);
			TopDocs td = search.search(query, 10000);// 获取匹配上元素的一个docid
			ScoreDoc[] sd = td.scoreDocs;// 加载所有的Documnet文档
			System.out.println("本次命中数据：" + sd.length);
			for (int i = 0; i < sd.length; i++) {
				int z = sd[i].doc;// 获取每一个文档编号
				Document doc = search.doc(z);// 获取文档
				System.out.println("id:" + doc.get("id"));
				System.out.println("name:" + doc.get("name"));
				System.out.println("content:" + doc.get("content"));
			}
			reader.close();// 关闭资源
			directory.close();// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 查询数据总量
	 * 
	 * @param indexFile
	 *            索引路径
	 * */
	public int findIndexDbCount(String indexFile) {
		int total = 0;
		try {
			Directory dir = FSDirectory.open(new File(indexFile));// 打开文件夹
			IndexReader reader = DirectoryReader.open(dir);// 读取数据
			total = reader.numDocs();// 数据总量
			reader.close();// 释放资源
			dir.close();// 释放资源
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	/***
	 * 删除方法
	 * 
	 * @param indexPath
	 *            索引路径
	 * @param id
	 *            根据ID删除
	 * */
	public void delete(String indexPath, String id) {
		try {
			directory = FSDirectory.open(new File(indexPath));// 打开文件索引目录
			IndexWriter writer = getWriter();
			IndexReader reader = DirectoryReader.open(directory);// 读取目录
			Query q = new TermQuery(new Term("id", id));
			writer.deleteDocuments(q);// 删除指定ID的Document
			writer.commit();// 提交
			writer.close();// 关闭
			reader.close();// 关闭
			System.out.println("删除id为" + id + "的记录成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 根据ID进行更行的方法
	 * 
	 * */
	public void updateByID(String indexPath, String docID, HashMap map) {
		try {
			directory = FSDirectory.open(new File(indexPath));// 打开文件索引目录
			IndexWriter writer = getWriter();
			// IndexReader reader=DirectoryReader.open(directory);//读取目录
			// Document doc=reader.document(Integer.parseInt(docID));
			Document d = new Document();
			d.add(new StringField("id", map.get("id").toString(), Store.YES));
			d.add(new TextField("name", map.get("name").toString(), Store.YES));
			d.add(new TextField("content", map.get("content").toString(),
					Store.YES));
			writer.updateDocument(new Term("id", docID), d);
			writer.commit();
			writer.close();// 关闭
			directory.close();// 关闭
			System.out.println("更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}