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

/**
 * 用于存储Java源码
 * 
 * @author jianggujin
 *
 */
public class JStringJavaFileObject extends JAbstractJavaFileObject {
    /**
     * 等待编译的源码内容
     */
    private String content;

    /**
     * 遵循Java规范的类名及文件
     * 
     */
    public JStringJavaFileObject(String className, String content) {
        super(className, Kind.SOURCE);
        this.content = content;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return content;
    }
}
