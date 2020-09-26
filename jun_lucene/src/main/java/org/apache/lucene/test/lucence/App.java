package org.apache.lucene.test.lucence;

import java.io.File;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	 Analyzer analyzer = new StandardAnalyzer();
    	 Directory directory = new RAMDirectory();
		 directory=FSDirectory.open(Paths.get(new File("D://lucence").getAbsoluteFile().toURI()));
		 IndexWriterConfig config=new IndexWriterConfig(analyzer);  
    	 IndexWriter indexWriter = new IndexWriter(directory, config);
    	 Document doc = new Document();
    	//StringField     
        //DoubleField  
        //BinaryDocValuesField  
    	 //Text 默认分词 其他不分词
    	 doc.add(new StringField("id", "1234567654321", Store.YES));
    	 doc.add(new TextField("name", "黄登峰是个中国人", Store.YES));
    	 doc.add(new TextField("content", "一个程序员", Store.YES));
    	 doc.add(new LongField("age", 12, Store.YES));
    	 indexWriter.addDocument(doc);
    	 indexWriter.commit();
    	 indexWriter.close();  
    	 
    	 IndexReader indexReader =DirectoryReader.open(directory) ;//一般系统中reader使用单例模式 
    	 IndexSearcher searcher = new IndexSearcher(indexReader);
    	 //常规单字段
    	// Query query = new  TermQuery(new Term("name", "黄登峰"));
    	 //通配符搜索*/
    	 //WildcardQuery query = new WildcardQuery(new Term("content", "因为*"));//*?
    	 
    	 
    	 WildcardQuery query=new WildcardQuery(new Term("name","峰"));   
    	 TopDocs topDocs = searcher.search(query, 100);  
    	 ScoreDoc[] scoreDocs = topDocs.scoreDocs;  
    	 System.out.println("size="+scoreDocs.length);  
    	 for(int i=0;i<scoreDocs.length;i++) {  
             Document document = searcher.doc(scoreDocs[i].doc);  
             System.out.println(document.get("name"));  
         }  
    }
}
