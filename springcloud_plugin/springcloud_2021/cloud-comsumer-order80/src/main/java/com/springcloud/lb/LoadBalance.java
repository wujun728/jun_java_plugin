package com.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalance {

    ServiceInstance instance(List<ServiceInstance> serviceInstances);

}
