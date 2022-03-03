package org.springrain.system.service;

import java.util.List;

import org.springrain.system.dto.FileDto;

public interface ISystemRootService extends IBaseSpringrainService {

	/**
	 * 列出文件
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */

	List<FileDto> findFileDtosByPath(String path, String rootPath)
			throws Exception;

}
