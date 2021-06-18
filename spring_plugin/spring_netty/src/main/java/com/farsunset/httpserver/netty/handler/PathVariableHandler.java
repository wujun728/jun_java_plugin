/**
 * Copyright 2013-2033 Xia Jun(3979434@qq.com).
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
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.farsunset.httpserver.netty.handler;

import com.farsunset.httpserver.dto.Response;
import com.farsunset.httpserver.netty.annotation.NettyHttpHandler;
import com.farsunset.httpserver.netty.http.NettyHttpRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@NettyHttpHandler(path = "/moment/list/",equal = false)
public class PathVariableHandler implements IFunctionHandler<List<HashMap<String,String>>> {
    @Override
    public Response<List<HashMap<String,String>>> execute(NettyHttpRequest request) {

        /**
         * 通过请求uri获取到path参数
         */
        String id = request.getStringPathValue(3);

        List<HashMap<String,String>> list = new LinkedList<>();
        HashMap<String,String> data = new HashMap<>();
        data.put("id","1");
        data.put("name","Bluesky");
        data.put("text","hello sea!");
        data.put("time","2018-08-08 08:08:08");
        list.add(data);
        return Response.ok(list);
    }
}
