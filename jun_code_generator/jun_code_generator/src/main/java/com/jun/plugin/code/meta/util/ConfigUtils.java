package com.jun.plugin.code.meta.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import com.jun.plugin.code.meta.build.TemplateBuilder;

public class ConfigUtils {
	
	
	public static Logger log = Logger.getLogger(ConfigUtils.class.toString());
    //配置文件
	public static Properties props = new Properties();

    //pojoPackage
    public static String PACKAGE_BASE;
    
    //pojoPackage
    public static String PACKAGE_POJO;

    //mapperPackage
    public static String PACKAGE_MAPPER;

    //serviceInterfacePackage
    public static String PACKAGE_SERVICE_INTERFACE;

    //serviceInterfaceImplPackage
    public static String PACKAGE_SERVICE_INTERFACE_IMPL;

    //controllerPackage
    public static String PACKAGE_CONTROLLER;

    //feignPackage
    public static String PACKAGE_FEIGN;

    //数据库账号
    public static String UNAME;

    //项目路径
    public static String PROJECT_PATH;

    //是否使用swagger
    public static Boolean SWAGGER;

    //服务名字
    public static String SERVICENAME;

    //swagger-ui路径
    public static String SWAGGERUI_PATH;
    
    public static String OUTROOT;
    public static String TEMPLATE_PATH;
    public static String TEMPLATE_NAME;
    public static String TABLEREMOVEPREFIXES;
    public static String ROWREMOVEPREFIXES;
    public static String SKIPTABLE;
    public static String INCLUETABLES;

    static {
        try {
            //加载配置文件
            InputStream is = TemplateBuilder.class.getClassLoader().getResourceAsStream("config.properties");

            //创建Properties对象
            props.load(is);

            //获取对应的配置信息
            PACKAGE_POJO = props.getProperty("pojoPackage");
            OUTROOT = TemplateBuilder.class.getClassLoader().getResource("").getPath().replace("/target/classes/","")+props.getProperty("outRoot");
            PACKAGE_BASE = props.getProperty("basePackage");
            PACKAGE_MAPPER = props.getProperty("mapperPackage");
            PACKAGE_SERVICE_INTERFACE = props.getProperty("serviceInterfacePackage");
            PACKAGE_SERVICE_INTERFACE_IMPL = props.getProperty("serviceInterfaceImplPackage");
            PACKAGE_CONTROLLER = props.getProperty("controllerPackage");
            PACKAGE_FEIGN= props.getProperty("feignPackage");
            UNAME = props.getProperty("uname");
            SWAGGER = Boolean.valueOf(props.getProperty("enableSwagger"));
            SERVICENAME = props.getProperty("serviceName");
            SWAGGERUI_PATH = props.getProperty("swaggeruipath");
            //工程路径
            PROJECT_PATH=TemplateBuilder.class.getClassLoader().getResource("").getPath().replace("/target/classes/","")+"/src/main/java/";
            TEMPLATE_PATH=TemplateBuilder.class.getClassLoader().getResource("").getPath().replace("/target/classes/","")+"/src/main/resources/"+props.getProperty("template_path");
            TEMPLATE_NAME=props.getProperty("template_path");
            TABLEREMOVEPREFIXES=props.getProperty("tableRemovePrefixes");
            ROWREMOVEPREFIXES=props.getProperty("rowRemovePrefixes");
            SKIPTABLE=props.getProperty("skipTable");
            INCLUETABLES=props.getProperty("inclueTables");

            //加载数据库驱动
            Class.forName(props.getProperty("driver"));
            log.info("配置加载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
