package com.alibaba.dubbo.rpc.protocol.feign;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.RpcException;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.ribbon.RibbonResourceFactory;
import com.netflix.ribbon.RibbonTransportFactory;
import com.netflix.ribbon.proxy.RibbonDynamicProxy;

/**
 * Created by wuyu on 2016/4/6.
 */
public class RibbonProtocol extends FeignProtocol {

    @Override
    protected <T> T doRefer(final Class<T> type, URL url) throws RpcException {

        final int timeout = url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
        final int connections = url.getParameter(Constants.CONNECTIONS_KEY, 20);
        final int retries = url.getParameter(Constants.RETRIES_KEY, 0);


        String schema = "http://";
        if (url.getProtocol().equalsIgnoreCase("ribbons")) {
            schema = "https://";
        }

        final String api = schema + url.getHost() + ":" + url.getPort();

        ClientConfigFactory clientConfigFactory = new ClientConfigFactory() {
            @Override
            public IClientConfig newConfig() {
                return new DefaultClientConfigImpl(type.getName())
                        .set(IClientConfigKey.Keys.ListOfServers, api)
                        .set(IClientConfigKey.Keys.ConnectTimeout, timeout)
                        .set(IClientConfigKey.Keys.ReadTimeout, timeout)
                        .set(IClientConfigKey.Keys.MaxAutoRetries, retries)
                        .set(IClientConfigKey.Keys.MaxTotalConnections, connections)
                        .set(IClientConfigKey.Keys.MaxConnectionsPerHost, connections);
            }
        };

        return RibbonDynamicProxy.newInstance(type, RibbonResourceFactory.DEFAULT, clientConfigFactory, RibbonTransportFactory.DEFAULT);
    }

}