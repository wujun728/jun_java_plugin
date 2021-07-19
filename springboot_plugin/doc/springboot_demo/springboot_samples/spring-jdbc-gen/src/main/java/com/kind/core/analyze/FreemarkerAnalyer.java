package com.kind.core.analyze;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

public class FreemarkerAnalyer {
    public static final String CHARSET = "UTF-8";
    private static final Logger log = Logger.getLogger(FreemarkerAnalyer.class);

    public static Template getTemplate(String templateName) {
        try {
            Configuration configuration = new Configuration();
            File file = new File(Thread.currentThread().getContextClassLoader().getResources("").nextElement().getFile());
            FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(file);
            configuration.setTemplateLoader(fileTemplateLoader);
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            configuration.setDefaultEncoding(CHARSET);
            return configuration.getTemplate(templateName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static void writeResult(String outputFile, Template template, Map<String, Object> varMap) {
        String resultString;
        ByteArrayOutputStream baos = null;
        Writer writer = null;
        try {
            baos = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(baos, CHARSET);
            template.process(varMap, writer);
            resultString = new String(baos.toByteArray(), CHARSET);
            FileUtils.write(new File(outputFile), resultString);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (null != baos) {
                try {
                    baos.close();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }

            if (null != writer) {
                try {
                    writer.close();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    }
}
