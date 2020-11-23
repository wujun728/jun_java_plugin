package com.kancy.emailplus.spring.boot.service.impl;

import com.kancy.emailplus.core.exception.TemplateException;
import com.kancy.emailplus.spring.boot.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FreemarkerEmailTemplateServiceImpl
 *
 * @author Wujun
 * @date 2020/2/21 0:11
 */
public class FreemarkerTemplateServiceImpl implements TemplateService {
    /**
     * 去除空白字符正则
     */
    private static final Pattern REMOVE_SPACE_PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    private String baseTemplatePath = "/templates/ftl";

    private Configuration configuration;

    public FreemarkerTemplateServiceImpl() {
    }

    public FreemarkerTemplateServiceImpl(String baseTemplatePath) {
        this.baseTemplatePath = baseTemplatePath;
    }

    /**
     * 渲染
     * @param templatePath
     * @param templateData
     * @return
     * @throws TemplateException
     * @return
     */
    @Override
    public Optional<String> render(String templatePath, Map<String, Object> templateData) {
        StringWriter result = null;
        try {
            Template template = getConfiguration().getTemplate(templatePath);
            result = new StringWriter();
            template.process(templateData, result);
        } catch (IOException | freemarker.template.TemplateException ex) {
            throw new TemplateException(ex.getMessage(), ex);
        }
        return Optional.ofNullable(result.getBuffer().toString());
    }

    /**
     * 渲染html格式，会自动压缩
     *
     * @param templatePath
     * @param templateData
     * @return
     * @throws TemplateException
     */
    @Override
    public Optional<String> renderHtml(String templatePath, Map<String, Object> templateData) {
        Optional<String> stringOptional = render(templatePath, templateData);
        if(stringOptional.isPresent()){
            Matcher matcher = REMOVE_SPACE_PATTERN.matcher(stringOptional.get());
            return Optional.ofNullable(matcher.replaceAll(""));
        }
        return stringOptional;
    }

    /**
     * 初始化并且线程安全
     * @return
     */
    protected final Configuration getConfiguration(){
        if (Objects.isNull(configuration)){
            synchronized (this){
                if (Objects.isNull(configuration)){
                    configuration = initConfiguration();
                }
            }
        }
        return configuration;
    }

    /**
     * 初始化配置
     * @return
     */
    protected Configuration initConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setClassForTemplateLoading(FreemarkerTemplateServiceImpl.class, baseTemplatePath);
        setConfiguration(configuration);
        return configuration;
    }

    /**
     * 设置属性
     * @param configuration
     */
    protected void setConfiguration(Configuration configuration) {
        configuration.setLocale(Locale.CHINA);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        configuration.setNumberFormat("#.######");
        configuration.setBooleanFormat("true,false");
    }

}
