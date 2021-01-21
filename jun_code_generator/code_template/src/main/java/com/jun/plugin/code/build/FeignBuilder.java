package com.jun.plugin.code.build;

import java.util.Map;

/****
 * @Author:shenkunlin
 * @Description:Feign构建
 * @Date 2019/6/14 19:13
 *****/
public class FeignBuilder {


    /***
     * 构建Feign
     * @param modelMap
     */
    public static void builder(Map<String,Object> modelMap){
        //生成Dao层文件
        BuilderFactory.builder(modelMap,
                "/template/feign",
                "Feign.java",
                TemplateBuilder.PACKAGE_FEIGN,
                "Feign.java");
    }

}
