package cn.jiangzeyin.system;

/**
 * Created by jiangzeyin on 2017/8/14.
 */
public interface DbLogInterface {
    void info(Object object);

    void error(String msg, Throwable t);

    void warn(Object msg);

    void warn(String msg, Throwable t);
}