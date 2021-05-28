package com.alibaba.boot.dubbo.discovery;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;
import com.alibaba.dubbo.registry.hazelcast.HazelcastRegistry;
import com.alibaba.dubbo.registry.multicast.MulticastRegistry;
import com.alibaba.dubbo.registry.redis.RedisRegistry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperClient;
import com.alibaba.dubbo.rpc.RpcException;
import com.netflix.config.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PreDestroy;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by wuyu on 2017/4/22.
 */
public class DubboDiscoveryClient implements DiscoveryClient, InitializingBean {

    protected ServerProperties serverProperties;

    protected List<RegistryConfig> registryConfigs;

    protected String serviceId = "dubbo-gateway";

    protected Set<String> support = new HashSet<>(Arrays.asList("feign", "feigns", "ribbon", "ribbons", "springmvc", "rest", "webservice", "hessian",
            "burlap", "spark", "play", "jfinal", "ratpack", "jersey", "hprose", "http", "jsonrpc", "xmlrpc"));

    protected DubboApplicationEventPublisher dubboApplicationEventPublisher;

    protected Logger logger = LoggerFactory.getLogger(DubboDiscoveryClient.class);

    public DubboDiscoveryClient(ServerProperties serverProperties, String serviceId, List<RegistryConfig> registryConfigs, DubboApplicationEventPublisher dubboApplicationEventPublisher) {
        this.serverProperties = serverProperties;
        this.serviceId = serviceId;
        this.registryConfigs = registryConfigs;
        this.dubboApplicationEventPublisher = dubboApplicationEventPublisher;
    }

    public DubboDiscoveryClient(ServerProperties serverProperties, String serviceId, List<RegistryConfig> registryConfigs, DubboApplicationEventPublisher dubboApplicationEventPublisher, List<String> protocols) {
        this.serverProperties = serverProperties;
        this.serviceId = serviceId;
        this.registryConfigs = registryConfigs;
        this.dubboApplicationEventPublisher = dubboApplicationEventPublisher;
        support.addAll(protocols);
    }

    public void subscribe(Collection<Registry> registries, DubboApplicationEventPublisher dubboApplicationEventPublisher) {
        for (Registry registry : registries) {
            URL url = registry.getUrl();
            registry.subscribe(URL.valueOf(url.getProtocol() + "://" + url.getHost() + "?interface=*&group=*&version=*&classifier=*"), dubboApplicationEventPublisher);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        subscribe(getRegistries(), this.dubboApplicationEventPublisher);
    }

    @Override
    public String description() {
        return "Dubbo discovery client";
    }

    @Override
    public ServiceInstance getLocalServiceInstance() {
        return new DefaultServiceInstance(serviceId, NetUtils.getLocalHost(), serverProperties.getPort(), serverProperties.getSsl() != null);
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        List<ServiceInstance> instances = new ArrayList<>();
        for (URL provider : getUrls()) {
            Set<String> hosts = new HashSet<>();
            String application = provider.getParameter("application");
            String host = provider.getHost() + ":" + provider.getPort();
            if (hosts.contains(host) || !support.contains(provider.getProtocol())) {
                continue;
            }

            DefaultServiceInstance instance;
            if ("feigns".equalsIgnoreCase(provider.getProtocol()) || "ribbons".equalsIgnoreCase(provider.getProtocol())) {
                instance = new DefaultServiceInstance(application, provider.getHost(), provider.getPort(), true);
            } else {
                instance = new DefaultServiceInstance(application, provider.getHost(), provider.getPort(), false);
            }

            hosts.add(host);
            instances.add(instance);
        }

        return instances;
    }

    @Override
    public List<String> getServices() {
        Set<String> applications = new LinkedHashSet<>();
        for (URL provider : getUrls()) {
            String application = provider.getParameter("application");

            Set<String> hosts = new LinkedHashSet<>();
            for (ServiceInstance serviceInstance : getInstances(application)) {
                String scheme = "http://";
                if (serviceInstance.isSecure()) {
                    scheme = "https://";
                }
                String host = scheme + serviceInstance.getHost() + ":" + serviceInstance.getPort();
                hosts.add(host);
            }

            if (logger.isDebugEnabled()) {
                logger.debug(application + ".ribbon.listOfServers = " + StringUtils.join(hosts, ","));
            }

            ConfigurationManager.getConfigInstance()
                    .addProperty(application + ".ribbon.listOfServers", StringUtils.join(hosts, ","));
            applications.add(application);
        }
        return new ArrayList<>(applications);
    }

    public Set<URL> getUrls() {
        Set<URL> urls = new LinkedHashSet<>();
        for (Registry registry : getRegistries()) {

            if (registry instanceof MulticastRegistry) {
                //组播模式由于没有中心节点，一旦重启,将丢失已有注册主机数据.并且重启后只会有最新注册的主机列表
                Map<URL, Set<URL>> registered = ((MulticastRegistry) registry).getReceived();
                for (Set<URL> providers : registered.values()) {
                    urls.addAll(providers);
                }
            }

            if (registry instanceof ZookeeperRegistry) {
                ZookeeperClient zookeeperClient = getZookeeperClient((ZookeeperRegistry) registry);
                List<String> children = zookeeperClient.getChildren("/dubbo");
                if (children == null) continue;
                for (String service : children) {
                    List<String> providers = zookeeperClient.getChildren("/dubbo/" + service + "/providers");
                    if (providers == null) continue;
                    for (String url : providers) {
                        urls.add(URL.valueOf(URL.decode(url)));
                    }
                }
            }

            if (registry instanceof RedisRegistry) {
                RedisRegistry redisRegistry = (RedisRegistry) registry;
                Set<URL> registered = redisRegistry.getRegistered();
                urls.addAll(registered);
            }

            if (registry instanceof HazelcastRegistry) {
                HazelcastRegistry hazelcastRegistry = (HazelcastRegistry) registry;
                Set<URL> registered = hazelcastRegistry.getRegistered();
                urls.addAll(registered);
            }

        }
        return urls;
    }


    public Collection<Registry> getRegistries() {
        Collection<Registry> registries = AbstractRegistryFactory.getRegistries();
        if ((registryConfigs.size() == registries.size()) && registries.size() != 0) {
            return registries;
        }
        registries = new ArrayList<>();
        for (RegistryConfig registryConfig : registryConfigs) {
            String protocol = registryConfig.getProtocol();
            String address = registryConfig.getAddress();
            RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getExtension(protocol);
            if (StringUtils.isBlank(protocol)) {
                registryFactory.getRegistry(URL.valueOf(address));
            } else if (StringUtils.isBlank(address) && !StringUtils.isBlank(protocol)) {
                registryFactory.getRegistry(URL.valueOf(protocol + "://" + NetUtils.getLocalHost()));
            } else {
                String[] addresses = address.split(",");
                if (addresses.length > 0) {
                    Registry registry;
                    if (addresses.length == 1) {
                        registry = registryFactory.getRegistry(URL.valueOf(protocol + "://" + addresses[0]));
                    } else {
                        String[] backup = Arrays.copyOfRange(addresses, 1, addresses.length);
                        registry = registryFactory.getRegistry(URL.valueOf(protocol + "://" + addresses[0] + "?buckup=" + StringUtils.join(backup, ",")));
                    }
                    registries.add(registry);
                }
            }
        }
        return registries;
    }


    protected ZookeeperClient getZookeeperClient(ZookeeperRegistry registry) {
        Field zkClientFiled = ReflectionUtils.findField(ZookeeperRegistry.class, "zkClient");
        zkClientFiled.setAccessible(true);
        try {
            return (ZookeeperClient) zkClientFiled.get(registry);
        } catch (IllegalAccessException e) {
            throw new RpcException(e);
        }
    }


    @PreDestroy
    public void destroy() {
        AbstractRegistryFactory.destroyAll();
    }
}