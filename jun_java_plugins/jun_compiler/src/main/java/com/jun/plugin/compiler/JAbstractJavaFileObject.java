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

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * Java文件对象，自动转换URI
 * 
 * @author jianggujin
 *
 */
public abstract class JAbstractJavaFileObject extends SimpleJavaFileObject {

    public JAbstractJavaFileObject(String className, Kind kind) {
        super(URI.create("string:///" + className.replace(".", "/") + Kind.SOURCE.extension), kind);
    }
}
