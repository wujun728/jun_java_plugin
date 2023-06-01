package com.chitry.log4jmonitor.core;

import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;

import java.io.Writer;

/**
 * @author chitry@126.com
 * @date 2016年9月28日 上午11:37:44
 * @topic log4j日志输出
 * @description TODO
 */
public class Log4jAppender extends WriterAppender {

    private Writer writer = new Log4jAsyncWriter();

    public Log4jAppender() {
        setWriter(writer);
    }

    public Log4jAppender(Layout layout) {
        this();
        super.layout = layout;
    }

}
