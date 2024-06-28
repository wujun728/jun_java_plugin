package io.github.wujun728.common.lb.chooser;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * service选择器类
 *
 */
public interface IRuleChooser {
    ServiceInstance choose(List<ServiceInstance> instances);
}
