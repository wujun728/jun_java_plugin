/**
 * Copyright 1999-2014 dangdang.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.redis.proxy.client.io;

import com.redis.proxy.client.factory.KryoFactory;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 *
 * @author zhanggaofeng
 */
public class KryoObjectInput {

        private Kryo kryo = KryoFactory.getDefaultFactory().getKryo();
        private final Input input;

        public KryoObjectInput(byte[] data) {
                input = new Input(new ByteArrayInputStream(data));
        }

        public boolean readBool() throws IOException {
                try {
                        return input.readBoolean();
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public byte readByte() throws IOException {
                try {
                        return input.readByte();
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public short readShort() throws IOException {
                try {
                        return input.readShort();
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public int readInt() throws IOException {
                try {
                        return input.readInt();
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public long readLong() throws IOException {
                try {
                        return input.readLong();
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public float readFloat() throws IOException {
                try {
                        return input.readFloat();
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public double readDouble() throws IOException {
                try {
                        return input.readDouble();
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public byte[] readBytes() throws IOException {
                try {
                        int len = input.readInt();
                        if (len < 0) {
                                return null;
                        } else if (len == 0) {
                                return new byte[]{};
                        } else {
                                return input.readBytes(len);
                        }
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public String readUTF() throws IOException {
                try {
                        return input.readString();
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public Object readObject() throws IOException, ClassNotFoundException {
                try {
                        return kryo.readClassAndObject(input);
                } catch (KryoException e) {
                        throw new IOException(e);
                }
        }

        public <T> T readObject(Class<T> clazz) throws IOException, ClassNotFoundException {
                return (T) readObject();
        }

        public <T> T readObject(Class<T> clazz, Type type) throws IOException, ClassNotFoundException {
                return (T) readObject(clazz);
        }

        public void cleanup() {
                KryoFactory.getDefaultFactory().returnKryo(kryo);
                kryo = null;
        }
}
