package com.battcn.boot.extend.configuration.mybatis.plugins;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.MYBATIS_PLUGIN;

/**
 * @author Levin
 */
@Data
@Validated
@ConfigurationProperties(prefix = MYBATIS_PLUGIN)
public class PreparePluginProperties {

    private boolean enabled = true;
    private Boolean superClass = false;
    private ClassField insert;
    private ClassField update;


    @Data
    public static class ClassField {
        private List<String> fields;
    }
}
