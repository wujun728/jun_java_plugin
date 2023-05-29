package com.jun.plugin.util4j.bytesStream;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputStreamUtils {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public static boolean compare(InputStream is1, InputStream is2, long num_bytes) throws IOException {
		int b;
		for (long num_read = 0; num_read < num_bytes; ++num_read) {
			if ((b = is1.read()) != is2.read())
				return false;
			else if (b < 0) // both EOF
				break;
		}
		return true;
	}

	public static boolean compare(InputStream is1, InputStream is2) throws IOException {
		int b = 0;
		while (b >= 0)
			if ((b = is1.read()) != is2.read())
				return false;
		return true;
	}

	public static byte[] getBytes(InputStream is, int max_len) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(max_len);
		for (int i = 0, b = is.read(); b >= 0 && i < max_len; b = is.read(), ++i)
			baos.write(b);
		return baos.toByteArray();
	}

	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int b = is.read(); b >= 0; b = is.read())
			baos.write(b);
		return baos.toByteArray();
	}

	public static String getContentsAsString(InputStream is, String enc)
			throws IOException, UnsupportedEncodingException {
		return new String(getBytes(is), enc);
	}

	public static String getContentsAsString(InputStream is) throws IOException {
		try {
			return getContentsAsString(is, System.getProperty("file.encoding", "8859_1"));
		} catch (UnsupportedEncodingException e) {
			throw new InternalError("You have no default character encoding, and " + "iso-8859-1 is unsupported?!?!");
		}
	}

	public static String getContentsAsString(InputStream is, int max_len, String enc)
			throws IOException, UnsupportedEncodingException {
		return new String(getBytes(is, max_len), enc);
	}

	public static String getContentsAsString(InputStream is, int max_len) throws IOException {
		try {
			return getContentsAsString(is, max_len, System.getProperty("file.encoding", "8859_1"));
		} catch (UnsupportedEncodingException e) {
			throw new InternalError("You have no default character encoding, and " + "iso-8859-1 is unsupported?!?!");
		}
	}

	public static void skipFully(InputStream is, long num_bytes) throws EOFException, IOException {
		long num_skipped = 0;
		while (num_skipped < num_bytes) {
			long just_skipped = is.skip(num_bytes - num_skipped);
			if (just_skipped > 0)
				num_skipped += just_skipped;
			else {
				if (is.read() < 0)
					throw new EOFException("Skipped only " + num_skipped + " bytes to end of file.");
				else
					++num_skipped;
			}
		}
	}
}