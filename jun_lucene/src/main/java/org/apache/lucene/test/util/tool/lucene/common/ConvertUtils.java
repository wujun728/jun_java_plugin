package org.apache.lucene.test.util.tool.lucene.common;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

/**
 * 转换工具类.
 * 
 * @author Baishui2004
 */
public class ConvertUtils {

	/**
	 * 转lucene文档.
	 * 
	 * @param file 简单文档Bean
	 * @return lucene文档
	 */
	public static Document fileToDocument(FileBean file) {
		Document document = new Document();
		document.add(new StringField("id", file.getId(), Field.Store.YES));
		document.add(new TextField("title", file.getTitle(), Field.Store.YES));
		document.add(new IntField("type", file.getType(), Field.Store.YES));
		document.add(new TextField("content", file.getContent(), Field.Store.YES));
		document.add(new StringField("uri", file.getUri(), Field.Store.YES));
		document.add(new LongField("time", file.getTime().getTime(), Field.Store.YES));
		return document;
	}

}
