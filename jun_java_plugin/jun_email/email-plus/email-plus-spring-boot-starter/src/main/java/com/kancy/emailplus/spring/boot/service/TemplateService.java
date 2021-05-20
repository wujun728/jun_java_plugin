package com.kancy.emailplus.spring.boot.service;

import com.kancy.emailplus.core.exception.TemplateException;

import java.util.Map;
import java.util.Optional;

/**
 * TemplateService
 *
 * @author Wujun
 * @date 2020/2/21 0:07
 */
public interface TemplateService {
    /**
     * 渲染
     * @param templatePath
     * @param templateData
     * @throws TemplateException
     * @return
     */
    Optional<String> render(String templatePath, Map<String, Object> templateData);

    /**
     * 渲染
     * @param templatePath
     * @param templateData
     * @param isHtml
     * @throws TemplateException
     * @return
     */
    default Optional<String> render(String templatePath, Map<String, Object> templateData, boolean isHtml) {
        return isHtml ? renderHtml(templatePath, templateData) : render(templatePath, templateData);
    }

    /**
     * 渲染html格式，会自动压缩
     * @param templatePath
     * @param templateData
     * @throws TemplateException
     * @return
     */
    Optional<String> renderHtml(String templatePath, Map<String, Object> templateData);
}
