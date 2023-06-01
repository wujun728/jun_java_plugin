/*
 * Copyright (C) 2016-2017 mzlion(mzllon@qq.com).
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
package com.mzlion.easyokhttp.http;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.*;

import java.io.IOException;

/**
 * 处理提交进度，一般对有进度展示需求的有用
 *
 * @author mzlion on 2016-12-14
 */
public class ProcessRequestBody extends RequestBody {

    private RequestBody delegate;
    private Listener listener;

    public ProcessRequestBody(RequestBody delegate, Listener listener) {
        this.delegate = delegate;
        this.listener = listener;
    }

    /**
     * Returns the Content-Type header for this body.
     */
    @Override
    public MediaType contentType() {
        return this.delegate.contentType();
    }

    /**
     * Returns the number of bytes that will be written to {@code out} in a call to {@link #writeTo},
     * or -1 if that count is unknown.
     */
    @Override
    public long contentLength() throws IOException {
        return this.delegate.contentLength();
    }

    /**
     * Writes the content of this request to {@code out}.
     *
     * @param sink {@link BufferedSink}
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        ProgressSink progressSink = new ProgressSink(sink);
        BufferedSink bufferedSink = Okio.buffer(progressSink);
        this.delegate.writeTo(bufferedSink);
        bufferedSink.flush();//必须调用flush保证都写入完成
    }

    /**
     * 计算字节处理进度
     */
    private final class ProgressSink extends ForwardingSink {

        private long bytesWritten;//当前写入字节数

        ProgressSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            this.bytesWritten += byteCount;
            listener.onRequestProgress(this.bytesWritten, contentLength());
        }
    }


    /**
     * 回调接口
     */
    public interface Listener {
        void onRequestProgress(final long bytesWritten, final long contentLength);
    }
}
