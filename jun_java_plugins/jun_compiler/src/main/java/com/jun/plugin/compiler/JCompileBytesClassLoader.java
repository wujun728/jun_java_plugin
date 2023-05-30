/**
 * Copyright 2020 jianggujin (www.jianggujin.com).
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
package com.jun.plugin.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 编译后的字节码加载器
 * 
 * @author jianggujin
 *
 */
public class JCompileBytesClassLoader extends ClassLoader {

    public JCompileBytesClassLoader() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public JCompileBytesClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * 加载编译后的字节码
     */
    public Class<?> loadCompileBytesClass(String className, byte[] compileBytes) {
        return new JCompileBytesClassLoader().defineClass(className, compileBytes, 0, compileBytes.length);
    }

    /**
     * 加载编译后的字节码
     * 
     */
    public Class<?> loadCompileBytesClass(String className, InputStream compileBytes) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
        int len = -1;
        while ((len = compileBytes.read(buffer)) != -1) {
            stream.write(buffer, 0, len);
        }
        stream.flush();
        return this.loadCompileBytesClass(className, stream.toByteArray());
    }
}
