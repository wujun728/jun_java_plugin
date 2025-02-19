package io.github.wujun728.db.orm.handler;

/**
 * 操作失败回调
 */
public interface DataFailHandler {
    void handle(long result);
}
