package org.apache.lucene.test.lucence;

import org.apache.lucene.document.Document;

/**
 * lucence document to 自定对象
 * 自定对象  to document
 * @author DF
 *
 */
public interface LucenceConvert<T> {
	public  T docToObject(Document doc);
	public  Document objectToDoc(T t);
}
