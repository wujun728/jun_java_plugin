package com.jun.plugin.zxing;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpClientHelper {
	static {
		System.setProperty("java.net.useSystemProxies", "true");
	}

	public enum REQUEST_METHOD {
		GET, POST, MULTIPART_FORM_DATA
	}

	public static InputStream get(String urlToRequest,
			Map<String, String> parameters) {
		return invokeRequest(urlToRequest, REQUEST_METHOD.GET, parameters);
	}

	public static InputStream post(String urlToRequest,
			Map<String, String> parameters) {
		return invokeRequest(urlToRequest, REQUEST_METHOD.POST, parameters);
	}

	public static String requestBodyString(String urlToRequest,
			Map<String, String> parameters) {
		return convertStreamToString(
				invokeRequest(urlToRequest, REQUEST_METHOD.GET, parameters),
				"utf-8");
	}

	public static String postBodyString(String urlToRequest,
			Map<String, String> parameters) {
		return convertStreamToString(
				invokeRequest(urlToRequest, REQUEST_METHOD.POST, parameters),
				"utf-8");
	}

	public static InputStream invokeRequest(String urlToRequest,
			REQUEST_METHOD method, Map<String, String> parameters) {
		if (urlToRequest == null || urlToRequest.trim().length() == 0)
			return null;
		if (parameters == null) {
			parameters = new HashMap<String, String>();
		}
		String urlParameters = constructURLString(parameters);

		if ((method == null || REQUEST_METHOD.GET.equals(method))
				&& urlParameters.length() < 900 && urlParameters.length() > 0) {
			if (urlToRequest.indexOf("?") == -1)
				urlToRequest += "?" + urlParameters;
			else
				urlToRequest += "&" + urlParameters;
			method = REQUEST_METHOD.GET;
		}
		InputStream in = null;
		try {
			URL url = new URL(urlToRequest);
			//System.out.println("Reuqest URL:::" + urlToRequest);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			OutputStream out;

			byte[] buff;

			if (REQUEST_METHOD.GET.equals(method)) {
				con.setRequestMethod("GET");
				con.setDoOutput(false);
				con.setDoInput(true);
				con.connect();
				in = con.getInputStream();
			} else if (REQUEST_METHOD.MULTIPART_FORM_DATA.equals(method)) {
				con.setRequestMethod("POST");
				con.setDoOutput(true);
				con.setDoInput(true);
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);

				String boundary = MultipartFormOutputStream.createBoundary();
				// The following request properties were recommended by the MPF
				// class
				con.setRequestProperty("Accept", "*/*");
				con.setRequestProperty("Content-Type",
						MultipartFormOutputStream.getContentType(boundary));
				con.setRequestProperty("Connection", "Keep-Alive");
				con.setRequestProperty("Cache-Control", "no-cache");
				con.connect();
				MultipartFormOutputStream mpout = new MultipartFormOutputStream(
						con.getOutputStream(), boundary);
				// First, write out the parameters
				for (Map.Entry<String, String> entry : parameters.entrySet()) {
					mpout.writeField(entry.getKey(), entry.getValue());
				}
				// Now write the image
				String mimeType = "image/jpg";
				String fullFilePath = parameters.get("file_path");
				if (fullFilePath.endsWith("gif")) {
					mimeType = "image/gif";
				} else if (fullFilePath.endsWith("png")) {
					mimeType = "image/png";
				} else if (fullFilePath.endsWith("tiff")) {
					mimeType = "image/tiff";
				}
				mpout.writeFile("uploadFile", mimeType, new File(fullFilePath));
				mpout.flush();
				mpout.close();
				// All done, get the InputStream
				in = con.getInputStream();
			} else {
				con.setRequestMethod("POST");
				con.setDoOutput(true);
				con.setDoInput(true);
				con.connect();
				out = con.getOutputStream();
				buff = urlParameters.getBytes("UTF8");
				out.write(buff);
				out.flush();
				out.close();
				in = con.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;

	}

	/**
	 * Expand the map of parameters to construct a URL string
	 * 
	 * @param parameters
	 * @return
	 */
	private static String constructURLString(Map<String, String> parameters) {
		StringBuffer url = new StringBuffer();

		boolean first = true;

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			try {
				// type checking, we can get nulls in here
				if ((entry.getValue() == null) || (entry.getKey() == null)) {
					continue;
				}
				if (entry.getValue().length() == 0) {
					continue;
				}

				if (first) {
					first = false;
				} else {
					url.append("&");
				}
				url.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
				// No Fixing this, really
				throw new Error("Unsupported Encoding Exception", ex);
			}
		}

		return url.toString();
	}

	public static String convertStreamToString(InputStream is, String encoding) {
		try {
			InputStreamReader input = new InputStreamReader(is, "UTF-8");
			final int CHARS_PER_PAGE = 5000; // counting spaces
			final char[] buffer = new char[CHARS_PER_PAGE];
			StringBuilder output = new StringBuilder(CHARS_PER_PAGE);
			try {
				for (int read = input.read(buffer, 0, buffer.length); read != -1; read = input
						.read(buffer, 0, buffer.length)) {
					output.append(buffer, 0, read);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return output.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}

}

class MultipartFormOutputStream {

	/**
	 * The line end characters.
	 */
	private static final String NEWLINE = "\r\n";

	/**
	 * The boundary prefix.
	 */
	private static final String PREFIX = "--";

	/**
	 * The output stream to write to.
	 */
	private DataOutputStream out = null;

	/**
	 * The multipart boundary string.
	 */
	private String boundary = null;

	/**
	 * Creates a new <code>MultiPartFormOutputStream</code> object using the
	 * specified output stream and boundary. The boundary is required to be
	 * created before using this method, as described in the description for the
	 * <code>getContentType(String)</code> method. The boundary is only checked
	 * for <code>null</code> or empty string, but it is recommended to be at
	 * least 6 characters. (Or use the static createBoundary() method to create
	 * one.)
	 * 
	 * @param os
	 *            the output stream
	 * @param boundary
	 *            the boundary
	 * @see #createBoundary()
	 * @see #getContentType(String)
	 */
	MultipartFormOutputStream(OutputStream os, String boundary) {
		if (os == null) {
			throw new IllegalArgumentException("Output stream is required.");
		}
		if (boundary == null || boundary.length() == 0) {
			throw new IllegalArgumentException("Boundary stream is required.");
		}
		this.out = new DataOutputStream(os);
		this.boundary = boundary;
	}

	/**
	 * Writes an boolean field value.
	 * 
	 * @param name
	 *            the field name (required)
	 * @param value
	 *            the field value
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeField(String name, boolean value)
			throws java.io.IOException {
		writeField(name, new Boolean(value).toString());
	}

	/**
	 * Writes an double field value.
	 * 
	 * @param name
	 *            the field name (required)
	 * @param value
	 *            the field value
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeField(String name, double value)
			throws java.io.IOException {
		writeField(name, Double.toString(value));
	}

	/**
	 * Writes an float field value.
	 * 
	 * @param name
	 *            the field name (required)
	 * @param value
	 *            the field value
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeField(String name, float value) throws java.io.IOException {
		writeField(name, Float.toString(value));
	}

	/**
	 * Writes an long field value.
	 * 
	 * @param name
	 *            the field name (required)
	 * @param value
	 *            the field value
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeField(String name, long value) throws java.io.IOException {
		writeField(name, Long.toString(value));
	}

	/**
	 * Writes an int field value.
	 * 
	 * @param name
	 *            the field name (required)
	 * @param value
	 *            the field value
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeField(String name, int value) throws java.io.IOException {
		writeField(name, Integer.toString(value));
	}

	/**
	 * Writes an short field value.
	 * 
	 * @param name
	 *            the field name (required)
	 * @param value
	 *            the field value
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeField(String name, short value) throws java.io.IOException {
		writeField(name, Short.toString(value));
	}

	/**
	 * Writes an char field value.
	 * 
	 * @param name
	 *            the field name (required)
	 * @param value
	 *            the field value
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeField(String name, char value) throws java.io.IOException {
		writeField(name, new Character(value).toString());
	}

	/**
	 * Writes an string field value. If the value is null, an empty string is
	 * sent ("").
	 * 
	 * @param name
	 *            the field name (required)
	 * @param value
	 *            the field value
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeField(String name, String value)
			throws java.io.IOException {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null or empty.");
		}
		if (value == null) {
			value = "";
		}
		/*
		 * --boundary\r\n Content-Disposition: form-data; name="<fieldName>"\r\n
		 * \r\n <value>\r\n
		 */
		// write boundary
		out.writeBytes(PREFIX);
		out.writeBytes(boundary);
		out.writeBytes(NEWLINE);
		// write content header
		out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"");
		out.writeBytes(NEWLINE);
		out.writeBytes(NEWLINE);
		// write content
		out.writeBytes(value);
		out.writeBytes(NEWLINE);
		out.flush();
	}

	/**
	 * Writes a file's contents. If the file is null, does not exists, or is a
	 * directory, a <code>java.lang.IllegalArgumentException</code> will be
	 * thrown.
	 * 
	 * @param name
	 *            the field name
	 * @param mimeType
	 *            the file content type (optional, recommended)
	 * @param file
	 *            the file (the file must exist)
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeFile(String name, String mimeType, File file)
			throws java.io.IOException {
		if (file == null) {
			throw new IllegalArgumentException("File cannot be null.");
		}
		if (!file.exists()) {
			throw new IllegalArgumentException("File does not exist.");
		}
		if (file.isDirectory()) {
			throw new IllegalArgumentException("File cannot be a directory.");
		}
		writeFile(name, mimeType, file.getCanonicalPath(), new FileInputStream(
				file));
	}

	/**
	 * Writes a input stream's contents. If the input stream is null, a
	 * <code>java.lang.IllegalArgumentException</code> will be thrown.
	 * 
	 * @param name
	 *            the field name
	 * @param mimeType
	 *            the file content type (optional, recommended)
	 * @param fileName
	 *            the file name (required)
	 * @param is
	 *            the input stream
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void writeFile(String name, String mimeType, String fileName,
			InputStream is) throws java.io.IOException {
		if (is == null) {
			throw new IllegalArgumentException("Input stream cannot be null.");
		}
		if (fileName == null || fileName.length() == 0) {
			throw new IllegalArgumentException(
					"File name cannot be null or empty.");
		}
		/*
		 * --boundary\r\n Content-Disposition: form-data; name="<fieldName>";
		 * filename="<filename>"\r\n Content-Type: <mime-type>\r\n \r\n
		 * <file-data>\r\n
		 */
		// write boundary
		out.writeBytes(PREFIX);
		out.writeBytes(boundary);
		out.writeBytes(NEWLINE);
		// write content header
		out.writeBytes("Content-Disposition: form-data; name=\"" + name
				+ "\"; filename=\"" + fileName + "\"");
		out.writeBytes(NEWLINE);
		if (mimeType != null) {
			out.writeBytes("Content-Type: " + mimeType);
			out.writeBytes(NEWLINE);
		}
		out.writeBytes(NEWLINE);
		// write content
		byte[] data = new byte[1024];
		int r = 0;
		while ((r = is.read(data, 0, data.length)) != -1) {
			out.write(data, 0, r);
		}
		// close input stream, but ignore any possible exception for it
		try {
			is.close();
		} catch (Exception e) {
		}
		out.writeBytes(NEWLINE);
		out.flush();
	}

	/**
	 * Writes the given bytes. The bytes are assumed to be the contents of a
	 * file, and will be sent as such. If the data is null, a
	 * <code>java.lang.IllegalArgumentException</code> will be thrown.
	 * 
	 * @param name
	 *            the field name
	 * @param mimeType
	 *            the file content type (optional, recommended)
	 * @param fileName
	 *            the file name (required)
	 * @param data
	 *            the file data
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	void writeFile(String name, String mimeType, String fileName, byte[] data)
			throws java.io.IOException {
		if (data == null) {
			throw new IllegalArgumentException("Data cannot be null.");
		}
		if (fileName == null || fileName.length() == 0) {
			throw new IllegalArgumentException(
					"File name cannot be null or empty.");
		}
		/*
		 * --boundary\r\n Content-Disposition: form-data; name="<fieldName>";
		 * filename="<filename>"\r\n Content-Type: <mime-type>\r\n \r\n
		 * <file-data>\r\n
		 */
		// write boundary
		out.writeBytes(PREFIX);
		out.writeBytes(boundary);
		out.writeBytes(NEWLINE);
		// write content header
		out.writeBytes("Content-Disposition: form-data; name=\"" + name
				+ "\"; filename=\"" + fileName + "\"");
		out.writeBytes(NEWLINE);
		if (mimeType != null) {
			out.writeBytes("Content-Type: " + mimeType);
			out.writeBytes(NEWLINE);
		}
		out.writeBytes(NEWLINE);
		// write content
		out.write(data, 0, data.length);
		out.writeBytes(NEWLINE);
		out.flush();
	}

	/**
	 * Flushes the stream. Actually, this method does nothing, as the only write
	 * methods are highly specialized and automatically flush.
	 */
	public void flush() {
		// out.flush();
	}

	/**
	 * Closes the stream. <br />
	 * <br />
	 * <b>NOTE:</b> This method <b>MUST</b> be called to finalize the multipart
	 * stream.
	 * 
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public void close() throws java.io.IOException {
		// write final boundary
		out.writeBytes(PREFIX);
		out.writeBytes(boundary);
		out.writeBytes(PREFIX);
		out.writeBytes(NEWLINE);
		out.flush();
		out.close();
	}

	/**
	 * Gets the multipart boundary string being used by this stream.
	 * 
	 * @return the boundary
	 */
	public String getBoundary() {
		return this.boundary;
	}

	/**
	 * Creates a new <code>java.net.URLConnection</code> object from the
	 * specified <code>java.net.URL</code>. This is a convenience method which
	 * will set the <code>doInput</code>, <code>doOutput</code>,
	 * <code>useCaches</code> and <code>defaultUseCaches</code> fields to the
	 * appropriate settings in the correct order.
	 * 
	 * @return a <code>java.net.URLConnection</code> object for the URL
	 * @throws java.io.IOException
	 *             on input/output errors
	 */
	public static URLConnection createConnection(URL url)
			throws java.io.IOException {
		URLConnection urlConn = url.openConnection();
		if (urlConn instanceof HttpURLConnection) {
			HttpURLConnection httpConn = (HttpURLConnection) urlConn;
			httpConn.setRequestMethod("POST");
		}
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);
		urlConn.setDefaultUseCaches(false);
		return urlConn;
	}

	/**
	 * Creates a multipart boundary string by concatenating 20 hyphens (-) and
	 * the hexadecimal (base-16) representation of the current time in
	 * milliseconds.
	 * 
	 * @return a multipart boundary string
	 * @see #getContentType(String)
	 */
	public static String createBoundary() {
		return "--------------------"
				+ Long.toString(System.currentTimeMillis(), 16);
	}

	/**
	 * Gets the content type string suitable for the
	 * <code>java.net.URLConnection</code> which includes the multipart boundary
	 * string. <br />
	 * <br />
	 * This method is static because, due to the nature of the
	 * <code>java.net.URLConnection</code> class, once the output stream for the
	 * connection is acquired, it's too late to set the content type (or any
	 * other request parameter). So one has to create a multipart boundary
	 * string first before using this class, such as with the
	 * <code>createBoundary()</code> method.
	 * 
	 * @param boundary
	 *            the boundary string
	 * @return the content type string
	 * @see #createBoundary()
	 */
	public static String getContentType(String boundary) {
		return "multipart/form-data; boundary=" + boundary;
	}
}
