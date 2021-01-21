package com.jun.plugin.code.build;

import javafx.scene.control.Tab;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

import com.jun.plugin.code.swagger.*;
import com.jun.plugin.code.util.*;

/****
 * @Author:shenkunlin
 * @Description:模板创建
 *               有该对象调用其他对象的构建
 * @Date 2019/6/14 19:14
 *****/
public class TemplateBuilder {

    //配置文件
    private static Properties props = new Properties();

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

    static {
        try {
            //加载配置文件
            InputStream is = TemplateBuilder.class.getClassLoader().getResourceAsStream("application.properties");

            //创建Properties对象
            props.load(is);

            //获取对应的配置信息
            PACKAGE_POJO = props.getProperty("pojoPackage");
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

            //加载数据库驱动
            Class.forName(props.getProperty("driver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 模板构建
     */
    public static void builder(){
        try {
            //获取数据库连接
            Connection conn = DriverManager.getConnection(props.getProperty("url"),props.getProperty("uname"),props.getProperty("pwd"));
            DatabaseMetaData metaData = conn.getMetaData();

            //获取数据库类型：MySQL
            String databaseType = metaData.getDatabaseProductName();

            //针对MySQL数据库进行相关生成操作
            if(databaseType.equals("MySQL")){
                //获取所有表结构
                ResultSet tableResultSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});

                //获取数据库名字
                String database = conn.getCatalog();

                //Swagger信息集合
                List<SwaggerModel> swaggerModels = new ArrayList<SwaggerModel>();       //Model
                List<SwaggerPath> swaggerPathList = new ArrayList<SwaggerPath>();    //Method

                //循环所有表信息
                while (tableResultSet.next()){
                    //获取表名
                    String tableName=tableResultSet.getString("TABLE_NAME");
                    //名字操作,去掉tab_,tb_，去掉_并转驼峰
                    String table = StringUtils.replace_(StringUtils.replaceTab(tableName));
                    //大写对象
                    String Table =StringUtils.firstUpper(table);

                    //需要生成的Pojo属性集合
                    List<ModelInfo> models = new ArrayList<ModelInfo>();
                    //所有需要导包的类型
                    Set<String> typeSet = new HashSet<String>();

                    //获取表所有的列
                    ResultSet cloumnsSet = metaData.getColumns(database, UNAME, tableName, null);
                    //获取主键
                    ResultSet keySet = metaData.getPrimaryKeys(database, UNAME, tableName);
                    String key ="",keyType="";
                    while (keySet.next()){
                        key=keySet.getString(4);
                    }

                    //构建SwaggerModel对象
                    SwaggerModel swaggerModel = new SwaggerModel();
                    swaggerModel.setName(Table);
                    swaggerModel.setType("object");
                    swaggerModel.setDescription(Table);
                    //属性集合
                    List<SwaggerModelProperties> swaggerModelProperties = new ArrayList<SwaggerModelProperties>();

                    while (cloumnsSet.next()){
                        //列的描述
                        String remarks = cloumnsSet.getString("REMARKS");
                        //获取列名
                       String columnName = cloumnsSet.getString("COLUMN_NAME");
                        //处理列名
                        String propertyName = StringUtils.replace_(columnName);
                       //获取类型，并转成JavaType
                       String javaType = JavaTypes.getType(cloumnsSet.getInt("DATA_TYPE"));
                       //创建该列的信息
                       models.add(new ModelInfo(javaType, JavaTypes.simpleName(javaType),propertyName,StringUtils.firstUpper(propertyName),remarks, key.equals(columnName),columnName,cloumnsSet.getString("IS_AUTOINCREMENT")));
                       //需要导包的类型
                        typeSet.add(javaType);
                        //主键类型
                        if(columnName.equals(key)){
                            keyType=JavaTypes.simpleName(javaType);
                        }

                        //SwaggerModelProperties创建
                        SwaggerModelProperties modelProperties = new SwaggerModelProperties();
                        modelProperties.setName(propertyName);
                        modelProperties.setType(JavaTypes.simpleNameLowerFirst(javaType));
                        if(modelProperties.getType().equals("integer")){
                            modelProperties.setFormat("int32");
                        }
                        modelProperties.setDescription(remarks);
                        swaggerModelProperties.add(modelProperties);
                    }
                    //属性集合
                    swaggerModel.setProperties(swaggerModelProperties);
                    swaggerModels.add(swaggerModel);

                    //创建该表的JavaBean
                    Map<String,Object> modelMap = new HashMap<String,Object>();
                    modelMap.put("table",table);
                    modelMap.put("Table",Table);
                    modelMap.put("swagger",SWAGGER);
                    modelMap.put("TableName",tableName);
                    modelMap.put("models",models);
                    modelMap.put("typeSet",typeSet);
                    //主键操作
                    modelMap.put("keySetMethod","set"+StringUtils.firstUpper(StringUtils.replace_(key)));
                    modelMap.put("keyType",keyType);
                    modelMap.put("serviceName",SERVICENAME);

                    //创建JavaBean
                    PojoBuilder.builder(modelMap);

                    //创建Controller
                    ControllerBuilder.builder(modelMap);

                    //创建Dao
                    DaoBuilder.builder(modelMap);

                    //创建Service接口
                    ServiceBuilder.builder(modelMap);

                    //创建ServiceImpl实现类
                    ServiceImplBuilder.builder(modelMap);

                    //创建Feign
                    FeignBuilder.builder(modelMap);

                    //添加swagger路径映射
                    String format="string";
                    if(keyType.equalsIgnoreCase("integer") || keyType.equalsIgnoreCase("long")){
                        format="int64";
                    }
                    swaggerPathList.addAll(swaggerMethodInit(Table,table,StringUtils.firstLower(keyType),format));
                }

                //构建Swagger文档数据-JSON数据
                Map<String,Object> swaggerModelMap = new HashMap<String,Object>();
                swaggerModelMap.put("swaggerModels",swaggerModels);
                swaggerModelMap.put("swaggerPathList",swaggerPathList);
                //生成Swagger文件
                SwaggerBuilder.builder(swaggerModelMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * 初始化方法信息
     * @param Table
     * @param type  主键ID类型
     * @param format
     * @return
     */
    public static List<SwaggerPath> swaggerMethodInit(String Table,String table,String type,String format){
        //集合存储
        List<SwaggerPath> swaggerPaths = new ArrayList<SwaggerPath>();

        //1.add  findAll  方法
        SwaggerPath addAndFindAllPath = new SwaggerPath(Table,table);
        addAndFindAllPath.setPath("/"+table);//设置请求路径
        //初始化方法
        List<SwaggerMethod> swaggerMethods = addAndFindAll(Table, table);
        addAndFindAllPath.setMethods(swaggerMethods);
        swaggerPaths.add(addAndFindAllPath);

        //2.delete    update   findById  方法
        SwaggerPath handlerByIdPath = new SwaggerPath(Table,table);
        handlerByIdPath.setPath("/"+table+"/{id}");//设置请求路径
        //初始化方法
        List<SwaggerMethod> handlerByIdMethods = handlerById(Table, table,type,format);
        handlerByIdPath.setMethods(handlerByIdMethods);
        swaggerPaths.add(handlerByIdPath);

        //3.搜索  方法
        SwaggerPath searchPath = new SwaggerPath(Table,table);
        searchPath.setPath("/"+table+"/search");//设置请求路径
        //初始化方法
        List<SwaggerMethod> searchMethods = searchMethod(Table, table);
        searchPath.setMethods(searchMethods);
        swaggerPaths.add(searchPath);

        //4.分页条件搜索  方法
        SwaggerPath pageSearchPath = new SwaggerPath(Table,table);
        pageSearchPath.setPath("/"+table+"/search/{page}/{size}");//设置请求路径
        //初始化方法
        List<SwaggerMethod> pageSearchMethods =searchPage(Table,table);
        pageSearchPath.setMethods(pageSearchMethods);
        swaggerPaths.add(pageSearchPath);
        return  swaggerPaths;
    }


    /***
     * 条件搜索
     * @param Table
     * @param table
     * @return
     */
    public static List<SwaggerMethod> searchMethod(String Table,String table) {
        //存储所有方法操作
        List<SwaggerMethod> swaggerMethods = new ArrayList<SwaggerMethod>();

        //1.不带分页条件搜索
        SwaggerMethod searchMethod = search(Table, "不带分页条件搜索","findListUsingPOST","post");
        //设置请求参数
        searchMethod.setSwaggerParameters(searchPageParameters(Table, 2, 1));
        //响应状态
        searchMethod.setResponses(responses(Table));
        //响应数据配置-200
        SwaggerResponse resp200Search = new SwaggerResponse();
        resp200Search.setCode(200);
        resp200Search.setDescription("不带分页搜索" + Table);
        resp200Search.setSchema("#/definitions/Result«List«" + Table + "»»");
        searchMethod.getResponses().add(resp200Search);
        swaggerMethods.add(searchMethod);

        return swaggerMethods;
    }

    /***
     * 分页和搜索
     * @param Table
     * @param table
     * @return
     */
    public static List<SwaggerMethod> searchPage(String Table,String table){
        //存储所有方法操作
        List<SwaggerMethod> swaggerMethods = new ArrayList<SwaggerMethod>();
        //1.分页列表
        SwaggerMethod pageMethod = search(Table,"分页列表查询","findPageUsingGET","get");
        //设置请求参数
        pageMethod.setSwaggerParameters(searchPageParameters(Table,1,2));
        //响应状态
        pageMethod.setResponses(responses(Table));
        //响应数据配置-200
        SwaggerResponse resp200Page = new SwaggerResponse();
        resp200Page.setCode(200);
        resp200Page.setDescription("不带分页搜索"+Table);
        resp200Page.setSchema("#/definitions/Result«List«"+Table+"»»");
        pageMethod.getResponses().add(resp200Page);
        swaggerMethods.add(pageMethod);

        //2.分页条件搜索
        SwaggerMethod pageSearchMethod = search(Table,"分页条件搜索","findPageUsingPOST","post");
        //设置请求参数
        pageSearchMethod.setSwaggerParameters(searchPageParameters(Table,1,1));
        //响应状态
        pageSearchMethod.setResponses(responses(Table));
        //响应数据配置-200
        SwaggerResponse resp200PageSearch = new SwaggerResponse();
        resp200PageSearch.setCode(200);
        resp200PageSearch.setDescription("分页条件搜索"+Table);
        resp200PageSearch.setSchema("#/definitions/Result«List«"+Table+"»»");
        pageSearchMethod.getResponses().add(resp200PageSearch);
        swaggerMethods.add(pageSearchMethod);
        return  swaggerMethods;
    }

    /***
     * 根据ID删除、修改、查询
     * @param Table
     * @param table
     * @return
     */
    public static List<SwaggerMethod> handlerById(String Table,String table,String type,String format){
        //存储所有方法操作
        List<SwaggerMethod> swaggerMethods = new ArrayList<SwaggerMethod>();

        //1.根据ID删除
        SwaggerMethod deleteMethod = delete(Table);
        //设置请求参数
        deleteMethod.setSwaggerParameters(byIdParameters(Table,type,format,"根据ID删除",1));
        //响应状态
        deleteMethod.setResponses(responses(Table));
        //响应数据配置-200
        SwaggerResponse resp200Delete = new SwaggerResponse();
        resp200Delete.setCode(200);
        resp200Delete.setDescription("根据ID删除"+Table);
        resp200Delete.setSchema("#/definitions/Result");
        deleteMethod.getResponses().add(resp200Delete);
        swaggerMethods.add(deleteMethod);

        //2.根据ID修改
        SwaggerMethod updateMethod = update(Table);
        //设置请求参数
        updateMethod.setSwaggerParameters(byIdParameters(Table,type,format,"根据ID修改",2));
        //响应状态
        updateMethod.setResponses(responses(Table));
        //响应数据配置-200
        SwaggerResponse resp200Update = new SwaggerResponse();
        resp200Update.setCode(200);
        resp200Update.setDescription("根据ID修改"+Table);
        resp200Update.setSchema("#/definitions/Result");
        updateMethod.getResponses().add(resp200Update);
        swaggerMethods.add(updateMethod);

        //3.根据ID查询
        SwaggerMethod findByIdMethod = findById(Table);
        //设置请求参数
        findByIdMethod.setSwaggerParameters(byIdParameters(Table,type,format,"根据ID修改",1));
        //响应状态
        findByIdMethod.setResponses(responses(Table));
        //响应数据配置-200
        SwaggerResponse resp200FindById = new SwaggerResponse();
        resp200FindById.setCode(200);
        resp200FindById.setDescription("根据ID修改"+Table);
        resp200FindById.setSchema("#/definitions/Result«"+Table+"»");
        findByIdMethod.getResponses().add(resp200FindById);
        swaggerMethods.add(findByIdMethod);
        return  swaggerMethods;
    }

    /***
     * 增加方法和查询所有
     * @param Table
     * @param table
     * @return
     */
    public static List<SwaggerMethod> addAndFindAll(String Table,String table){
        //存储所有方法操作
        List<SwaggerMethod> swaggerMethods = new ArrayList<SwaggerMethod>();

        //1.findAll
        SwaggerMethod findAllMethod =findAll(Table);
        //响应状态
        findAllMethod.setResponses(responses(Table));
        //响应数据配置-200
        SwaggerResponse resp200FindAll = new SwaggerResponse();
        resp200FindAll.setCode(200);
        resp200FindAll.setDescription("查询所有"+Table);
        resp200FindAll.setSchema("#/definitions/Result«List«"+Table+"»»");
        findAllMethod.getResponses().add(resp200FindAll);
        swaggerMethods.add(findAllMethod);

        //2.add
        SwaggerMethod addMethod =add(Table);
        //入参
        addMethod.setSwaggerParameters(addParameters(Table));
        //响应状态
        addMethod.setResponses(responses(Table));
        //响应数据配置-200
        SwaggerResponse resp200Add = new SwaggerResponse();
        resp200Add.setCode(200);
        resp200Add.setDescription("添加"+Table);
        resp200Add.setSchema("#/definitions/Result");
        addMethod.getResponses().add(resp200Add);
        swaggerMethods.add(addMethod);
        return swaggerMethods;
    }

    /***
     * 分页搜索
     * @param Table
     * @param isPage:是否分页     1:分页   2：不分
     * @param isSearch:是否条件搜索  1：搜索  2：不搜索
     * @return
     */
    public static List<SwaggerParameters> searchPageParameters(String Table,int isPage,int isSearch){
        //集合
        List<SwaggerParameters> swaggerParametersList = new ArrayList<SwaggerParameters>();
        if(isPage==1){
            //当前页
            SwaggerParameters pageParameters = new SwaggerParameters();
            pageParameters.setIn("path");
            pageParameters.setName("page");
            pageParameters.setDescription("当前页");
            pageParameters.setRequired(true);
            pageParameters.setType("integer");
            pageParameters.setFormat("int64");
            swaggerParametersList.add(pageParameters);
            //每页显示条数
            SwaggerParameters sizeParameters = new SwaggerParameters();
            sizeParameters.setIn("path");
            sizeParameters.setName("size");
            sizeParameters.setDescription("每页显示条数");
            sizeParameters.setRequired(true);
            sizeParameters.setType("integer");
            sizeParameters.setFormat("int64");
            swaggerParametersList.add(sizeParameters);
        }

        //是否需要JavaBean参数
        if(isSearch==1){
            SwaggerParameters swaggerParametersModel = new SwaggerParameters();
            swaggerParametersModel.setIn("body");
            swaggerParametersModel.setName(Table);
            swaggerParametersModel.setDescription("传入"+ Table+"的JSON对象");
            swaggerParametersModel.setRequired(false);
            swaggerParametersModel.setSchema("#/definitions/"+Table);
            swaggerParametersList.add(swaggerParametersModel);
        }
        return swaggerParametersList;
    }

    /***
     * 根据ID操作数据
     * @param Table
     * @param buildModel:是否创建JavaBean对象   1不创建，2创建
     * @return
     */
    public static List<SwaggerParameters> byIdParameters(String Table,String type,String format,String message,int buildModel){
        //集合
        List<SwaggerParameters> swaggerParametersList = new ArrayList<SwaggerParameters>();
        //入参
        SwaggerParameters swaggerParameters = new SwaggerParameters();
        swaggerParameters.setIn("path");
        swaggerParameters.setName("id");
        swaggerParameters.setDescription(message+Table+"方法详情");
        swaggerParameters.setRequired(true);
        swaggerParameters.setType(type);
        swaggerParameters.setFormat(format);
        swaggerParametersList.add(swaggerParameters);

        //是否需要JavaBean参数
        if(buildModel==2){
            SwaggerParameters swaggerParametersModel = new SwaggerParameters();
            swaggerParametersModel.setIn("body");
            swaggerParametersModel.setName(Table);
            swaggerParametersModel.setDescription("传入"+ Table+"的JSON对象");
            swaggerParametersModel.setRequired(false);
            swaggerParametersModel.setSchema("#/definitions/"+Table);
            swaggerParametersList.add(swaggerParametersModel);
        }
        return swaggerParametersList;
    }

    /***
     * POST添加数据入参
     * @param Table
     * @return
     */
    public static List<SwaggerParameters> addParameters(String Table){
        List<SwaggerParameters> swaggerParametersList = new ArrayList<SwaggerParameters>();
        //入参
        SwaggerParameters swaggerParameters = new SwaggerParameters();
        swaggerParameters.setIn("body");
        swaggerParameters.setName("body");
        swaggerParameters.setDescription("添加"+Table+"方法详情");
        swaggerParameters.setRequired(true);
        swaggerParameters.setSchema("#/definitions/"+Table);
        swaggerParametersList.add(swaggerParameters);
        return swaggerParametersList;
    }


    /***
     * 条件搜索
     * @param Table
     * @return
     */
    public static SwaggerMethod search(String Table,String message,String opId,String method){
        SwaggerMethod searchMethod = new SwaggerMethod();
        //searchMethod.setMethod("post");
        searchMethod.setMethod(method);
        searchMethod.setTags(Table+"Controller");
        searchMethod.setSummary(message+Table);
        searchMethod.setDescription(message+Table+"方法详情");
        //searchMethod.setOperationId("findListUsingPOST");
        searchMethod.setOperationId(opId);
        searchMethod.setConsumes("application/json");
        searchMethod.setProduces("application/json");
        return searchMethod;
    }



    /***
     * 根据ID查询方法
     * @param Table
     * @return
     */
    public static SwaggerMethod findById(String Table){
        SwaggerMethod deleteMethod = new SwaggerMethod();
        deleteMethod.setMethod("get");
        deleteMethod.setTags(Table+"Controller");
        deleteMethod.setSummary("根据ID查询"+Table);
        deleteMethod.setDescription("根据ID查询"+Table+"方法详情");
        deleteMethod.setOperationId("findByIdUsingGET");
        deleteMethod.setConsumes("application/json");
        deleteMethod.setProduces("application/json");
        return deleteMethod;
    }

    /***
     * 根据ID修改方法
     * @param Table
     * @return
     */
    public static SwaggerMethod update(String Table){
        SwaggerMethod deleteMethod = new SwaggerMethod();
        deleteMethod.setMethod("put");
        deleteMethod.setTags(Table+"Controller");
        deleteMethod.setSummary("根据ID修改"+Table);
        deleteMethod.setDescription("根据ID修改"+Table+"方法详情");
        deleteMethod.setOperationId("updateUsingPUT");
        deleteMethod.setConsumes("application/json");
        deleteMethod.setProduces("application/json");
        return deleteMethod;
    }

    /***
     * 根据ID删除方法
     * @param Table
     * @return
     */
    public static SwaggerMethod delete(String Table){
        SwaggerMethod deleteMethod = new SwaggerMethod();
        deleteMethod.setMethod("delete");
        deleteMethod.setTags(Table+"Controller");
        deleteMethod.setSummary("根据ID删除"+Table);
        deleteMethod.setDescription("根据ID删除"+Table+"方法详情");
        deleteMethod.setOperationId("deleteUsingDELETE");
        deleteMethod.setConsumes("application/json");
        deleteMethod.setProduces("application/json");
        return deleteMethod;
    }

    /***
     * 添加数据
     */
    public static SwaggerMethod add(String Table){
        SwaggerMethod addMethod = new SwaggerMethod();
        addMethod.setMethod("post");
        addMethod.setTags(Table+"Controller");
        addMethod.setSummary("添加"+Table);
        addMethod.setDescription("添加"+Table+"方法详情");
        addMethod.setOperationId("addUsingPOST");
        addMethod.setConsumes("application/json");
        addMethod.setProduces("application/json");
        return addMethod;
    }


    /***
     * 查询所有
     * @param Table
     * @return
     */
    public static SwaggerMethod findAll(String Table){
        SwaggerMethod findAllMethod = new SwaggerMethod();
        findAllMethod.setMethod("get");
        findAllMethod.setTags(Table+"Controller");
        findAllMethod.setSummary("查询所有"+Table);
        findAllMethod.setDescription("查询所有"+Table+"方法详情");
        findAllMethod.setOperationId("findAllUsingGET");
        findAllMethod.setConsumes("application/json");
        findAllMethod.setProduces("application/json");
        return findAllMethod;
    }


    /***
     * 响应状态
     * @param Table
     * @return
     */
    public static List<SwaggerResponse> responses(String Table){
        List<SwaggerResponse> responses = new ArrayList<SwaggerResponse>();
        //响应数据配置-400
        SwaggerResponse resp400 = new SwaggerResponse();
        resp400.setCode(400);
        resp400.setDescription("Invalid status value(无效的状态值)");
        //响应数据配置-403
        SwaggerResponse resp403 = new SwaggerResponse();
        resp403.setCode(403);
        resp403.setDescription("403 Forbidden(请求被拒绝)");
        //响应数据配置-404
        SwaggerResponse resp404 = new SwaggerResponse();
        resp404.setCode(404);
        resp404.setDescription("not found(没有找到相关资源)");
        //响应数据配置-405
        SwaggerResponse resp405 = new SwaggerResponse();
        resp405.setCode(405);
        resp405.setDescription("Invalid input(无效的输入)");
        //响应数据配置-500
        SwaggerResponse resp500 = new SwaggerResponse();
        resp500.setCode(500);
        resp500.setDescription("服务器内部错误");
        responses.add(resp400);
        responses.add(resp403);
        responses.add(resp404);
        responses.add(resp405);
        responses.add(resp500);
        return responses;
    }

}
