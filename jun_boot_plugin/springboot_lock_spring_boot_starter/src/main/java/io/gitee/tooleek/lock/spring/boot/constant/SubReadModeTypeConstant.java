package io.gitee.tooleek.lock.spring.boot.constant;

/**
 * 读取操作/订阅操作的负载均衡模式常量
 *
 * @author Wujun
 * @version 1.1.0
 * @apiNote 知识改变命运，技术改变世界
 * @since 2018-12-23 15:32
 */
public interface SubReadModeTypeConstant {
    String SLAVE = "SLAVE";
    String MASTER = "MASTER";
    String MASTER_SLAVE = "MASTER_SLAVE";
}
