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
package net.ymate.platform.serv;

/**
 * 编解码器接口
 *
 * @param <T> 编码返回对象类型
 * @author 刘镇 (suninformation@163.com) on 15/11/9 下午6:41
 * @version 1.0
 */
public interface ICodec<T> {

    /**
     * 初始化编解码器
     *
     * @param charset 字符集名称
     * @return 返回当前编解码器实例
     */
    ICodec init(String charset);

    /**
     * @param message 预编码对象
     * @return 返回编码后的对象
     */
    T encode(Object message);

    /**
     * @param source 预解码对象
     * @return 返回解码后的对象
     */
    Object decode(T source);
}
