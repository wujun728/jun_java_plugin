package org.iherus.codegen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * FileUtils
 */
public class FileUtils {

	private FileUtils() {

	}

	public static byte[] readFileToByteArray(File file) throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return toByteArray(in, file.length());
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static byte[] toByteArray(InputStream input, long size) throws IOException {
		if (size > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
		}
		return toByteArray(input, (int) size);
	}

	public static byte[] toByteArray(InputStream input, int size) throws IOException {
		if (size < 0) {
			throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
		}
		if (size == 0) {
			return new byte[0];
		}
		byte[] data = new byte[size];
		int offset = 0;
		int readed;
		while (offset < size && (readed = input.read(data, offset, size - offset)) != -1) {
			offset += readed;
		}
		if (offset != size) {
			throw new IOException("Unexpected readed size. current: " + offset + ", excepted: " + size);
		}
		return data;
	}

	public static void writeByteArrayToFile(byte[] data, File file) throws IOException {
		writeByteArrayToFile(data, file, false);
	}

	public static void writeByteArrayToFile(byte[] data, File file, boolean append) throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file, append);
			out.write(data);
			out.close(); // don't swallow close Exception if copy completes
							// normally
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}
		return new FileInputStream(file);
	}

	public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file + "' cannot be written to");
			}
		} else {
			File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					throw new IOException("Directory '" + parent + "' could not be created");
				}
			}
		}
		return new FileOutputStream(file, append);
	}

	public static FileOutputStream openOutputStream(File file) throws IOException {
		return openOutputStream(file, false);
	}

}
