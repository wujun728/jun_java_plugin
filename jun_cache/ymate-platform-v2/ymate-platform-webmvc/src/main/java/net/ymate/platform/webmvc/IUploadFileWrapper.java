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
package net.ymate.platform.webmvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 上传文件包装器
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-26 下午1:24:51
 * @version 1.0
 */
public interface IUploadFileWrapper {

    /**
     * @return 获取完整的文件名及路径
     */
    String getPath();

    /**
     * @return 获取文件名称
     */
    String getName();

    /**
     * @return 获取文件大小
     */
    long getSize();

    /**
     * @return 获取文件Content-Type
     */
    String getContentType();

    /**
     * 转移文件
     *
     * @param dest 目标
     * @throws Exception 可能产生的异常
     */
    void transferTo(File dest) throws Exception;

    /**
     * 保存文件
     *
     * @param dest 目标
     * @throws Exception 可能产生的异常
     */
    void writeTo(File dest) throws Exception;

    /**
     * 删除文件
     */
    void delete();

    /**
     * @return 获取文件输入流对象
     * @throws IOException 可能产生的异常
     */
    InputStream getInputStream() throws IOException;

    /**
     * @return 获取文件输出流对象
     * @throws IOException 可能产生的异常
     */
    OutputStream getOutputStream() throws IOException;
}
