package com.alibaba.dubbo.rpc.protocol.feign;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.spring.extension.SpringExtensionFactory;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProxyProtocol;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import feign.Feign;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.Target;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.net.ssl.SSLContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by wuyu on 2016/4/6.
 */
public class FeignProtocol extends AbstractProxyProtocol {


    private static ObjectFactory<HttpMessageConverters> objectFactory = new ObjectFactory<HttpMessageConverters>() {
        @Override
        public HttpMessageConverters getObject() throws BeansException {
            return getApplicationContext().getBean(HttpMessageConverters.class);
        }
    };

    @Override
    protected <T> Runnable doExport(T impl, final Class<T> type, URL url) throws RpcException {

        //不做任何操作,由springboot对外提供该服务，dubbo只负责注册服务
        return new Runnable() {
            @Override
            public void run() {
                RequestMappingHandlerMapping requestMappingHandlerMapping = getApplicationContext().getBean(RequestMappingHandlerMapping.class);
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
                    Object bean = entry.getValue().getBean();
                    if (type.isAssignableFrom(AopUtils.getTargetClass(bean))) {
                        requestMappingHandlerMapping.unregisterMapping(entry.getKey());
                    }
                }
            }
        };
    }

    @Override
    protected <T> T doRefer(Class<T> type, URL url) throws RpcException {

        int timeout = url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
        int connections = url.getParameter(Constants.CONNECTIONS_KEY, 20);
        int retries = url.getParameter(Constants.RETRIES_KEY, 0);


        String schema = "http://";
        if (url.getProtocol().equalsIgnoreCase("feigns")) {
            schema = "https://";
        }

        String api = schema + url.getHost() + ":" + url.getPort();

        FeignClient feignClient = type.getAnnotation(FeignClient.class);


        if (feignClient != null) {
            //如果feign注解携带url，将以url为准
            if (!StringUtils.isBlank(feignClient.url())) {
                api = getEnvironment().resolvePlaceholders(feignClient.url());
            }

            if (!StringUtils.isBlank(feignClient.path())) {
                api = api + ("/" + feignClient.path()).replaceAll("[/]+", "/");
            }
        }

        return target(api, type, connections, timeout, retries);
    }

    @Override
    public int getDefaultPort() {
        //该端口与springboot保持一致
        String port = getEnvironment().resolvePlaceholders("${server.port}");
        return !StringUtils.isBlank(port) ? Integer.parseInt(port) : 8080;
    }

    public static <T> T target(Class<T> type) {
        return target(getEnvironment().resolvePlaceholders(type.getAnnotation(FeignClient.class).url()), type, 20, 3000, 0);
    }

    public static <T> T target(String url, Class<T> type) {
        return target(url, type, 20, 3000, 0);
    }

    public static <T> T target(String url, Class<T> type, final int connections, final int timeout, int retries) {
        SSLContext sslContext = SSLContexts.createSystemDefault();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();

        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(manager)
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(connections)
                .setMaxConnTotal(connections)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .build();

        FeignClient feignClient = type.getAnnotation(FeignClient.class);
        SetterFactory setterFactory = new SetterFactory() {
            @Override
            public HystrixCommand.Setter create(Target<?> target, Method method) {
                String groupKey = target.name();
                String commandKey = Feign.configKey(target.type(), method);
                return HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(timeout)
                                .withExecutionIsolationSemaphoreMaxConcurrentRequests(connections))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
            }
        };

        HystrixFeign.Builder builder = HystrixFeign.builder()
                .setterFactory(setterFactory)
                .requestInterceptors(getApplicationContext().getBeansOfType(RequestInterceptor.class).values())
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new SpringDecoder(objectFactory))
                .client(new ApacheHttpClient(httpClient))
                .errorDecoder(new ErrorDecoder.Default())
                .retryer(new Retryer.Default(100, 500, retries));


        if (feignClient != null) {
            Class<?> fallbackFactory = feignClient.fallbackFactory();
            if (void.class != fallbackFactory) {
                if (void.class != fallbackFactory) {
                    setterFactory = getApplicationContext().getBean(SetterFactory.class);
                }
            }

            builder.setterFactory(setterFactory);

            if (feignClient.decode404()) {
                builder.decode404();
            }

            Class<?> fallback = feignClient.fallback();
            if (void.class != fallback) {
                try {
                    Map<String, ?> beansOfType = getApplicationContext().getBeansOfType(fallback);
                    if (beansOfType.size() > 0) {
                        return builder.target(type, url, (T) getApplicationContext().getBean(fallback));
                    }
                    return builder.target(type, url, (T) fallback.newInstance());

                } catch (Exception e) {
                    throw new RpcException(e);
                }
            }
        }
        return builder.target(type, url);
    }


    @SuppressWarnings("unchecked")
    private static WebApplicationContext getApplicationContext() {
        Field contextsFiled = ReflectionUtils.findField(SpringExtensionFactory.class, "contexts");
        contextsFiled.setAccessible(true);
        for (ApplicationContext applicationContext : (Set<ApplicationContext>) ReflectionUtils.getField(contextsFiled, null)) {
            if (applicationContext instanceof WebApplicationContext) {
                return (WebApplicationContext) applicationContext;
            }
        }
        throw new RpcException("not found webApplicationContext!");
    }

    private static Environment getEnvironment() {
        return getApplicationContext().getEnvironment();
    }


}