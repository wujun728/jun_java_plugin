
#### @Lock 注解参数介绍

```
    /**
     * REENTRANT(可重入锁),FAIR(公平锁),MULTIPLE(联锁),REDLOCK(红锁),READ(读锁), WRITE(写锁), 
     * AUTO(自动模式,当参数只有一个.使用 REENTRANT 参数多个 MULTIPLE)
     */
    LockModel lockModel() default LockModel.AUTO;
    /**
     * 需要锁定的keys
     * @return
     */
    String[] keys() default {};
    /**
     * 锁超时时间,默认30000毫秒(可在配置文件全局设置)
     * @return
     */
    long lockWatchdogTimeout() default 0;
    /**
     * 等待加锁超时时间,默认10000毫秒 -1 则表示一直等待(可在配置文件全局设置)
     * @return
     */
    long attemptTimeout() default 0;
```