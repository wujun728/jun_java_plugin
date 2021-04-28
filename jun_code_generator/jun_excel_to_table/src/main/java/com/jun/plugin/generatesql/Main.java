package com.jun.plugin.generatesql;

import com.jun.plugin.generatesql.entity.GenerateProperties;
import com.jun.plugin.generatesql.service.BuildJavaProject;
import com.jun.plugin.generatesql.service.DataBaseAutoImport;
import com.jun.plugin.generatesql.service.ReadConfiguration;
import com.jun.plugin.generatesql.service.ReadExcel;

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
