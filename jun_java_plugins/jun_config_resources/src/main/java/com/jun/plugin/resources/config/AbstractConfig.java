package com.jun.plugin.resources.config;

import java.io.IOException;

import com.jun.plugin.resources.Resources;
import com.jun.plugin.resources.SuffixConstants;
import com.jun.plugin.resources.extend.PropertiesResources;
import com.jun.plugin.resources.extend.XmlResources;
import com.jun.plugin.resources.extend.YamlResources;

/**
 * Created by Hong on 2018/1/3.
 */
public abstract class AbstractConfig {

    private final static String[] resourcesNames = {SuffixConstants.YML, SuffixConstants.YAML, SuffixConstants.PROPERTIES, SuffixConstants.XML};

    /**
     * 获取资源文件
     *
     * @param name classpath 文件名
     * @return Resources
     * @throws IOException IOException
     */
    protected Resources getResources(String name) throws IOException {
        name = getResourcesName(name);
        if (name.endsWith(SuffixConstants.YAML) || name.endsWith(SuffixConstants.YML)) {
            //Yaml资源
            YamlResources resources = new YamlResources();
            resources.loadByClassPath(name);
            return resources;
        } else if (name.endsWith(SuffixConstants.PROPERTIES)) {
            //Properties资源
            PropertiesResources resources = new PropertiesResources();
            resources.loadByClassPath(name);
            return resources;
        } else if (name.endsWith(SuffixConstants.XML)) {
            //Xml资源
            XmlResources resources = new XmlResources();
            resources.loadByClassPath(name);
            return resources;
        }
        return null;
    }

    /**
     * 获取存在的资源名
     *
     * @param resourcesName 资源名（不含后缀）
     * @return 资源名
     */
    private String getResourcesName(String resourcesName) {
        for (String name : resourcesNames) {
            name = resourcesName + "." + name;
            if (getClass().getResourceAsStream("/" + name) != null) {
                return name;
            }
        }
        return "";
    }

}
