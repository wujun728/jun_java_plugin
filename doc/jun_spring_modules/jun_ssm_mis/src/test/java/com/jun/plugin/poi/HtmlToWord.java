package com.jun.plugin.poi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class HtmlToWord {

	public static boolean writeWordFile(String body) {

		boolean w = false;
		String path = "D:/";
		try {
			if (!"".equals(path)) {
				// 检查目录是否存在
				File fileDir = new File(path);
				if (fileDir.exists()) {
					// 生成临时文件名称
					String fileName = "a1122.doc";
					String content = "<html>" + "<head>导出</head>"
							+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + "<body>" + body
							+ "</body>" + "</html>";
					byte b[] = content.getBytes("UTF-8");
					// byte b[] = content.getBytes("UTF-8");
					ByteArrayInputStream bais = new ByteArrayInputStream(b);
					POIFSFileSystem poifs = new POIFSFileSystem();
					DirectoryEntry directory = poifs.getRoot();
					DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
					FileOutputStream ostream = new FileOutputStream(path + fileName);
					poifs.writeFilesystem(ostream);
					bais.close();
					ostream.close();

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return w;
	}

	public static void main(String[] args) {
		String body = "";
		writeWordFile(body);
	}

}
