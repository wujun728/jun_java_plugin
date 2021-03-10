package com.alibaba.boot.dubbo.zipkin;


import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;

/**
 * Created by wuyu on 2017/5/3.
 */
public class ConsumerSpan implements Filter {


    @Autowired
    private Tracer tracer;

    @Autowired
    private DubboSpanInjector dubboSpanInjector;

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        URL url = invoker.getUrl();
        String spanName = url.getServiceInterface() + "." + invocation.getMethodName();
        Span newSpan = tracer.createSpan(spanName);
        try {
            dubboSpanInjector.inject(newSpan, RpcContext.getContext());
            newSpan.logEvent(Span.CLIENT_SEND);
            return invoker.invoke(invocation);
        } catch (RpcException e) {
            newSpan.tag("error", e.getMessage());
            throw e;
        } finally {
            if (tracer.isTracing()) {
                tracer.getCurrentSpan().logEvent(Span.CLIENT_RECV);
                tracer.close(tracer.getCurrentSpan());
            }
        }
    }

}
