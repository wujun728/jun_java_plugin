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

package com.jun.plugin.httphelper.request.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jun.plugin.httphelper.WSHttpHelperConstant;
import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.http.WSHttpTaskExecutor;
import com.jun.plugin.httphelper.model.ResponseResult;
import com.jun.plugin.httphelper.model.WSRequestContext;
import com.jun.plugin.httphelper.request.WSHttpRequest;
import com.jun.plugin.httphelper.request.WSHttpRequestFactory;
import com.jun.plugin.httphelper.request.handler.ResponseProHandler;

/**
 * Created by Administrator on 16-1-2.
 */
public class DownloadAllImageHandle implements ResponseProHandler {
    @Override
    public ResponseResult handler(WSRequestContext context, ResponseResult result) throws WSException {
        String body = result.getBody(String.class);
        // 提取下载图片正则表达式
        String reg="\"objURL\":\"([^\"]*\\.jpg)\",";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(body);
        String savePath= "C:/testDownloadImage/";
        System.out.println(savePath);
        int i=0;
        while(matcher.find()){
            String imageUrl=matcher.group(1);
            WSHttpRequest request=WSHttpRequestFactory.getHttpRequest("saveImageRequest");

            request.getContext().addInputData("savePath",savePath+i+".jpg");
            request.getContext().addInputData("url",imageUrl);
            // 同步下载
            //request.execute();
            // 异步下载：多个下载请求同时进行
            request.asyncExecute();

            i++;
        }
        return result;
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRO_HANDLER_USER;
    }
}
