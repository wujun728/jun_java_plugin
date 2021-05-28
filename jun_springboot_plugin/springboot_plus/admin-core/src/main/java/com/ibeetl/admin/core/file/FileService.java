package com.ibeetl.admin.core.file;

import java.io.OutputStream;
import java.util.List;
/**
 * 文件持久化，默认为文件系统，可以扩展到fastfds等
 * @author Wujun
 *
 */
public interface FileService {
	/**
	 * 得到一个临时文件操作
	 * @param name
	 * @return
	 */
	public FileItem createFileTemp(String name);
	
	/**
	 * 创建一个持久的文档
	 * @param name
	 * @param bizType
	 * @param bizId
	 * @param userId
	 * @param orgId
	 * @param tags
	 * @return
	 */
	 public FileItem createFileItem(String name,String bizType,String bizId,Long userId,Long orgId,String fileBatchId,List<FileTag> tags);

	
	public FileItem loadFileItemByPath(String path);
	public FileItem getFileItemById(Long id);
	public FileItem getFileItemById(Long id,String batchFileId);
	
	public List<FileItem> queryByUserId(Long userId,List<FileTag> tags);
	public List<FileItem> queryByBiz(String bizType,String bizId); 
	public List<FileItem> queryByBatchId(String fileBatchId); 
	/**
	 * 删除某个文件
	 * @param id
	 * @param fileBatchId，用于验证
	 */
	public void removeFile(Long id,String fileBatchId);
	
	/**
	 * 完善附件信息
	 * @param fileBatchId
	 * @param bizType
	 * @param bizId
	 */
	public void updateFile(String fileBatchId,String bizType,String bizId);
	
	

    
	
}
