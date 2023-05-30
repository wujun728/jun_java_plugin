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
import java.io.OutputStream;

/**
 * 用于存储编译后的字节码，输出到内存中
 * 
 * @author jianggujin
 *
 */
public class JMemoryJavaFileObject extends JAbstractJavaFileObject {
    /**
     * 编译后的字节码输出流
     */
    private ByteArrayOutputStream compileStream;

    /**
     * 遵循Java规范的类名
     * 
     */
    public JMemoryJavaFileObject(String className, Kind kind) {
        super(className, kind);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        this.compileStream = new ByteArrayOutputStream();
        return this.compileStream;
    }

    /**
     * 获得编译后的字节数组
     * 
     */
    public byte[] getCompiledBytes() {
        return this.compileStream.toByteArray();
    }
}
