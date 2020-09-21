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
package net.ymate.platform.plugin;

/**
 * 插件配置信息元数据描述类
 *
 * @author 刘镇 (suninformation@163.com) on 2011-10-17 下午04:52:05
 * @version 1.0
 */
public class PluginMeta {

    /**
     * 插件唯一ID
     */
    private String id;

    /**
     * 插件名称
     */
    private String name;

    /**
     * 插件别名
     */
    private String alias;

    /**
     * 插件初始启动类
     */
    private Class<? extends IPlugin> initClass;

    /**
     * 插件版本
     */
    private String version;

    /**
     * 插件文件存放路径
     */
    private String path;

    /**
     * 插件作者
     */
    private String author;

    /**
     * 作者联系邮箱
     */
    private String email;

    /**
     * 当前插件类加载器
     */
    private ClassLoader classLoader;

    /**
     * 插件扩展对象
     */
    private Object extendObject;

    /**
     * 是否加载时自动启动运行
     */
    private boolean automatic;

    /**
     * 是否禁用当前插件
     */
    private boolean disabled;

    /**
     * 插件描述
     */
    private String description;

    public PluginMeta(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public PluginMeta(ClassLoader classLoader,
                      String id,
                      String name,
                      String alias,
                      Class<? extends IPlugin> initClass,
                      String version,
                      String path,
                      String author,
                      String email,
                      Object extendObject,
                      boolean automatic,
                      boolean disabled,
                      String description) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.initClass = initClass;
        this.version = version;
        this.path = path;
        this.author = author;
        this.email = email;
        this.classLoader = classLoader;
        this.extendObject = extendObject;
        this.automatic = automatic;
        this.disabled = disabled;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Class<? extends IPlugin> getInitClass() {
        return initClass;
    }

    public void setInitClass(Class<? extends IPlugin> initClass) {
        this.initClass = initClass;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Object getExtendObject() {
        return extendObject;
    }

    public void setExtendObject(Object extendObject) {
        this.extendObject = extendObject;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
