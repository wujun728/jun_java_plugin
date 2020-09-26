package net.oschina.j2cache.broadcast;

import net.oschina.j2cache.CacheBroadcastChannel;
import net.oschina.j2cache.utils.CacheCustoms;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.CacheExpiredListener;
import net.oschina.j2cache.CacheFactory;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

/**
 * 缓存信息变更的广播通道 - JGROUP的多播模式实现
 *
 * @author Wujun
 */
public class JGroupBroadcastChannel extends ReceiverAdapter implements CacheExpiredListener, CacheBroadcastChannel {
    private static final Logger logger  = LoggerFactory.getLogger(JGroupBroadcastChannel.class);

    private JChannel channel;
    private CacheFactory factory;

    /**
     * 初始化组播网络配置
     * @param confFilePath JGroup Channel network config file path
     * @param clusterName 集群分组名称
     */
    public JGroupBroadcastChannel(final String confFilePath, final String clusterName, final CacheFactory factory) throws Exception {
        if (confFilePath == null || "".equals(confFilePath)) {
            throw new CacheException(String.format("组播的网络配置文件加载失败: %s", confFilePath));
        }

        // 2.初始化JGroup channel
        long startTime = System.currentTimeMillis();

        URL networkXML = getClass().getResource(confFilePath);
        if (networkXML == null) {
            networkXML = getClass().getClassLoader().getParent().getResource(confFilePath);
        }
        if (networkXML == null) {
            throw new CacheException(String.format("组播的网络配置文件加载失败: %s", confFilePath));
        }
        try {
            channel = new JChannel(networkXML);
        } catch (Exception e) {
            throw new CacheException("Cache JGroup network start fail.", e);
        }
        channel.setReceiver(this);
        // channel.setName(name);
        channel.connect(clusterName);

        // Factory
        this.factory = factory;

        logger.info("成功创建缓存广播通道(JGroup Connection to channel) : {}, cluster name is : {}, 耗时 : {} ms", channel.getAddress().toString(), clusterName, System.currentTimeMillis() - startTime);
    }

    /**
     * 广播消息接收方法
     * @param msg {@link Message}
     */
    @Override
    public void receive(Message msg) {
        byte[] buff = msg.getBuffer();
        // 1.无效消息校验
        if (buff.length < 1) {
            logger.warn("Cache JGroup Message is empty.");
            return ;
        }

        // 2.不处理自己发送的消息
        if (msg.getSrc().equals(channel.getAddress())) {
            return ;
        }

        // 3.正式处理
        try {
            Command cmd = Command.parse(buff);

            if (cmd == null) {
                return ;
            }

            switch (cmd.getOperator()) {
                case CacheCustoms.OPT_DELTED_KEY:
                    onDeleteCacheKey(cmd.getRegion(), cmd.getKey());
                    break;
                default:
                    logger.warn("尚未支持的消息类型 : {}", cmd.getOperator());
            }

        } catch (Exception e) {
            logger.error("未能正确处理接收到的JGroup消息.", e);
        }

    }

    @Override
    public void notifyElementExpired(String region, Object key) {
        logger.debug("缓存数据过期 -> region:{}, key:{}", region, key);

        // 1.清除二级缓存
        if (factory.isOpenSecondCache()) {
            if (key instanceof List) {
                factory.getProvider(CacheCustoms.CACHE_LV_2).buildCache(region, false, null).evict((List) key);
            } else {
                factory.getProvider(CacheCustoms.CACHE_LV_2).buildCache(region, false, null).evict(key);
            }
        }

        // 2.推送清除缓存的广播消息
        if (factory.isUseCluster() && BroadcastType.JGROUPS_MULTICAST.equals(factory.getCacheBroadcast()))
            sendCmdBroadcast(CacheCustoms.OPT_DELTED_KEY, region, key);
    }

    /**
     * 删除一级缓存的内容
     * @param region 缓存region name
     * @param key 缓存 key name
     */
    @Override
    public void onDeleteCacheKey(String region, Object key) {
        if (key instanceof List) {
            factory.getProvider(CacheCustoms.CACHE_LV_1).buildCache(region, true, this).evict((List) key);
        } else {
            factory.getProvider(CacheCustoms.CACHE_LV_1).buildCache(region, true, this).evict(key);
        }
    }

    /**
     * 发送JGroup广播消息
     * @param optKey 操作代码
     * @param region 缓存region name
     * @param key 缓存 key
     */
    @Override
    public void sendCmdBroadcast(byte optKey, String region, Object key) throws CacheException {
        Command cmd = new Command(optKey, region, key);

        Message msg = new Message(null, null, cmd.toBuff());
        try {
            channel.send(msg);
        } catch (Exception e) {
            logger.error("发送 代码为 [{}] 的JGroup事件消息失败 -> region:{}, key:{}", optKey, region, key, e);
        }
    }

}
