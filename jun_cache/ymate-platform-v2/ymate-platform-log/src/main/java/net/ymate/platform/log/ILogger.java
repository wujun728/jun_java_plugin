/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.log;

import org.apache.commons.lang.StringUtils;

/**
 * 日志记录器接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-27 下午03:58:07
 * @version 1.0
 */
public interface ILogger {

    /**
     * 日志记录级别
     */
    enum LogLevel {

        ALL(Integer.MAX_VALUE, "all", "[ALL]"),
        TRACE(600, "trace", "[TRACE]"),
        DEBUG(500, "debug", "[DEBUG]"),
        INFO(400, "info", "[INFO]"),
        WARN(300, "warn", "[WARN]"),
        ERROR(200, "error", "[ERROR]"),
        FATAL(100, "fatal", "[FATAL]"),
        OFF(0, "off", "[OFF]");

        /**
         * 日志级别名称
         */
        private String name;

        /**
         * 日志级别显示名称
         */
        private String dispName;

        /**
         * 日志级别值
         */
        private int level;

        /**
         * 构造器
         *
         * @param level    日志输出级别值
         * @param name     日志输出级别名称
         * @param dispName 日志输出显示名称
         */
        LogLevel(int level, String name, String dispName) {
            this.level = level;
            this.name = name;
            this.dispName = dispName;
        }

        public static LogLevel parse(String levelName) {
            if (StringUtils.isNotBlank(levelName)) {
                for (LogLevel level : LogLevel.values()) {
                    if (level.name.equalsIgnoreCase(levelName)) {
                        return level;
                    }
                }
            }
            return null;
        }

        public int getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public String getDispName() {
            return dispName;
        }
    }

    /**
     * 使用ILogConfig配置文件加载日志记录器，并提供默认记录级别
     *
     * @param owner      日志记录器模块管理器
     * @param loggerName 启动的日志记录名称
     * @return 返回日志记录器实例
     * @throws Exception 初始化时可能产生的异常
     */
    ILogger init(ILog owner, String loggerName) throws Exception;

    /**
     * @param loggerName 日志记录器名称
     * @return 获取指定名称的日志记录器对象
     * @throws Exception 获取日志记录器时可能产生异常
     */
    ILogger getLogger(String loggerName) throws Exception;

    Object getLoggerImpl();

    String getLoggerName();

    /**
     * 销毁(停止)当前的日志记录器，需要清除所占用的资源，而且日志记录器一旦被停止，将无法恢复使用
     */
    void destroy();

    /**
     * 设置是否控制台输出
     *
     * @param enable 状态
     * @return 返回当前日志记录器
     */
    ILogger console(boolean enable);

    /**
     * 设置调用者深度
     *
     * @param depth 必须大于等于零
     * @return 返回当前日志记录器
     */
    ILogger depth(int depth);

    /**
     * 是否存在某个日志记录器
     *
     * @param loggerName 日志记录器名称
     * @return 如果当前日志记录器存在那么返回true，如果不存在那么返回false
     */
    boolean contains(String loggerName);

    /**
     * @return 返回当前日志级别
     */
    LogLevel getLevel();

    //

    void log(String info, LogLevel level);

    void log(Throwable e, LogLevel level);

    void log(String info, Throwable e, LogLevel level);

    //

    void trace(String info);

    void trace(Throwable e);

    void trace(String info, Throwable e);

    //

    void debug(String info);

    void debug(Throwable e);

    void debug(String info, Throwable e);

    //

    void info(String info);

    void info(Throwable e);

    void info(String info, Throwable e);

    //
    void warn(String info);

    void warn(Throwable e);

    void warn(String info, Throwable e);

    //

    void error(String info);

    void error(Throwable e);

    void error(String info, Throwable e);

    //

    void fatal(String info);

    void fatal(Throwable e);

    void fatal(String info, Throwable e);

    //

    boolean isDebugEnabled();

    boolean isErrorEnabled();

    boolean isFatalEnabled();

    boolean isInfoEnabled();

    boolean isTraceEnabled();

    boolean isWarnEnabled();
}
