package com.zccoder.java.book1.ch5.block;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 标题：清单5.12 使用FutureTask预载稍后需要的数据<br>
 * 描述：<br>
 * 时间：2018/10/26<br>
 *
 * @author zc
 **/
public class Preloader {

    private final FutureTask<ProductInfo> future = new FutureTask<>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws Exception {
            // 返回加载对象
            return loadProductInfo();
        }
    });
    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException ex) {
            Throwable cause = ex.getCause();
            throw launderThrowable(cause);
        }
    }

    private static RuntimeException launderThrowable(Throwable cause) {
        if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        } else if (cause instanceof Error) {
            throw (Error) cause;
        } else {
            throw new IllegalStateException("No unchecked", cause);
        }
    }

    private ProductInfo loadProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("预加载");
        return productInfo;
    }

}
