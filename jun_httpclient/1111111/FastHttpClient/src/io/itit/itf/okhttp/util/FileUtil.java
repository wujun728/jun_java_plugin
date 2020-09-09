package io.itit.itf.okhttp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**
 * 
 * @author icecooly
 *
 */
public class FileUtil {
	//
	private static final int BUFFER = 2048;

	private FileUtil() {
	}
	//
	public static void unzip(String file, String destFolder) throws IOException {
		BufferedOutputStream dest = null;
		BufferedInputStream is = null;
		ZipEntry entry;
		ZipFile zipfile = new ZipFile(file);
		Enumeration<? extends ZipEntry> e = zipfile.entries();
		while (e.hasMoreElements()) {
			entry = (ZipEntry) e.nextElement();
			if (entry.isDirectory()) {
				File f = new File(destFolder, entry.getName());
				f.mkdirs();
			} else {
				InputStream iis = zipfile.getInputStream(entry);
				is = new BufferedInputStream(iis);
				int count;
				byte data[] = new byte[BUFFER];
				File theFile = new File(destFolder, entry.getName());
				FileOutputStream fos = new FileOutputStream(theFile);
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
				is.close();
				iis.close();
			}
		}
		zipfile.close();
	}

	//
	public static void copyFile(String sourceURL, String destFilePath)
			throws Exception {
		File destFile = new File(destFilePath);
		URL url = new URL(sourceURL);
		FileOutputStream fos = new FileOutputStream(destFile);
		InputStream is = url.openStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = is.read(buffer))) {
			fos.write(buffer, 0, n);
		}
		IOUtil.closeQuietly(is);
		IOUtil.closeQuietly(fos);
	}

	//
	public static File createEmptyDir(String path) {
		File tempDir = new File(path);
		if (tempDir.exists() && !tempDir.isDirectory()) {
			throw new RuntimeException("file:" + path + " already exists.");
		}
		if (tempDir.exists()) {
			if (!deleteDirectory(tempDir)) {
				throw new RuntimeException("can not delete old dir:" + path);
			}
		}
		if (!tempDir.mkdirs()) {
			throw new RuntimeException("can not create dir:" + path);
		}
		return tempDir;
	}

	//
	public static File createTemporaryDirectory(String prefix) {
		File tempDir = null;
		try {
			tempDir = File.createTempFile(prefix, "");
		} catch (IOException e) {
			throw new RuntimeException("could not create temporary file "
					+ prefix, e);
		}
		boolean success = tempDir.delete();
		if (!success) {
			throw new RuntimeException("could not delete temporary file "
					+ tempDir);
		}
		success = tempDir.mkdir();
		if (!success) {
			throw new RuntimeException("could not create temporary directory "
					+ tempDir);
		}
		return tempDir;
	}

	public static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					deleteDirectory(file);
				} else {
					// noinspection ResultOfMethodCallIgnored
					file.delete();
				}
			}
		}
		return (directory.delete());
	}

	/**
	 * get content from file
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(String filePath) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtil.copy(new FileInputStream(filePath), bos);
		return bos.toByteArray();
	}

	/**
	 * get content from file
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String getContent(String filePath) throws IOException {
		return getContent(new File(filePath));
	}

	/**
	 * get content from file
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String getContent(File file) throws IOException {
		return new String(Files.readAllBytes(file.toPath()),
				StandardCharsets.UTF_8);
	}

	//
	public static void saveContent(String content, File file)
			throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file);
				ByteArrayInputStream bis = new ByteArrayInputStream(
						content.getBytes())) {
			IOUtil.copy(bis, fos);
		}
	}

	public static void saveContent(byte bb[], File file) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file);
				ByteArrayInputStream bis = new ByteArrayInputStream(bb)) {
			IOUtil.copy(bis, fos);
		}
	}
}
