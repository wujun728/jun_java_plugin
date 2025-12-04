package cn.chen.project.generatesql;

import cn.chen.project.generatesql.entity.GenerateProperties;
import cn.chen.project.generatesql.service.BuildJavaProject;
import cn.chen.project.generatesql.service.DataBaseAutoImport;
import cn.chen.project.generatesql.service.ReadConfiguration;
import cn.chen.project.generatesql.service.ReadExcel;

/**
 * @Author chenzedeng
 * @Email yustart@foxmail.com
 * @Create 2020-02-29 4:04 下午
 */
public class Main {

    public static void main(String[] args) {
        ReadConfiguration readConfiguration = new ReadConfiguration();
        GenerateProperties properties = readConfiguration.getGenerateProperties();

        ReadExcel readExcel = new ReadExcel(properties);
        if (properties.isOutputScriptFile()) {
            readExcel.outputFile();
            System.out.println("SQL文件写出成功");
        }

        if (properties.isAutoRunScript()) {
            DataBaseAutoImport autoImport = new DataBaseAutoImport(properties);
            autoImport.execute(readExcel.getScriptList());
            System.out.println("创建数据表成功");
        }

        if (properties.isBuildJava()) {
            BuildJavaProject.build(properties);
        }

        System.out.println("程序结束");
    }

}
