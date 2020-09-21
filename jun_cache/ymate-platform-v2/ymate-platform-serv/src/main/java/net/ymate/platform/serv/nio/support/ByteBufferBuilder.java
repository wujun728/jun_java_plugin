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
package net.ymate.platform.serv.nio.support;

import java.nio.ByteBuffer;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/13 下午3:29
 * @version 1.0
 */
public final class ByteBufferBuilder {

    private ByteBuffer __buffer;

    public static ByteBufferBuilder allocate() {
        return new ByteBufferBuilder(0);
    }

    public static ByteBufferBuilder allocate(int capacity) {
        return new ByteBufferBuilder(capacity);
    }

    public static ByteBufferBuilder allocateDirect(int capacity) {
        return new ByteBufferBuilder(ByteBuffer.allocateDirect(capacity));
    }

    public static ByteBufferBuilder wrap(ByteBuffer buffer) {
        return new ByteBufferBuilder(buffer);
    }

    private ByteBufferBuilder(int capacity) {
        if (capacity > 0) {
            __buffer = ByteBuffer.allocate(capacity);
        }
    }

    private ByteBufferBuilder(ByteBuffer buffer) {
        __buffer = buffer;
    }

    private void __bufferSafety(int length) {
        if (__buffer == null) {
            __buffer = ByteBuffer.allocate(length * 2);
        } else {
            int _currentSize = __buffer.capacity();
            int _newSize = __buffer.position() + length;
            while (_newSize > _currentSize) {
                _currentSize = _currentSize * 2;
            }
            if (_currentSize != __buffer.capacity()) {
                ByteBuffer _newBuffer = null;
                if (__buffer.isDirect()) {
                    _newBuffer = ByteBuffer.allocateDirect(_currentSize);
                } else {
                    _newBuffer = ByteBuffer.allocate(_currentSize);
                }
                _newBuffer.put(__buffer.array());
                _newBuffer.position(__buffer.position());
                __buffer = _newBuffer;
            }
        }
    }

    public ByteBufferBuilder append(byte[] src, int offset, int length) {
        __bufferSafety(length);
        __buffer.put(src, offset, length);
        return this;
    }

    public ByteBufferBuilder append(byte value) {
        return append(new byte[]{value});
    }

    public ByteBufferBuilder append(byte[] src) {
        return append(src, 0, src.length);
    }

    public ByteBufferBuilder append(char c) {
        __bufferSafety(2);
        __buffer.putChar(c);
        return this;
    }

    public ByteBufferBuilder append(short value) {
        __bufferSafety(2);
        __buffer.putShort(value);
        return this;
    }

    public ByteBufferBuilder append(long value) {
        __bufferSafety(8);
        __buffer.putLong(value);
        return this;
    }

    public ByteBufferBuilder append(int value) {
        __bufferSafety(4);
        __buffer.putInt(value);
        return this;
    }

    public ByteBufferBuilder append(String value) {
        append(value.getBytes());
        return this;
    }

    public ByteBufferBuilder append(ByteBuffer buffer) {
        __bufferSafety(buffer.capacity());
        __buffer.put(buffer);
        return this;
    }

    public byte get() {
        return __buffer.get();
    }

    public ByteBufferBuilder get(byte[] dst) {
        __buffer.get(dst);
        return this;
    }

    public ByteBufferBuilder get(byte[] dst, int offset, int length) {
        __buffer.get(dst, offset, length);
        return this;
    }

    public short getShort() {
        return __buffer.getShort();
    }

    public int getInt() {
        return __buffer.getInt();
    }

    public long getLong() {
        return __buffer.getLong();
    }

    public final ByteBufferBuilder clear() {
        __buffer.clear();
        return this;
    }

    public final ByteBufferBuilder flip() {
        __buffer.flip();
        return this;
    }

    public final ByteBufferBuilder mark() {
        __buffer.mark();
        return this;
    }

    public final ByteBufferBuilder reset() {
        __buffer.reset();
        return this;
    }

    public final int remaining() {
        return __buffer.remaining();
    }

    public final ByteBufferBuilder rewind() {
        __buffer.rewind();
        return this;
    }

    public final int position() {
        return __buffer.position();
    }

    public final ByteBufferBuilder position(int newPosition) {
        __buffer.position(newPosition);
        return this;
    }

    public final int limit() {
        return __buffer.limit();
    }

    public final ByteBufferBuilder limit(int newLimit) {
        __buffer.limit(newLimit);
        return this;
    }

    public ByteBufferBuilder compact() {
        __buffer.compact();
        return this;
    }

    public ByteBufferBuilder duplicate() {
        return ByteBufferBuilder.wrap(__buffer.duplicate());
    }

    public final byte[] array() {
        return __buffer.array();
    }

    public ByteBuffer buffer() {
        return __buffer;
    }
}
