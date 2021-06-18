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
package com.farsunset.httpserver.netty.path;


import com.farsunset.httpserver.netty.annotation.NettyHttpHandler;

public class Path {
    private String method;
    private String uri;
    private boolean equal;

    public static Path make(NettyHttpHandler annotation){
        return new Path(annotation);
    }
    public Path(NettyHttpHandler annotation){
        method = annotation.method();
        uri = annotation.path();
        equal = annotation.equal();
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    @Override
    public String toString(){
        return  method.toUpperCase() + " " + uri.toUpperCase();
    }
    @Override
    public int hashCode(){
        return  ("HTTP " + method.toUpperCase() + " " + uri.toUpperCase()).hashCode();
    }
    @Override
    public boolean equals(Object object){
        if (object instanceof  Path){
            Path path = (Path) object;
            return method.equalsIgnoreCase(path.method) && uri.equalsIgnoreCase(path.uri);
        }
        return false;
    }
}
