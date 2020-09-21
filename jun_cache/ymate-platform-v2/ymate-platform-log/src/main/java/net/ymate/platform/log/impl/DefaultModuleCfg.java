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
package net.ymate.platform.log.impl;

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.log.ILog;
import net.ymate.platform.log.ILogModuleCfg;
import net.ymate.platform.log.ILogger;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Map;

/**
 * 默认日志记录器模块配置类
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-23 下午6:26:42
 * @version 1.0
 */
public class DefaultModuleCfg implements ILogModuleCfg {

    private File configFile;
    private File outputDir;
    private String loggerName;
    private Class<? extends ILogger> loggerClass;
    private boolean allowOutputConsole;

    @SuppressWarnings("unchecked")
    public DefaultModuleCfg(YMP owner) {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(ILog.MODULE_NAME);
        //
        String _cfgsClassName = "net.ymate.platform.configuration.Cfgs";
        if (!owner.getConfig().getExcludedModules().contains("configuration")
                && !owner.getConfig().getExcludedModules().contains(_cfgsClassName)) {
            try {
                // 尝试加载配置体系模块，若存在则将决定配置文件加载的路径
                owner.getModule((Class<? extends IModule>) ClassUtils.loadClass(_cfgsClassName, this.getClass()));
            } catch (Exception ignored) {
            }
        }
        //
        this.configFile = new File(RuntimeUtils.replaceEnvVariable(StringUtils.defaultIfBlank(_moduleCfgs.get("config_file"), "${root}/cfgs/log4j.xml")));
        if (!this.configFile.isAbsolute() || !this.configFile.exists() || this.configFile.isDirectory() || !this.configFile.canRead()) {
            throw new IllegalArgumentException("The parameter configFile is invalid or is not a file");
        }
        //
        this.outputDir = new File(RuntimeUtils.replaceEnvVariable(StringUtils.defaultIfBlank(_moduleCfgs.get("output_dir"), "${root}/logs/")));
        if (!this.outputDir.isAbsolute() || !this.outputDir.exists() || !this.outputDir.isDirectory() || !this.outputDir.canRead()) {
            throw new IllegalArgumentException("The parameter outputDir is invalid or is not a directory");
        }
        //
        this.loggerName = StringUtils.defaultIfBlank(_moduleCfgs.get("logger_name"), "default").trim();
        //
        try {
            if (StringUtils.isNotBlank(_moduleCfgs.get("logger_class"))) {
                this.loggerClass = (Class<? extends ILogger>) ClassUtils.loadClass(_moduleCfgs.get("logger_class"), this.getClass());
            } else {
                this.loggerClass = DefaultLogger.class;
            }
        } catch (Exception e) {
            this.loggerClass = DefaultLogger.class;
        }
        //
        this.allowOutputConsole = new BlurObject(_moduleCfgs.get("allow_output_console")).toBooleanValue();
    }

    public File getConfigFile() {
        return this.configFile;
    }

    public File getOutputDir() {
        return this.outputDir;
    }

    public String getLoggerName() {
        return this.loggerName;
    }

    public Class<? extends ILogger> getLoggerClass() {
        return this.loggerClass;
    }

    public boolean allowOutputConsole() {
        return this.allowOutputConsole;
    }
}
