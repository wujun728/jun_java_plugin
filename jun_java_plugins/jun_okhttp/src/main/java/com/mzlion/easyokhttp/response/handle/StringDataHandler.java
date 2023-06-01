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

import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 字符串数据处理器
 *
 * @author mzlion on 2016/12/14.
 */
public class StringDataHandler implements DataHandler<String> {

    /**
     * 返回字符串数据处理器
     *
     * @return 字符串数据处理器
     */
    public static StringDataHandler create() {
        return Holder.handler;
    }

    /**
     * 单例Holder
     *
     * @author mzlion
     */
    private static class Holder {
        /**
         * 因为{@linkplain StringDataHandler}使用频率非常高，所以这里直接缓存
         */
        private static StringDataHandler handler = new StringDataHandler();
    }

    /**
     * 字符编码
     */
    private Charset charset;

    /**
     * 获取字符编码
     *
     * @return 字符编码
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * 设置字符编码
     *
     * @param charset 字符编码
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * 得到相应结果后,将相应数据转为需要的数据格式
     *
     * @param response 需要转换的对象
     * @return 转换结果
     */
    @Override
    public String handle(Response response) throws IOException {
        if (this.charset != null) {
            return new String(response.body().bytes());
        }
        return response.body().string();
    }
}
