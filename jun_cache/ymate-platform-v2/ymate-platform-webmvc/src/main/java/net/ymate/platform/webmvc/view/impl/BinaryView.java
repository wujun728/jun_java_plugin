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
package net.ymate.platform.webmvc.view.impl;

import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.core.util.FileUtils;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.MimeTypeUtils;
import net.ymate.platform.webmvc.view.AbstractView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.net.URLEncoder;

/**
 * 二进制数据流视图
 *
 * @author 刘镇 (suninformation@163.com) on 2011-10-23 上午11:32:55
 * @version 1.0
 */
public class BinaryView extends AbstractView {

    private static final Log __LOG = LogFactory.getLog(BinaryView.class);

    protected String __fileName;
    protected Object __data;

    private long __length = -1;

    /**
     * @param targetFile 目标文件
     * @return 加载文件并转换成二进制视图类对象，若目标文件不存在或无较则返回NULL
     * @throws Exception 任何可能发生的异常
     */
    public static BinaryView bind(File targetFile) throws Exception {
        if (targetFile != null && targetFile.exists() && targetFile.isFile() && targetFile.canRead()) {
            BinaryView _binaryView = new BinaryView(targetFile);
            _binaryView.setContentType(MimeTypeUtils.getFileMimeType(FileUtils.getExtName(targetFile.getPath())));
            return _binaryView;
        }
        return null;
    }

    /**
     * 构造器
     *
     * @param data 数据对象
     */
    public BinaryView(Object data) {
        __data = data;
    }

    /**
     * 构造器
     *
     * @param inputStream 数据输入流
     * @param length      输入流数据长度
     */
    public BinaryView(InputStream inputStream, long length) {
        __data = inputStream;
        if (length > 0) {
            __length = length;
        }
    }

    protected void __doRenderView() throws Exception {
        HttpServletRequest _request = WebContext.getRequest();
        HttpServletResponse _response = WebContext.getResponse();
        //
        if (StringUtils.isNotBlank(__fileName)) {
            StringBuilder _dispositionSB = new StringBuilder("attachment;filename=");
            if (_request.getHeader("User-Agent").toLowerCase().contains("firefox")) {
                _dispositionSB.append(new String(__fileName.getBytes("UTF-8"), "ISO8859-1"));
            } else {
                _dispositionSB.append(URLEncoder.encode(__fileName, "UTF-8"));
            }
            _response.setHeader("Content-Disposition", _dispositionSB.toString());
        }
        //
        if (__data == null) {
            return;
        }
        // 文件
        if (__data instanceof File) {
            // 读取文件数据长度
            __length = ((File) __data).length();
            // 尝试计算Range以配合断点续传
            PairObject<Long, Long> _rangePO = __doParseRange(__length);
            // 若为断点续传
            if (_rangePO != null) {
                __doSetRangeHeader(_response, _rangePO);
                // 开始续传文件流
                IOUtils.copyLarge(new FileInputStream((File) __data), _response.getOutputStream(), _rangePO.getKey(), _rangePO.getValue());
            } else {
                // 正常下载
                _response.setContentLength(BlurObject.bind(__length).toIntValue());
                IOUtils.copyLarge(new FileInputStream((File) __data), _response.getOutputStream());
            }
        }
        // 字节数组
        else if (__data instanceof byte[]) {
            byte[] _datas = (byte[]) __data;
            _response.setContentLength(_datas.length);
            IOUtils.write(_datas, _response.getOutputStream());
        }
        // 字符数组
        else if (__data instanceof char[]) {
            char[] _datas = (char[]) __data;
            _response.setContentLength(_datas.length);
            IOUtils.write(_datas, _response.getOutputStream());
        }
        // 文本流
        else if (__data instanceof Reader) {
            Reader r = (Reader) __data;
            IOUtils.copy(r, _response.getOutputStream());
        }
        // 二进制流
        else if (__data instanceof InputStream) {
            PairObject<Long, Long> _rangePO = __doParseRange(__length);
            if (_rangePO != null) {
                __doSetRangeHeader(_response, _rangePO);
                IOUtils.copyLarge((InputStream) __data, _response.getOutputStream(), _rangePO.getKey(), _rangePO.getValue());
            } else {
                _response.setContentLength(BlurObject.bind(__length).toIntValue());
                IOUtils.copyLarge((InputStream) __data, _response.getOutputStream());
            }
        }
        // 普通对象
        else {
            String _content = StringUtils.trimToEmpty(BlurObject.bind(__data).toStringValue());
            _response.setContentLength(_content.length());
            IOUtils.write(_content, _response.getOutputStream());
        }
    }

    private void __doSetRangeHeader(HttpServletResponse response, PairObject<Long, Long> range) {
        // 表示使用了断点续传（默认是“none”，可以不指定）
        addHeader("Accept-Ranges", "bytes");
        // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
        long _totalLength = range.getValue() - range.getKey();
        addHeader("Content-Length", _totalLength + "");
        // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
        addHeader("Content-Range", "bytes " + range.getKey() + "-" + (range.getValue() - 1) + "/" + __length);
        // response.setHeader("Connection", "Close"); //此语句将不能用IE直接下载
        // Status: 206
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
    }

    /**
     * 分析请求头中的Range参数，计算并返回本次数据的开始和结束位置
     *
     * @param length 数据大小
     * @return 若非断点续传则返回null
     */
    private PairObject<Long, Long> __doParseRange(long length) {
        PairObject<Long, Long> _returnValue = null;
        // 通过请求头Range参数判断是否采用断点续传
        String _rangeStr = WebContext.getRequest().getHeader("Range");
        if (_rangeStr != null && _rangeStr.startsWith("bytes=") && _rangeStr.length() >= 7) {
            _rangeStr = StringUtils.substringAfter(_rangeStr, "bytes=");
            String[] _ranges = StringUtils.split(_rangeStr, ",");
            // 可能存在多个Range，目前仅处理第一个...
            for (String _range : _ranges) {
                if (StringUtils.isBlank(_range)) {
                    return null;
                }
                // bytes=-100
                if (_range.startsWith("-")) {
                    long _end = Long.parseLong(_range);
                    long _start = length + _end;
                    if (_start < 0) {
                        return null;
                    }
                    _returnValue = new PairObject<Long, Long>(_start, length);
                    break;
                }
                // bytes=1024-
                if (_range.endsWith("-")) {
                    long _start = Long.parseLong(StringUtils.substringBefore(_range, "-"));
                    if (_start < 0) {
                        return null;
                    }
                    _returnValue = new PairObject<Long, Long>(_start, length);
                    break;
                }
                // bytes=10-1024
                if (_range.contains("-")) {
                    String[] _tmp = _range.split("-");
                    long _start = Long.parseLong(_tmp[0]);
                    long _end = Long.parseLong(_tmp[1]);
                    if (_start > _end) {
                        return null;
                    }
                    _returnValue = new PairObject<Long, Long>(_start, _end + 1);
                }
            }
        }
        return _returnValue;
    }

    /**
     * @param dispFileName 显示的文件名称
     * @return 设置采用档案下载的方式
     */
    public BinaryView useAttachment(String dispFileName) {
        __fileName = dispFileName;
        return this;
    }
}
