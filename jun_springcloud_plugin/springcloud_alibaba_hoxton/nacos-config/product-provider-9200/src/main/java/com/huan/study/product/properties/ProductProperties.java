package com.huan.study.product.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author huan.fu 2020/11/18 - 20:47
 */
@ConfigurationProperties(prefix = "product")
@Configuration
@Data
public class ProductProperties {

    private String name;
    private List<String> colors;
    private Long price;

    private Map<String, ProductProperties> productMap;
}
