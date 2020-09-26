package org.apache.lucene.test.util.tool.lucene.common;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 分词工具类.
 * 
 * @author Baishui2004
 */
public class SegmenterUtils {

	/**
	 * IKAnalyzer分词(自动加载扩展停止词典: stopword.dic, 停止词典中的词不会出现在分词结果中).
	 */
	public static List<String> ikSegmenter(String sentence) {
		StringReader reader = new StringReader(sentence);
		// 此处IKSegmenter类构造方法的第2个参数useSmart: 是否采用智能切分策略, true使用智能切分, false使用最细粒度切分.
		IKSegmenter segmenter = new IKSegmenter(reader, true);

		List<String> words = new ArrayList<String>();
		try {
			while (true) {
				Lexeme lexeme = segmenter.next();
				if (lexeme == null) {
					break;
				} else {
					words.add(lexeme.getLexemeText());
				}
			}
		} catch (IOException e) {
			Log.log.error("IKAnalyzer Segmenter error.", e);
		}

		reader.close();

		return words;
	}

}
