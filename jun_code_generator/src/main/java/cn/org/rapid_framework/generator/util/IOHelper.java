package cn.org.rapid_framework.generator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class IOHelper {
	public static Writer NULL_WRITER = new NullWriter();
	
	public static void copy(Reader reader,Writer writer) throws IOException {
		char[] buf = new char[8192];
		int n = 0;
		while((n = reader.read(buf)) != -1) {
			writer.write(buf,0,n);
		}
	}
	
    public static void copy(InputStream in,OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int n = 0;
        while((n = in.read(buf)) != -1) {
            out.write(buf,0,n);
        }
    }
	
    public static List readLines(Reader input) throws IOException {
        BufferedReader reader = new BufferedReader(input);
        List list = new ArrayList();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }
    
	public static String readFile(File file) throws IOException {
		Reader in = new FileReader(file);
		StringWriter out = new StringWriter();
		copy(in,out);
		in.close();
		return out.toString();
	}
	
	public static String readFile(File file,String encoding) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		try {
		    return toString(encoding, inputStream);
		}finally{
		    inputStream.close();
		}
	}

	public static String toString(InputStream inputStream) throws UnsupportedEncodingException, IOException {
		Reader reader = new InputStreamReader(inputStream);
		StringWriter writer = new StringWriter();
		copy(reader,writer);
		return writer.toString();
	}
	
	public static String toString(String encoding, InputStream inputStream) throws UnsupportedEncodingException, IOException {
		Reader reader = new InputStreamReader(inputStream,encoding);
		StringWriter writer = new StringWriter();
		copy(reader,writer);
		return writer.toString();
	}

    public static void saveFile(File file,String content)  {
        saveFile(file,content,null,false);
    }

    public static void saveFile(File file,String content,boolean append)  {
        saveFile(file,content,null,append);
    }
    
    public static void saveFile(File file,String content,String encoding)  {
        saveFile(file,content,encoding,false);
    }
    
	public static void saveFile(File file,String content,String encoding,boolean append)  {
		try {
		FileOutputStream output = new FileOutputStream(file,append);
		Writer writer = StringHelper.isBlank(encoding) ? new OutputStreamWriter(output) : new OutputStreamWriter(output,encoding);
		writer.write(content);
		writer.close();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	private static class NullWriter extends Writer {
		public void close() throws IOException {
		}
		public void flush() throws IOException {
		}
		public void write(char[] cbuf, int off, int len) throws IOException {
		}
	}

    public static void copyAndClose(InputStream in,OutputStream out) throws IOException {
        try {
            copy(in,out);
        }finally {
            close(in,out);
        }
    }

    public static void close(InputStream in, OutputStream out) {
        try { if(in != null) in.close();}catch(Exception e){};
        try { if(out != null) out.close();}catch(Exception e){};
    }
}
