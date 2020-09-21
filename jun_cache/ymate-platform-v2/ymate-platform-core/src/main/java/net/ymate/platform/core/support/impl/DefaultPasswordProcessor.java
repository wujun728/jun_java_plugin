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
package net.ymate.platform.core.support.impl;

import net.ymate.platform.core.support.IPasswordProcessor;
import net.ymate.platform.core.util.CodecUtils;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 默认密码处理器类
 *
 * @author 刘镇 (suninformation@163.com) on 15/4/13 下午3:47
 * @version 1.0
 */
public class DefaultPasswordProcessor implements IPasswordProcessor {

    private final String __KEY = DigestUtils.md5Hex(DefaultPasswordProcessor.class.getName());

    public String encrypt(String source) throws Exception {
        return CodecUtils.DES.encrypt(source, __KEY);
    }

    public String decrypt(String target) throws Exception {
        return CodecUtils.DES.decrypt(target, __KEY);
    }
}
