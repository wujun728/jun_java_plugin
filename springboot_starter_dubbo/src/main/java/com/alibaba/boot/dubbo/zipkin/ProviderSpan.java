package com.alibaba.boot.dubbo.zipkin;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanReporter;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.sampler.NeverSampler;

import java.util.Map;

/**
 * Created by wuyu on 2017/5/3.
 */
public class ProviderSpan implements Filter {


    @Autowired
    private Tracer tracer;

    @Autowired
    private SpanReporter spanReporter;

    @Autowired
    private DubboSpanExtractor dubboSpanExtractor;

    @Autowired
    private DubboSpanInjector dubboSpanInjector;

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        URL url = invoker.getUrl();

        Span span = null;
        try {
            Map<String, String> attachments = RpcContext.getContext().getAttachments();
            String spanName = url.getServiceInterface() + "." + invocation.getMethodName();
            Span parent = dubboSpanExtractor.joinTrace(RpcContext.getContext());
            boolean skip = Span.SPAN_NOT_SAMPLED.equals(attachments.get(Span.SAMPLED_NAME));
            if (parent != null) {
                span = tracer.createSpan(spanName, parent);
                if (parent.isRemote()) {
                    parent.logEvent(Span.SERVER_RECV);
                }
            } else {
                if (skip) {
                    span = tracer.createSpan(spanName, NeverSampler.INSTANCE);
                } else {
                    span = tracer.createSpan(spanName);
                }
                span.logEvent(Span.SERVER_RECV);
            }

            dubboSpanInjector.inject(span, RpcContext.getContext());
            return invoker.invoke(invocation);

        } catch (RpcException e) {
            span.tag("error", e.getMessage());
            throw e;
        } finally {
            if (span != null) {
                if (span.hasSavedSpan()) {
                    Span parent = span.getSavedSpan();
                    if (parent.isRemote()) {
                        parent.logEvent(Span.SERVER_SEND);
                        parent.stop();
                        spanReporter.report(parent);
                    }
                } else {
                    span.logEvent(Span.SERVER_SEND);
                }
                tracer.close(span);
            }
        }

    }

}
