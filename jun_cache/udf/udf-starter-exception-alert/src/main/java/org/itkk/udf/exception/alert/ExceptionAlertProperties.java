package org.itkk.udf.exception.alert;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ExceptionAlertProperties
 */
@Component
@ConfigurationProperties(prefix = "org.itkk.exception.alert.properties")
@Data
public class ExceptionAlertProperties {

    /**
     * enable
     */
    private Boolean enable = true;

    /**
     * excludeType
     */
    private String excludeType;

    /**
     * excludeMessage
     */
    private String excludeMessage;

    /**
     * excludeStackTrace
     */
    private String excludeStackTrace;

}
