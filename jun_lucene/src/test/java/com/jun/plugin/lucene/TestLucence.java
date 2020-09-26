package com.jun.plugin.lucene;

import java.util.Date;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.test.lucence.LucenceSearch;
import org.apache.lucene.test.lucence.LucenceUtil;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.junit.Test;

import com.alibaba.fastjson.JSON;


/**
 * 测试用例
 * @author DF
 *
 */
public class TestLucence {

	/**
	 * 创建索引
	 */
	@Test
	public void t1(){
		User u = new User("2", "黄登峰", "1", 18, "我是介绍", 19.98, "湖北省随州市",new Date());
		LucenceUtil.addIndex(new UserConvert().objectToDoc(u));
	}
	/**
	 * 删除所有索引
	 */
	@Test
	public void t2(){
		LucenceUtil.deleteAllIndex();
	}
	/**
	 * 打印查询
	 */
	public void print(Query query ){
		List<User> list =LucenceSearch.findAll(query, new UserConvert());
		String json = JSON.toJSONString(list);
		System.out.println(json);
	}
	/**
	 * 删除索引
	 */
	@Test
	public void t3(){
		LucenceUtil.deleteIndex(new Term("id", "1"));
	}
	/**
	 * 更新索引
	 */
	@Test
	public void t4(){
		User u = new User("2", "黄登峰update", "1", 19, "我是介绍", 19.98, "湖北省随州市",new Date());
		LucenceUtil.updateIndex(new Term("id", "2"), new UserConvert().objectToDoc(u));
	}
	/**
	 * 简单查询
	 */
	@Test
	public void t5(){
		/**
		 * TermQuery 最小的查询单元 且不会分词
		 */
		Query query = new TermQuery(new Term("name", "峰"));
		print(query);
	}
	/**
	 * 布尔查询，当出现  与/或时可以使用该查询
	 */
	@Test
	public void t6(){
		TermQuery tquery1 = new TermQuery(new Term("address", "湖"));
		TermQuery tquery2 = new TermQuery(new Term("name", "峰"));
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(tquery1, Occur.MUST);
        builder.add(tquery2, Occur.MUST);
        BooleanQuery booleanQuery  = builder.build();
        print(booleanQuery);
	}
	/**
	 * TermRangeQuery 字符串 
	 */
	@Test
	public void t7(){
		//后两个参数   是否包含起始边界的设置 一般比string 的数字 或者字符串
		TermRangeQuery termRangeQuery = TermRangeQuery.newStringRange("name", "a", "z", true, true);
		print(termRangeQuery);
	}
	/**
	 * 模糊查询 word work  worl 这种的查找与拼错的单词
	 */
	@Test
	public void t8(){
		FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("address", "湖"));
		print(fuzzyQuery);
	}
	/**
	 * 数字范围
	 */
	@Test
	public void t9(){
		//所有的数字都可以
		NumericRangeQuery<Integer> umericRangeQuery = NumericRangeQuery.newIntRange("age", 10, 20, true, true);
		print(umericRangeQuery);
	}
	/**
	 * 前缀查询
	 */
	@Test
	public void t10(){
		PrefixQuery prefixQuery = new PrefixQuery(new Term("address", "湖"));
		print(prefixQuery);
	}
	/**
	 * 通配符查询
	 * * 表示零个以上
	 *	? 表示一个
	 */
	@Test
	public void test11(){
		WildcardQuery wildcardQuery = new WildcardQuery(new Term("address", "湖?"));
		print(wildcardQuery);
	}
	/**
	 * 短语查询PhraseQuery  Search mails that have phrase 'job opening j2ee'
	 * 
	 */
	@Test
	public void t12(){
		PhraseQuery.Builder builder = new PhraseQuery.Builder();
		//必须相同列
		builder.add(new Term("name", "job"));
		builder.add(new Term("name", "opening"));
		//短语之间的间隔
		builder.setSlop(1);
		print(builder.build());
		/**
		 * MultiPhraseQuery multiPhraseQuery = new MultiPhraseQuery();
		multiPhraseQuery.add(new Term("name", "job"));
		multiPhraseQuery.add(new Term("name", "opening"));
		multiPhraseQuery.setSlop(3);
		 */
	}
	/**
	 * 多条件 builder.build() 返回的是query 还可以随意组合
	 */
	@Test
	public void t13(){
		TermQuery tquery1 = new TermQuery(new Term("address", "湖"));
		TermQuery tquery2 = new TermQuery(new Term("name", "峰"));
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(tquery1, Occur.MUST);
        builder.add(tquery2, Occur.MUST);
        builder.add(NumericRangeQuery.newIntRange("age", 10, 20, true, true), Occur.MUST);
        BooleanQuery booleanQuery  = builder.build();
        print(booleanQuery);
	}
}
