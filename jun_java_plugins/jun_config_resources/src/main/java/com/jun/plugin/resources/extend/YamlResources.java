package com.jun.plugin.resources.extend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.jun.plugin.resources.core.AbstractResources;
import com.jun.plugin.resources.core.properties.YamlProperties;
import com.jun.plugin.resources.utils.ResourcesUtils;

/**
 * Created by Hong on 2017/12/26.
 */
public final class YamlResources extends AbstractResources {

    /**
     * 从class中读取资源
     *
     * @param path 包路径（/区分路径层级关系）
     * @throws IOException IOException
     */
    public void loadByClassPath(String path) throws IOException {
        this.load(ResourcesUtils.classInputStream(path));
    }

    /**
     * 从文件中读取资源
     *
     * @param file 文件
     * @throws IOException IOException
     */
    public void loadByFile(File file) throws IOException {
        this.load(new FileInputStream(file));
    }

    /**
     * 从指定路径中读取资源
     *
     * @param path 路径
     * @throws IOException IOException
     */
    public void loadByFilePath(String path) throws IOException {
        this.loadByFile(new File(path));
    }


    @Override
    public void load(InputStream is) throws IOException {
        if (is == null) {
            return;
        }
        YamlProperties properties = new YamlProperties();
        properties.load(is);
        map.putAll(properties);
    }

    @Override
    public Map<Object, Object> getConfig() {
        return map;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
