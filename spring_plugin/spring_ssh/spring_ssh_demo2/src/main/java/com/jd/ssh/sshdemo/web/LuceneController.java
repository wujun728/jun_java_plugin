package com.jd.ssh.sshdemo.web;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LuceneController {
/*
	private static Logger logger = Logger.getLogger(LuceneController.class);

	@Autowired(required = false)
	// 这里我写了required = false,需要时再引入，不写的话会报错，大家有更好解决方案请留言哈
	private Analyzer myAnalyzer;
	@Autowired(required = false)
	private IndexWriter indexWriter;
	@Autowired(required = false)
	private IndexSearcher searcher;

	@RequestMapping(value = "search.do", method = RequestMethod.GET)
	public String testsSearch(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		search();
		return "test";
	}

	@RequestMapping(value = "idSearch.do", method = RequestMethod.GET)
	public String idSearch(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		idSearch();
		return "test";
	}

	@RequestMapping(value = "moreSearch.do", method = RequestMethod.GET)
	public String moreSearch(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		searchMore();
		return "test";
	}

	@RequestMapping(value = "create.do", method = RequestMethod.GET)
	public String testsCreate(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		create("整形值添加");
		// create(request.getParameter("name"));
		return "test";
	}

	@RequestMapping(value = "delete.do", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		delete("id", request.getParameter("id"));
		return "test";
	}

	@RequestMapping(value = "optimize.do", method = RequestMethod.GET)
	public String optimize(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		//indexWriter.optimize();// 优化索引方法，不建议经常调用，会很耗时，隔段时间调优下即可
		//indexWriter.
		return "test";
	}

	// 关于更新一个文档要注意一点，虽然它提供了updateDocument，但我觉得他是先删再加，所以大家要把所以值都写上，虽然可能只更新一个字段
	@RequestMapping(value = "update.do", method = RequestMethod.GET)
	public String update(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Term term = new Term("id", "1999991");
		Document doc = new Document();
		doc.add(new Field("id", String.valueOf(1999991), Store.YES,
				Index.NOT_ANALYZED));
		doc.add(new Field("name", 555555 + "555555" + 555555, Store.YES,
				Index.ANALYZED));
		doc.add(new Field("level1", String.valueOf(555555), Store.YES,
				Index.NOT_ANALYZED));
		doc.add(new Field("level2", String.valueOf(555555), Store.YES,
				Index.NOT_ANALYZED));
		doc.add(new Field("level3", String.valueOf(555555), Store.YES,
				Index.NOT_ANALYZED));
		doc.add(new Field("brand_id", String.valueOf(555555 + 100000),
				Store.YES, Index.NOT_ANALYZED));
		indexWriter.updateDocument(term, doc);
		indexWriter.commit();// 凡是涉及到索引变化的动作都要提交才能生效
		return "test";
	}

	// delete,没啥说的哈
	private void delete(String field, String text)
			throws CorruptIndexException, IOException {
		Term term1 = new Term(field, text);
		indexWriter.deleteDocuments(term1);
		indexWriter.commit();
	}

	public void create(String string) throws Exception {
		long begin = System.currentTimeMillis();
		for (int m = 604; m < 605; m++) {
			for (int i = m * 10000; i < (m + 1) * 10000; i++) {
				Document doc = new Document();
				// doc.add(new Field("id", String.valueOf(i), Store.YES,
				// Index.NOT_ANALYZED_NO_NORMS));
				NumericDocValuesField field = new NumericDocValuesField("id", 6, Field.Store.YES,	false);
				field.setIntValue(i);
				doc.add(field);// 这里不建议这样写，无论什么格式都以字符串形式灌入数据最好，否则会因为不匹配而查不到，经验之谈哈，如下面这样：
				doc.add(new Field("name", i + string + i, Store.YES,
						Index.ANALYZED));// 关于索引策略，建议需要模糊查询字段进行分词策略，其他则不分词
				doc.add(new Field("level1", String.valueOf(3), Store.YES,
						Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("level2", String.valueOf(2), Store.YES,
						Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("level3", String.valueOf(1), Store.YES,
						Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("brand_id", String.valueOf(i + 100000),
						Store.YES, Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("hehe", String.valueOf(i + 100000),
						Store.YES, Index.NOT_ANALYZED_NO_NORMS));
				indexWriter.addDocument(doc);
			}
			System.out.println(m);
		}
		indexWriter.commit();
		System.out.println("create cost:"
				+ (System.currentTimeMillis() - begin) / 1000 + "s");
	}

	// 这里的查询是说：搜索name字段关键词为“整形的”,level3字段值为1的内容，两者条件是 'and'的关系
	public void search() throws Exception {
		long begin = System.currentTimeMillis();
		String[] queryString = { "整形", "1" };// 注意字段与值要一一对应哦，同下
		String[] fields = { "name", "level3" };// //注意字段与值要一一对应哦，同上
		BooleanClause.Occur[] clauses = { BooleanClause.Occur.MUST,
				BooleanClause.Occur.MUST };// 这里就是 and 的关系，详细策略看文档哈
		Query query = MultiFieldQueryParser.parse(Version.LUCENE_30,
				queryString, fields, clauses, myAnalyzer);
		IndexReader readerNow = searcher.getIndexReader();
		// 这个判断很重要，就是当我们刚灌入了数据就希望查询出来，因为前者写索引时关闭了reader,所以我们现在查询时要打开它
		if (!readerNow.isCurrent()) {
			searcher = new IndexSearcher(readerNow.reopen());
		}
		System.out.println(searcher.maxDoc());
		Sort sort = new Sort();
		sort.setSort(new SortField("id", SortField.INT, true));
		TopDocs topDocs = searcher.search(query, null, 53, sort);// 排序策略
		// TopDocs topDocs = searcher.search(query, 50);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println("id:" + doc.get("id"));
			System.out.println("name:" + doc.get("name"));
			System.out.println("level3:" + doc.get("level3"));
			System.out.println("new field:" + doc.get("hehe"));
		}
		System.out.println("search cost:"
				+ (System.currentTimeMillis() - begin) / 1000 + "s");
	}

	private void idSearch() throws ParseException, CorruptIndexException,
			IOException {
		long begin = System.currentTimeMillis();
		QueryParser qp = new QueryParser(Version.LUCENE_30, "id", myAnalyzer);

		Query query = qp.parse("4040011");
		IndexReader readerNow = searcher.getIndexReader();
		if (!readerNow.isCurrent()) {
			searcher = new IndexSearcher(readerNow.reopen());
		}
		TopDocs topDocs = searcher.search(query, null, 53);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println("id:" + doc.get("id"));
			System.out.println("name:" + doc.get("name"));
			System.out.println("level3:" + doc.get("level3"));
			System.out.println("new field:" + doc.get("hehe"));
		}
		System.out.println("search cost:"
				+ (System.currentTimeMillis() - begin) / 1000 + "s");
	}

	public void searchMore() throws Exception {
		long begin = System.currentTimeMillis();
		String[] queryStringOne = { "kkk", "222222" };
		String[] queryStringTwo = { "99980", "222222" };
		String[] fields = { "name", "level2" };
		BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD,
				BooleanClause.Occur.SHOULD };
		Query queryOne = MultiFieldQueryParser.parse(Version.LUCENE_30,
				queryStringOne, fields, clauses, myAnalyzer);
		Query queryTwo = MultiFieldQueryParser.parse(Version.LUCENE_30,
				queryStringTwo, fields, clauses, myAnalyzer);
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(queryOne, BooleanClause.Occur.MUST);
		booleanQuery.add(queryTwo, BooleanClause.Occur.MUST);
		IndexReader readerNow = searcher.getIndexReader();
		if (!readerNow.isCurrent()) {
			searcher = new IndexSearcher(readerNow.reopen());
		}
		System.out.println(searcher.maxDoc());
		Sort sort = new Sort();
		sort.setSort(new SortField("id", SortField.INT, true));
		TopDocs topDocs = searcher.search(booleanQuery, null, 53, sort);
		// TopDocs topDocs = searcher.search(query, 50);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println("id:" + doc.get("id"));
			System.out.println("name:" + doc.get("name"));
			System.out.println("level3:" + doc.get("level3"));
			System.out.println("new field:" + doc.get("hehe"));
		}
		System.out.println("search cost:"
				+ (System.currentTimeMillis() - begin) / 1000 + "s");
	}

	@RequestMapping(value = "result.do", method = RequestMethod.GET)
	public void getAnalyzerResult() throws IOException {
		StringReader reader = new StringReader("爱国者mp3");
		TokenStream ts = myAnalyzer.tokenStream("name", reader);
		ts.addAttribute(TermAttribute.class);
		while (ts.incrementToken()) {
			TermAttribute ta = ts.getAttribute(TermAttribute.class);
			System.out.println(ta.term());
		}
	}*/

}
