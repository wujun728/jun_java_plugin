/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.webmvc.util;

import net.ymate.platform.core.util.FileUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.IUploadFileWrapper;
import net.ymate.platform.webmvc.IWebMvc;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传处理助手类；注：文件上传页面Form表单必须采用POST方式提交并设置属性：enctype="multipart/form-data"，否则将无法处理
 *
 * @author 刘镇 (suninformation@163.com) on 2011-6-5 下午02:50:07
 * @version 1.0
 */
public final class FileUploadHelper {

    private static final Log __LOG = LogFactory.getLog(FileUploadHelper.class);

    private HttpServletRequest __request;

    /**
     * 监听器
     */
    private ProgressListener __listener;

    /**
     * 上传文件临时目录（不支持自定义文件流处理）
     */
    private File __uploadTempDir;

    /**
     * 上传文件最大值
     */
    private long __fileSizeMax = -1; // 10485760 = 10M

    /**
     * 上传文件总量的最大值
     */
    private long __sizeMax = -1;

    /**
     * 内存缓冲区的大小,默认值为10K,如果文件大于10K,将使用临时文件缓存上传文件
     */
    private int __sizeThreshold = 10240; // 4096 = 4K

    private String __charsetEncoding;

    public static FileUploadHelper bind(IWebMvc owner, HttpServletRequest request) {
        return new FileUploadHelper(owner, request);
    }

    private FileUploadHelper(IWebMvc owner, HttpServletRequest request) {
        __request = request;
        if (StringUtils.isBlank(__charsetEncoding = owner.getModuleCfg().getDefaultCharsetEncoding())) {
            __charsetEncoding = __request.getCharacterEncoding();
        }
    }

    /**
     * @param processer 文件上传处理器
     * @return 处理表单提交，使用提供的文件上传处理器处理文件流
     * @throws FileUploadException 文件上传时可能产生的异常
     * @throws IOException         文件读写可能产生的异常
     */
    public UploadFormWrapper processUpload(IUploadFileItemProcesser processer) throws FileUploadException, IOException {
        boolean _isMultipart = ServletFileUpload.isMultipartContent(__request);
        if (_isMultipart) {
            if (null != processer) {
                return __doUploadFileAsStream(processer);
            } else {
                return UploadFileAsDiskBased();
            }
        }
        return new UploadFormWrapper();
    }

    /**
     * @return 处理表单提交
     * @throws FileUploadException 文件上传时可能产生的异常
     * @throws IOException         文件读写可能产生的异常
     */
    public UploadFormWrapper processUpload() throws FileUploadException, IOException {
        return this.processUpload(null);
    }

    /**
     * 采用文件流的方式处理上传文件（即将上传文件流对象交给用户做进一步处理）
     *
     * @param processer 文件上传处理器
     * @throws FileUploadException 文件上传时可能产生的异常
     * @throws IOException         文件读写可能产生的异常
     */
    private UploadFormWrapper __doUploadFileAsStream(IUploadFileItemProcesser processer) throws FileUploadException, IOException {
        ServletFileUpload _upload = new ServletFileUpload();
        _upload.setFileSizeMax(__fileSizeMax);
        _upload.setSizeMax(__sizeMax);
        if (__listener != null) {
            _upload.setProgressListener(__listener);
        }
        Map<String, List<String>> tmpParams = new HashMap<String, List<String>>();
        Map<String, List<UploadFileWrapper>> tmpFiles = new HashMap<String, List<UploadFileWrapper>>();
        //
        FileItemIterator _fileItemIT = _upload.getItemIterator(__request);
        while (_fileItemIT.hasNext()) {
            FileItemStream _item = _fileItemIT.next();
            if (_item.isFormField()) {
                List<String> _valueList = tmpParams.get(_item.getFieldName());
                if (_valueList == null) {
                    _valueList = new ArrayList<String>();
                    tmpParams.put(_item.getFieldName(), _valueList);
                }
                _valueList.add(Streams.asString(_item.openStream(), __charsetEncoding));
            } else {
                List<UploadFileWrapper> _valueList = tmpFiles.get(_item.getFieldName());
                if (_valueList == null) {
                    _valueList = new ArrayList<UploadFileWrapper>();
                    tmpFiles.put(_item.getFieldName(), _valueList);
                }
                // 交给用户接口处理
                _valueList.add(processer.process(_item));
            }
        }
        //
        UploadFormWrapper _form = new UploadFormWrapper();
        for (Map.Entry<String, List<String>> entry : tmpParams.entrySet()) {
            _form.getFieldMap().put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
        }
        for (Map.Entry<String, List<UploadFileWrapper>> entry : tmpFiles.entrySet()) {
            _form.getFileMap().put(entry.getKey(), entry.getValue().toArray(new UploadFileWrapper[entry.getValue().size()]));
        }
        return _form;
    }

    /**
     * 采用文件方式处理上传文件（即先将文件上传后，再交给用户已上传文件对象集合）
     *
     * @throws FileUploadException 文件上传时可能产生的异常
     */
    private UploadFormWrapper UploadFileAsDiskBased() throws FileUploadException {
        DiskFileItemFactory _factory = new DiskFileItemFactory();
        _factory.setRepository(__uploadTempDir);
        _factory.setSizeThreshold(__sizeThreshold);
        //
        ServletFileUpload _upload = new ServletFileUpload(_factory);
        _upload.setFileSizeMax(__fileSizeMax);
        _upload.setSizeMax(__sizeMax);
        if (__listener != null) {
            _upload.setProgressListener(__listener);
        }
        UploadFormWrapper _form = new UploadFormWrapper();
        Map<String, List<String>> tmpParams = new HashMap<String, List<String>>();
        Map<String, List<UploadFileWrapper>> tmpFiles = new HashMap<String, List<UploadFileWrapper>>();
        //
        List<FileItem> _items = _upload.parseRequest(__request);
        for (FileItem _item : _items) {
            if (_item.isFormField()) {
                List<String> _valueList = tmpParams.get(_item.getFieldName());
                if (_valueList == null) {
                    _valueList = new ArrayList<String>();
                    tmpParams.put(_item.getFieldName(), _valueList);
                }
                try {
                    _valueList.add(_item.getString(__charsetEncoding));
                } catch (UnsupportedEncodingException e) {
                    __LOG.warn("", RuntimeUtils.unwrapThrow(e));
                }
            } else {
                List<UploadFileWrapper> _valueList = tmpFiles.get(_item.getFieldName());
                if (_valueList == null) {
                    _valueList = new ArrayList<UploadFileWrapper>();
                    tmpFiles.put(_item.getFieldName(), _valueList);
                }
                _valueList.add(new UploadFileWrapper(_item));
            }
        }
        //
        for (Map.Entry<String, List<String>> entry : tmpParams.entrySet()) {
            _form.getFieldMap().put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
        }
        for (Map.Entry<String, List<UploadFileWrapper>> entry : tmpFiles.entrySet()) {
            _form.getFileMap().put(entry.getKey(), entry.getValue().toArray(new UploadFileWrapper[entry.getValue().size()]));
        }
        return _form;
    }

    /**
     * @return 监听器
     */
    public ProgressListener getFileUploadListener() {
        return __listener;
    }

    /**
     * @param listener 文件上传进度监听器
     * @return 设置监听器
     */
    public FileUploadHelper setFileUploadListener(ProgressListener listener) {
        this.__listener = listener;
        return this;
    }

    /**
     * @return 上传文件临时目录（不支持自定义文件流处理）
     */
    public File getUploadTempDir() {
        return __uploadTempDir;
    }

    /**
     * @param uploadDir 临时目录
     * @return 上传文件临时目录（不支持自定义文件流处理），默认使用：System.getProperty("java.io.tmpdir")
     */
    public FileUploadHelper setUploadTempDir(File uploadDir) {
        __uploadTempDir = uploadDir;
        return this;
    }

    /**
     * @return 上传文件最大值
     */
    public long getFileSizeMax() {
        return __fileSizeMax;
    }

    /**
     * @param fileSize 文件大小
     * @return 设置上传文件最大值
     */
    public FileUploadHelper setFileSizeMax(long fileSize) {
        __fileSizeMax = fileSize;
        return this;
    }

    /**
     * @return 内存缓冲区的大小, 默认值为10K, 如果文件大于10K, 将使用临时文件缓存上传文件
     */
    public int getSizeThreshold() {
        return __sizeThreshold;
    }

    /**
     * @param threshold 缓冲区大小
     * @return 内存缓冲区的大小, 默认值为10K, 如果文件大于10K, 将使用临时文件缓存上传文件
     */
    public FileUploadHelper setSizeThreshold(int threshold) {
        __sizeThreshold = threshold;
        return this;
    }

    /**
     * @return 上传文件总量的最大值
     */
    public long getSizeMax() {
        return __sizeMax;
    }

    /**
     * @param size 文件总量大小
     * @return 设置上传文件总量的最大值
     */
    public FileUploadHelper setSizeMax(long size) {
        __sizeMax = size;
        return this;
    }

    /**
     * 文件上传处理回调接口定义，用于将每个文件交给开发者自行处理
     *
     * @author 刘镇 (suninformation@163.com) on 2011-6-5 下午03:47:49
     * @version 1.0
     */
    public interface IUploadFileItemProcesser {

        /**
         * 处理文件或文件流
         *
         * @param item FileItemStream
         * @return 返回文件上传包装器
         * @throws IOException         文件读写可能产生的异常
         * @throws FileUploadException 文件上传时可能产生的异常
         */
        UploadFileWrapper process(FileItemStream item) throws IOException, FileUploadException;

    }

    /**
     * 文件上传表单包装器；
     *
     * @author 刘镇 (suninformation@163.com) on 2011-6-7 上午09:50:56
     * @version 1.0
     */
    public static class UploadFormWrapper {

        private Map<String, String[]> __fieldMap = new HashMap<String, String[]>();

        private Map<String, IUploadFileWrapper[]> __fileMap = new HashMap<String, IUploadFileWrapper[]>();

        public UploadFormWrapper() {
        }

        public UploadFormWrapper(Map<String, String[]> fieldMap, Map<String, UploadFileWrapper[]> fileMap) {
            __fieldMap.putAll(fieldMap);
            __fileMap.putAll(fileMap);
        }

        public Map<String, String[]> getFieldMap() {
            return __fieldMap;
        }

        public Map<String, IUploadFileWrapper[]> getFileMap() {
            return __fileMap;
        }

        public String[] getField(String fieldName) {
            return __fieldMap.get(fieldName);
        }

        public IUploadFileWrapper[] getFile(String fieldName) {
            return __fileMap.get(fieldName);
        }

    }

    /**
     * 上传文件对象包装器
     *
     * @author 刘镇 (suninformation@163.com) on 2011-6-6 上午01:16:45
     * @version 1.0
     */
    public static class UploadFileWrapper implements IUploadFileWrapper {

        private FileItem __fileItemObj;
        private File __fileObj;
        private boolean __isFileObj;

        public UploadFileWrapper(FileItem fileItem) {
            __fileItemObj = fileItem;
        }

        public UploadFileWrapper(File file) {
            __fileObj = file;
            __isFileObj = true;
        }

        public String getPath() {
            if (__isFileObj) {
                if (__fileObj == null) {
                    return "";
                } else {
                    return __fileObj.getAbsolutePath();
                }
            }
            return __fileItemObj.getName();
        }

        public String getName() {
            String _filePath = null;
            if (__isFileObj) {
                _filePath = __fileObj == null ? "" : __fileObj.getAbsolutePath();
            } else {
                _filePath = __fileItemObj.getName();
            }
            if (_filePath != null) {
                int _pos = _filePath.lastIndexOf('\\');
                if (_pos == -1) {
                    _pos = _filePath.lastIndexOf('/');
                }
                _filePath = _filePath.substring(_pos + 1);
            }
            return _filePath;
        }

        /**
         * 获取文件内容，将其存储在字节数组中（不适合对大文件操作）
         *
         * @return byte[]
         */
        public byte[] get() {
            if (__isFileObj) {
                if (__fileObj == null) {
                    return null;
                }
                byte[] _fileData = new byte[(int) __fileObj.length()];
                FileInputStream _fis = null;
                try {
                    _fis = new FileInputStream(__fileObj);
                    _fis.read(_fileData);
                    return _fileData;
                } catch (IOException e) {
                    return null;
                } finally {
                    if (_fis != null) {
                        try {
                            _fis.close();
                        } catch (IOException ignore) {
                        }
                    }
                }
            }
            return __fileItemObj.get();
        }

        public void delete() {
            if (__isFileObj) {
                if (__fileObj != null && __fileObj.exists()) {
                    __fileObj.delete();
                }
            } else {
                this.__fileItemObj.delete();
            }
        }

        public void transferTo(File dest) throws Exception {
            this.__fileItemObj.write(dest);
        }

        public void writeTo(File file) throws Exception {
            if (__isFileObj) {
                if (__fileObj == null) {
                    throw new IOException("Cannot write, file object was null!");
                }
                if (!__fileObj.renameTo(file)) {
                    BufferedInputStream _inStream = null;
                    BufferedOutputStream _outStream = null;
                    try {
                        _inStream = new BufferedInputStream(new FileInputStream(__fileObj));
                        _outStream = new BufferedOutputStream(new FileOutputStream(file));
                        IOUtils.copy(_inStream, _outStream);
                    } finally {
                        if (_inStream != null) {
                            try {
                                _inStream.close();
                            } catch (IOException ignore) {
                            }
                        }
                        if (_outStream != null) {
                            try {
                                _outStream.close();
                            } catch (IOException ignore) {
                            }
                        }
                    }
                } else {
                    throw new IOException("Cannot write file to disk!");
                }
            } else {
                __fileItemObj.write(file);
            }
        }

        public InputStream getInputStream() throws IOException {
            if (__isFileObj) {
                if (__fileObj == null) {
                    throw new IOException("Cannot get input stream, file object was null!");
                }
                return new FileInputStream(__fileObj);
            }
            return this.__fileItemObj.getInputStream();
        }

        public long getSize() {
            if (__isFileObj) {
                if (__fileObj == null) {
                    return 0;
                } else {
                    return __fileObj.length();
                }
            }
            return __fileItemObj.getSize();
        }

        public OutputStream getOutputStream() throws IOException {
            if (this.__isFileObj) {
                if (__fileObj == null) {
                    throw new IOException("Cannot get output stream, file object was null!");
                }
                return new FileOutputStream(__fileObj);
            }
            return __fileItemObj.getOutputStream();
        }

        public String getContentType() {
            if (__isFileObj) {
                if (__fileObj != null) {
                    return MimeTypeUtils.getFileMimeType(FileUtils.getExtName(__fileObj.getAbsolutePath()));
                }
            } else if (__fileItemObj != null) {
                return __fileItemObj.getContentType();
            }
            return null;
        }
    }
}
