package com.jun.plugin.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.lionsoul.jcseg.analyzer.v5x.JcsegAnalyzer5X;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

/**
 * 分词测试
 * @author DF
 *
 */
public class TestAnalyzer {

	/**
	 * 使用指定的分词器对指定的文本进行分词，并打印出分出的词
	 * 
	 * @param analyzer
	 * @param text
	 * @throws IOException 
	 * @throws Exception
	 */
	public void testAnalyer(Analyzer analyzer,String keyWord) throws IOException {
	System.out.println("当前使用的分词器：" + analyzer.getClass().getSimpleName());
	   TokenStream tokenStream = (TokenStream) analyzer.tokenStream("content", new StringReader(keyWord));
	    tokenStream.addAttribute(CharTermAttribute.class);
	    tokenStream.reset();
	    while (tokenStream.incrementToken()) {
	       CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
	          System.out.println(new String(charTermAttribute.toString()));
	   }
	}

	/**
	 * JcsegAnalyzer5X 国产
	 * @throws Exception
	 */
	@Test
	public void t1() throws Exception{
		Analyzer analyzer = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE);
		testAnalyer(analyzer, "当前使用的分词器The Lucene PMC is pleased to announce the release of the Apache Solr Reference Guide for Solr 4.4.我是中国人");
	}
	/**
	 * lucence 自带 中文单个分词
	 * @throws IOException 
	 */
	@Test
	public void t2() throws IOException{
		Analyzer analyzer = new StandardAnalyzer();
		testAnalyer(analyzer, "当前使用的分词器The Lucene PMC is pleased to announce the release of the Apache Solr Reference Guide for Solr 4.4.我是中国人");
	}
	/**
	 * MMSegAnalyzer
	 * @throws IOException
	 */
	@Test
	public void t3() throws IOException{
		Analyzer analyzer = new MMSegAnalyzer();
		testAnalyer(analyzer, "当前使用的分词器The Lucene PMC is pleased to announce the release of the Apache Solr Reference Guide for Solr 4.4.我是中国人");
	}
}
