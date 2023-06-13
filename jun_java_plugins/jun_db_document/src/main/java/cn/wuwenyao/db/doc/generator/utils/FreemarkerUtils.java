package cn.wuwenyao.db.doc.generator.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/***
 * freemaker工具类
 *
 * @author wwy
 *
 */
public class FreemarkerUtils {

    private static final String COLON = ":";

    /**
     * 通过文件名加载模版
     *
     * @param ftlName
     */
    public static Template getTemplate(String ftlName) throws Exception {
        try {
            // 通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate(ftlName, IOUtils.toString(FreemarkerUtils.class.getClassLoader().getResourceAsStream(ftlName)));
            cfg.setEncoding(Locale.CHINA, "utf-8");
            // 设定去哪里读取相应的ftl模板文件
            cfg.setTemplateLoader(stringLoader);
            // 在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(ftlName);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getClassResources() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")))
                .replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(COLON) != 1) {
            path = File.separator + path;
        }
        return path;
    }

}
