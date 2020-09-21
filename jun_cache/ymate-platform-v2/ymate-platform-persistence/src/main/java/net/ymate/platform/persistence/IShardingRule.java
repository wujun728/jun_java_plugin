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
package net.ymate.platform.persistence;

/**
 * 数据分片(表)规则计算接口
 *
 * @author 刘镇 (suninformation@163.com) on 16/7/3 下午11:04
 * @version 1.0
 */
public interface IShardingRule {

    /**
     * @param originalName  原始分片(表)名称
     * @param shardingParam 分片(表)辅助计算参数
     * @return 返回分片(表)名称
     */
    String getShardName(String originalName, Object shardingParam);
}
