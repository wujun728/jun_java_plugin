package org.frameworkset.spi.remote.http.ssl;
/**
 * Copyright 2008 biaoping.yin
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.nio.ByteBuffer;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2020/1/15 10:28
 * @author biaoping.yin
 * @version 1.0
 */
public class EmptyArrays {
	public static final int[] EMPTY_INTS = new int[0];
	public static final byte[] EMPTY_BYTES = new byte[0];
	public static final char[] EMPTY_CHARS = new char[0];
	public static final Object[] EMPTY_OBJECTS = new Object[0];
	public static final Class<?>[] EMPTY_CLASSES = new Class[0];
	public static final String[] EMPTY_STRINGS = new String[0];
	public static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
	public static final ByteBuffer[] EMPTY_BYTE_BUFFERS = new ByteBuffer[0];
	public static final Certificate[] EMPTY_CERTIFICATES = new Certificate[0];
	public static final X509Certificate[] EMPTY_X509_CERTIFICATES = new X509Certificate[0];
	public static final javax.security.cert.X509Certificate[] EMPTY_JAVAX_X509_CERTIFICATES = new javax.security.cert.X509Certificate[0];

	private EmptyArrays() {
	}
}
