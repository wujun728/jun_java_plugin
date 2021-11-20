package com.alibaba.boot.dubbo.zipkin;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;

/**
 * Created by wuyu on 2017/5/3.
 */
@Activate(group = {Constants.PROVIDER, Constants.CONSUMER}, value = "zipkinFilter")
public class ZipkinFilter implements Filter {

    @Autowired
    private ProviderSpan providerSpan;

    @Autowired
    private ConsumerSpan consumerSpan;

    @Autowired
    private Tracer tracer;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String sideKey = invoker.getUrl().getParameter(Constants.SIDE_KEY);

        if (Constants.PROVIDER.equalsIgnoreCase(sideKey)) {
            return providerSpan.invoke(invoker, invocation);
        } else if (Constants.CONSUMER.equalsIgnoreCase(sideKey)) {
            return consumerSpan.invoke(invoker, invocation);
        }


        return invoker.invoke(invocation);
    }


}
