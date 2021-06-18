package org.ws.httphelper.model.config;

import java.util.Map;

/**
 * Created by Administrator on 15-12-29.
 */
public class HttpClientConfig extends WSHttpHelperConfigData {

    public HttpClientConfig(Map<String, Object> data) {
        super(data);
    }

    public String getHttpCharset() {
        return getValue("http:charset");
    }

    public int getConnectionTimeout() {
        return getInt("http:connection-timeout");
    }

    public int getSocketTimeout() {
        return getInt("http:socket-timeout");
    }

    public int getPoolQueueCapacity() {
        return getInt("pool:QueueCapacity");
    }

    public int getPoolMaxPoolSize() {
        return getInt("pool:MaxPoolSize");
    }

    public int getCorePoolSize() {
        return getInt("pool:CorePoolSize");
    }

    public int getKeepAliveSeconds() {
        return getInt("pool:KeepAliveSeconds");
    }

}
