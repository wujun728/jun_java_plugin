package com.jun.plugin.dfs.core.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jun.plugin.dfs.core.entity.FileInfoEntity;

/**
 * 文件信息mapper
 */
@Mapper
public interface FileInfoMapper {

    /**
     * 添加文件信息
     *
     * @param fileInfo
     * @return 返回id
     */
    void addFileInfo(@Param("fileInfo") FileInfoEntity fileInfo);

    /**
     * 按id更新文件信息
     *
     * @param fileInfo
     */
    void updateFileInfoById(@Param("fileInfo") FileInfoEntity fileInfo);

    /**
     * 按fileId更新文件信息
     *
     * @param fileInfo
     */
    void updateFileInfoByFileId(@Param("fileInfo") FileInfoEntity fileInfo);

    /**
     * 获取文件长度
     *
     * @param fileId
     * @return
     */
    long getFileLengthByFileId(@Param("fileId") String fileId);

    /**
     * 获取文件名
     *
     * @param fileId
     * @return
     */
    String getFileNameByFileId(@Param("fileId") String fileId);
}
