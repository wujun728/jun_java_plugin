package cn.kiiwii.framework.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2017/1/14.
 */
@ConfigurationProperties(prefix = "dubbo")
public class DubboProperties {
    private ApplicationConfigProperties application;
    private ProtocolConfigProperties protocol;
    private ProviderConfigProperties provider;
    private RegistryConfigProperties registry;
    private MonitorConfigProperties monitor;
    private AnnotationConfigProperties annotation;
    private ConsumerConfigProperties consumer;

    public class AnnotationConfigProperties {
        private String packageName;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }
    }

    public class ApplicationConfigProperties {
        // 应用名称
        private String name;
        // 模块版本
        private String version;
        // 应用负责人
        private String owner;
        // 组织名(BU或部门)
        private String organization;
        // 分层
        private String architecture;
        // 环境，如：dev/test/run
        private String environment;
        // Java代码编译器
        private String compiler;
        // 日志输出方式
        private String logger;
        // 注册中心
        private List<RegistryConfig> registries;
        // 是否为缺省
        private Boolean isDefault;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getArchitecture() {
            return architecture;
        }

        public void setArchitecture(String architecture) {
            this.architecture = architecture;
        }

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getCompiler() {
            return compiler;
        }

        public void setCompiler(String compiler) {
            this.compiler = compiler;
        }

        public String getLogger() {
            return logger;
        }

        public void setLogger(String logger) {
            this.logger = logger;
        }

        public List<RegistryConfig> getRegistries() {
            return registries;
        }

        public void setRegistries(List<RegistryConfig> registries) {
            this.registries = registries;
        }

        public Boolean getDefault() {
            return isDefault;
        }

        public void setDefault(Boolean aDefault) {
            isDefault = aDefault;
        }
    }

    public class ProtocolConfigProperties {
        // 服务协议
        private String name;

        // 服务IP地址(多网卡时使用)
        private String host;

        // 服务端口
        private Integer port;

        // 上下文路径
        private String contextpath;

        // 线程池类型
        private String threadpool;

        // 线程池大小(固定大小)
        private Integer threads;

        // IO线程池大小(固定大小)
        private Integer iothreads;

        // 线程池队列大小
        private Integer queues;

        // 最大接收连接数
        private Integer accepts;

        // 协议编码
        private String codec;

        // 序列化方式
        private String serialization;

        // 字符集
        private String charset;

        // 最大请求数据长度
        private Integer payload;

        // 缓存区大小
        private Integer buffer;

        // 心跳间隔
        private Integer heartbeat;

        // 访问日志
        private String accesslog;

        // 网络传输方式
        private String transporter;

        // 信息交换方式
        private String exchanger;

        // 信息线程模型派发方式
        private String dispatcher;

        // 对称网络组网方式
        private String networker;

        // 服务器端实现
        private String server;

        // 客户端实现
        private String client;

        // 支持的telnet命令，多个命令用逗号分隔
        private String telnet;

        // 命令行提示符
        private String prompt;

        // status检查
        private String status;

        // 是否注册
        private Boolean register;

        // 是否长连接
        // TODO add this to provider config
        private Boolean keepAlive;

        // 序列化的优化器的实现类名
        // TODO add this to provider config
        private String optimizer;

        private String extension;

        // 参数
        private Map<String, String> parameters;

        // 是否为缺省
        private Boolean isDefault;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getContextpath() {
            return contextpath;
        }

        public void setContextpath(String contextpath) {
            this.contextpath = contextpath;
        }

        public String getThreadpool() {
            return threadpool;
        }

        public void setThreadpool(String threadpool) {
            this.threadpool = threadpool;
        }

        public Integer getThreads() {
            return threads;
        }

        public void setThreads(Integer threads) {
            this.threads = threads;
        }

        public Integer getIothreads() {
            return iothreads;
        }

        public void setIothreads(Integer iothreads) {
            this.iothreads = iothreads;
        }

        public Integer getQueues() {
            return queues;
        }

        public void setQueues(Integer queues) {
            this.queues = queues;
        }

        public Integer getAccepts() {
            return accepts;
        }

        public void setAccepts(Integer accepts) {
            this.accepts = accepts;
        }

        public String getCodec() {
            return codec;
        }

        public void setCodec(String codec) {
            this.codec = codec;
        }

        public String getSerialization() {
            return serialization;
        }

        public void setSerialization(String serialization) {
            this.serialization = serialization;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public Integer getPayload() {
            return payload;
        }

        public void setPayload(Integer payload) {
            this.payload = payload;
        }

        public Integer getBuffer() {
            return buffer;
        }

        public void setBuffer(Integer buffer) {
            this.buffer = buffer;
        }

        public Integer getHeartbeat() {
            return heartbeat;
        }

        public void setHeartbeat(Integer heartbeat) {
            this.heartbeat = heartbeat;
        }

        public String getAccesslog() {
            return accesslog;
        }

        public void setAccesslog(String accesslog) {
            this.accesslog = accesslog;
        }

        public String getTransporter() {
            return transporter;
        }

        public void setTransporter(String transporter) {
            this.transporter = transporter;
        }

        public String getExchanger() {
            return exchanger;
        }

        public void setExchanger(String exchanger) {
            this.exchanger = exchanger;
        }

        public String getDispatcher() {
            return dispatcher;
        }

        public void setDispatcher(String dispatcher) {
            this.dispatcher = dispatcher;
        }

        public String getNetworker() {
            return networker;
        }

        public void setNetworker(String networker) {
            this.networker = networker;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getTelnet() {
            return telnet;
        }

        public void setTelnet(String telnet) {
            this.telnet = telnet;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Boolean getRegister() {
            return register;
        }

        public void setRegister(Boolean register) {
            this.register = register;
        }

        public Boolean getKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(Boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public String getOptimizer() {
            return optimizer;
        }

        public void setOptimizer(String optimizer) {
            this.optimizer = optimizer;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public Boolean getDefault() {
            return isDefault;
        }

        public void setDefault(Boolean aDefault) {
            isDefault = aDefault;
        }
    }

    public class ConsumerConfigProperties {

        // 是否为缺省
        private Boolean             isDefault;
        // 检查服务提供者是否存在
        private Boolean             check;

        // 是否加载时即刻初始化
        private Boolean             init;

        // 是否使用泛接口
        private String             generic;

        // 优先从JVM内获取引用实例
        private Boolean             injvm;

        // lazy create connection
        private Boolean             lazy;

        private String              reconnect;

        private Boolean             sticky;

        //stub是否支持event事件. //TODO slove merge problem
        private Boolean             stubevent ;//= Constants.DEFAULT_STUB_EVENT;

        // 版本
        private String               version;

        // 服务分组
        private String               group;

        // 远程调用超时时间(毫秒)
        private Integer             timeout;

        // 重试次数
        private Integer             retries;

        // 最大并发调用
        private Integer             actives;

        // 负载均衡
        private String              loadbalance;

        // 是否异步
        private Boolean             async;

        // 异步发送是否等待发送成功
        private Boolean             sent;

        // 服务接口的失败mock实现类名
        private String              mock;

        // 合并器
        private String              merger;

        // 服务接口的失败mock实现类名
        private String              cache;

        // 服务接口的失败mock实现类名
        private String              validation;

        // 服务接口的本地实现类名
        private String               local;

        // 服务接口的本地实现类名
        private String               stub;

        // 服务监控
        private MonitorConfig monitor;

        // 代理类型
        private String               proxy;

        // 集群方式
        private String               cluster;

        // 过滤器
        private String               filter;

        // 监听器
        private String               listener;

        // 负责人
        private String               owner;

        // 连接数限制,0表示共享连接，否则为该服务独享连接数
        private Integer              connections;

        // 连接数限制
        private String               layer;

        // 应用信息
        private ApplicationConfig    application;

        // 模块信息
        private ModuleConfig         module;

        // 注册中心
        private List<RegistryConfig> registries;

        // callback实例个数限制
        private Integer                callbacks;

        // 连接事件
        protected String              onconnect;

        // 断开事件
        protected String              ondisconnect;

        // 服务暴露或引用的scope,如果为local，则表示只在当前JVM内查找.
        private String scope;

        public Boolean getDefault() {
            return isDefault;
        }

        public void setDefault(Boolean aDefault) {
            isDefault = aDefault;
        }

        public Boolean getCheck() {
            return check;
        }

        public void setCheck(Boolean check) {
            this.check = check;
        }

        public Boolean getInit() {
            return init;
        }

        public void setInit(Boolean init) {
            this.init = init;
        }

        public String getGeneric() {
            return generic;
        }

        public void setGeneric(String generic) {
            this.generic = generic;
        }

        public Boolean getInjvm() {
            return injvm;
        }

        public void setInjvm(Boolean injvm) {
            this.injvm = injvm;
        }

        public Boolean getLazy() {
            return lazy;
        }

        public void setLazy(Boolean lazy) {
            this.lazy = lazy;
        }

        public String getReconnect() {
            return reconnect;
        }

        public void setReconnect(String reconnect) {
            this.reconnect = reconnect;
        }

        public Boolean getSticky() {
            return sticky;
        }

        public void setSticky(Boolean sticky) {
            this.sticky = sticky;
        }

        public Boolean getStubevent() {
            return stubevent;
        }

        public void setStubevent(Boolean stubevent) {
            this.stubevent = stubevent;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        public Integer getRetries() {
            return retries;
        }

        public void setRetries(Integer retries) {
            this.retries = retries;
        }

        public Integer getActives() {
            return actives;
        }

        public void setActives(Integer actives) {
            this.actives = actives;
        }

        public String getLoadbalance() {
            return loadbalance;
        }

        public void setLoadbalance(String loadbalance) {
            this.loadbalance = loadbalance;
        }

        public Boolean getAsync() {
            return async;
        }

        public void setAsync(Boolean async) {
            this.async = async;
        }

        public Boolean getSent() {
            return sent;
        }

        public void setSent(Boolean sent) {
            this.sent = sent;
        }

        public String getMock() {
            return mock;
        }

        public void setMock(String mock) {
            this.mock = mock;
        }

        public String getMerger() {
            return merger;
        }

        public void setMerger(String merger) {
            this.merger = merger;
        }

        public String getCache() {
            return cache;
        }

        public void setCache(String cache) {
            this.cache = cache;
        }

        public String getValidation() {
            return validation;
        }

        public void setValidation(String validation) {
            this.validation = validation;
        }

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
        }

        public String getStub() {
            return stub;
        }

        public void setStub(String stub) {
            this.stub = stub;
        }

        public MonitorConfig getMonitor() {
            return monitor;
        }

        public void setMonitor(MonitorConfig monitor) {
            this.monitor = monitor;
        }

        public String getProxy() {
            return proxy;
        }

        public void setProxy(String proxy) {
            this.proxy = proxy;
        }

        public String getCluster() {
            return cluster;
        }

        public void setCluster(String cluster) {
            this.cluster = cluster;
        }

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }

        public String getListener() {
            return listener;
        }

        public void setListener(String listener) {
            this.listener = listener;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public Integer getConnections() {
            return connections;
        }

        public void setConnections(Integer connections) {
            this.connections = connections;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public ApplicationConfig getApplication() {
            return application;
        }

        public void setApplication(ApplicationConfig application) {
            this.application = application;
        }

        public ModuleConfig getModule() {
            return module;
        }

        public void setModule(ModuleConfig module) {
            this.module = module;
        }

        public List<RegistryConfig> getRegistries() {
            return registries;
        }

        public void setRegistries(List<RegistryConfig> registries) {
            this.registries = registries;
        }

        public Integer getCallbacks() {
            return callbacks;
        }

        public void setCallbacks(Integer callbacks) {
            this.callbacks = callbacks;
        }

        public String getOnconnect() {
            return onconnect;
        }

        public void setOnconnect(String onconnect) {
            this.onconnect = onconnect;
        }

        public String getOndisconnect() {
            return ondisconnect;
        }

        public void setOndisconnect(String ondisconnect) {
            this.ondisconnect = ondisconnect;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    public class ProviderConfigProperties {
        // 服务IP地址(多网卡时使用)
        private String host;

        // 服务端口
        private Integer port;

        // 上下
        private String contextpath;

        // 线程池类型
        private String threadpool;

        // 线程池大小(固定大小)
        private Integer threads;

        // IO线程池大小(固定大小)
        private Integer iothreads;

        // 线程池队列大小
        private Integer queues;

        // 最大接收连接数
        private Integer accepts;

        // 协议编码
        private String codec;

        // 序列化方式
        private String serialization;

        // 字符集
        private String charset;

        // 最大请求数据长度
        private Integer payload;

        // 缓存区大小
        private Integer buffer;

        // 网络传输方式
        private String transporter;

        // 信息交换方式
        private String exchanger;

        // 信息线程模型派发方式
        private String dispatcher;

        // 对称网络组网方式
        private String networker;

        // 服务器端实现
        private String server;

        // 客户端实现
        private String client;

        // 支持的telnet命令，多个命令用逗号分隔
        private String telnet;

        // 命令行提示符
        private String prompt;

        // status检查
        private String status;

        // 停止时等候时间
        private Integer wait;

        // 是否为缺省
        private Boolean isDefault;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getContextpath() {
            return contextpath;
        }

        public void setContextpath(String contextpath) {
            this.contextpath = contextpath;
        }

        public String getThreadpool() {
            return threadpool;
        }

        public void setThreadpool(String threadpool) {
            this.threadpool = threadpool;
        }

        public Integer getThreads() {
            return threads;
        }

        public void setThreads(Integer threads) {
            this.threads = threads;
        }

        public Integer getIothreads() {
            return iothreads;
        }

        public void setIothreads(Integer iothreads) {
            this.iothreads = iothreads;
        }

        public Integer getQueues() {
            return queues;
        }

        public void setQueues(Integer queues) {
            this.queues = queues;
        }

        public Integer getAccepts() {
            return accepts;
        }

        public void setAccepts(Integer accepts) {
            this.accepts = accepts;
        }

        public String getCodec() {
            return codec;
        }

        public void setCodec(String codec) {
            this.codec = codec;
        }

        public String getSerialization() {
            return serialization;
        }

        public void setSerialization(String serialization) {
            this.serialization = serialization;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public Integer getPayload() {
            return payload;
        }

        public void setPayload(Integer payload) {
            this.payload = payload;
        }

        public Integer getBuffer() {
            return buffer;
        }

        public void setBuffer(Integer buffer) {
            this.buffer = buffer;
        }

        public String getTransporter() {
            return transporter;
        }

        public void setTransporter(String transporter) {
            this.transporter = transporter;
        }

        public String getExchanger() {
            return exchanger;
        }

        public void setExchanger(String exchanger) {
            this.exchanger = exchanger;
        }

        public String getDispatcher() {
            return dispatcher;
        }

        public void setDispatcher(String dispatcher) {
            this.dispatcher = dispatcher;
        }

        public String getNetworker() {
            return networker;
        }

        public void setNetworker(String networker) {
            this.networker = networker;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getTelnet() {
            return telnet;
        }

        public void setTelnet(String telnet) {
            this.telnet = telnet;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getWait() {
            return wait;
        }

        public void setWait(Integer wait) {
            this.wait = wait;
        }

        public Boolean getDefault() {
            return isDefault;
        }

        public void setDefault(Boolean aDefault) {
            isDefault = aDefault;
        }
    }

    public class RegistryConfigProperties {
        // 注册中心地址
        private String address;

        // 注册中心登录用户名
        private String username;

        // 注册中心登录密码
        private String password;

        // 注册中心缺省端口
        private Integer port;

        // 注册中心协议
        private String protocol;

        // 客户端实现
        private String transporter;

        private String server;

        private String client;

        private String cluster;

        private String group;

        private String version;

        // 注册中心请求超时时间(毫秒)
        private Integer timeout;

        // 注册中心会话超时时间(毫秒)
        private Integer session;

        // 动态注册中心列表存储文件
        private String file;

        // 停止时等候完成通知时间
        private Integer wait;

        // 启动时检查注册中心是否存在
        private Boolean check;

        // 在该注册中心上注册是动态的还是静态的服务
        private Boolean dynamic;

        // 在该注册中心上服务是否暴露
        private Boolean register;

        // 在该注册中心上服务是否引用
        private Boolean subscribe;

        // 自定义参数
        private Map<String, String> parameters;

        // 是否为缺省
        private Boolean isDefault;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getTransporter() {
            return transporter;
        }

        public void setTransporter(String transporter) {
            this.transporter = transporter;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getCluster() {
            return cluster;
        }

        public void setCluster(String cluster) {
            this.cluster = cluster;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        public Integer getSession() {
            return session;
        }

        public void setSession(Integer session) {
            this.session = session;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public Integer getWait() {
            return wait;
        }

        public void setWait(Integer wait) {
            this.wait = wait;
        }

        public Boolean getCheck() {
            return check;
        }

        public void setCheck(Boolean check) {
            this.check = check;
        }

        public Boolean getDynamic() {
            return dynamic;
        }

        public void setDynamic(Boolean dynamic) {
            this.dynamic = dynamic;
        }

        public Boolean getRegister() {
            return register;
        }

        public void setRegister(Boolean register) {
            this.register = register;
        }

        public Boolean getSubscribe() {
            return subscribe;
        }

        public void setSubscribe(Boolean subscribe) {
            this.subscribe = subscribe;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public Boolean getDefault() {
            return isDefault;
        }

        public void setDefault(Boolean aDefault) {
            isDefault = aDefault;
        }
    }

    public class MonitorConfigProperties {
        private String protocol;

        private String address;

        private String username;

        private String password;

        private String group;

        private String version;

        // 自定义参数
        private Map<String, String> parameters;

        // 是否为缺省
        private Boolean isDefault;

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public Boolean getDefault() {
            return isDefault;
        }

        public void setDefault(Boolean aDefault) {
            isDefault = aDefault;
        }
    }

    public DubboProperties() {
        application = new ApplicationConfigProperties();
        protocol = new ProtocolConfigProperties();
        provider = new ProviderConfigProperties();
        registry = new RegistryConfigProperties();
        monitor = new MonitorConfigProperties();
        annotation = new AnnotationConfigProperties();
        consumer = new ConsumerConfigProperties();
    }

    public ApplicationConfigProperties getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfigProperties application) {
        this.application = application;
    }

    public ProtocolConfigProperties getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolConfigProperties protocol) {
        this.protocol = protocol;
    }

    public ProviderConfigProperties getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfigProperties provider) {
        this.provider = provider;
    }

    public RegistryConfigProperties getRegistry() {
        return registry;
    }

    public void setRegistry(RegistryConfigProperties registry) {
        this.registry = registry;
    }

    public MonitorConfigProperties getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfigProperties monitor) {
        this.monitor = monitor;
    }

    public ConsumerConfigProperties getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfigProperties consumer) {
        this.consumer = consumer;
    }

    public AnnotationConfigProperties getAnnotation() {
        return annotation;
    }

    public void setAnnotation(AnnotationConfigProperties annotation) {
        this.annotation = annotation;
    }
}
