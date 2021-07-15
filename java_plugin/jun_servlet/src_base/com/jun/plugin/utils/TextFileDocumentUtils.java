package com.jun.plugin.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextFileDocumentUtils {
 
	public static String readFileContent(String path) {
		BufferedReader reader = null;
		StringBuffer content = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			for (String line = null; (line = reader.readLine()) != null;) {
				content.append(line).append("\n");
			}

			return content.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
