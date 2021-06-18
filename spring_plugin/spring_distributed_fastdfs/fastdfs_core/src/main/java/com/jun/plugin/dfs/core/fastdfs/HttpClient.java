package com.jun.plugin.dfs.core.fastdfs;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jun.plugin.dfs.base.ErrorCodeEnum;
import com.jun.plugin.dfs.base.spring.SpringContextHolder;
import com.jun.plugin.dfs.listener.InitializeConfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.*;

@Slf4j
public class HttpClient {

    /**
     * 大于10M分批上传
     */
    private static final int NEED_BATCH_UPLOAD_SIZE = 1024 * 1024 * 10;
    /**
     * 上传缓存
     */
    private static final int UPLOAD_BUFFER_SIZE = 1024 * 1024 * 10;
    /**
     * 下载缓存，这里只有http下载，所有将缓存设小，所以也不适应大于10M的文件下载
     */
    private static final int DOWNLOAD_BUFFER_SIZE = 128 * 1024;
    private static HttpClient instance = new HttpClient();
    private InitializeConfig config = SpringContextHolder.getBean(InitializeConfig.class);
    private int uploadThreadSize = config.getUpload();

    private TrackerClient trackerClient;

    private String trackerServers = null;

    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("DFS-UPLOAD-TASK-THREAD-%d").build();

    /**
     * 处理上传任务的线程池
     */
    private ExecutorService uploadExecutorService = new ThreadPoolExecutor(uploadThreadSize, uploadThreadSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    private HttpClient() {
        try {
            Properties props = new Properties();
            props.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS, config.getTrackers());
            props.setProperty(ClientGlobal.PROP_KEY_CHARSET, config.getCharset());
            props.setProperty(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, config.getConnectTimeoutInSeconds());
            props.setProperty(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, config.getNetworkTimeoutInSeconds());
            ClientGlobal.initByProperties(props);
        } catch (Exception e) {
            log.error("init fastdfs error", e);
            System.exit(1);
        }
        trackerClient = new TrackerClient();
    }

    public static HttpClient getInstance() {
        return instance;
    }

    public String getTrackersConfig() {
        if (StringUtils.isEmpty(trackerServers)) {
            try {
                String trackerServersConf = config.getTrackers();
                trackerServers = trackerServersConf.trim();
            } catch (Exception e) {
                log.error("init error", e);
                System.exit(1);
            }
        }
        return trackerServers;
    }

    /**
     * 分配服务端的链接
     *
     * @return
     * @throws MyException
     */
    private StorageClient1 assignResourse() throws MyException {
        return assignResourse(null);
    }

    /**
     * 分配服务端的链接
     *
     * @param fileId
     * @return
     * @throws MyException
     */
    private StorageClient1 assignResourse(String fileId) throws MyException {
        StorageClient1 client = new StorageClient1();
        try {
            if (client.getTrackerServer() == null
                    || (client.getTrackerServer() != null && client.getTrackerServer().getSocket().isClosed())) {
                TrackerServer trackerServer = trackerClient.getConnection();
                if (trackerServer == null) {
                    throw new MyException("can't get trackerServer!");
                }
                client.setTrackerServer(trackerServer);
            }

            if (client.getStorageServer() == null
                    || (client.getStorageServer() != null && client.getStorageServer().getSocket().isClosed())) {
                StorageServer storageServer;
                if (fileId != null && fileId.length() > 0) {
                    int pos = fileId.indexOf(StorageClient1.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR);
                    storageServer = trackerClient.getFetchStorage(client.getTrackerServer(), fileId.substring(0, pos),
                            fileId.substring(pos + 1));
                } else {
                    storageServer = trackerClient.getStoreStorage(client.getTrackerServer(), null);
                }
                if (storageServer == null) {
                    releaseResourse(client);
                    throw new MyException("can't get storageServer!");
                }
                client.setStorageServer(storageServer);
            }

            return client;

        } catch (Exception e) {
            throw new MyException("connect to server error!");
        }
    }

    /**
     * 释放服务端的链接
     */
    private void releaseResourse(StorageClient1 client) {
        try {
            if (client != null) {
                log.debug("release ：client-> " + client + ",storageServer-> " + client.getStorageServer() + ",trackerServer-> " + client.getTrackerServer());
                if (client.getStorageServer() != null) {
                    client.getStorageServer().close();
                }
                if (client.getTrackerServer() != null) {
                    client.getTrackerServer().close();
                }
                client.setTrackerServer(null);
                client.setStorageServer(null);
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * 上传文件
     *
     * @param file 需要上传的文件
     * @return 返回fileId
     * @throws FileNotFoundException
     * @throws MyException
     */
    public String uploadFile(File file) throws FileNotFoundException, MyException {
        return uploadFile(file, null, null);
    }

    /**
     * 上传文件
     *
     * @param file      需要上传的文件
     * @param groupName 指定文件上传的组，可以为null
     * @return 返回fileId
     * @throws FileNotFoundException
     * @throws MyException
     */
    public String uploadFile(File file, String groupName) throws FileNotFoundException, MyException {
        return uploadFile(file, groupName, null);
    }

    /**
     * 上传文件
     *
     * @param file      需要上传的文件
     * @param groupName 指定文件上传的组（卷），为空表示不上指定
     * @param metaList  文件额外属性
     * @return 返回fileId
     * @throws FileNotFoundException
     * @throws MyException
     */
    public String uploadFile(File file, String groupName, NameValuePair[] metaList) throws FileNotFoundException, MyException {
        String fileName = file.getName();
        String extName = FilenameUtils.getExtension(fileName);
        return uploadFile(new FileInputStream(file), groupName, extName, metaList);
    }

    public String uploadFile(InputStream in, String extName) throws MyException {
        return uploadFile(in, null, extName, null);
    }

    public String uploadFile(InputStream in, String groupName, String extName) throws MyException {
        return uploadFile(in, groupName, extName, null);
    }

    public String uploadFile(InputStream in, String extName, NameValuePair[] metaList) throws MyException {
        return uploadFile(in, null, extName, metaList);
    }

    /**
     * 上传文件
     *
     * @param in        需要上传的输入流
     * @param groupName 指定文件上传的组（卷），为空表示不指定
     * @param extName   文件扩展名(不能包含'.')
     * @param metaList  文件额外属性
     * @return 返回fileId
     * @throws MyException
     */
    public String uploadFile(InputStream in, String groupName, String extName, NameValuePair[] metaList) throws MyException {
        if (in == null) {
            throw new MyException("inputStream is null!");
        }
        StorageClient1 client = assignResourse();
        String fileId = null;
        try {
            long length = in.available();
            boolean isSetGroup = false;
            if (StringUtils.isNotEmpty(groupName)) {
                // 上传到指定的组（卷）
                isSetGroup = true;
            }
            // 超过指定大小就进行分批上传
            if (length > NEED_BATCH_UPLOAD_SIZE) {
                if (!(in instanceof BufferedInputStream)) {
                    in = new BufferedInputStream(in);
                }
                int readFlag;
                byte[] bytes = new byte[UPLOAD_BUFFER_SIZE];
                // 已上传字节数
                int uploadCount = 0;
                while ((readFlag = in.read(bytes)) > 0) {
                    // 需要新上传文件
                    if (fileId == null) {
                        if (isSetGroup) {
                            fileId = client.upload_appender_file1(groupName, bytes, extName, metaList);
                        } else {
                            fileId = client.upload_appender_file1(bytes, extName, metaList);
                        }
                    } else {
                        client.append_file1(fileId, bytes, 0, readFlag);
                    }
                    uploadCount += readFlag;
                    if (log.isDebugEnabled()) {
                        log.debug("uploaded:" + uploadCount);
                    }
                }
            } else {
                byte[] fileBytes = new byte[(int) length];
                in.read(fileBytes);
                if (isSetGroup) {
                    fileId = client.upload_appender_file1(groupName, fileBytes, extName, metaList);
                } else {
                    fileId = client.upload_appender_file1(fileBytes, extName, metaList);
                }
            }
        } catch (IOException e) {
            log.error("upload file read file error!", e);
        } catch (MyException e) {
            log.error(e.getMessage(), e);
            throw new MyException(e.getMessage());
        } catch (Exception e) {
            log.error("upload file client error!", e);
            throw new MyException(e.getMessage());
        } finally {
            releaseResourse(client);
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileId;
    }

    /**
     * http下载文件
     *
     * @param fileId      上传成功后的fileId
     * @param response
     * @param direct      是否直接显示，true表示可以直接显示,false表示可以下载保存成文件(默认)
     * @param fileName    下载后的文件名
     * @param fileExtName 文件扩展名
     * @throws MyException
     */
    public ErrorCodeEnum httpDownloadFile(String fileId, HttpServletResponse response, boolean direct, String fileName, String fileExtName) throws MyException {
        StorageClient1 client = assignResourse(fileId);
        long fileLength = -1L;
        try {
            FileInfo fileInfo = client.get_file_info1(fileId);
            if (fileInfo != null) {
                fileLength = fileInfo.getFileSize();
            }
            if (fileLength < 0) {
                return ErrorCodeEnum.RESOURCE_NOT_FOUND;
            } else {
                response.setContentLengthLong(fileLength);
                if (!direct) {
                    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
                }
            }
            int bufferSize = DOWNLOAD_BUFFER_SIZE;
            if (fileLength > 10 * 1024 * 1024) {
                // 文件大于10M将缓存设为1M
                bufferSize = 1024 * 1024;
            }
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream(), bufferSize);
            int offset = 0;
            while (fileLength > 0) {
                if (fileLength < bufferSize) {
                    bufferSize = (int) fileLength;
                }
                byte[] bytes = client.download_file1(fileId, offset, bufferSize);
                fileLength -= bytes.length;
                offset += bytes.length;
                out.write(bytes);
                if (log.isDebugEnabled()) {
                    log.debug("fileLeftBytes:" + fileLength);
                }
            }
            out.flush();
        } catch (MyException e) {
            log.error(e.getMessage(), e);
            throw new MyException(e.getMessage());
        } catch (Exception e) {
            log.error("download file error! fileId:" + fileId, e);
            throw new MyException("download file error!");
        } finally {
            releaseResourse(client);
        }
        return ErrorCodeEnum.OK;
    }

    /**
     * 删除文件
     *
     * @param fileId
     * @return 0成功, 2文件不存在, 其他值失败
     */
    public int deleteFile(String fileId) {
        StorageClient1 client = null;
        try {
            client = assignResourse(fileId);
            return client.delete_file1(fileId);
        } catch (Exception e) {
            log.error("delete file error! fileId:" + fileId, e);
            return -1;
        } finally {
            releaseResourse(client);
        }
    }

    /**
     * 提交一个上传任务
     * 异步执行
     *
     * @param fileInfoId 文件信息对应的id,上传成功后用于更新文件信息
     * @param groupName  上传到的组名 与fastdfs组对应
     * @param in         输入流
     * @param extName    文件扩展名
     */
    public void executeUploadTask(Integer fileInfoId, InputStream in, String groupName, String extName) {
        DFSUploadFileTask task = new DFSUploadFileTask(fileInfoId, in, groupName, extName);
        uploadExecutorService.execute(task);
    }
}
