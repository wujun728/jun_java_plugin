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

/**
 * 配置体系模块配置接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2012-11-28 下午9:04:52
 * @version 1.0
 */
public interface IConfigModuleCfg {

    /**
     * @return 返回配置体系根路径
     */
    String getConfigHome();

    /**
     * @return 返回项目名称
     */
    String getProjectName();

    /**
     * @return 返回模块名称
     */
    String getModuleName();

    /**
     * @return 返回配置提供者接口类型
     */
    Class<? extends IConfigurationProvider> getProviderClass();
}
