package com.jun.web.biz.test;

// ZipCompress.java
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class ZipCompress {
	public static void main(String[] args) throws IOException {
		FileOutputStream f = new FileOutputStream("test.zip"); // ��������ļ���
		CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32()); // ����������֤��
		ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(csum)); // ����zip��
		out.setComment("A test of Java Zipping");
		for (int i = 0; i < args.length; i++) {
			System.out.println("Writing file " + args[i]);
			BufferedReader in = new BufferedReader(new FileReader(args[i]));
			out.putNextEntry(new ZipEntry(args[i])); // ����ѹ��ʵ��
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close(); // �ر��ļ������ͷ���Դ
		}
		out.close();
		System.out.println("Checksum: " + csum.getChecksum().getValue()); // ���Ч����
		// ��ѹ��
		System.out.println("Reading file");
		FileInputStream fi = new FileInputStream("test.zip");
		CheckedInputStream csumi = new CheckedInputStream(fi, new Adler32());
		ZipInputStream in2 = new ZipInputStream(new BufferedInputStream(csumi));
		ZipEntry ze;
		while ((ze = in2.getNextEntry()) != null) {
			System.out.println("Reading file " + ze);
			int x;
			while ((x = in2.read()) != -1)
				System.out.write(x);
		}
		System.out.println("Checksum: " + csumi.getChecksum().getValue());
		in2.close();
		// �����ѹ����ĸ����ļ���
		ZipFile zf = new ZipFile("test.zip");
		Enumeration e = zf.entries();
		while (e.hasMoreElements()) {
			ZipEntry ze2 = (ZipEntry) e.nextElement();
			System.out.println("File: " + ze2);
		}
	}
}