package com.zengtengpeng.properties;


import com.zengtengpeng.enums.LockModel;
import com.zengtengpeng.enums.Model;
import org.redisson.config.SslProvider;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.net.URI;
import java.net.URL;

@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    private Model model=Model.SINGLE;
    private String codec="org.redisson.codec.JsonJacksonCodec";
    private Integer threads;
    private Integer nettyThreads;
    private TransportMode transportMode=TransportMode.NIO;

    //公共参数
    private Integer idleConnectionTimeout = 10000;
//    private Integer pingTimeout = 1000;
    private Integer connectTimeout = 10000;
    private Integer timeout = 3000;
    private Integer retryAttempts = 3;
    private Integer retryInterval = 1500;
    private String password;
    private Integer subscriptionsPerConnection = 5;
    private String clientName;
    private Boolean sslEnableEndpointIdentification = true;
    private SslProvider sslProvider=SslProvider.JDK;
    private URL sslTruststore;
    private String sslTruststorePassword;
    private URL sslKeystore;
    private String sslKeystorePassword;
    private Integer pingConnectionInterval=1000;
    private Boolean keepAlive=false;
    private Boolean tcpNoDelay=false;
    private Boolean referenceEnabled = true;
    private Long lockWatchdogTimeout=30000L;
    private Boolean keepPubSubOrder=true;
    private Boolean decodeInExecutor=false;
    private Boolean useScriptCache=false;
    private Integer minCleanUpDelay=5;
    private Integer maxCleanUpDelay=1800;
    //锁的模式 如果不设置 单个key默认可重入锁 多个key默认联锁
    private LockModel lockModel;

    //等待加锁超时时间 -1一直等待
    private Long attemptTimeout= 10000L;

    //数据缓存时间 默认30分钟
    private Long dataValidTime=1000*60* 30L;
    //结束

    @NestedConfigurationProperty
    private SingleServerConfig singleServerConfig;
    @NestedConfigurationProperty
    private MultipleServerConfig multipleServerConfig;


    public Long getDataValidTime() {
        return dataValidTime;
    }

    public void setDataValidTime(Long dataValidTime) {
        this.dataValidTime = dataValidTime;
    }

    public LockModel getLockModel() {
        return lockModel;
    }

    public void setLockModel(LockModel lockModel) {
        this.lockModel = lockModel;
    }

    public Long getAttemptTimeout() {
        return attemptTimeout;
    }

    public void setAttemptTimeout(Long attemptTimeout) {
        this.attemptTimeout = attemptTimeout;
    }

    public Boolean getReferenceEnabled() {
        return referenceEnabled;
    }

    public void setReferenceEnabled(Boolean referenceEnabled) {
        this.referenceEnabled = referenceEnabled;
    }

    public Long getLockWatchdogTimeout() {
        return lockWatchdogTimeout;
    }

    public void setLockWatchdogTimeout(Long lockWatchdogTimeout) {
        this.lockWatchdogTimeout = lockWatchdogTimeout;
    }

    public Boolean getKeepPubSubOrder() {
        return keepPubSubOrder;
    }

    public void setKeepPubSubOrder(Boolean keepPubSubOrder) {
        this.keepPubSubOrder = keepPubSubOrder;
    }

    public Boolean getDecodeInExecutor() {
        return decodeInExecutor;
    }

    public void setDecodeInExecutor(Boolean decodeInExecutor) {
        this.decodeInExecutor = decodeInExecutor;
    }

    public Boolean getUseScriptCache() {
        return useScriptCache;
    }

    public void setUseScriptCache(Boolean useScriptCache) {
        this.useScriptCache = useScriptCache;
    }

    public Integer getMinCleanUpDelay() {
        return minCleanUpDelay;
    }

    public void setMinCleanUpDelay(Integer minCleanUpDelay) {
        this.minCleanUpDelay = minCleanUpDelay;
    }

    public Integer getMaxCleanUpDelay() {
        return maxCleanUpDelay;
    }

    public void setMaxCleanUpDelay(Integer maxCleanUpDelay) {
        this.maxCleanUpDelay = maxCleanUpDelay;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public SingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public MultipleServerConfig getMultipleServerConfig() {
        return multipleServerConfig;
    }

    public void setMultipleServerConfig(MultipleServerConfig multipleServerConfig) {
        this.multipleServerConfig = multipleServerConfig;
    }

    public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }


    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setRetryAttempts(Integer retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public void setSubscriptionsPerConnection(Integer subscriptionsPerConnection) {
        this.subscriptionsPerConnection = subscriptionsPerConnection;
    }

    public Boolean getSslEnableEndpointIdentification() {
        return sslEnableEndpointIdentification;
    }

    public void setSslEnableEndpointIdentification(Boolean sslEnableEndpointIdentification) {
        this.sslEnableEndpointIdentification = sslEnableEndpointIdentification;
    }

    public void setPingConnectionInterval(Integer pingConnectionInterval) {
        this.pingConnectionInterval = pingConnectionInterval;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(Boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(Integer nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(int idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSubscriptionsPerConnection() {
        return subscriptionsPerConnection;
    }

    public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
        this.subscriptionsPerConnection = subscriptionsPerConnection;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isSslEnableEndpointIdentification() {
        return sslEnableEndpointIdentification;
    }

    public void setSslEnableEndpointIdentification(boolean sslEnableEndpointIdentification) {
        this.sslEnableEndpointIdentification = sslEnableEndpointIdentification;
    }

    public SslProvider getSslProvider() {
        return sslProvider;
    }

    public void setSslProvider(SslProvider sslProvider) {
        this.sslProvider = sslProvider;
    }

    public String getSslTruststorePassword() {
        return sslTruststorePassword;
    }

    public void setSslTruststorePassword(String sslTruststorePassword) {
        this.sslTruststorePassword = sslTruststorePassword;
    }

    public URL getSslTruststore() {
        return sslTruststore;
    }

    public void setSslTruststore(URL sslTruststore) {
        this.sslTruststore = sslTruststore;
    }

    public URL getSslKeystore() {
        return sslKeystore;
    }

    public void setSslKeystore(URL sslKeystore) {
        this.sslKeystore = sslKeystore;
    }

    public String getSslKeystorePassword() {
        return sslKeystorePassword;
    }

    public void setSslKeystorePassword(String sslKeystorePassword) {
        this.sslKeystorePassword = sslKeystorePassword;
    }

    public int getPingConnectionInterval() {
        return pingConnectionInterval;
    }

    public void setPingConnectionInterval(int pingConnectionInterval) {
        this.pingConnectionInterval = pingConnectionInterval;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }
}
