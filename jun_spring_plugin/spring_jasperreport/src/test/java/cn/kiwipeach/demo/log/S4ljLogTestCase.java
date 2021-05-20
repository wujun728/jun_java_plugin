/*
 * Copyright 2017 KiWiPeach.
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
package cn.kiwipeach.demo.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create Date: 2018/01/08
 * Description: 日志功能测试案例
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class S4ljLogTestCase {

    private final Logger logger = LoggerFactory.getLogger(S4ljLogTestCase.class);

    @Test
    public void testLogLevel(){
        //trace,debug,info,warn,error
        int n = 1000000;
        for (int i=0;i<n;i++){
            logger.trace("trace level...");
            logger.debug("debug level...");
            logger.info("info level...");
            logger.warn("warn level...");
            logger.error("error level...");
        }
    }
}
