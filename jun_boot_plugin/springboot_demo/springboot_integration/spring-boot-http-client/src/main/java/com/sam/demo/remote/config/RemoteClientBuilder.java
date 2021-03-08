package com.sam.demo.remote.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class RemoteClientBuilder implements EnvironmentAware {
    private static final Logger log = LoggerFactory.getLogger(RemoteClientBuilder.class);

    private Map<String, Object> clients = new ConcurrentHashMap<>();

    private Environment env;

    private HttpInvokerRequestExecutor httpInvokerRequestExecutor;
    
    @Value("${remote.maxTotalConn:0}")
    private int maxTotalConn; // 最大链接数, 默认为0, 使用simple模式
    
    @Value("${remote.maxConnPerRoute:200}")
    private int maxConnPerRoute; // 单URL并发链接数

    @Value("${remote.connectTimeout:2000}")
    private int connectTimeout; // 建立链接超时 2s

    @Value("${remote.readTimeout:30000}")
    private int readTimeout; // 数据读取超时 30s

    public HttpInvokerRequestExecutor getHttpInvokerRequestExecutor() {
        if (this.httpInvokerRequestExecutor == null) {
            if (maxTotalConn > 0) {
                HttpComponentsHttpInvokerRequestExecutor executor = new HttpComponentsHttpInvokerRequestExecutor();
                executor.setHttpClient(createDefaultHttpClient());
                executor.setConnectTimeout(connectTimeout);
                executor.setReadTimeout(readTimeout);
                this.httpInvokerRequestExecutor = executor;
            } else {
                SimpleHttpInvokerRequestExecutor executor = new SimpleHttpInvokerRequestExecutor();
                executor.setBeanClassLoader(ClassUtils.getDefaultClassLoader());
                executor.setConnectTimeout(connectTimeout);
                executor.setReadTimeout(readTimeout);
                this.httpInvokerRequestExecutor = executor;
            }
        }
        return this.httpInvokerRequestExecutor;
    }

    public void setHttpInvokerRequestExecutor(HttpInvokerRequestExecutor httpInvokerRequestExecutor) {
        this.httpInvokerRequestExecutor = httpInvokerRequestExecutor;
    }
    
    private HttpClient createDefaultHttpClient() {
        Registry<ConnectionSocketFactory> schemeRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(schemeRegistry);
        connectionManager.setMaxTotal(maxTotalConn);
        connectionManager.setDefaultMaxPerRoute(maxConnPerRoute);

        return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    public <T> T build(Class<T> serviceInterface, String url) {
        return build(serviceInterface, url, RemoteType.HTTP, null);
    }

    @SuppressWarnings("unchecked")
    public <T> T build(Class<T> serviceInterface, String url, RemoteType type, String name) {
        Object value = null;

        synchronized (clients) {
            String key = serviceInterface.getName();

            // 从缓存中获取 @RemoteClient 的实例
            value = clients.get(key);
            if (value == null) {
                if (StringUtils.isEmpty(name)) {
                    name = serviceInterface.getSimpleName();
                }

                url = resolverUrl(url);
                if (!url.endsWith(name)) {
                    if (url.endsWith("/")) {
                        url += name;
                    } else {
                        url += ("/" + name);
                    }
                }

                if (RemoteType.HTTP == type) {
                    HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
                    factory.setServiceUrl(url);
                    factory.setServiceInterface(serviceInterface);
                    factory.setHttpInvokerRequestExecutor(getHttpInvokerRequestExecutor());
                    factory.afterPropertiesSet();
                    
                    value = factory.getObject();
                } else if (RemoteType.HESSIAN == type) {
                    HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
                    factory.setServiceUrl(url);
                    factory.setServiceInterface(serviceInterface);
                    factory.afterPropertiesSet();

                    value = factory.getObject();
                } else if (RemoteType.RMI == type) {
                    RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
                    factory.setServiceUrl(url);
                    factory.setServiceInterface(serviceInterface);
                    factory.afterPropertiesSet();

                    value = factory.getObject();
                }

                // 缓存 @RemoteClient 的实例
                clients.put(key, value);
            } else {
                log.debug("Build remote client class:{} from cache ", serviceInterface.getName());
            }
        }

        return (T) value;
    }

    private String resolverUrl(String url) {
        url = url.trim();

        if (url.startsWith("${") && url.endsWith("}")) {
            url = url.substring(2, url.length() - 1);

            url = env.getProperty(url);
        }

        return url;
    }
}
