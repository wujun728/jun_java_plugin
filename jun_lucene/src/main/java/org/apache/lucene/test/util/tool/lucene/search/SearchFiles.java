package org.apache.lucene.test.util.tool.lucene.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.test.util.tool.lucene.common.Config;
import org.apache.lucene.test.util.tool.lucene.common.Log;
import org.apache.lucene.test.util.tool.lucene.common.SearchResult;

/**
 * 检索文档(索引).
 * 
 * @author Baishui2004
 */
public class SearchFiles {

	public static SearchResult search(String field, String queryString, int hitsPerPage) {
		SearchResult searchResult = new SearchResult();

		IndexReader reader = null;
		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(Config.IndexDirectory)));
			IndexSearcher searcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser( field, Config.ANALYZER);
			Query query = parser.parse(queryString);

			// Collect enough docs to show 5 pages
			TopDocs topDocs = searcher.search(query, 5 * hitsPerPage);
			searchResult.setTopDocs(topDocs);

			List<Document> documents = new ArrayList<Document>();
			ScoreDoc[] hits = topDocs.scoreDocs;
			for (int i = 0; i < hits.length; i++) {
				documents.add(searcher.doc(hits[i].doc));
			}
			searchResult.setDocuments(documents);
		} catch (IOException e) {
			Log.log.error("IndexSearcher IO error.", e);
		} catch (ParseException e) {
			Log.log.error("QueryParser parse error.", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.log.error("Close IndexReader error.", e);
				}
			}
		}

		return searchResult;
	}

}
