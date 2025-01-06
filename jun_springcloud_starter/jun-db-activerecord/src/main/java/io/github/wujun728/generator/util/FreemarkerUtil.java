package io.github.wujun728.generator.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;


import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * freemarker tool
 *
 */
@Slf4j
public class FreemarkerUtil {

    /**
     * freemarker config
     */
    private static Configuration freemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    static{
        String templatePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        int wei = templatePath.lastIndexOf("WEB-INF/classes/");
        if (wei > -1) {
            templatePath = templatePath.substring(0, wei);
        }
        init2DirectoryForTemplateLoading(templatePath+ "templates");
    }

    public static void init2ClassForTemplateLoading(String templatePath){
        freemarkerConfig.setClassForTemplateLoading(FreemarkerUtil.class,templatePath);
        freemarkerConfig.setNumberFormat("#");
        freemarkerConfig.setClassicCompatible(true);
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setLocale(Locale.CHINA);
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
    public static void init2DirectoryForTemplateLoading(String templateDirectoryPath){
        try {
            freemarkerConfig.setDirectoryForTemplateLoading(new File(templateDirectoryPath));
            freemarkerConfig.setNumberFormat("#");
            freemarkerConfig.setClassicCompatible(true);
            freemarkerConfig.setDefaultEncoding("UTF-8");
            freemarkerConfig.setLocale(Locale.CHINA);
            freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (FileNotFoundException e) {
            log.info("默认加载[\\templates]目录下模板失败，目录不存在。");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * process String
     *
     * @param templateName
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processString(String templateName, Map<String, Object> params)
            throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName);
        String htmlText = processTemplateIntoString(template, params);
        return htmlText;
    }

    /**
     * process Template Into String
     * @param template
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplateIntoString(Template template, Object model)
            throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }


    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************

    public static void main(String[] args) throws Exception {
        genStringTemplate();
    }

    public static void genStringTemplate() throws Exception {
        String templateName = "hello-template";
        String templateValue = "hello,${name}";

        Map<String, Object> root = new HashMap<>(4);
        root.put("name", "你好");
        root.put("age", 25);
        System.out.println(processStringTemplate( templateName, templateValue,root));
        // -------------------- 进行模板的修改 ------------------------
        templateValue = "hello,${name},我今年,${age}岁.";
        System.out.println(processStringTemplate(  templateValue,root));

    }


    @Deprecated
    public static String genTemplateStr(Map<String, Object> params,String templateContent)
            throws IOException, TemplateException {
        return genTemplateStr(params,"temp",templateContent);
    }
    /**
     *  解析模板
     * @param params 内容
     * @param templateName 参数
     * @param templateContent 参数
     * @return
     */
    public static String genTemplateStr(Map<String, Object> params,String templateName,String templateContent)
            throws IOException, TemplateException {
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        Template template = new Template(templateName, new StringReader(templateContent));
        StringWriter result = new StringWriter();
        template.process(params, result);
        String htmlText = result.toString();
        return htmlText;
    }

    public static String processStringTemplate(String templateStr, Map<String, Object> root) throws IOException, TemplateException {
        return processStringTemplate("temp",templateStr,root);
    }
    /**
     * 解析模板
     * @param templateName
     * @throws IOException
     * @throws TemplateException
     */
    public static String processStringTemplate(String templateName, String templateStr, Map<String, Object> root) throws IOException, TemplateException {
        Configuration configuration = configuration();
        StringWriter stringWriter = new StringWriter();
        Template template = new Template(templateName, templateStr, configuration);
        template.process(root, stringWriter);
        //System.out.println(stringWriter.toString());
        return stringWriter.toString();
    }

    /**
     * 配置 freemarker configuration
     * @return
     */
    private static Configuration configuration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }



}
