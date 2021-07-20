package io.gitee.tooleek.lock.spring.boot.exception;

/**
 * 未知读取操作的负载均衡模式类型
 *
 * @author Wujun
 * @version 1.1.0
 * @apiNote 知识改变命运，技术改变世界
 * @since 2018-12-23 15:32
 */
public class UnknownReadModeException extends RuntimeException {

    private static final long serialVersionUID = 809616566885491658L;

    public UnknownReadModeException() {
        super("未知读取操作的负载均衡模式类型");
    }

}
