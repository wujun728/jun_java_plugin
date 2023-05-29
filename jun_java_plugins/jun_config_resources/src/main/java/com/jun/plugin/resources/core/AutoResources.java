package com.jun.plugin.resources.core;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.jun.plugin.resources.PrefixConstants;
import com.jun.plugin.resources.Resources;
import com.jun.plugin.resources.SuffixConstants;
import com.jun.plugin.resources.convert.impl.*;
import com.jun.plugin.resources.extend.*;
import com.jun.plugin.resources.utils.ResourcesUtils;
import com.jun.plugin.resources.utils.StringUtils;

/**
 * 自动识别资源格式并读取，支持ant.core.resources.profile配置
 * Created by Hong on 2017/12/27.
 */
public class AutoResources extends AbstractResourceName implements Resources {

    private Resources resources;

    public AutoResources(String name) throws IOException {
        String prefixName = getPrefixName(name);
        String propertiesName = ResourcesUtils.getPropertiesName(name);
        //获取修正后的正确资源名
        if (PrefixConstants.DB.equalsIgnoreCase(prefixName)) {
            //来源 Db
            DbResources resources = new DbResources();
            if (StringUtils.isEmpty(propertiesName)) {
                resources.load();
            } else {
                resources.load(propertiesName);
            }
            this.resources = resources;
            return;
        } else if (PrefixConstants.GIT.equalsIgnoreCase(prefixName)) {
            //来源 Git
            GitResources resources = new GitResources();
            if (StringUtils.isEmpty(propertiesName)) {
                resources.load();
            } else {
                resources.load(propertiesName);
            }
            this.resources = resources;
            return;
        }

        String suffixName = getSuffixName(name);
        if (SuffixConstants.YAML.equalsIgnoreCase(suffixName) || SuffixConstants.YML.equalsIgnoreCase(suffixName)) {
            //Yaml资源
            YamlResources resources = new YamlResources();
            if (super.isClassFile(prefixName)) {
                resources.loadByClassPath(propertiesName);
            } else {
                resources.loadByFilePath(propertiesName);
            }
            this.resources = resources;
        } else if (SuffixConstants.PROPERTIES.equalsIgnoreCase(suffixName)) {
            //Properties资源
            PropertiesResources resources = new PropertiesResources();
            if (super.isClassFile(prefixName)) {
                resources.loadByClassPath(propertiesName);
            } else {
                resources.loadByFilePath(propertiesName);
            }
            this.resources = resources;
        } else if (SuffixConstants.XML.equalsIgnoreCase(suffixName)) {
            //Xml资源
            XmlResources resources = new XmlResources();
            if (super.isClassFile(prefixName)) {
                resources.loadByClassPath(propertiesName);
            } else {
                resources.loadByFilePath(propertiesName);
            }
            this.resources = resources;
        }
    }

    @Override
    public Map<Object, Object> getResources() {
        if (resources != null) {
            return resources.getResources();
        }
        return Collections.emptyMap();
    }

    @Override
    public void writeLocalProperties() {
        resources.writeLocalProperties();
    }

    @Override
    public String getValue(String key) {
        return resources == null ? null : new StringConvert().convert(resources.getResources().get(key));
    }

    @Override
    public Boolean getBooleanValue(String key) {
        return resources == null ? null : new BooleanConvert().convert(resources.getResources().get(key));
    }

    @Override
    public Integer getIntegerValue(String key) {
        return resources == null ? null : new IntegerConvert().convert(resources.getResources().get(key));
    }

    @Override
    public Float getFloatValue(String key) {
        return resources == null ? null : new FloatConvert().convert(resources.getResources().get(key));
    }

    @Override
    public Double getDoubleValue(String key) {
        return resources == null ? null : new DoubleConvert().convert(resources.getResources().get(key));
    }

    @Override
    public Character getCharValue(String key) {
        return resources == null ? null : new CharConvert().convert(resources.getResources().get(key));
    }

    @Override
    public Object get(String key) {
        return resources == null ? null : resources.getResources().get(key);
    }

    @Override
    public Map<Object, Object> getConfig() {
        return resources.getResources();
    }

    @Override
    public void clear() {
        this.resources.clear();
    }
}
