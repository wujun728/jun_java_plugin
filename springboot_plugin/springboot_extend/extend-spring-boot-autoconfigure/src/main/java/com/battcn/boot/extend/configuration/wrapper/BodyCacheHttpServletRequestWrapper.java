package com.battcn.boot.extend.configuration.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * QQ交流群：391619659
 *
 * @author Levin
 * @since 2018-12-17
 */
public class BodyCacheHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final static byte[] DEFAULT_BYTE = new byte[0];
    private byte[] body;
    private ServletInputStreamWrapper inputStreamWrapper;

    BodyCacheHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = IOUtils.toByteArray(request.getInputStream());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body != null ? this.body : DEFAULT_BYTE);
        // 初始 ServletInputStreamWrapper
        this.inputStreamWrapper = new ServletInputStreamWrapper(byteArrayInputStream);
        // 设置 InputStream 到我们自己的包装类中
        this.inputStreamWrapper.setInputStream(byteArrayInputStream);
    }


    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.inputStreamWrapper;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.inputStreamWrapper));
    }


    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    private static class ServletInputStreamWrapper extends ServletInputStream {

        private InputStream inputStream;

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }
    }
}