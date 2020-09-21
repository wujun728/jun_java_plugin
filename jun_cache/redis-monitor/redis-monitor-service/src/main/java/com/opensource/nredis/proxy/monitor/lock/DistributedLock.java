package com.opensource.nredis.proxy.monitor.lock;


/**
 * 抽象类
 * @author liubing
 *
 */
public abstract class DistributedLock {
	
	  /**
     * 获取锁
     * 
     * @return
     */
    public boolean tryLock() throws Exception{
        try {
            return lock();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取锁
     * 
     * @param timeout 获取所超时时间(毫秒值)
     * @return
     */
    public boolean tryLock(long timeout) throws Exception{
        do {
            if (tryLock()) {
                return true;
            }
            timeout -= 100;
            try {
                Thread.sleep(Math.min(100, 100 + timeout));
            } catch (InterruptedException e) {
            }
        } while (timeout > 0);

        return false;
    }

    /**
     * 获取锁
     * 
     * @return
     */
    protected abstract boolean lock()throws Exception;

    /**
     * 检查所是否有效,锁是否存在or锁是否已被其他进程重新获取
     * 
     * @return
     */
    public abstract boolean check()throws Exception;

    /**
     * 维持心跳，仅在heartbeatTime < timeout时需要
     * <p>
     * 如果heartbeatTime == timeout，此操作没有意义，因为在心跳时key已失效了
     * 
     * @return
     */
    public abstract boolean heartbeat()throws Exception;

    /**
     * 释放锁
     */
    public abstract boolean unLock() throws Exception;
    
}
