package com.jun.plugin.utils.file;


/**
 * 文件存储服务
 */
public class StorageService extends Storage {

	private String file_path;
	private String read_path;
	
	public StorageService(String rootPath, String ext){
		this.file_path = rootPath + 
				"uploads" + java.io.File.separator + 
				ext + java.io.File.separator;
		this.read_path = "/uploads/" + ext + "/";
	}
	
	@Override
	public String getBasePath() {
		return file_path;
	}
	
	public String getReadPath() {
		return read_path;
	}
}
