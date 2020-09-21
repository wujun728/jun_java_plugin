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
package net.ymate.platform.webmvc;

import net.ymate.platform.cache.CacheElement;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.webmvc.base.Type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author 刘镇 (suninformation@163.com) on 16/3/28 下午10:55
 * @version 1.0
 */
public class PageMeta extends CacheElement {

    private static final int FOUR_KB = 4196;
    private static final int GZIP_MAGIC_NUMBER_BYTE_1 = 31;
    private static final int GZIP_MAGIC_NUMBER_BYTE_2 = -117;
    private static final int ONE_YEAR_IN_SECONDS = 60 * 60 * 24 * 365;
    private final Map<String, PairObject<Type.HeaderType, Object>> responseHeaders = new TreeMap<String, PairObject<Type.HeaderType, Object>>(String.CASE_INSENSITIVE_ORDER);
    private String contentType;
    private byte[] gzippedBody;
    private byte[] ungzippedBody;
    private boolean storeGzipped;

    public PageMeta(final String contentType,
                    final Map<String, PairObject<Type.HeaderType, Object>> headers,
                    final byte[] body,
                    boolean storeGzipped) throws Exception {

        if (headers != null) {
            this.responseHeaders.putAll(headers);
        }
        this.contentType = contentType;
        this.storeGzipped = storeGzipped;
        //
        if (storeGzipped) {
            ungzippedBody = null;
            if (isBodyParameterGzipped()) {
                gzippedBody = body;
            } else {
                gzippedBody = gzip(body);
            }
        } else {
            if (isBodyParameterGzipped()) {
                throw new IllegalArgumentException("Non gzip content has been gzipped.");
            } else {
                ungzippedBody = body;
            }
        }
    }

    private byte[] gzip(byte[] ungzipped) throws IOException {
        if (!isGzipped(ungzipped)) {
            final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bytes);
            gzipOutputStream.write(ungzipped);
            gzipOutputStream.close();
            return bytes.toByteArray();
        }
        return ungzipped;
    }

    private boolean isBodyParameterGzipped() {
        return this.responseHeaders.containsKey("gzip");
    }

    private static boolean isGzipped(byte[] candidate) {
        return !(candidate == null || candidate.length < 2) && (candidate[0] == GZIP_MAGIC_NUMBER_BYTE_1 && candidate[1] == GZIP_MAGIC_NUMBER_BYTE_2);
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getGzippedBody() {
        if (storeGzipped) {
            return gzippedBody;
        }
        return null;
    }

    public Map<String, PairObject<Type.HeaderType, Object>> getHeaders() {
        return this.responseHeaders;
    }

    public byte[] getUngzippedBody() throws IOException {
        if (storeGzipped) {
            return ungzip(gzippedBody);
        }
        return ungzippedBody;
    }

    @Override
    public void setTimeout(int timeout) {
        if (timeout == 0 || timeout > ONE_YEAR_IN_SECONDS) {
            super.setTimeout(ONE_YEAR_IN_SECONDS);
        } else {
            super.setTimeout(timeout);
        }
    }

    private byte[] ungzip(final byte[] gzipped) throws IOException {
        final GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(gzipped));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(gzipped.length);
        final byte[] buffer = new byte[FOUR_KB];
        int bytesRead = 0;
        while (bytesRead != -1) {
            bytesRead = inputStream.read(buffer, 0, FOUR_KB);
            if (bytesRead != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        }
        byte[] ungzipped = byteArrayOutputStream.toByteArray();
        inputStream.close();
        byteArrayOutputStream.close();
        return ungzipped;
    }

    public boolean isStoreGzipped() {
        return storeGzipped && gzippedBody != null;
    }
}
