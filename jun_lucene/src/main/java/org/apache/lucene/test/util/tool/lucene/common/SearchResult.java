package org.apache.lucene.test.util.tool.lucene.common;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.TopDocs;

/**
 * 检索结果.
 * 
 * @author Baishui2004
 */
public class SearchResult {

	private TopDocs topDocs;

	private List<Document> documents;

	public TopDocs getTopDocs() {
		return topDocs;
	}

	public void setTopDocs(TopDocs topDocs) {
		this.topDocs = topDocs;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

}