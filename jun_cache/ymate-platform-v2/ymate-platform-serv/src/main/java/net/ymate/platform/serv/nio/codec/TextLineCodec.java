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
 * @author 刘镇 (suninformation@163.com) on 15/11/20 下午2:52
 * @version 1.0
 */
public class TextLineCodec implements INioCodec {

    private static final Log _LOG = LogFactory.getLog(TextLineCodec.class);

    private static final String TEXT_EOF = "\r\n";

    private String __charset;

    public ICodec init(String charset) {
        __charset = StringUtils.defaultIfBlank(charset, "UTF-8");
        return this;
    }

    public ByteBufferBuilder encode(Object message) {
        try {
            String _msgStr = message.toString().concat(TEXT_EOF);
            byte[] _bytes = _msgStr.getBytes(__charset);
            return ByteBufferBuilder.allocate(_bytes.length).append(_bytes).flip();
        } catch (UnsupportedEncodingException e) {
            _LOG.warn(e.getMessage(), RuntimeUtils.unwrapThrow(e));
        }
        return null;
    }

    public Object decode(ByteBufferBuilder buffer) {
        try {
            int _counter = 0;
            ByteBufferBuilder _tmpBuffer = ByteBufferBuilder.allocate();
            do {
                byte b = buffer.get();
                switch (b) {
                    case '\r':
                        break;
                    case '\n':
                        if (_tmpBuffer.buffer() == null ) {
                            break;
                        }
                        byte[] _bytes = new byte[_counter];
                        _tmpBuffer.flip().get(_bytes);
                        return new String(_bytes, __charset);
                    default:
                        _tmpBuffer.append(b);
                        _counter++;
                }
            } while (buffer.buffer().hasRemaining());
        } catch (UnsupportedEncodingException e) {
            _LOG.warn(e.getMessage(), RuntimeUtils.unwrapThrow(e));
        }
        return null;
    }
}
