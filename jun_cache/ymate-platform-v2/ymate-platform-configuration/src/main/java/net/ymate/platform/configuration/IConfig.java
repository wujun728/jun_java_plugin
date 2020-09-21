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
package net.ymate.platform.configuration;

import net.ymate.platform.core.YMP;

import java.io.File;

/**
 * 配置体系管理器接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 15-3-9 上午12:24
 * @version 1.0
 */
public interface IConfig {

    String MODULE_NAME = "configuration";

    String __PROJECTS_FOLDER_NAME = "projects";
    String __MODULES_FOLDER_NAME = "modules";

    String __CONFIG_HOME = "CONFIG_HOME";
    String __USER_HOME = "user.home";
    String __USER_DIR = "user.dir";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回配置体系模块配置对象
     */
    IConfigModuleCfg getModuleCfg();

    /**
     * @return 返回配置体系主目录路径
     */
    String getConfigHome();

    /**
     * @return 返回项目主目录路径
     */
    String getProjectHome();

    /**
     * @return 返回模块主目录路径
     */
    String getModuleHome();

    /**
     * @return 返回系统变量user.home值
     */
    String getUserHome();

    /**
     * @return 返回系统变量user.dir值
     */
    String getUserDir();

    /**
     * 搜索配置文件真实资源路径，先在配置体系中查找，再到项目 CLASSPATH 路径中查找，若 cfgFile 以 "jar:" 开头则直接返回
     *
     * @param cfgFile 配置文件相对路径及名称
     * @return 配置文件真实路径
     */
    String searchPath(String cfgFile);

    /**
     * 按照模块路径-&gt;项目路径-&gt;主路径(CONFIG_HOME)-&gt;用户路径(user.dir)-&gt;系统用户路径(user.home)的顺序寻找指定文件
     *
     * @param cfgFile 配置文件路径及名称
     * @return 找到的文件File对象，只要找到存在的File，立即停止寻找并返回当前File实例
     */
    File searchFile(String cfgFile);

    /**
     * @param cfgFileName 配置所需要的装载参数
     * @param search      是否采用搜索
     * @return 根据配置文件名称自动分析文件类型(xml或properties)并填充配置对象, 若未找到则返回null
     */
    IConfiguration loadCfg(String cfgFileName, boolean search);

    IConfiguration loadCfg(String cfgFileName);

    boolean fillCfg(IConfiguration config, String cfgFileName);

    /**
     * 填充配置对象
     *
     * @param config      配置对象，不可为空
     * @param cfgFileName 配置所需要的装载参数
     * @param search      是否采用搜索
     * @return 是否成功装载配置
     */
    boolean fillCfg(IConfiguration config, String cfgFileName, boolean search);

    boolean fillCfg(IConfiguration config);

    /**
     * 装载配置，根据Configuration注解指定的配置文件进行加载，否则默认使用当前配置类对象的SimpleName作为配置文件名，即：SimpleName.CfgTagName.xml
     *
     * @param config 配置对象，不可为空
     * @param search 是否采用搜索
     * @return 是否成功装载配置
     */
    boolean fillCfg(IConfiguration config, boolean search);

    /**
     * 根据自定义配置提供者填充配置对象
     *
     * @param providerClass 配置提供者类对象，若为空则采用框架默认
     * @param config        配置对象，不可为空
     * @param cfgFileName   配置所需要的装载参数
     * @param search        是否采用搜索
     * @return 是否成功装载配置
     */
    boolean fillCfg(Class<? extends IConfigurationProvider> providerClass, IConfiguration config, String cfgFileName, boolean search);
}
