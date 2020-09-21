package org.itkk.udf.core.constant;

/**
 * IdWorkerConstant
 */
public class IdWorkerConstant {

    /**
     * 描述 : 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    public static final long TWEPOCH = 1288834974657L;

    /**
     * 描述 : 机器标识位数
     */
    public static final long WORKER_ID_BITS = 5L;

    /**
     * 描述 : 数据中心标识位数
     */
    public static final long DATACENTER_ID_BITS = 5L;

    /**
     * 描述 : 机器ID最大值
     */
    public static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    /**
     * 描述 : 数据中心ID最大值
     */
    public static final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);

    /**
     * 描述 : 毫秒内自增位
     */
    public static final long SEQUENCE_BITS = 12L;

    /**
     * 描述 : 机器ID偏左移12位
     */
    public static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 描述 : 数据中心ID左移17位
     */
    public static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 描述 : 时间毫秒左移22位
     */
    public static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /**
     * 描述 : 序列号最大值 , 一微秒能产生的ID个数
     */
    public static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    /**
     * 私有化构造函数
     */
    private IdWorkerConstant() {

    }
}
