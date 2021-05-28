package com.mall.admin.interceptor;



import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 *  Stream 流只能读取一次
 *  解决拦截器中获取json读取了request中的流的数据，再次读取该流时（如@requestBody），
 *  由于流中的数据已经没了，所以第二次读取的时候就会抛出异常。
 *  详情参考 https://blog.csdn.net/mazhen1991/article/details/80695473
 *   解决方案：定义一个过滤器将流中的数据读取到一个数组中，并重写getInputStream()和getRead()方法，
 *   后续获取流中的数据的时候，直接去数组中读取
 * @author Wujun
 * @version 1.0
 * @create_at 2018/12/22 20:48
 */
@Component
public class StreamFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    static class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private final byte[] body;

        public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            body = toByteArray(request.getInputStream());
        }

        private byte[] toByteArray(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int i = 0;
            while ((i = in.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
            return out.toByteArray();
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }


        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            return new ServletInputStreamImpl(byteArrayInputStream);
        }
    }

    private static class ServletInputStreamImpl extends ServletInputStream {

        private ByteArrayInputStream byteArrayInputStream;

        public ServletInputStreamImpl(ByteArrayInputStream byteArrayInputStream) {
            this.byteArrayInputStream = byteArrayInputStream;
        }

        @Override
        public boolean isFinished() {
            return false;
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
            return byteArrayInputStream.read();
        }
    }
}
