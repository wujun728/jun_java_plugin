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

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * Java内存编译器
 * 
 * @author jianggujin
 *
 */
public class JMemoryJavaCompiler implements Closeable {
    private final Pattern packagePattern = Pattern.compile("package\\s+(\\S+)\\s*;");
    private final Pattern classPattern = Pattern.compile("class\\s+(\\S+)\\s+");
    /**
     * 获取编译器实例
     */
    private final JavaCompiler compiler;
    /**
     * 标准文件管理器
     */
    private final StandardJavaFileManager standardFileManager;
    /**
     * 内存文件管理器
     */
    private final JMemoryJavaFileManage memoryJavaFileManage;

    private final ConcurrentHashMap<String, Object> parallelLockMap;

    public JMemoryJavaCompiler() {
        this(true);
    }

    /**
     * 构造方法
     * 
     * @param paralleLock 编译并行锁
     */
    public JMemoryJavaCompiler(boolean paralleLock) {
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.standardFileManager = compiler.getStandardFileManager(null, null, null);
        this.memoryJavaFileManage = new JMemoryJavaFileManage(standardFileManager);
        this.parallelLockMap = paralleLock ? new ConcurrentHashMap<String, Object>() : null;
    }

    /**
     * 编译
     * 
     * @param content
     * @return
     * @throws Exception
     */
    public JCompileResult compile(String content) throws Exception {
        return this.compile(getFullClassName(content), content);
    }

    /**
     * 编译
     * 
     * @param className
     * @param content
     * @return
     * @throws Exception
     */
    public JCompileResult compile(String className, String content) throws Exception {
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        JStringJavaFileObject stringJavaFileObject = new JStringJavaFileObject(className, content);
        synchronized (getClassCompilingLock(className)) {
            CompilationTask task = this.compiler.getTask(null, this.memoryJavaFileManage, diagnosticCollector, null,
                    null, Arrays.asList(stringJavaFileObject));
            long start = System.currentTimeMillis();
            boolean success = task.call();
            long useTime = System.currentTimeMillis() - start;
            return new JCompileResult(success, useTime, diagnosticCollector, className,
                    this.memoryJavaFileManage.getCompiledBytes(className));
        }
    }

    /**
     * 通过代码简单解析全限定类名
     * 
     * @param content
     * @return
     */
    private String getFullClassName(String content) {
        StringBuilder builder = new StringBuilder();
        Matcher matcher = packagePattern.matcher(content);
        if (matcher.find()) {
            builder.append(matcher.group(1)).append(".");
        }
        matcher = classPattern.matcher(content);
        if (matcher.find()) {
            builder.append(matcher.group(1));
        } else {
            throw new IllegalArgumentException("无法获得类名称");
        }
        return builder.toString();
    }

    /**
     * 类编译锁
     * 
     * @param className 类名
     * @return 锁对象
     */
    private Object getClassCompilingLock(String className) {
        Object lock = this;
        if (this.parallelLockMap != null) {
            Object newLock = new Object();
            lock = this.parallelLockMap.putIfAbsent(className, newLock);
            if (lock == null) {
                lock = newLock;
            }
        }
        return lock;
    }

    @Override
    public void close() throws IOException {
        if (this.memoryJavaFileManage != null) {
            this.memoryJavaFileManage.close();
        }
    }
}
