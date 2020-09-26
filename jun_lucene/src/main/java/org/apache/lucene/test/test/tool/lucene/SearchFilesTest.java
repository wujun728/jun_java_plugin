package org.apache.lucene.test.test.tool.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 测试SearchFiles.
 * 
 * @author Baishui2004
 */
public class SearchFilesTest {

	public static String LUNCENE_WORKSPACE = "E:/WorkSpace/LuceneWorkSpace/";

	public static void main(String[] args) throws Exception {
		System.out.println("英文分词Lucene\n");
		main_lucene();
		System.out.println("\n\n中文分词IKAnalyzer\n");
		main_ik();
	}

	/**
	 * 英文分词.
	 * 
	 * @throws Exception
	 */
	public static void main_lucene() throws Exception {
		Analyzer analyzer = new StandardAnalyzer();
		SearchFiles.search(LUNCENE_WORKSPACE + "TestIndex_en2/", "Single-document in-memory", analyzer);
	}

	/**
	 * 中文分词IKAnalyzer.
	 * 
	 * @throws Exception
	 */
	public static void main_ik() throws Exception {
		Analyzer analyzer = new IKAnalyzer();
		SearchFiles.search(LUNCENE_WORKSPACE + "TestIndex_ch_ik2/", "100本名著浓缩成了100句话", analyzer);
	}

}
