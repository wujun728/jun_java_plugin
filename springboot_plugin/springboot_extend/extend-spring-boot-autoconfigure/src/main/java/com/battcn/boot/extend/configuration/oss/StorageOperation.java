package com.battcn.boot.extend.configuration.oss;

import com.battcn.boot.extend.configuration.oss.domain.DownloadResponse;
import com.battcn.boot.extend.configuration.oss.domain.StorageItem;
import com.battcn.boot.extend.configuration.oss.domain.StorageResponse;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 文件存储
 *
 * @author Levin
 */
public interface StorageOperation {

    AtomicLong FILE_UPLOAD_SUCCESS = new AtomicLong();
    AtomicLong FILE_UPLOAD_FAIL = new AtomicLong();
    AtomicLong FILE_GET_COUNTS = new AtomicLong();
    AtomicLong FILE_DELETE_COUNTS = new AtomicLong();

    /**
     * 文件下载（流式下载）
     *
     * @param fileName 文件名
     * @return BufferedReader BufferedReader
     */

    DownloadResponse download(String fileName);

    /**
     * 文件下载（流式下载）
     *
     * @param bucketName 存储桶名
     * @param fileName   文件名
     * @return BufferedReader BufferedReader
     */

    DownloadResponse download(String bucketName, String fileName);

    /**
     * 文件下载（文件下载到本地）
     *
     * @param bucketName 存储桶名
     * @param fileName   文件名
     * @param file       保存的本地文件路径
     */

    void download(String bucketName, String fileName, File file);

    /**
     * 文件下载（文件下载到本地）
     *
     * @param fileName 文件名
     * @param file     保存的本地文件路径
     */

    void download(String fileName, File file);

    /**
     * 文件列表
     *
     * @return 文件内容
     */

    List<StorageItem> list();

    /**
     * 重命名
     *
     * @param oldName 原始名称
     * @param newName 新名称
     */

    void rename(String oldName, String newName);

    /**
     * 重命名
     *
     * @param bucketName 存储桶名
     * @param oldName    原始名称
     * @param newName    新名称
     */

    void rename(String bucketName, String oldName, String newName);

    /**
     * 上传文件到指定的 bucket
     *
     * @param fileName 文件名字
     * @param content  文件内容
     * @return StorageResponse
     */

    StorageResponse upload(String fileName, byte[] content);

    /**
     * 上传文件到指定的 bucket
     *
     * @param bucketName 存储桶名
     * @param fileName   文件名字
     * @param content    文件内容
     * @return StorageResponse
     */

    StorageResponse upload(String bucketName, String fileName, InputStream content);

    /**
     * 上传文件到指定的 bucket
     *
     * @param bucketName 存储桶名
     * @param fileName   文件名字
     * @param content    文件内容
     * @return StorageResponse
     */

    StorageResponse upload(String bucketName, String fileName, byte[] content);

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */

    void remove(String fileName);

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名
     * @param fileName   文件名
     */

    void remove(String bucketName, String fileName);

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名
     * @param path       文件路径
     */

    void remove(String bucketName, Path path);


}
