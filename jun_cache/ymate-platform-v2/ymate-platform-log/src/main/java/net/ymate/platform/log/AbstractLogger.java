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

import net.ymate.platform.core.util.DateTimeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 抽象日志记录器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-21 下午12:27:37
 * @version 1.0
 */
public abstract class AbstractLogger implements ILogger {

    private boolean __allowOutputConsole;

    // 堆栈深度，向上寻找堆栈长度
    private int __depth = 3;

    // 打印堆栈数量，超过这个数量会省略输出
    public static int PRINT_STACK_COUNT = 5;

    public ILogger console(boolean enable) {
        __allowOutputConsole = enable;
        return this;
    }

    public ILogger depth(int depth) {
        __depth = depth;
        return this;
    }

    protected abstract void __doLogWrite(LogLevel level, LogInfo content);

    protected void __doBuildEx(String info, Throwable e, ILogger.LogLevel level) {
        LogInfo _info = new LogInfo(getLoggerName(),
                level.getDispName(),
                Thread.currentThread().getId(), __doMakeCallerInfo(), info, __doMakeStackInfo(e),
                DateTimeUtils.formatTime(System.currentTimeMillis(), DateTimeUtils.YYYY_MM_DD_HH_MM_SS_SSS));
        //
        __doLogWrite(level, _info);
        // 判断是否输出到控制台
        if (__allowOutputConsole) {
            System.out.println(_info.toString());
        }
    }

    /**
     * 获取调用者信息
     *
     * @return 找到的堆栈信息，格式为：className.methodName:lineNumber，如果找不到则返回NO_STACK_TRACE:-1
     */
    protected String __doMakeCallerInfo() {
        StackTraceElement[] _stacks = new Throwable().getStackTrace();
        // 追溯到对应的调用行，如果对应行不存在，则不给出无法确定行号的输出
        if (__depth >= 0 && _stacks.length > 1 + __depth) {
            StackTraceElement _element = _stacks[1 + __depth];
            return _element.getClassName() + "." + _element.getMethodName() + ":" + _element.getLineNumber() + StringUtils.EMPTY;
        }
        return "NO_STACK_TRACE:-1";
    }

    /**
     * 将异常转换为堆栈输出串
     *
     * @param e 需要输出的异常对象
     * @return 转换出的字符串，不为空
     */
    protected String __doMakeStackInfo(Throwable e) {
        if (e == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder _stackSB = new StringBuilder(e.getClass().getName())
                .append(": ")
                .append(StringUtils.EMPTY)
                .append(StringUtils.trimToEmpty(e.getMessage()))
                .append("\n");
        StackTraceElement[] _stacks = e.getStackTrace();
        for (StackTraceElement _stack : _stacks) {
            _stackSB.append("\tat ") // 在堆栈行开始增加空格
                    .append(_stack)
                    .append("\n");
        }
        __ex(_stackSB, e.getCause());
        return _stackSB.toString();
    }

    /**
     * 将异常输出到字符缓冲中
     *
     * @param stackSB 需要输出到的目标字符串缓冲，不可为空
     * @param t       需要输出的异常
     * @return 如果还有引起异常的源，那么返回true
     */
    protected boolean __ex(StringBuilder stackSB, Throwable t) {
        if (t != null) {
            stackSB.append("Caused by: ")
                    .append(t.getClass().getName())
                    .append(": ")
                    .append(StringUtils.trimToEmpty(t.getMessage()))
                    .append("\n");
            StackTraceElement[] _traces = t.getStackTrace();
            int _tracesSize = _traces.length;
            for (int _idx = 0; _idx < _tracesSize; _idx++) {
                if (_idx < PRINT_STACK_COUNT) {
                    stackSB.append("\tat ") // 在堆栈行开始增加空格
                            .append(_traces[_idx]).append("\n");
                } else {
                    stackSB.append("\t... ")
                            .append(_tracesSize - PRINT_STACK_COUNT)
                            .append(" more\n");
                    break;
                }
            }
            if (__ex(stackSB, t.getCause())) {
                return true;
            }
        }
        return false;
    }
}
