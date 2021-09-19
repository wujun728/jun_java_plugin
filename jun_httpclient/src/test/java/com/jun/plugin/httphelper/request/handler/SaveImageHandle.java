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

import org.apache.commons.io.FileUtils;

import com.jun.plugin.httphelper.WSHttpHelperConstant;
import com.jun.plugin.httphelper.annotation.WSRequest;
import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.model.ResponseResult;
import com.jun.plugin.httphelper.model.WSRequestContext;
import com.jun.plugin.httphelper.request.handler.ResponseProHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 16-1-2.
 */
public class SaveImageHandle implements ResponseProHandler {

    @Override
    public ResponseResult handler(WSRequestContext context, ResponseResult result) throws WSException {
        String savePath = String.valueOf(context.getInputDataMap().get("savePath"));
        System.out.println("正在下载:" + savePath);

        if(context.getResponseType()== WSRequest.ResponseType.BYTE_ARRAY){
            File file = new File(savePath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            byte[] body = (byte[])result.getBody();
            try {
                FileUtils.writeByteArrayToFile(file,body);
            } catch (IOException e) {
                new WSException(e.getMessage(),e);
            }
        }
        return result;
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRO_HANDLER_USER;
    }
}
