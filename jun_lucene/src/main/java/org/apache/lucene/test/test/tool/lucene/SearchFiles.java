package org.apache.lucene.test.test.tool.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchFiles {

	public static void search(String index, String queryString, Analyzer analyzer) throws Exception {

		String field = "contents";
		String queries = null;
		boolean raw = false;
		int hitsPerPage = 10;

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);

		QueryParser parser = new QueryParser( field, analyzer);
		Query query = parser.parse(queryString);
		System.out.println("Searching for: " + query.toString(field));

		doPagingSearch(searcher, query, hitsPerPage, raw, (queries == null) && (queryString == null));

		reader.close();
	}

	private static void doPagingSearch(IndexSearcher searcher, Query query, int hitsPerPage, boolean raw,
			boolean interactive) throws IOException {

		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);
		while (true) {
			if (end > hits.length) {
				System.out.println("Only results 1 - " + hits.length + " of " + numTotalHits
						+ " total matching documents collected.");
				hits = searcher.search(query, numTotalHits).scoreDocs;
			}

			end = Math.min(hits.length, start + hitsPerPage);

			for (int i = start; i < end; i++) {
				if (raw) {
					System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
				} else {
					Document doc = searcher.doc(hits[i].doc);
					String path = doc.get("path");
					if (path != null) {
						System.out.println((i + 1) + ". " + path);
						String title = doc.get("title");
						if (title != null)
							System.out.println("   Title: " + doc.get("title"));
					} else {
						System.out.println((i + 1) + ". " + "No path for this document");
					}
				}
			}

			if ((!interactive) || (end == 0)) {
				break;
			}
		}
	}
}