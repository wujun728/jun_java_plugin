package com.jun.plugin.resources.core;

import com.jun.plugin.resources.PrefixConstants;
import com.jun.plugin.resources.utils.ResourcesUtils;

/**
 * profile资源名处理
 * Created By Hong on 2018/8/17
 **/
public abstract class AbstractResourceName {

    /**
     * 获取前缀名
     *
     * @return classpath, file, db, git
     */
    public String getPrefixName(String sourceName) {
        int index = sourceName.indexOf(":");
        if (index > -1) {
            return sourceName.substring(0, index).toLowerCase();
        } else {
            return "";
        }
    }

    /**
     * 获取后缀名
     *
     * @return yml, yaml, xml, properties
     */
    public String getSuffixName(String sourceName) {
        int index = sourceName.lastIndexOf(".");
        if (index > -1) {
            return sourceName.substring(index + 1).toLowerCase();
        } else {
            return "";
        }
    }

    /**
     * 是否是Classpath包内文件
     *
     * @param prefixName 前缀
     * @return trur 是包内文件
     */
    protected boolean isClassFile(String prefixName) {
        if (PrefixConstants.CLASS.equalsIgnoreCase(prefixName)) {
            return true;
        }
        return false;
    }

}
