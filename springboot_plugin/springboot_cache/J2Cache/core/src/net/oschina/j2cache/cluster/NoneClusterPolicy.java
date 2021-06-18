/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.cluster;

import net.oschina.j2cache.Command;

import java.util.Properties;

/**
 * 实现空的集群通知策略
 * @author Wujun
 */
public class NoneClusterPolicy implements ClusterPolicy {

    @Override
    public void connect(Properties props) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void publish(Command cmd) {

    }
}
