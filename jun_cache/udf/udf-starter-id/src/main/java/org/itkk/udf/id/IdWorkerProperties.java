package org.itkk.udf.id;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * IdWorkerProperties
 */
@Component
@ConfigurationProperties(prefix = "org.itkk.id.properties")
@Validated
@Data
public class IdWorkerProperties {

    /**
     * 最大个数
     */
    private int maxCount = IdWorkerFactory.MAX_SEQUENCE;

}
