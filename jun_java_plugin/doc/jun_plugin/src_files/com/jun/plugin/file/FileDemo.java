/**
 * 
 */
package com.jun.plugin.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

class ReciveRunnable implements Runnable {
	private PipedInputStream input = null;

	public ReciveRunnable() {
		this.input = new PipedInputStream();
	}

	public PipedInputStream getInput() {
		return this.input;
	}

	public void run() {
		byte[] b = new byte[1000];
		int len = 0;
		try {
			len = this.input.read(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("���ܵ�����Ϊ " + (new String(b, 0, len)));
	}
}

class SendRunnable implements Runnable {
	private PipedOutputStream out = null;

	public SendRunnable() {
		out = new PipedOutputStream();
	}

	public PipedOutputStream getOut() {
		return this.out;
	}

	public void run() {
		String message = "hello , Rollen and Zhanghao";
		try {
			out.write(message.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 *
 */
public class FileDemo {
	
	@Test
	public void File() throws Exception {
		// BufferReaderDemo(args);
		// SystemGetProperty(args);
		// FileOutput(args);
		// DataInputStreamDemo(args);
		// DataOutputStreamDemo(args);
		// DeleteFile(args);
		// FileAppend(args);
		// FileCopy(args);
		// FileList(args);
		// FileOut(args);
		// ListContent(args);
		// NewDir(args);
		// NewFile(args);
		// ObjectInputStreamDemo(args);
		// ObjectOutputStreamDemo(args);
		// OutputRedirect(args);
		// Piped(args);
		// PushBackInputStreamDemo(args);
		// RandomAccess(args);
		// ReadEof(args);
		// ReaderFile(args);
		// ScannerDemo(args);
		// SequenceInputStreamDemo(args);
		// SystemIn(args);
		// SystemOut(args);
		// WriterFile(args);
//		ZipFileDemo(args);
//		ZipFileDemo2(args);
//		ZipFileDemo3(args);
//		ZipOutputStreamDemo(args);
//		ZipOutputStreamDemo1(args);
	}

	/* *
	 * 
	 * @param srcfile 文件名数组
	 * 
	 * @param zipfile 压缩后文件
	 */
	public static void zipFiles(java.io.File[] srcfile, java.io.File zipfile) {
		byte[] buf = new byte[1024 * 1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				FileInputStream in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestZipFiles2() throws Exception {
//		FileZip.zipFiles(srcfile, zipfile);
	}
	
	@Test
	public void TestZipFiles() throws Exception {
		File file = new File("d:" + File.separator + "test.zip");
		if (!file.exists()) {
			System.err.println(" file not found");
			return;
		}
		File outFile = null;
		ZipFile zipFile = new ZipFile(file);
		ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
		ZipEntry entry = null;
		InputStream input = null;
		OutputStream out = null;
		while ((entry = zipInput.getNextEntry()) != null) {
			System.out.println("开始解压缩" + entry.getName() + "文件。。。");
			outFile = new File("d:" + File.separator + entry.getName());
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdir();
			}
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			input = zipFile.getInputStream(entry);
			out = new FileOutputStream(outFile);
			int temp = 0;
			while ((temp = input.read()) != -1) {
				// System.out.println(temp);
				out.write(temp);
			}
			input.close();
			out.close();
		}
		System.out.println("Done!");
	}
	
	
	public static void ZipOutputStreamDemo1(String[] args) throws IOException {
		// Ҫ��ѹ�����ļ���
		File file = new File("d:" + File.separator + "temp");
		File zipFile = new File("d:" + File.separator + "zipFile.zip");
		InputStream input = null;
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		zipOut.setComment("hello");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; ++i) {
				input = new FileInputStream(files[i]);
				zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
				int temp = 0;
				while ((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
				input.close();
			}
		}
		zipOut.close();
	}

	public static void ZipOutputStreamDemo(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "hello.text");
		File zipFile = new File("d:" + File.separator + "hello1.zip");
		InputStream input = new FileInputStream(file);
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		zipOut.putNextEntry(new ZipEntry(file.getName()));
		// ����ע��
		zipOut.setComment("hello");
		int temp = 0;
		while ((temp = input.read()) != -1) {
			zipOut.write(temp);
		}
		input.close();
		zipOut.close();
	}

	public static void ZipFileDemo3(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "zipFile.zip");
		File outFile = null;
		ZipFile zipFile = new ZipFile(file);
		ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
		ZipEntry entry = null;
		InputStream input = null;
		OutputStream output = null;
		while ((entry = zipInput.getNextEntry()) != null) {
			System.out.println("��ѹ��" + entry.getName() + "�ļ�");
			outFile = new File("d:" + File.separator + entry.getName());
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdir();
			}
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			input = zipFile.getInputStream(entry);
			output = new FileOutputStream(outFile);
			int temp = 0;
			while ((temp = input.read()) != -1) {
				output.write(temp);
			}
			input.close();
			output.close();
		}
	}

	public static void ZipFileDemo2(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "hello.zip");
		File outFile = new File("d:" + File.separator + "unZipFile.txt");
		ZipFile zipFile = new ZipFile(file);
		ZipEntry entry = zipFile.getEntry("hello.txt");
		InputStream input = zipFile.getInputStream(entry);
		OutputStream output = new FileOutputStream(outFile);
		int temp = 0;
		while ((temp = input.read()) != -1) {
			output.write(temp);
		}
		input.close();
		output.close();
	}

	public static void ZipFileDemo(String... args) throws IOException {
		File file = new File("d:" + File.separator + "hello.zip");
		ZipFile zipFile = new ZipFile(file);
		System.out.println("ѹ���ļ������Ϊ��" + zipFile.getName());
	}

	public static void WriterFile(String[] args) throws IOException {
		File f = new File("d:" + File.separator + "test.java");
		Writer out = new FileWriter(f, true);
		String str = "�ź�";
		out.write(str);
		out.close();
	}

	public static void SystemOut(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File f = new File("D:" + File.separator + "test.txt");
		OutputStream out = System.out;
		out.write("�ź�".getBytes());
		out.close();
	}

	public static void SystemIn(String[] args) {
		File file = new File("d:" + File.separator + "hello.txt");
		if (!file.exists()) {
			return;
		} else {
			try {
				System.setIn(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			byte[] bytes = new byte[1024];
			int len = 0;
			try {
				len = System.in.read(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("���������Ϊ��" + new String(bytes, 0, len));
		}
	}

	public static void SequenceInputStreamDemo(String[] args) throws IOException {
		File file1 = new File("d:" + File.separator + "test.java");
		File file2 = new File("d:" + File.separator + "hello.txt");
		File file3 = new File("d:" + File.separator + "demo.txt");
		InputStream input1 = new FileInputStream(file1);
		InputStream input2 = new FileInputStream(file2);
		OutputStream output = new FileOutputStream(file3);
		// �ϲ���
		SequenceInputStream sis = new SequenceInputStream(input1, input2);
		int temp = 0;
		while ((temp = sis.read()) != -1) {
			output.write(temp);
		}
		input1.close();
		input2.close();
		output.close();
		sis.close();
	}

	public static void ScannerDemo(String[] args) {
		/*
		 * Scanner sc = new Scanner(System.in); int num = sc.nextInt(); System.out.println(num); float f = sc.nextFloat(); System.out.println(f);
		 */
		File f1 = new File("D:" + File.separator + "test.java");
		Scanner sc1 = null;
		try {
			sc1 = new Scanner(f1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str = sc1.next();

		System.out.println("��ȡ��������:" + str);
	}

	public static void ReadFile(String[] args) throws IOException {
		File f = new File("D:" + File.separator + "hello.txt");
		InputStream in = new FileInputStream(f);
		byte[] b = new byte[1024];
		// byte[] b = new byte[(int)f.length()]; //��ʡ�ռ� Ԥ���ļ���С�Ŀռ�
		// for (int i = 0; i < b.length; i++) {
		// b[i]=(byte)in.read();
		// }
		int len = in.read(b);
		in.close();
		System.out.println(new String(b, 0, len));
	}

	public static void ReaderFile(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File f = new File("D:" + File.separator + "test.java");
		Reader read = new FileReader(f);
		char[] ch = new char[100];
		int len = read.read(ch);
		read.close();
		/*
		 * int temp=0; int count=0; while((temp=read.read())!=(-1)){ ch[count++]=(char)temp; }
		 */
		System.out.println(new String(ch, 0, len));
	}

	public static void ReadEof(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		InputStream in = new FileInputStream(f);
		byte[] b = new byte[1024];
		int count = 0;
		int temp = 0;
		while ((temp = in.read()) != (-1)) {
			b[count++] = (byte) temp;
		}
		in.close();
		System.out.println(new String(b));
	}

	public static void RandomAccess(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		RandomAccessFile demo = new RandomAccessFile(f, "rw");
		demo.writeBytes("asdsad");
		demo.writeInt(12);
		demo.writeBoolean(true);
		demo.writeChar('A');
		demo.writeFloat(1.21f);
		demo.writeDouble(12.123);
		demo.close();
	}

	public static void PushBackInputStreamDemo(String[] args) throws IOException {
		String str = "hello,rollenholt";
		PushbackInputStream push = null;
		ByteArrayInputStream bat = null;
		bat = new ByteArrayInputStream(str.getBytes());
		push = new PushbackInputStream(bat);
		int temp = 0;
		while ((temp = push.read()) != -1) {
			if (temp == ',') {
				push.unread(temp);
				temp = push.read();
				System.out.print("(����" + (char) temp + ") ");
			} else {
				System.out.print((char) temp);
			}
		}
	}

	public static void Piped(String[] args) throws IOException {
		SendRunnable send = new SendRunnable();
		ReciveRunnable recive = new ReciveRunnable();
		try {
			// �ܵ�����
			send.getOut().connect(recive.getInput());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread(send).start();
		new Thread(recive).start();
	}

	public static void OutputRedirect(String[] args) {
		File f = new File("D:" + File.separator + "test.java");
		System.out.println("Hello");
		try {
			System.setOut(new PrintStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("============Test.java============");
	}

	public static void ObjectOutputStreamDemo(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "hello.txt");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(new Object());
		oos.close();
	}

	public static void ObjectInputStreamDemo(String[] args) throws Exception {
		File file = new File("d:" + File.separator + "hello.txt");
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
		Object obj = input.readObject();
		input.close();
		System.out.println(obj);
	}

	public static void NewFile(String[] args) {

		File f = new File("D:\\text.java");
		try {
			f.createNewFile();
			System.out.println(File.separator);// windows linux
			System.out.println(File.pathSeparator);
			System.out.println(File.pathSeparatorChar);
			System.out.println(File.separatorChar);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void NewDir(String[] args) {

		File f = new File("D:" + File.separator + "hello");
		try {
			f.mkdir();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ListContent(String[] args) {
		File f = new File("D:" + File.separator);
		print(f);

	}

	public static void print(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				File[] s = f.listFiles();
				if (s != null) {
					for (int i = 0; i < s.length; i++) {
						print(s[i]);
					}
				}
			} else {
				System.out.println(f);
			}
		}
	}

	public static void FileOut(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File f = new File("D:" + File.separator + "hello.txt");
		try {
			OutputStream fos = new FileOutputStream(f);
			String s = "���";
			byte[] b = s.getBytes();
			fos.write(b);
			/*
			 * for(int i =0 ;i<b.length;i++){ fos.write(b); }
			 */
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void FileList(String[] args) {
		String FileName = "d:" + File.separator;
		File f = new File(FileName);
		if (f.isDirectory()) {
			System.out.println("+++++++++++++++++++Yes+++++++++++++++++++");
		} else {
			System.out.println("+++++++++++++++++++No++++++++++++++++++++");
		}
		String[] s = f.list(); // ����ַ�����
		File[] s1 = f.listFiles();// �ļ�����
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i] + "-------" + s1[i]);
		}
	}

	public static void FileCopy(String[] args) throws IOException {

		File f1 = new File("D:" + File.separator + "test.java");
		InputStream in = new FileInputStream(f1);
		// byte[] b = new byte[(int)f1.length()];

		File f2 = new File("D:" + File.separator + "test1.java");
		OutputStream os = new FileOutputStream(f2);
		int temp = 0;
		while ((temp = in.read()) != (-1)) {
			os.write(temp);
		}
		os.close();
		in.close();
	}

	public static void FileAppend(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File f = new File("D:" + File.separator + "hello.txt");

		// BufferedWriter fos = new BufferedWriter(new FileWriter("D:"+File.separator+"hello.txt", true));
		OutputStream fos = new FileOutputStream(f, true);
		fos.write(("商户名称：西安银信博锐\r\n").getBytes());
		fos.write(("交易流水号：123456\r\n").getBytes());
		fos.write(("时间：2013-02-22 12:33:35\r\n").getBytes());
		fos.write(("交易信息：交易成功\r\n").getBytes());
		fos.write(("卡号：123456\r\n").getBytes());
		fos.write(("购买量：\r\n").getBytes());
		fos.write(("冷水：5吨，3.3元\r\n").getBytes());
		fos.write(("热水：10吨，36.5元\r\n").getBytes());
		fos.write(("电量：100度，100元\r\n").getBytes());
		fos.close();
		/*
		 * fos.write(("商户名称：西安银信博锐\r\n")); fos.write(("交易流水号：123456\r\n")); fos.write(("时间：2013-02-22 12:33:35\r\n")); fos.write(("交易信息：交易成功\r\n")); fos.write(("卡号：123456\r\n"));
		 * fos.write(("购买量：\r\n")); fos.write(("冷水：5吨，3.3元\r\n")); fos.write(("热水：10吨，36.5元\r\n")); fos.write(("电量：100度，100元\r\n"));
		 */
		fos.close();
	}

	public static void DeleteFile(String[] args) {
		File f = new File("d:" + File.separator + "text.java");
		if (f.exists()) {
			f.delete();
		} else {
			System.out.println("δ�ҵ��ļ�");
		}
	}

	public static void DataOutputStreamDemo(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "hello.txt");
		char[] ch = { 'A', 'B', 'C' };
		DataOutputStream out = null;
		out = new DataOutputStream(new FileOutputStream(file));
		for (char temp : ch) {
			out.writeChar(temp);
		}
		out.close();
	}

	public static void BufferReaderDemo(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// OutputStreramWriter
		// InputStreamReader
		try {
			System.out.println(" " + br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static void SystemGetProperty(String[] args) {
		System.out.println("CharSetDemo:" + System.getProperty("file.encoding"));
		Collection collection = System.getProperties().values();
		for (Iterator iterator = collection.iterator(); iterator.hasNext(); iterator.next()) {
			System.err.println("info=" + iterator.next().toString());
		}
	}

	public static void FileOutput(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "hello.txt");
		OutputStream out = new FileOutputStream(file);
		byte[] bytes = "AAAAAA 俊 C ".getBytes("UTF-8");
		out.write(bytes);
		out.close();
	}

	public static void DataInputStreamDemo(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "hello.txt");
		DataInputStream input = new DataInputStream(new FileInputStream(file));
		char[] ch = new char[10];
		int count = 0;
		char temp;
		while ((temp = input.readChar()) != 'C') {
			ch[count++] = temp;
		}
		System.out.println(ch);
	}

}