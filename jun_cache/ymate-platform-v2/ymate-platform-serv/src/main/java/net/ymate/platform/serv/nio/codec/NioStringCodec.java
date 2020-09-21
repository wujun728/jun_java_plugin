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
package net.ymate.platform.serv.nio.codec;

import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.serv.ICodec;
import net.ymate.platform.serv.nio.INioCodec;
import net.ymate.platform.serv.nio.support.ByteBufferBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/15 下午7:05
 * @version 1.0
 */
public class NioStringCodec implements INioCodec {

    private static final Log _LOG = LogFactory.getLog(NioStringCodec.class);

    private String __charset;

    public ICodec init(String charset) {
        __charset = StringUtils.defaultIfBlank(charset, "UTF-8");
        return this;
    }

    public ByteBufferBuilder encode(Object message) {
        if (message instanceof String) {
            try {
                byte[] _bytes = ((String) message).getBytes(__charset);
                return ByteBufferBuilder.allocate()
                        .append(_bytes.length)
                        .append(_bytes).flip();
            } catch (UnsupportedEncodingException e) {
                _LOG.warn(e.getMessage(), RuntimeUtils.unwrapThrow(e));
            }
        }
        return null;
    }

    public Object decode(ByteBufferBuilder buffer) {
        if (buffer.remaining() < 4) {
            return null;
        }
        buffer.mark();
        int _len = buffer.getInt();
        if (buffer.remaining() < _len) {
            buffer.reset();
            return null;
        }
        byte[] _bytes = new byte[_len];
        buffer.get(_bytes);
        try {
            return new String(_bytes, __charset);
        } catch (UnsupportedEncodingException e) {
            _LOG.warn(e.getMessage(), RuntimeUtils.unwrapThrow(e));
        }
        return null;
    }
}
