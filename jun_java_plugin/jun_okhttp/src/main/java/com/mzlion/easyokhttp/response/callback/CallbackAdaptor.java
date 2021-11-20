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
package com.mzlion.easyokhttp.response.callback;

import com.mzlion.easyokhttp.request.AbsHttpRequest;
import com.mzlion.easyokhttp.response.handle.DataHandler;
import okhttp3.Call;
import okhttp3.Response;

/**
 * {@linkplain Callback}的默认实现
 *
 * @author mzlion on 2016/12/14.
 */
public class CallbackAdaptor<T> implements Callback<T> {

    /**
     * 在请求前调用,在这里可以设置一些参数
     *
     * @param httpRequest 请求对象
     * @return 返回{@code false}则取消此次请求
     */
    @Override
    public boolean onBefore(AbsHttpRequest httpRequest) {
        return true;
    }

    /**
     * 上传请求调用
     *
     * @param currentSize 当前上传的大小
     * @param totalSize   总的大小
     * @param progress    完成进度
     */
    @Override
    public void postProgress(final long currentSize, final long totalSize, final float progress) {

    }

    /**
     * 请求失败调用
     *
     * @param call      The real call
     * @param exception Exception
     */
    @Override
    public void onError(Call call, Exception exception) {

    }

    /**
     * 请求完成调用
     *
     * @param response 原始的Response,方便调用者自行处理
     */
    @Override
    public void onComplete(Response response) {

    }

    /**
     * 获取数据处理器,用于解析转换响应结果
     *
     * @return {@linkplain DataHandler}
     */
    @Override
    public DataHandler<T> getDataHandler() {
        return null;
    }

    /**
     * 根据数据处理器得到处理结果,调用者直接使用处理后的数据
     * 如果{@linkplain #getDataHandler()}返回{@code null}则{@code data}也为{@code null}
     *
     * @param data 响应经过处理的数据
     * @see #getDataHandler()
     */
    @Override
    public void onSuccess(T data) {

    }
}
