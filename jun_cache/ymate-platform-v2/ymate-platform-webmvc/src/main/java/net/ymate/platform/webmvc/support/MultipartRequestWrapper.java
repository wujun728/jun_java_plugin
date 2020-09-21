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
package net.ymate.platform.webmvc.support;

import net.ymate.platform.webmvc.IMultipartRequestWrapper;
import net.ymate.platform.webmvc.IUploadFileWrapper;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.util.FileUploadHelper;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 表单类型为"multipart/form-data"请求包装类
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-5 上午10:19:47
 * @version 1.0
 */
public class MultipartRequestWrapper extends HttpServletRequestWrapper implements IMultipartRequestWrapper {

    private FileUploadHelper.UploadFormWrapper __formWarpper;

    public MultipartRequestWrapper(IWebMvc owner, HttpServletRequest request) throws IOException, FileUploadException {
        super(request);
        // 绑定并初始化文件上传帮助类
        __formWarpper = FileUploadHelper.bind(owner, request)
                .setUploadTempDir(new File(StringUtils.defaultIfBlank(owner.getModuleCfg().getUploadTempDir(), System.getProperty("java.io.tmpdir"))))
                .setFileSizeMax(owner.getModuleCfg().getUploadTotalSizeMax())
                .setSizeMax(owner.getModuleCfg().getUploadFileSizeMax())
                .setSizeThreshold(owner.getModuleCfg().getUploadSizeThreshold())
                .setFileUploadListener(owner.getModuleCfg().getUploadFileListener())
                .processUpload();
    }

    public String getParameter(String name) {
        String _returnStr = super.getParameter(name);
        if (StringUtils.isBlank(_returnStr)) {
            String[] params = __formWarpper.getFieldMap().get(name);
            _returnStr = (params == null ? null : params[0]);
        }
        return _returnStr;
    }

    public String[] getParameterValues(String name) {
        String[] _returnStr = super.getParameterValues(name);
        if (_returnStr == null || _returnStr.length == 0) {
            _returnStr = __formWarpper.getFieldMap().get(name);
        }
        return _returnStr;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> _returnMap = new HashMap<String, String[]>(super.getParameterMap());
        _returnMap.putAll(__formWarpper.getFieldMap());
        return Collections.unmodifiableMap(_returnMap);
    }

    public Enumeration<String> getParameterNames() {
        return new Enumeration<String>() {
            private Iterator<String> it = getParameterMap().keySet().iterator();

            public boolean hasMoreElements() {
                return it.hasNext();
            }

            public String nextElement() {
                return it.next();
            }
        };
    }

    /**
     * @param name 文件字段名称
     * @return 获取上传的文件
     */
    public IUploadFileWrapper getUploadFile(String name) {
        IUploadFileWrapper[] files = __formWarpper.getFileMap().get(name);
        return files == null ? null : files[0];
    }

    /**
     * @param name 文件字段名称
     * @return 获取上传的文数组
     */
    public IUploadFileWrapper[] getUploadFiles(String name) {
        return __formWarpper.getFileMap().get(name);
    }

    /**
     * @return 获取所有的上传文件
     */
    public Set<IUploadFileWrapper> getUploadFiles() {
        Set<IUploadFileWrapper> _returnValues = new HashSet<IUploadFileWrapper>();
        for (IUploadFileWrapper[] _fileWraps : __formWarpper.getFileMap().values()) {
            if (ArrayUtils.isNotEmpty(_fileWraps)) {
                Collections.addAll(_returnValues, _fileWraps);
            }
        }
        return _returnValues;
    }
}
