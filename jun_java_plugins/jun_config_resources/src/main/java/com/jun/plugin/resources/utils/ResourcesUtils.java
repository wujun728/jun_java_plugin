package com.jun.plugin.resources.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.jun.plugin.resources.KeyConstants;
import com.jun.plugin.resources.PrefixConstants;
import com.jun.plugin.resources.config.Config;
import com.jun.plugin.resources.config.GlobalConfig;

/**
 * 读取多环境配置文件名
 * Created by Hong on 2019/4/19
 */
public class ResourcesUtils {

    private final static String START_WITH = "/";


    /**
     * 获取带profile的资源名
     *
     * @param var1 文件名
     * @return
     */
    public static String getProfileProperties(String var1) {
        String profile = GlobalConfig.get().getValue(KeyConstants.PROFILE);
        if(StringUtils.isEmpty(profile)) {
            profile = System.getProperty(KeyConstants.PROFILE);
        }
        return getProfileProperties(var1, profile);
    }

    /**
     * @param var1    文件名
     * @param profile 多环境
     * @return
     */
    public static String getProfileProperties(String var1, String profile) {
        if (StringUtils.isEmpty(profile)) {
            return var1;
        }
        profile = "-" + profile;

        StringBuilder builder = new StringBuilder(var1);

        //如果文件名已经存在profile，就不继续后续操作了
        if (builder.indexOf(profile) > -1) {
            return builder.toString();
        }

        int index = builder.lastIndexOf(".");
        if (index == -1) {
            builder.append(profile);
        } else {
            builder.insert(index, profile);
        }
        return builder.toString();
    }

    /**
     * 去除前缀的文件名
     *
     * @param var1 带前缀的文件名
     * @return
     */
    public static String getPropertiesName(String var1) {
        if (var1.indexOf(":") > -1) {
            return var1.substring(var1.indexOf(":") + 1);
        }
        return var1;
    }

    /**
     * classpath转换成可用的包内路径
     *
     * @param var1 classpath路径
     * @return 包内正确路径
     */
    public static String convert(String var1) {
        if (!var1.startsWith(START_WITH)) {
            var1 = START_WITH + var1;
        }
        return var1;
    }

    /**
     * @param var1        文件路径
     * @param isClassPath 是否是包内文件
     * @return
     */
    public static boolean exists(String var1, boolean isClassPath) {
        if (isClassPath) {
            //包内文件是否存在
            String name = convert(var1);
            try (InputStream inputStream = ResourcesUtils.class.getClass().getResourceAsStream(name)) {
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        //包外文件是否存在
        return new File(var1).exists();
    }

    public static InputStream classInputStream(String path) {
        path = convert(path);
        return ResourcesUtils.class.getClass().getResourceAsStream(path);
    }

}
