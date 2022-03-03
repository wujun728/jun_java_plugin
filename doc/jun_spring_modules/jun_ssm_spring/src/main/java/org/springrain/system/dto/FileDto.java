package org.springrain.system.dto;

import java.util.Date;

public class FileDto {

	private String name;
	//相对路径
	private String path;
	//绝对路径
	private String absolutePath;

	// 0:File,1:dic
	private Integer type;
	private FileDto parentFile;
	
	private Date date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public FileDto getParentFile() {
		return parentFile;
	}

	public void setParentFile(FileDto parentFile) {
		this.parentFile = parentFile;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
