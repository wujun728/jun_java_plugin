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
package net.ymate.platform.persistence.base;

import java.io.Serializable;

/**
 * 实体模型接口
 *
 * @author 刘镇 (suninformation@163.com) on 2010-12-20 下午02:16:46
 * @version 1.0
 */
public interface IEntity<PK extends Serializable> extends Serializable {

    /**
     * @return 获取实体主键值
     */
    PK getId();

    /**
     * 设置实体主键值
     *
     * @param id 主键值
     */
    void setId(PK id);
}
