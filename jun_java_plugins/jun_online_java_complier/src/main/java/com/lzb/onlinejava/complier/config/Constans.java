package com.lzb.onlinejava.complier.config;

import com.lzb.onlinejava.complier.util.ResourcesUtil;
import org.springframework.util.ResourceUtils;

/**
 * author: haiyangp
 * date:  2017/9/23
 * desc: 配置常量
 */
public class Constans {

//    public static final String classPath = "C:\\Users\\bin\\Desktop\\java\\";

    public static String classPath = "C:\\Users\\ucmed\\Desktop\\java\\";
    public static final String excuteMainMethodName = "main";

    static {
        Constans.classPath = ResourcesUtil.getValue("path", "file");
    }
}
