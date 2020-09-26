package org.apache.lucene.test.util.tool.lucene.index;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.test.util.tool.lucene.common.Config;

/**
 * 创建/更新/删除(文档)索引.
 * 
 * @author Baishui2004
 */
public class IndexFiles {

	/**
	 * 创建IndexWriter.
	 * 
	 * @return IndexWriter
	 */
	public static IndexWriter newIndexWriter() throws IOException {
		Directory dir = FSDirectory.open(Paths.get(Config.IndexDirectory));
		IndexWriterConfig iwc = new IndexWriterConfig( Config.ANALYZER);
		// Add new documents to an existing index
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(dir, iwc);
		return writer;
	}

	/**
	 * 创建单个文档的索引.
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void addIndex(Document document, IndexWriter writer) throws IOException {
		writer.addDocument(document);
	}

	/**
	 * 创建多个文档的索引.
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void addIndex(Document[] documents, IndexWriter writer) throws IOException {
		addIndex(Arrays.asList(documents), writer);
	}

	/**
	 * 创建多个文档的索引.
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void addIndex(List<Document> documents, IndexWriter writer) throws IOException {
		writer.addDocuments(documents);
	}

	/**
	 * 更新单个文档的索引[如果没有对应id的文档则创建].
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void updateIndex(Document document, IndexWriter writer) throws IOException {
		writer.updateDocument(new Term(Config.IDNAME, document.get(Config.IDNAME)), document);
	}

	/**
	 * 更新单个多个的索引[如果没有对应id的文档则创建].
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void updateIndex(Document[] documents, IndexWriter writer) throws IOException {
		for (Document document : documents) {
			updateIndex(document, writer);
		}
	}

	/**
	 * 更新单个多个的索引[如果没有对应id的文档则创建].
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void updateIndex(List<Document> documents, IndexWriter writer) throws IOException {
		for (Document document : documents) {
			updateIndex(document, writer);
		}
	}

	/**
	 * 删除单个文档的索引.
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void deleteIndex(Document document, IndexWriter writer) throws IOException {
		writer.deleteDocuments(new Term(Config.IDNAME, document.get(Config.IDNAME)));
	}

	/**
	 * 删除多个文档的索引.
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void deleteIndex(Document[] documents, IndexWriter writer) throws IOException {
		Term[] terms = new Term[documents.length];
		for (int i = 0; i < documents.length; i++) {
			terms[i] = new Term(Config.IDNAME, documents[i].get(Config.IDNAME));
		}
		writer.deleteDocuments(terms);
	}

	/**
	 * 删除多个文档的索引.
	 * 
	 * @param document Lucene文档
	 * @throws IOException
	 */
	public static void deleteIndex(List<Document> documents, IndexWriter writer) throws IOException {
		Term[] terms = new Term[documents.size()];
		for (int i = 0; i < documents.size(); i++) {
			terms[i] = new Term(Config.IDNAME, documents.get(i).get(Config.IDNAME));
		}
		writer.deleteDocuments(terms);
	}

}
