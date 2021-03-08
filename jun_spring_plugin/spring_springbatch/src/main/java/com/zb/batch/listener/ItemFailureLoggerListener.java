package com.zb.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.stereotype.Component;

/**
 * 错误监听处理
 * 
 * 作者: zhoubang 
 * 日期：2015年7月27日 上午9:10:01
 */
@Component("itemFailureLoggerListener")
public class ItemFailureLoggerListener extends
        ItemListenerSupport<Object, Object> {
    private final static Logger LOG = LoggerFactory
            .getLogger(ItemFailureLoggerListener.class);

    public void onReadError(Exception ex) {
        LOG.error("Encountered error on read", ex);
    }

    public void onWriteError(Exception ex, Object item) {
        LOG.error("Encountered error on write", ex);
    }

    public ItemFailureLoggerListener() {
    }
}
