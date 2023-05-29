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

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

/**
 * 内存文件管理器，用于存储编译后的字节码，输出到内存中
 * 
 * @author jianggujin
 *
 */
public class JMemoryJavaFileManage extends ForwardingJavaFileManager<JavaFileManager> {
    /**
     * 临时缓存
     */
    private Map<String, JMemoryJavaFileObject> caches;

    public JMemoryJavaFileManage(JavaFileManager fileManager) {
        super(fileManager);
        this.caches = new ConcurrentHashMap<>();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling)
            throws IOException {
        JMemoryJavaFileObject memoryJavaFileObject = new JMemoryJavaFileObject(className, kind);
        this.caches.put(className, memoryJavaFileObject);
        return memoryJavaFileObject;
    }

    /**
     * 获得编译后的字节数组，同时会将其从缓存中移除
     * 
     * @param className
     * 
     * @return
     */
    public byte[] getCompiledBytes(String className) {
        JMemoryJavaFileObject memoryJavaFileObject = this.caches.remove(className);
        return memoryJavaFileObject == null ? null : memoryJavaFileObject.getCompiledBytes();
    }
}
