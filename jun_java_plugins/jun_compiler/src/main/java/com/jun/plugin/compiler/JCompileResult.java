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

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

/**
 * 编译结果
 * 
 * @author jianggujin
 *
 */
public class JCompileResult {
    /**
     * 编译结果
     */
    private final boolean success;
    /**
     * 编译用时
     */
    private final long useTime;
    /**
     * 编译诊断信息
     */
    private final DiagnosticCollector<JavaFileObject> diagnosticCollector;
    /**
     * 类名
     */
    private final String className;
    /**
     * 编译后的字节码
     */
    private final byte[] compiledBytes;
    /**
     * 编译后的类
     */
    private Class<?> compiledClass;

    public JCompileResult(boolean success, long useTime, DiagnosticCollector<JavaFileObject> diagnosticCollector,
            String className, byte[] compiledBytes) {
        this.success = success;
        this.useTime = useTime;
        this.diagnosticCollector = diagnosticCollector;
        this.className = className;
        this.compiledBytes = compiledBytes;
    }

    /**
     * 是否成功
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 获得编译使用时间
     * 
     */
    public long getUseTime() {
        return useTime;
    }

    public DiagnosticCollector<JavaFileObject> getDiagnosticCollector() {
        return diagnosticCollector;
    }

    /**
     * 将诊断结果转换为字符串
     * 
     */
    public String getDiagnostics() {
        StringBuilder builder = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> diagnostic : this.diagnosticCollector.getDiagnostics()) {
            builder.append(diagnostic.toString()).append("\r\n");
        }
        return builder.toString();
    }

    /**
     * 获得类名
     * 
     */
    public String getClassName() {
        return className;
    }

    /**
     * 获得编译后字节码的字节数组
     * 
     */
    public byte[] getCompiledBytes() {
        return compiledBytes;
    }

    /**
     * 创建对象
     * 
     */
    public Object newInstance() throws Exception {
        return this.getCompiledClass().newInstance();
    }

    /**
     * 获得编译后的类
     * 
     */
    public Class<?> getCompiledClass() {
        if (this.compiledClass == null) {
            synchronized (this) {
                if (this.compiledClass == null) {
                    this.compiledClass = new JCompileBytesClassLoader().loadCompileBytesClass(this.className,
                            this.compiledBytes);
                }
            }
        }
        return this.compiledClass;
    }

    @Override
    public String toString() {
        return "[success=" + success + ", useTime=" + useTime + ", className=" + className + ", diagnostics="
                + getDiagnostics() + "]";
    }

}
