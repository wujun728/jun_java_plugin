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

import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 16/4/29 下午2:28
 * @version 1.0
 */
public interface IMultipartRequestWrapper {

    /**
     * @param name 文件字段名称
     * @return 获取上传的文件
     */
    IUploadFileWrapper getUploadFile(String name);

    /**
     * @param name 文件字段名称
     * @return 获取上传的文件数组
     */
    IUploadFileWrapper[] getUploadFiles(String name);

    /**
     * @return 获取所有的上传文件
     */
    Set<IUploadFileWrapper> getUploadFiles();
}
