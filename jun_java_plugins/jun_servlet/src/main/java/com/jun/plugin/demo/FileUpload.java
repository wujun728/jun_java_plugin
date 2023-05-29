package com.jun.plugin.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.io.*;

public class FileUpload {
	// ��������������洢�ϴ��ļ������֣�·����
	private String savePath, filepath, filename, contentType;
	// �������û��ڱ?��������ݵ�����/ֵ��
	private Dictionary fields;

	// ����ļ�����
	public String getFilename() {
		return filename;
	}
	// ����ϴ��ļ���·��
	public String getFilepath() {
		return filepath;
	}
	// �趨�ϴ��ļ���·�������û���趨�ͱ����ڷ�����Ĭ��Ŀ¼
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	// �õ��ļ�����
	public String getContentType() {
		return contentType;
	}

	public String getFieldValue(String fieldName) {
		if (fields == null || fieldName == null)
			return null;
		return (String) fields.get(fieldName);
	}

	private void setFilename(String s) {
		if (s == null)
			return;
		int pos = s.indexOf("filename=\"");
		if (pos != -1) {
			filepath = s.substring(pos + 10, s.length() - 1);
			pos = filepath.lastIndexOf("\\");
			if (pos != -1)
				filename = filepath.substring(pos + 1);
			else
				filename = filepath;
		}
	}

	private void setContentType(String s) {
		if (s == null)
			return;
		int pos = s.indexOf(": ");
		if (pos != -1)
			contentType = s.substring(pos + 2, s.length());
	}

	public void doUpload(HttpServletRequest request) throws IOException {
		ServletInputStream in = request.getInputStream();
		byte[] line = new byte[128];
		int i = in.readLine(line, 0, 128);
		if (i < 3)
			return;
		int boundaryLength = i - 2;
		String boundary = new String(line, 0, boundaryLength); //-2�������ַ� 
		fields = new Hashtable();

		while (i != -1) {
			String newLine = new String(line, 0, i);
			if (newLine
				.startsWith("Content-Disposition: form-data; name=\"")) {
				if (newLine.indexOf("filename=\"") != -1) {
					setFilename(new String(line, 0, i - 2));
					if (filename == null)
						return;

					//�ļ����� 
					i = in.readLine(line, 0, 128);
					setContentType(new String(line, 0, i - 2));
					i = in.readLine(line, 0, 128);

					//���� 
					i = in.readLine(line, 0, 128);
					newLine = new String(line, 0, i);
					PrintWriter pw =
						new PrintWriter(
							new BufferedWriter(
								new FileWriter(
									(savePath == null ? "" : savePath)
										+ filename)));
					while (i != -1 && !newLine.startsWith(boundary)) {
						// �ļ����ݵ����һ�а����ַ� 

						// ������Ǳ����鵱ǰ���Ƿ����� 

						// ��һ�� 
						i = in.readLine(line, 0, 128);
						if ((i == boundaryLength + 2
							|| i == boundaryLength + 4)
							&& (new String(line, 0, i).startsWith(boundary)))
							pw.print(
								newLine.substring(0, newLine.length() - 2));
						else
							pw.print(newLine);
						newLine = new String(line, 0, i);
					}
					pw.close();
				} else {
					// ��ͨ�?����Ԫ�� 
					// ��ȡ����Ԫ������ 
					int pos = newLine.indexOf("name=\"");
					String fieldName =
						newLine.substring(pos + 6, newLine.length() - 3);
					i = in.readLine(line, 0, 128);
					i = in.readLine(line, 0, 128);
					newLine = new String(line, 0, i);
					StringBuffer fieldValue = new StringBuffer(128);
					while (i != -1 && !newLine.startsWith(boundary)) {
						// ���һ�а����ַ� 
						// ������Ǳ����鵱ǰ���Ƿ������һ�� 
						i = in.readLine(line, 0, 128);
						if ((i == boundaryLength + 2
							|| i == boundaryLength + 4)
							&& (new String(line, 0, i).startsWith(boundary)))
							fieldValue.append(
								newLine.substring(0, newLine.length() - 2));
						else
							fieldValue.append(newLine);
						newLine = new String(line, 0, i);
					}
					fields.put(fieldName, fieldValue.toString());
				}
			}
			i = in.readLine(line, 0, 128);
		}
	}
}