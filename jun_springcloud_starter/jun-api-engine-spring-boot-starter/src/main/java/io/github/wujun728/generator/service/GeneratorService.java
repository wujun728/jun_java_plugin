package io.github.wujun728.generator.service;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * GeneratorService
 */
public interface GeneratorService {

    String getTemplateConfig() throws IOException;

    public Map<String, String> getResultByParams(Map<String, Object> params) throws IOException, TemplateException;

}
