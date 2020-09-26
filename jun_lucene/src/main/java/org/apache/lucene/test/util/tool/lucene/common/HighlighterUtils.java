package org.apache.lucene.test.util.tool.lucene.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;

/**
 * 检索工具类.
 * 
 * @author Baishui2004
 */
public class HighlighterUtils {

	/**
	 * 高亮显示.
	 * 
	 * @param queryString 查询关键词
	 * @param content 内容
	 * @return 高亮内容.
	 */
	public static String highlighter(String queryString, String content) {
		return (String) highlighters(queryString, content);
	}

	/**
	 * 高亮显示.
	 * 
	 * @param queryString 查询关键词
	 * @param contents 内容
	 * @return 高亮内容.
	 */
	@SuppressWarnings("unchecked")
	public static List<String> highlighter(String queryString, List<String> contents) {
		return (List<String>) highlighters(queryString, contents);
	}

	/**
	 * 高亮显示.
	 * 
	 * @param queryString 查询关键词
	 * @param contents 内容
	 * @return 高亮内容.
	 */
	@SuppressWarnings("rawtypes")
	private static Object highlighters(String queryString, Object contents) {
		String fragment = null;
		List<String> fragments = null;
		if (contents instanceof String) {
			fragment = contents.toString();
		} else if (contents instanceof List<?>) {
			fragments = new ArrayList<String>();
		}

		String field = null;
		QueryParser parser = new QueryParser(  field, Config.ANALYZER);
		try {
			Query query = parser.parse(queryString);
			QueryScorer scorer = new QueryScorer(query);

			Highlighter highlighter = new Highlighter(Config.SIMPLE_HTML_FORMATTER, scorer);
			highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));

			if (contents instanceof String) {
				fragment = highlighter.getBestFragment(Config.ANALYZER, field, contents.toString());
			} else if (contents instanceof List<?>) {
				List objects = (List) contents;
				for (Object object : objects) {
					if (object instanceof String) {
						fragments.add(highlighter.getBestFragment(Config.ANALYZER, field, object.toString()));
					}
				}
			}
		} catch (ParseException e) {
			Log.log.error("QueryParser parse error.", e);
		} catch (IOException e) {
			Log.log.error("Highlighter content error.", e);
		} catch (InvalidTokenOffsetsException e) {
			Log.log.error("Highlighter content error.", e);
		}

		if (contents instanceof List<?>) {
			return fragments;
		} else {
			return fragment;
		}
	}

}
