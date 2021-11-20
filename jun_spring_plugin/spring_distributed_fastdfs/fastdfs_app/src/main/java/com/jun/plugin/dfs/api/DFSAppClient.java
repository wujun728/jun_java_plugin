package com.jun.plugin.dfs.api;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jun.plugin.dfs.api.response.ServerData;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.*;

public class DFSAppClient {

    private static final String V1_SERVER = "dfs/v1/server";
    private static final String V1_SUPLOAD = "dfs/v1/sUpload";
    private static final String V1_EUPLOAD = "dfs/v1/eUpload";
    private static final String V1_DELETE = "dfs/v1/delete";
    private static final String V1_DOWNLOAD = "dfs/v1/download";
    /**
     * 上传缓存
     */
    private static int UPLOAD_BUFFER_SIZE = 1024 * 1024;
    /**
     * 下载缓存
     */
    private static int DOWNLOAD_BUFFER_SIZE = 1024 * 1024;
    /**
     * 需要分批上传的大小
     */
    private static int NEED_BATCH_UPLOAD_SIZE = UPLOAD_BUFFER_SIZE;
    /**
     * 线程池大小
     */
    private static int CORE_THREAD_SIZE = 5;
    private static String GET_SERVER_URL = null;
    private static String START_UPLOAD_URL = null;
    private static String END_UPLOAD_URL = null;
    private static String DELETE_URL = null;
    private static String DOWNLOAD_URL = null;

    private static DFSAppClient instance = new DFSAppClient();

    private TrackerClient trackerClient = null;

    private APIConfigure config = null;

    private String clientAppKey;
    private String clientGroupName;

    private ExecutorService executorService = null;

    public static DFSAppClient instance() {
        return instance;
    }

    /**
     * 初始化设置参数(只需要初始化一次)
     *
     * @param config API参数配置
     */
    public void initAPIConfigure(APIConfigure config) throws MyException {
        try {
            this.config = config;

            if (this.config.getUploadBufferSize() > 0) {
                UPLOAD_BUFFER_SIZE = this.config.getUploadBufferSize();
                NEED_BATCH_UPLOAD_SIZE = UPLOAD_BUFFER_SIZE;
            }

            if (this.config.getDownloadBufferSize() > 0) {
                DOWNLOAD_BUFFER_SIZE = this.config.getDownloadBufferSize();
            }

            if (this.config.getCoreThreadSize() > 0) {
                CORE_THREAD_SIZE = this.config.getCoreThreadSize();
            }

            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("DFS-HTTP-TASK-THREAD-%d").build();
            executorService = new ThreadPoolExecutor(CORE_THREAD_SIZE, CORE_THREAD_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

            if (this.config.getHttpServerUrl() == null || this.config.getHttpServerUrl().trim().length() == 0) {
                throw new MyException("init server error, http server url is empty!");
            }

            String baseServerUrl = this.config.getHttpServerUrl();
            if (!baseServerUrl.endsWith("/")) {
                baseServerUrl += "/";
            }

            GET_SERVER_URL = baseServerUrl + V1_SERVER;
            START_UPLOAD_URL = baseServerUrl + V1_SUPLOAD;
            END_UPLOAD_URL = baseServerUrl + V1_EUPLOAD;
            DELETE_URL = baseServerUrl + V1_DELETE;
            DOWNLOAD_URL = baseServerUrl + V1_DOWNLOAD;

            clientAppKey = this.config.getAppKey();
            ServerData serverData = APIHttpUtils.getServerInfo(GET_SERVER_URL, clientAppKey);
            clientGroupName = serverData.getBody().getGroupName();
            if (StringUtils.isEmpty(clientAppKey) || StringUtils.isEmpty(clientGroupName)) {
                throw new MyException("init server error, can't get appKey or groupName from http server!");
            }
            Properties props = new Properties();
            props.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS, serverData.getBody().getTrackerServers());
            ClientGlobal.initByProperties(props);
        } catch (Exception e) {
            throw new MyException("init server error, " + e.getMessage());
        }
        trackerClient = new TrackerClient();
    }

    /**
     * 分配服务端的链接
     *
     * @return
     * @throws MyException
     */
    private StorageClient1 assignResource() throws MyException {
        return assignResourseByFileId(null);
    }

    /**
     * 分配服务端的链接
     *
     * @param fileId
     * @return
     * @throws MyException
     */
    private StorageClient1 assignResourseByFileId(String fileId) throws MyException {
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
                    releaseResource(client);
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
     * 分配服务端的链接
     *
     * @param groupName 不能为空
     * @return
     * @throws MyException
     */
    private StorageClient1 assignResourceByGroupName(String groupName) throws MyException {
        if (groupName == null || groupName.trim().length() == 0) {
            throw new MyException("can't get connect, because groupName is null!");
        }
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
                StorageServer storageServer = trackerClient.getStoreStorage(client.getTrackerServer(), groupName);
                if (storageServer == null) {
                    releaseResource(client);
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
    private void releaseResource(StorageClient1 client) {
        try {
            if (client != null) {
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
        return uploadFile(file, null);
    }

    /**
     * 上传文件
     *
     * @param is
     * @param fileName
     * @param fileLength
     * @return
     * @throws MyException
     */
    public String uploadFile(InputStream is, String fileName, long fileLength) throws MyException {
        Integer fileInfoId;
        String extName = FilenameUtils.getExtension(fileName);
        try {
            if (START_UPLOAD_URL == null || END_UPLOAD_URL == null || clientGroupName == null) {
                throw new MyException("DFS app api sdk not initialized");
            }
            fileInfoId = APIHttpUtils.startUpload(START_UPLOAD_URL, clientAppKey, fileName, fileLength);
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        return uploadFile(is, clientGroupName, extName, null, fileInfoId);
    }

    /**
     * 上传文件
     *
     * @param file     需要上传的文件
     * @param metaList 文件额外属性
     * @return 返回fileId
     * @throws FileNotFoundException
     * @throws MyException
     */
    private String uploadFile(File file, NameValuePair[] metaList) throws FileNotFoundException, MyException {
        String fileName = file.getName();
        String extName = FilenameUtils.getExtension(fileName);
        Integer fileInfoId;
        try {
            long fileLength = file.length();
            if (START_UPLOAD_URL == null || END_UPLOAD_URL == null || clientGroupName == null) {
                throw new MyException("DFS app api sdk not initialized");
            }
            fileInfoId = APIHttpUtils.startUpload(START_UPLOAD_URL, clientAppKey, fileName, fileLength);
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        return uploadFile(new FileInputStream(file), clientGroupName, extName, metaList, fileInfoId);
    }

    /**
     * 上传文件
     *
     * @param in         需要上传的输入流
     * @param groupName  指定文件上传的组（卷），为空表示不指定
     * @param extName    文件扩展名(不能包含'.')
     * @param metaList   文件额外属性
     * @param fileInfoId core server生成的文件id
     * @return 返回fileId
     * @throws MyException
     */
    private String uploadFile(InputStream in, String groupName, String extName, NameValuePair[] metaList, Integer fileInfoId) throws MyException {
        if (in == null) {
            throw new MyException("inputStream is null!");
        }
        StorageClient1 client = null;
        String fileId = null;
        try {
            boolean isSetGroup = false;
            if (StringUtils.isNotEmpty(groupName)) {
                // 上传到指定的组（卷）
                isSetGroup = true;
            }
            if (isSetGroup) {
                client = assignResourceByGroupName(groupName);
            } else {
                client = assignResource();
            }
            long length = in.available();
            // 超过指定大小就进行分批上传
            if (length > NEED_BATCH_UPLOAD_SIZE) {
                if (!(in instanceof BufferedInputStream)) {
                    in = new BufferedInputStream(in);
                }
                int readFlag;
                byte[] bytes = new byte[UPLOAD_BUFFER_SIZE];
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
                }
            } else {
                byte[] fileBytes = new byte[(int) length];
                in.read(fileBytes);
                if (isSetGroup) {
                    fileId = client.upload_file1(groupName, fileBytes, extName, metaList);
                } else {
                    fileId = client.upload_file1(fileBytes, extName, metaList);
                }
            }
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            releaseResource(client);
        }

        try {
            if (StringUtils.isNotEmpty(fileId) && fileInfoId != null && fileInfoId > 0) {
                executorService.execute(new EndUpLoadHttpTask(END_UPLOAD_URL, fileId, fileInfoId));
            }
        } catch (Exception ignored) {
        }
        return fileId;
    }

    /**
     * 下载
     *
     * @param fileId  fastdfs的文件id
     * @param out     输出流
     * @param isClose 是否关闭输出流
     * @throws MyException
     */
    public void downloadFile(String fileId, OutputStream out, boolean isClose) throws MyException {
        downloadFile(fileId, out, isClose, -1L);
    }

    /**
     * 下载文件
     *
     * @param fileId     上传成功后的fileId
     * @param out        输出流
     * @param isClose    是否关闭输出流
     * @param fileLength 文件长度,-1表示未知
     * @throws MyException
     * @throws IOException
     */
    private void downloadFile(String fileId, OutputStream out, boolean isClose, long fileLength) throws MyException {
        StorageClient1 client = null;
        try {
            if (clientGroupName == null) {
                throw new MyException("DFS app api sdk not initialized");
            }
            client = assignResourseByFileId(fileId);
            if (fileLength < 0) {
                FileInfo fileInfo = client.get_file_info1(fileId);
                if (fileInfo != null) {
                    fileLength = fileInfo.getFileSize();
                }
            }
            if (fileLength < 0) {
                throw new MyException("Unknown file info! fileId:" + fileId);
            }
            if (!(out instanceof BufferedOutputStream)) {
                out = new BufferedOutputStream(out);
            }
            int bufferSize = DOWNLOAD_BUFFER_SIZE;
            int offset = 0;
            while (fileLength > 0) {
                if (fileLength < bufferSize) {
                    bufferSize = (int) fileLength;
                }
                byte[] bytes = client.download_file1(fileId, offset, bufferSize);
                fileLength -= bytes.length;
                offset += bytes.length;
                out.write(bytes);
                out.flush();
            }
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        } catch (Exception e) {
            throw new MyException("download file error, " + e.getMessage());
        } finally {
            releaseResource(client);
            if (isClose) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileId
     * @return 0 成功,2文件不存在,其他值失败
     * @throws MyException
     */
    public int deleteFile(String fileId) throws MyException {
        StorageClient1 client = null;
        int result;
        if (DELETE_URL == null || clientGroupName == null) {
            throw new MyException("DFS app api sdk not initialized");
        }
        try {
            client = assignResourseByFileId(fileId);
            result = client.delete_file1(fileId);
        } catch (Exception e) {
            result = -1;
        } finally {
            releaseResource(client);
        }

        if (result == 0) {
            try {
                executorService.execute(new DeleteHttpTask(DELETE_URL, fileId));
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    /**
     * 下载Url前缀
     *
     * @return
     */
    public String getHttpDownloadUrl() {
        return DOWNLOAD_URL;
    }

    @AllArgsConstructor
    class EndUpLoadHttpTask implements Runnable {

        private String url;

        private String fileId;

        private Integer fileInfoId;

        @Override
        public void run() {
            APIHttpUtils.endUpload(url, fileId, fileInfoId);
        }
    }

    @AllArgsConstructor
    class DeleteHttpTask implements Runnable {

        private String url;

        private String fileId;

        @Override
        public void run() {
            APIHttpUtils.delete(url, fileId);
        }
    }
}
