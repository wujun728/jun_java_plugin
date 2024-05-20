package io.github.wujun728.uidgenerator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "uid.gen")
public class UidGeneratorProperties {

    //相对于时间基点"2016-05-20"的增量值，单位：秒，可使用的时间为2^timeBis 秒例如：timeBits=30，则可使用230秒，约34年，timeBits=31，则可使用231秒，约68年
    private Integer timeBits;

    //机器id，最多可支持2^22约420w次机器启动。内置实现为在启动时由数据库分配，默认分配策略为用后即弃，每次启动都会重新生成一批ID，因此重启次数也是会有限制的，后续可提供复用策略。
    private Integer workerBits;

    //每秒下的并发序列，9 bits可支持每台服务器每秒512个并发。
    private Integer seqBits;

    //指集成UidGenerator生成分布式ID服务第一次上线的时间，可配置，也一定要根据你的上线时间进行配置，因为默认的epoch时间可是2016-09-20，不配置的话，会浪费好几年的可用时间。
    private String epochStr;

    //RingBuffer size扩容参数, 可提高UID生成的吞吐量默认:3， 原bufferSize=8192, 扩容后bufferSize= 8192 << 3 = 65536
    private Integer boostPower;

    //另外一种RingBuffer填充时机, 在Schedule线程中, 周期性检查填充默认:不配置此项, 即不实用Schedule线程. 如需使用, 请指定Schedule线程时间间隔, 单位:秒
    private Long scheduleInterval;
}
