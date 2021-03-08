package com.alibaba.dubbo.registry.hazelcast;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;

/**
 * Created by wuyu on 2017/4/24.
 */
public class HazelcastRegistryFactory extends AbstractRegistryFactory {

    @Override
    protected Registry createRegistry(URL url) {
        return new HazelcastRegistry(url);
    }
}
