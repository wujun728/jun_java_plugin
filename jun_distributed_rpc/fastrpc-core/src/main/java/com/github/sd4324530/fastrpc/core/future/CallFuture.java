package com.github.sd4324530.fastrpc.core.future;

import com.github.sd4324530.fastrpc.core.exception.FastrpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 请求句柄
 * @author peiyu
 */
public class CallFuture<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private T value;

    private final Semaphore semaphore;

    public CallFuture(){
        this.semaphore = new Semaphore(0);
    }

    public void setValue(T value) {
        this.value = value;
        this.semaphore.release();
    }

    public T getValue() {
        try {
            if(!this.semaphore.tryAcquire(5, TimeUnit.SECONDS)) {
                throw new FastrpcException("RPC调用超时，5秒没有返回结果..");
            }
        } catch (InterruptedException e) {
            log.error("阻塞异常", e);
        }
        return this.value;
    }
}
