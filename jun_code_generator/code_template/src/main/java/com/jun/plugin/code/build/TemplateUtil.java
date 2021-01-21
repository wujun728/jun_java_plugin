package com.jun.plugin.code.build;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/****
 * @Author:shenkunlin
 * @Description:创建模板，以及输出生成的文件
 * @Date 2019/6/14 20:09
 *****/
public class TemplateUtil {

    /***
     * 创建模板对象
     * @param path
     * @param ftl
     * @return
     * @throws Exception
     */
    public static Template loadTemplate(String path, String ftl) throws Exception{
        // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File(path));
        // 第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        // 第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate(ftl);
       return template;
    }


    /***
     * 输出文件
     */
    public static void writer(Template template,Map dataModel,String file) throws Exception{
        //包参数
        dataModel.put("package_controller",TemplateBuilder.PACKAGE_CONTROLLER);
        dataModel.put("package_pojo",TemplateBuilder.PACKAGE_POJO);
        dataModel.put("package_mapper",TemplateBuilder.PACKAGE_MAPPER);
        dataModel.put("package_service",TemplateBuilder.PACKAGE_SERVICE_INTERFACE);
        dataModel.put("package_service_impl",TemplateBuilder.PACKAGE_SERVICE_INTERFACE_IMPL);

        // 创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        Writer out = new FileWriter(new File(file));
        // 调用模板对象的process方法输出文件。
        template.process(dataModel, out);
        // 关闭流。
        out.close();
    }
}
