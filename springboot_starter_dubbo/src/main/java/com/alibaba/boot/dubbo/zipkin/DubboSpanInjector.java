package com.alibaba.boot.dubbo.zipkin;

import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanInjector;

import java.util.Map;

/**
 * Created by wuyu on 2017/5/3.
 */
public class DubboSpanInjector implements SpanInjector<RpcContext> {
    @Override
    public void inject(Span span, RpcContext carrier) {
        Map<String, String> attachments = carrier.getAttachments();
        if (span.getTraceId() != 0) {
            attachments.put(Span.TRACE_ID_NAME, Span.idToHex(span.getTraceId()));
        }
        if (span.getSpanId() != 0) {
            attachments.put(Span.SPAN_ID_NAME, Span.idToHex(span.getSpanId()));
        }
        attachments.put(Span.SAMPLED_NAME, span.isExportable() ? Span.SPAN_SAMPLED : Span.SPAN_NOT_SAMPLED);
        attachments.put(Span.SPAN_NAME_NAME, span.getName());
        Long parentId = getParentId(span);
        if (parentId != null && parentId != 0) {
            attachments.put(Span.PARENT_ID_NAME, Span.idToHex(parentId));
        }
        attachments.put(Span.PROCESS_ID_NAME, span.getProcessId());

    }

    private Long getParentId(Span span) {
        return !span.getParents().isEmpty() ? span.getParents().get(0) : null;
    }

}
