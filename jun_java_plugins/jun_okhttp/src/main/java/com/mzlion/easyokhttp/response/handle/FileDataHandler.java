/*
 * Copyright (C) 2016-2017 mzlion(mzllon@qq.com).
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
package com.mzlion.easyokhttp.response.handle;

import com.mzlion.core.io.FileUtils;
import com.mzlion.core.lang.Assert;
import com.mzlion.core.lang.StringUtils;
import com.mzlion.easyokhttp.utils.Utils;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;

/**
 * 文件处理器,一般用于从远程下载资源(如图片、报表等)
 * 支持自动获取文件名，也支持自定义文件名
 *
 * @author mzlion on 2016/12/14.
 */
public class FileDataHandler implements DataHandler<File> {

    /**
     * 保存的文件目录
     */
    private final String dirPath;

    /**
     * 保存的文件名
     */
    private String filename;

    public FileDataHandler(String dirPath) {
        Assert.notNull(dirPath, "DirPath may not be null.");
        this.dirPath = dirPath;
    }

    public FileDataHandler(String dirPath, String filename) {
        this(dirPath);
        this.filename = filename;
    }

    /**
     * 返回保存的文件目录
     *
     * @return 保存的文件目录
     */
    public String getDirPath() {
        return dirPath;
    }

    /**
     * 返回保存的文件名
     *
     * @return 保存的文件名
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 设置保存的文件名
     *
     * @param filename 保存的文件名
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 得到相应结果后,将相应数据转为需要的数据格式
     *
     * @param response 需要转换的对象
     * @return 存储的文件信息
     * @throws IOException 出现异常
     */
    @Override
    public File handle(final Response response) throws IOException {
        String name = this.filename;
        if (StringUtils.isEmpty(name)) name = Utils.getFilename(response);
        File saveFile = new File(this.dirPath, name);
        FileUtils.copyStream(response.body().byteStream(), saveFile);
        return saveFile;
    }
}
