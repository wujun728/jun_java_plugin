package com.ic911.htools.commons;

import java.text.SimpleDateFormat;
import java.util.List;

import com.ic911.core.hadoop.hdfs.HDFSFile;


public class ModelToJson {
	public static String getJson(List<HDFSFile> hdfsFileList){
		StringBuffer strBuffer = new StringBuffer("[");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i = 0 , j = hdfsFileList.size(); i < j ; i ++){
			HDFSFile hdfsFile = hdfsFileList.get(i);
			strBuffer.append("{fileName:'");
			strBuffer.append(hdfsFile.getFileName());
			strBuffer.append("', parentDir:'");
			strBuffer.append(hdfsFile.getParentDir());
			strBuffer.append("', filePath:'");
			strBuffer.append(hdfsFile.getFilePath());
			strBuffer.append("', dir:");
			strBuffer.append(hdfsFile.isDir());
			strBuffer.append(", fullFileName:'");
			strBuffer.append(hdfsFile.getFullFileName());
			strBuffer.append("', len:'");
			strBuffer.append(hdfsFile.getLen());
			strBuffer.append("', blockSize:'");
			strBuffer.append(hdfsFile.getBlockSize());
			strBuffer.append("', replication:'");
			strBuffer.append(hdfsFile.getReplication());
			strBuffer.append("', modifyTime:'");
			strBuffer.append(sdf.format(hdfsFile.getModifyTime()));
			strBuffer.append("', accessTime:'");
			strBuffer.append(hdfsFile.getAccessTime());
			strBuffer.append("', permission:'");
			strBuffer.append(hdfsFile.getPermission());
			strBuffer.append("'}");
			if(i < j - 1){
				strBuffer.append(",");
			}
		}
		strBuffer.append("]");
		return strBuffer.toString();
	}
	
	public static String getBeanToJson(HDFSFile hdfsFile){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer strBuffer = new StringBuffer("[");
			strBuffer.append("{fileName:'");
			strBuffer.append(hdfsFile.getFileName());
			strBuffer.append("', parentDir:'");
			strBuffer.append(hdfsFile.getParentDir());
			strBuffer.append("', filePath:'");
			strBuffer.append(hdfsFile.getFilePath());
			strBuffer.append("', dir:");
			strBuffer.append(hdfsFile.isDir());
			strBuffer.append(", fullFileName:'");
			strBuffer.append(hdfsFile.getFullFileName());
			strBuffer.append("', len:'");
			strBuffer.append(hdfsFile.getLen());
			strBuffer.append("', blockSize:'");
			strBuffer.append(hdfsFile.getBlockSize());
			strBuffer.append("', replication:'");
			strBuffer.append(hdfsFile.getReplication());
			strBuffer.append("', modifyTime:'");
			strBuffer.append(sdf.format(hdfsFile.getModifyTime()));
			strBuffer.append("', accessTime:'");
			strBuffer.append(hdfsFile.getAccessTime());
			strBuffer.append("', permission:'");
			strBuffer.append(hdfsFile.getPermission());
			strBuffer.append("'}");
		strBuffer.append("]");
		return strBuffer.toString();
	}
}
