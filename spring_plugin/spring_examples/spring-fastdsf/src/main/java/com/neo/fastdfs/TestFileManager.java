package com.neo.fastdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageServer;


public class TestFileManager {
	
	public static void main(String[] args) {
		TestFileManager tfm = new TestFileManager();
		try {
			tfm.upload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upload() throws Exception {
		File content = new File("d://test.jpg");

		FileInputStream fis = new FileInputStream(content);
		byte[] file_buff = null;
		if (fis != null) {
			int len = fis.available();
			file_buff = new byte[len];
			fis.read(file_buff);
		}

		FastDFSFile file = new FastDFSFile("520", file_buff, "jpg");

		//String[] fileAbsolutePath = FileManager.upload(file);
		//System.out.println(fileAbsolutePath);
		fis.close();
	}

	public void getFile() throws Exception {
		FileInfo file = FileManager.getFile("group1", "M00/00/00/wKgMlVb4v-uASLLDAABTJ-HUpUQ818.jpg");
		String sourceIpAddr = file.getSourceIpAddr();
		long size = file.getFileSize();
		System.out.println("ip:" + sourceIpAddr + ",size:" + size);
	}
	
	public void downFile() throws Exception {
		InputStream ins = FileManager.downFile("group1", "M00/00/00/wKgMlVb4v-uASLLDAABTJ-HUpUQ818.jpg");
		System.out.println("file:" + ins);
		File file = new File("E:\\test1.jpg");
		this.inputstreamtofile(ins, file);
	}
	
	public void delFile() throws Exception {
		FileManager.deleteFile("group1", "M00/00/00/wKgMlVbiLr6AdJiHAAHuVaxqjTg560.jpg");
	}

	public void getStorageServer() throws Exception {
		StorageServer[] ss = FileManager.getStoreStorages("group1");

		for (int k = 0; k < ss.length; k++) {
			System.err.println(k + 1 + ". " + ss[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + ss[k].getInetSocketAddress().getPort());
		}
	}

	public void getFetchStorages() throws Exception {
		ServerInfo[] servers = FileManager.getFetchStorages("group1", "M00/00/00/wKgMlVbg6syAZpFXAAHuVaxqjTg222.jpg");

		for (int k = 0; k < servers.length; k++) {
			System.err.println(k + 1 + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());
		}
	}
	
	public static void inputstreamtofile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}