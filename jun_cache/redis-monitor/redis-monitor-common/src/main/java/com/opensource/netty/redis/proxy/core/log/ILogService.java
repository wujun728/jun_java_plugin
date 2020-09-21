/**
 * 
 */
package com.opensource.netty.redis.proxy.core.log;

/**
 * log服务。方便适配不同的log方式和配置
 * @author liubing
 *
 */
public interface ILogService {
	
	void trace(String msg);

    void trace(String format, Object... argArray);

    void debug(String msg);

    void debug(String format, Object... argArray);

    void debug(String msg, Throwable t);

    void info(String msg);

    void info(String format, Object... argArray);

    void info(String msg, Throwable t);

    void warn(String msg);

    void warn(String format, Object... argArray);

    void warn(String msg, Throwable t);

    void error(String msg);

    void error(String format, Object... argArray);

    void error(String msg, Throwable t);

    void accessLog(String msg);

    void accessProfileLog(String format, Object... argArray);

    void accessStatsLog(String msg);

    void accessStatsLog(String format, Object... argArray);

    boolean isTraceEnabled();

    boolean isDebugEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();

    boolean isStatsEnabled();
}
