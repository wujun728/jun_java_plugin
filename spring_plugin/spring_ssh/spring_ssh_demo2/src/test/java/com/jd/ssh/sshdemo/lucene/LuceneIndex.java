package com.jd.ssh.sshdemo.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
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

class Constants {  
    public final static String INDEX_FILE_PATH = "D:\\indextest"; //索引的文件的存放路径 测试时可以在本目录下自行建一些文档,内容自行编辑即可  
    public final static String INDEX_STORE_PATH = "D:\\index"; //索引的存放位置  
} 
public class LuceneIndex {
	
	/**
	 * 创建索引
	 * @param analyzer
	 * @throws Exception
	 */
	public static void createIndex(Analyzer analyzer) throws Exception{
		Directory dire=FSDirectory.open(new File(Constants.INDEX_STORE_PATH));
		IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_46, analyzer);
		IndexWriter iw=new IndexWriter(dire, iwc);
		LuceneIndex.addDoc(iw);
		iw.close();
	}
	
	/**
	 * 动态添加Document
	 * @param iw
	 * @throws Exception
	 */
	public static void addDoc(IndexWriter iw)  throws Exception{
		File[] files=new File(Constants.INDEX_FILE_PATH).listFiles();
		for (File file : files) {
			Document doc=new Document();
			String content=LuceneIndex.getContent(file);
			String name=file.getName();
			String path=file.getAbsolutePath();
			doc.add(new TextField("content", content, Store.YES));
			doc.add(new TextField("name", name, Store.YES));
			doc.add(new TextField("path", path,Store.YES));
			System.out.println(name+"==="+content+"==="+path);
			iw.addDocument(doc);
			iw.commit();
		}
	}
	
	/**
	 * 获取文本内容
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String getContent(File file) throws Exception{
		FileInputStream fis=new FileInputStream(file);
		InputStreamReader isr=new InputStreamReader(fis);
		BufferedReader br=new BufferedReader(isr);
		StringBuffer sb=new StringBuffer();
		String line=br.readLine();
		while(line!=null){
			sb.append(line+"\n");
			line=null;
		}
		return sb.toString();
	}
	
	/**
	 * 搜索
	 * @param query
	 * @throws Exception
	 */
	private static void search(Query query) throws Exception {
		Directory dire=FSDirectory.open(new File(Constants.INDEX_STORE_PATH));
		IndexReader ir=DirectoryReader.open(dire);
		IndexSearcher is=new IndexSearcher(ir);
		TopDocs td=is.search(query, 1000);
		System.out.println("共为您查找到"+td.totalHits+"条结果");
		ScoreDoc[] sds =td.scoreDocs;
		for (ScoreDoc sd : sds) { 
			Document d = is.doc(sd.doc); 
			System.out.println(d.get("path") + ":["+d.get("path")+"]"); 
		}
	}
	
	
	public static void main(String[] args) throws Exception, Exception {
		Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);
		LuceneIndex.createIndex(analyzer);
		QueryParser parser = new QueryParser(Version.LUCENE_46, "content", analyzer); 
		Query query = parser.parse("人");
		LuceneIndex.search(query);
	}
}
