package com.jun.plugin.security.util.idgen;

import com.jun.plugin.security.util.LocalHostUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Random;

/**
 * 雪花算法生成分布式ID
 *
 * @version 2022-05-21 9:53
 **/
public class Snowflake {
    final static Logger log = LoggerFactory.getLogger(Snowflake.class);

    // 时间起始标记点，一旦确定不能变动
    private final static long TWEPOCH = 1577811661657L;

    // 机器标识位数
    private final static long WORKER_ID_BITS = 5L;

    // 数据中心标识位数
    private final static long DATA_CENTER_ID_BITS = 5L;

    // 工作机器ID最大值 31
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    // 数据中心ID最大值 31
    private final static long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    // 毫秒内自增位
    private final static long SEQUENCE_BITS = 12L;

    // 机器ID偏左移12位
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;

    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    // 时间毫秒左移22位
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    // 序号掩码，用于与自增后的序列号进行位“与”操作，如果值为 0，则代表自增后的序列号超过了 4095。
    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private long lastTimestamp = -1L;

    private long sequence = 0L;
    private final long workerId;
    private final long dataCenterId;

    // 私有化示例要加上volatile，防止jvm重排序，导致空指针
    private static volatile Snowflake snowflake = null;
    private static final Object lock = new Object();

    // IP 地址信息，用来生成工作机器 ID 和数据中心 ID。
    private InetAddress address;


    // 单例禁止new实例化
    private Snowflake(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("%s 工作机器ID最大值 必须是 %d 到 %d 之间", dataCenterId, 0, MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("%s 数据中心ID最大值 必须是 %d 到 %d 之间", dataCenterId, 0, MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    // 单例禁止new实例化
    private Snowflake(InetAddress address) {
        this.address = address;
        this.dataCenterId = getDataCenterId(MAX_DATA_CENTER_ID);
        this.workerId = getWorkerId(dataCenterId, MAX_DATA_CENTER_ID);
    }

    /**
     * 雪花算法 ID 生成器。
     */
    private Snowflake() {
        this(null);
    }


    /**
     * 根据 MAC + PID 的 hashCode 获取 16 个低位生成工作机器 ID。
     */
    protected long getWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuilder mpId = new StringBuilder();
        mpId.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (name != null && !name.isEmpty()) {
            // GET jvmPid
            mpId.append(name.split("@")[0]);
        }
        // MAC + PID 的 hashCode 获取16个低位
        return (mpId.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }


    /**
     * 根据网卡 MAC 地址计算余数作为数据中心 ID。
     */
    protected long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            if (address == null) {
                address = InetAddress.getLocalHost();
            }
            NetworkInterface network = NetworkInterface.getByInetAddress(address);
            if (null == network) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 2]) | (0x0000FF00 & (((long) mac[mac.length - 1]) << 8))) >> 6;
                    id = id % (maxDataCenterId + 1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("dataCenterId generate fail", e);
        }
        return id;
    }


    /**
     * 获取单例（懒汉式单例，有线程安全问题，所以加锁）
     *
     * @return
     */
    public static Snowflake getInstance() {
        if (snowflake == null) {
            synchronized (lock) {
                if (snowflake == null) {
                    snowflake = new Snowflake(LocalHostUtil.getInetAddress());
                }
            }
        }
        return snowflake;
    }

    /**
     * 生成1-31之间的随机数（用于随机生成机器id和数据中心id）
     *
     * @return long
     */
    private static long getRandom(long maxId) {
        int max = (int) (maxId);
        int min = 1;
        Random random = new Random();
        long result = random.nextInt(max - min) + min;
        return result;
    }

    /**
     * 根据算法，生成下一个ID
     *
     * @return
     */
    private synchronized long generateId() {
        long timestamp = time();
        if (timestamp < lastTimestamp) {
            log.error("时钟向后移动，拒绝生成id " + (lastTimestamp - timestamp) + " milliseconds");
        }
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;

        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;

        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.time();
        while (timestamp <= lastTimestamp) {
            timestamp = this.time();
        }
        return timestamp;
    }

    private long time() {
        return System.currentTimeMillis();
    }


    /**
     * 获取雪花算法ID，返回String类型
     *
     * @return
     */
    public static String nextId() {
        return String.valueOf(Snowflake.getInstance().generateId());
    }


    /**
     * 获取雪花算法ID，返回String类型
     *
     * @return
     */
    public static long nextLongId() {
        return Snowflake.getInstance().generateId();
    }

}

