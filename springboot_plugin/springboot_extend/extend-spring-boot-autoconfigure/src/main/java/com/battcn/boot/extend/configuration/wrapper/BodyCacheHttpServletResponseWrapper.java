package com.battcn.boot.extend.configuration.wrapper;

import org.apache.commons.io.output.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * QQ交流群：391619659
 *
 * @author Levin
 * @since 2018-12-17
 */
public class BodyCacheHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private PrintWriter writer = new PrintWriter(byteArrayOutputStream);

    BodyCacheHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public byte[] getBody() {
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                TeeOutputStream write = new TeeOutputStream(BodyCacheHttpServletResponseWrapper.super.getOutputStream(), byteArrayOutputStream);
                write.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new ServletPrintWriter(super.getWriter(), writer);
    }

    private static class ServletPrintWriter extends PrintWriter {

        PrintWriter printWriter;

        ServletPrintWriter(PrintWriter main, PrintWriter printWriter) {
            super(main, true);
            this.printWriter = printWriter;
        }

        @Override
        public void write(char[] buff, int off, int len) {
            super.write(buff, off, len);
            super.flush();
            printWriter.write(buff, off, len);
            printWriter.flush();
        }

        @Override
        public void write(String s, int off, int len) {
            super.write(s, off, len);
            super.flush();
            printWriter.write(s, off, len);
            printWriter.flush();
        }

        @Override
        public void write(int c) {
            super.write(c);
            super.flush();
            printWriter.write(c);
            printWriter.flush();
        }

        @Override
        public void flush() {
            super.flush();
            printWriter.flush();
        }
    }
}