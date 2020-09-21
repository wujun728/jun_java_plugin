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
package net.ymate.platform.core.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 运行时工具类，获取运行时相关信息
 *
 * @author 刘镇 (suninformation@163.com) on 2010-8-2 上午10:10:16
 * @version 1.0
 */
public class RuntimeUtils {

    private static final Log _LOG = LogFactory.getLog(RuntimeUtils.class);

    /**
     * 系统环境变量映射
     */
    private static final Map<String, String> SYSTEM_ENV_MAP = new HashMap<String, String>();

    static {
        initSystemEnvs();
    }

    /**
     * 初始化系统环境，获取当前系统环境变量
     */
    public static void initSystemEnvs() {
        Process p = null;
        BufferedReader br = null;
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                p = Runtime.getRuntime().exec("cmd /c set");
            } else if (SystemUtils.IS_OS_UNIX) {
                p = Runtime.getRuntime().exec("/bin/sh -c set");
            } else {
                _LOG.warn("Unknown os.name=" + SystemUtils.OS_NAME);
                SYSTEM_ENV_MAP.clear();
            }
            if (p != null) {
                br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    int i = line.indexOf('=');
                    if (i > -1) {
                        String key = line.substring(0, i);
                        String value = line.substring(i + 1);
                        SYSTEM_ENV_MAP.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            _LOG.warn(RuntimeUtils.unwrapThrow(e));
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    _LOG.warn("", e);
                }
            }
            if (p != null) {
                p.destroy();
            }
        }
    }

    /**
     * 获取系统运行时，可以进行缓存
     *
     * @return 环境变量对应表
     */
    public static Map<String, String> getSystemEnvs() {
        if (SYSTEM_ENV_MAP.isEmpty()) {
            initSystemEnvs();
        }
        return SYSTEM_ENV_MAP;
    }

    /**
     * 获取指定名称的环境值
     *
     * @param envName 环境名，如果为空，返回null
     * @return 当指定名称为空或者对应名称环境变量不存在时返回空
     */
    public static String getSystemEnv(String envName) {
        if (StringUtils.isNotBlank(envName)) {
            if (SYSTEM_ENV_MAP.isEmpty()) {
                initSystemEnvs();
            }
            return SYSTEM_ENV_MAP.get(envName);
        }
        return null;
    }

    /**
     * @return 当前操作系统是否为类Unix系统
     */
    public static boolean isUnixOrLinux() {
        return SystemUtils.IS_OS_UNIX;
    }

    /**
     * @return 当前操作系统是否为Windows系统
     */
    public static boolean isWindows() {
        return SystemUtils.IS_OS_WINDOWS;
    }

    /**
     * @return 获取应用根路径（若WEB工程则基于.../WEB-INF/返回，若普通工程则返回类所在路径）
     */
    public static String getRootPath() {
        return getRootPath(true);
    }

    /**
     * @param safe 若WEB工程是否保留WEB-INF
     * @return 返回应用根路径
     */
    public static String getRootPath(boolean safe) {
        //
        String _rootPath = null;
        //
        URL _rootURL = RuntimeUtils.class.getClassLoader().getResource("/");
        if (_rootURL == null) {
            _rootURL = RuntimeUtils.class.getClassLoader().getResource("");
            if (_rootURL != null) {
                _rootPath = _rootURL.getPath();
            }
        } else {
            _rootPath = StringUtils.removeEnd(StringUtils.substringBefore(_rootURL.getPath(), safe ? "classes/" : "WEB-INF/"), "/");
        }
        //
        if (_rootPath != null) {
            _rootPath = StringUtils.replace(_rootPath, "%20", " ");
            if (isWindows()) {
                _rootPath = StringUtils.removeStart(_rootPath, "/");
            }
        }
        return StringUtils.trimToEmpty(_rootPath);
    }

    /**
     * @param origin 原始字符串
     * @return 替换${root}、${user.dir}和${user.home}环境变量
     */
    public static String replaceEnvVariable(String origin) {
        if ((origin = StringUtils.trimToNull(origin)) != null) {
            String _defaultPath = getRootPath();
            if (StringUtils.startsWith(origin, "${root}")) {
                origin = ExpressionUtils.bind(origin).set("root", _defaultPath).getResult();
            } else if (StringUtils.startsWith(origin, "${user.dir}")) {
                origin = ExpressionUtils.bind(origin).set("user.dir", System.getProperty("user.dir", _defaultPath)).getResult();
            } else if (StringUtils.startsWith(origin, "${user.home}")) {
                origin = ExpressionUtils.bind(origin).set("user.home", System.getProperty("user.home", _defaultPath)).getResult();
            }
        }
        return origin;
    }

    /**
     * 根据格式化字符串，生成运行时异常
     *
     * @param format 格式
     * @param args   参数
     * @return 运行时异常
     */
    public static RuntimeException makeRuntimeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }

    /**
     * 将抛出对象包裹成运行时异常，并增加描述
     *
     * @param e    抛出对象
     * @param fmt  格式
     * @param args 参数
     * @return 运行时异常
     */
    public static RuntimeException wrapRuntimeThrow(Throwable e, String fmt, Object... args) {
        return new RuntimeException(String.format(fmt, args), e);
    }

    /**
     * 用运行时异常包裹抛出对象，如果抛出对象本身就是运行时异常，则直接返回
     * <p>
     * 若 e 对象是 InvocationTargetException，则将其剥离，仅包裹其 TargetException 对象
     * </p>
     *
     * @param e 抛出对象
     * @return 运行时异常
     */
    public static RuntimeException wrapRuntimeThrow(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        if (e instanceof InvocationTargetException) {
            return wrapRuntimeThrow(((InvocationTargetException) e).getTargetException());
        }
        return new RuntimeException(e);
    }

    public static Throwable unwrapThrow(Throwable e) {
        if (e == null) {
            return null;
        }
        if (e instanceof InvocationTargetException) {
            InvocationTargetException itE = (InvocationTargetException) e;
            if (itE.getTargetException() != null) {
                return unwrapThrow(itE.getTargetException());
            }
        }
        if (e.getCause() != null) {
            return unwrapThrow(e.getCause());
        }
        return e;
    }

    /**
     * 垃圾回收，返回回收的字节数
     *
     * @return 回收的字节数，如果为负数则表示当前内存使用情况很差，基本属于没有内存可用了
     */
    public static long gc() {
        Runtime rt = Runtime.getRuntime();
        long lastUsed = rt.totalMemory() - rt.freeMemory();
        rt.gc();
        return lastUsed - rt.totalMemory() + rt.freeMemory();
    }

    public static Map<String, String> keyStartsWith(Map<String, String> map, String keyPrefix) {
        Map<String, String> _returnValues = new HashMap<String, String>();
        for (Map.Entry<String, String> _entry : map.entrySet()) {
            String _key = _entry.getKey();
            if (StringUtils.startsWith(_key, keyPrefix)) {
                String _cfgKey = StringUtils.substring(_key, keyPrefix.length());
                _returnValues.put(_cfgKey, _entry.getValue());
            }
        }
        return _returnValues;
    }
}
