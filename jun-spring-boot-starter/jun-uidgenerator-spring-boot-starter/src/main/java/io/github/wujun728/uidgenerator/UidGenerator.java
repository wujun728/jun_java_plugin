/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.wujun728.uidgenerator;

import io.github.wujun728.uidgenerator.exception.UidGenerateException;

/**
 * Represents a unique id generator.
 * @Use 启动类添加@MapperScan("com.houger")
 * 配置文件添加：
 * spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 * spring.datasource.url=jdbc:mysql://127.0.0.1:3306/DB_BASE?useSSL=false&characterEncoding=UTF-8&allowMultiQueries=true
 * spring.datasource.username=root
 * spring.datasource.password=root
 *
 * # mybatis configure
 * mybatis.mapper-locations=classpath:mapper/*.xml
 * mybatis.type-aliases-package=com.houger.worker.entity
 *
 * uid.gen.timeBits=32
 * uid.gen.workerBits=22
 * uid.gen.seqBits=9
 * uid.gen.epochStr=2022-11-11
 * uid.gen.boostPower=3
 * uid.gen.scheduleInterval=60
 */
public interface UidGenerator {

    /**
     * Get a unique ID
     *
     * @return UID
     * @throws UidGenerateException
     */
    long getUID() throws UidGenerateException;

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);

}
