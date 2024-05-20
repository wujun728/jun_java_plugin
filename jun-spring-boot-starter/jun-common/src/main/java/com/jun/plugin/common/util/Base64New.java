package com.jun.plugin.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;


public class Base64New {

	public static void main(String[] args) throws IOException {
			//String conten = "H4sIAAAAAAAAAO1 ";
			String conten = FileUtils.readFileToString(new File("D:\\Documents\\Desktop\\svop_user.txt"));
			//decode(conten);
			File file = new File("D:\\Documents\\Desktop\\svop_resource_scen_inst.7z");
			FileUtils.writeByteArrayToFile(file, decodeV2(conten));
	}
	
	public static String compress(String content,String charset) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(content.getBytes(StandardCharsets.UTF_8));
		if(gzip!=null) {
			gzip.close();
		}
//		BASE64Encoder
		return new String(Base64Utils.encode(out.toByteArray()));
//		return new String(java.util.Base64.getEncoder().encode(out.toByteArray()));
	}
	
	public static String decode(String content) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		BASE64Decoder base64 = new BASE64Decoder();
		try {
			byte [] bytes = Base64Utils.decodeFromString(content);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			GZIPInputStream gzip = new GZIPInputStream(byteArrayInputStream);
			byte [] b = new byte[128];
			int n;
			while((n=gzip.read(b))>=0) {
				out.write(b,0,n);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		System.err.println(out.toString());
		return out.toString();
	}
	

	public static byte[] decodeV2(String content) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		BASE64Decoder base64 = new BASE64Decoder();
		try {
			byte [] bytes = Base64Utils.decodeFromString(content);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			GZIPInputStream gzip = new GZIPInputStream(byteArrayInputStream);
			byte [] b = new byte[128];
			int n;
			while((n=gzip.read(b))>=0) {
				out.write(b,0,n);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		//System.err.println(out.toString());
		return out.toByteArray();
	}

}
