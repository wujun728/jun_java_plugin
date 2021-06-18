/**
 * Copyright (c) 2011-2014, guaika (junchen1314@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package xin.guaika.cloud.drools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xin.guaika.cloud.drools.model.User;

/**
 * <P>
 * Drools 测试
 * </P>
 * 
 * @author Wujun
 * @Data 2017年8月30日 上午10:08:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=DroolsTestApplication.class)
public class DroolsTest {
	 private static final Logger _log = LoggerFactory.getLogger(DroolsTest.class);
	    
    @Autowired
    private KieSession kieSession;
    
    @Test
    public void test1() {
    	User user = new User();
    	user.setName("test");
    	kieSession.insert(user);
        int ruleFiredCount = kieSession.fireAllRules();
        _log.info("rule:{}",ruleFiredCount);
    }
}
