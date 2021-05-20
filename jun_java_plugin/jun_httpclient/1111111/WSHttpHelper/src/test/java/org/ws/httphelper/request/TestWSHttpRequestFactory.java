/*
 * Copyright (c) 2015-2016, AlexGao
 * http://git.oschina.net/wolfsmoke/WSHttpHelper
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ws.httphelper.request;

import junit.framework.TestCase;
import org.ws.httphelper.http.WSHttpTaskExecutor;

/**
 * Created by Administrator on 16-1-2.
 */
public class TestWSHttpRequestFactory extends TestCase{
    public void testRequestPathXmlConfigRequest()throws Exception{
        WSHttpRequest request = WSHttpRequestFactory.getHttpRequest("downloadImage");
        // 若不传入word参数，默认使用配置的值
        //request.getContext().addInputData("word","宠物萌图");
        request.execute();
        // 等待所有线程执行完成
        WSHttpTaskExecutor.getInstance().waitForFinish(Thread.currentThread());
    }
}
