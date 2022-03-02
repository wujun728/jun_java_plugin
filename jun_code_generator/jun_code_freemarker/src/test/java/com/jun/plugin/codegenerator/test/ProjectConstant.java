package com.jun.plugin.codegenerator.test;

/**
 * 项目常量
 */
public final class ProjectConstant {
    public static final String BASE_IMPORT_PACKAGE = "com.jun.plugin.base";//生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）
    
    public static final String BASE_PACKAGE = "com.jun.plugin.biz";//生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//生成的Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";//生成的Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//生成的Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//生成的ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//生成的Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_IMPORT_PACKAGE+".core.Mapper";//Mapper插件基础接口的完全限定名
}
