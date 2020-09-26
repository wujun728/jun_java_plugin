package org.apache.lucene.test.test.tool.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexFiles {

	public static void index(String docsPath, String indexPath, Analyzer analyzer) {
		boolean create = true;

		File docDir = new File(docsPath);
		try {
			Date start = new Date();
			System.out.println("Indexing to directory '" + indexPath + "'...");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

			if (create) {
				// Create a new index in the directory, removing any previously indexed documents
				iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			} else {
				// Add new documents to an existing index
				iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			}

			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDocs(writer, docDir);

			writer.close();
			System.out.println(new Date().getTime() - start.getTime() + " total milliseconds");
		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
	}

	private static void indexDocs(IndexWriter writer, File file) throws IOException {
		if (file.canRead())
			if (file.isDirectory()) {
				String[] files = file.list();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException fnfe) {
					return;
				}

				try {
					Document doc = new Document();

					Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
					doc.add(pathField);

					LongField modifiedField = new LongField("modified", file.lastModified(), Field.Store.YES);
					doc.add(modifiedField);

					doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));

					if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
						System.out.println("adding " + file);
						writer.addDocument(doc);
					} else {
						System.out.println("updating " + file);
						writer.updateDocument(new Term("path", file.getPath()), doc);
					}
				} finally {
					fis.close();
				}
			}
	}
}