package org.apache.lucene.test.util.tool.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.test.util.tool.lucene.common.ConvertUtils;
import org.apache.lucene.test.util.tool.lucene.common.DocumentType;
import org.apache.lucene.test.util.tool.lucene.common.FileBean;
import org.apache.lucene.test.util.tool.lucene.common.HighlighterUtils;
import org.apache.lucene.test.util.tool.lucene.common.Log;
import org.apache.lucene.test.util.tool.lucene.common.SearchResult;
import org.apache.lucene.test.util.tool.lucene.common.SegmenterUtils;
import org.apache.lucene.test.util.tool.lucene.index.IndexFiles;
import org.apache.lucene.test.util.tool.lucene.search.SearchFiles;

/**
 * 测试.
 * 
 * @author Baishui2004
 */
public class MainTest {

	public static void main(String[] args) {
		ikSegmenter(); // IKAnalyzer分词
		operateIndex(); // 创建/更新/删除索引
		search(); // 检索
		highlighter(); // 高亮显示
	}

	/**
	 * IKAnalyzer分词.
	 */
	public static void ikSegmenter() {
		String sentence = "你好啊，今天天气真好啊，你吃过早饭了吗？";
		System.out.println("\n句子“" + sentence + "”的分词结果：" + SegmenterUtils.ikSegmenter(sentence));
	}

	/**
	 * 创建/更新/删除索引.
	 */
	public static void operateIndex() {
		IndexWriter writer = null;
		try {
			writer = IndexFiles.newIndexWriter();

			List<Document> documents = new ArrayList<Document>();
			for (int i = 0; i < 100; i++) {
				FileBean fileBean = new FileBean();
				fileBean.setId(Integer.toString(i));
				fileBean.setTitle("测试标题" + i);
				fileBean.setType(DocumentType.TEXT);
				fileBean.setContent("你好啊，测试测试哦" + i);
				fileBean.setUri("http://www.thebestofyouth.com/" + i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					//
				}
				fileBean.setTime(new Date());
				documents.add(ConvertUtils.fileToDocument(fileBean));
			}

			// IndexFiles.addIndex(documents, writer);
			IndexFiles.updateIndex(documents, writer);
			// IndexFiles.deleteIndex(documents, writer);
			// writer.deleteAll(); // Delete all documents in the index.

			// Forces merge policy to merge segments until there are <= maxNumSegments.
			// writer.forceMerge(1);

			// commit(): Commits all pending changes (added & deleted documents, segment merges, added indexes, etc.) to the index, 
			//   and syncs all referenced index files, 
			//   such that a reader will see the changes and the index updates will survive an OS or machine crash or power loss.
			writer.commit();
		} catch (IOException e) {
			Log.log.error("New IndexWriter error.", e);
		} finally {
			if (writer != null) {
				try {
					// close(): Commits all changes to an index, waits for pending merges to complete, and closes all associated files.
					writer.close();
				} catch (IOException e) {
					Log.log.error("Close IndexWriter error.", e);
				}
			}
		}
	}

	/**
	 * 检索.
	 */
	public static void search() {
		SearchResult searchResult = SearchFiles.search("content", "你好", 2);
		System.out.println("\n\n检索获得结果总数：" + searchResult.getTopDocs().totalHits);
		List<Document> documents = searchResult.getDocuments();
		System.out.println("\n当前展示结果总数：" + documents.size() + "，列出如下：");
		for (int i = 0; i < documents.size(); i++) {
			Document document = documents.get(i);
			System.out.print("Id: " + document.get("id") + ", Title: " + document.get("title") + ", Type: "
					+ document.get("type") + ", Content: " + document.get("content") + ", Uri: " + document.get("uri")
					+ ", Time: " + new Date(Long.parseLong(document.get("time"))) + "\n");
		}
	}

	/**
	 * 高亮显示.
	 */
	public static void highlighter() {
		String queryString = "你好 好啊";
		String sentence = "你好，今天天气真好啊！";

		System.out.println("\n\n" + HighlighterUtils.highlighter(queryString, sentence));

		List<String> contents = new ArrayList<String>();
		contents.add(sentence);
		contents.add("你好，好啊，今天天气真好啊！");
		System.out.println(HighlighterUtils.highlighter(queryString, contents));
	}

}
