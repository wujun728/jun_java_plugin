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

import java.io.IOException;

/**
 * 数据处理定义接口,将得到的响应结果转为所需的数据
 *
 * @author mzlion on 2016/12/14.
 * @see JsonDataHandler
 * @see StringDataHandler
 */
public interface DataHandler<T> {

    /**
     * 得到相应结果后,将相应数据转为需要的数据格式
     *
     * @param response 需要转换的对象
     * @return 转换结果
     * @throws IOException 出现异常
     */
    T handle(final okhttp3.Response response) throws IOException;

}
