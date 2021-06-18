package org.typroject.tyboot.core.restful.config;

import io.swagger.annotations.Api;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.utils.ResponseModel;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 自动加载系统所有的API信息并保存到缓存中
 */
public class LoadApiInfo implements CommandLineRunner {


    /**
     * 接口所在包
     */
    private String packageName;

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 接口地址前缀
     */
    private String contextPath;

    /**
     * redis
     */
    private RedisTemplate redisTemplate;





    public LoadApiInfo(String packageName,String serverName, String contextPath, RedisTemplate redisTemplate)
    {
        this.packageName    = packageName;
        this.contextPath    = contextPath;
        this.serverName     = serverName;
        this.redisTemplate  = redisTemplate;
    }





    public void run(String... var1) throws Exception
    {
       List<TyApiInfo> apiList = readApis();

        redisTemplate.delete(TyApiInfo.getCacheKeyForTyApiInfoOfHash());
       for(TyApiInfo tyApiInfo : apiList)
       {
           boolean result = redisTemplate.opsForHash().putIfAbsent(TyApiInfo.getCacheKeyForTyApiInfoOfHash(),tyApiInfo.getApiCode(),tyApiInfo);
           if(!result)
           {
               throw new Exception("重复的接口定义:"+tyApiInfo.getApiCode());
           }
       }
        //将接口信息保存到数据
        //将接口信息加载到缓存

    }


    /**
     * 从指定的包开始读取所有的API定义
     * @return
     * @throws Exception
     */
    private  List<TyApiInfo> readApis() throws Exception
    {

        List<TyApiInfo> returnList = new ArrayList<>();

        List<Class<?>> apiClazz =  getRequestMappingValue(packageName);

        if(!ValidationUtil.isEmpty(apiClazz))
        {
            for(Class clzz : apiClazz)
            {
                TycloudResource  tycloudResource    = (TycloudResource)clzz.getAnnotation(TycloudResource.class);
                if(!ValidationUtil.isEmpty(tycloudResource))
                {
                    RequestMapping requestMapping   = (RequestMapping)clzz.getAnnotation(RequestMapping.class);
                    Api             api             = (Api)clzz.getAnnotation(Api.class);

                    String apiCode                  = serverName+"."+tycloudResource.module()+"."+tycloudResource.value();
                    String resourceCode             = tycloudResource.value();
                    String resourceName             = api.value();
                    String resourceUrl                   = contextPath+requestMapping.value()[0];
                    returnList.addAll(readByMethod(clzz,apiCode,resourceUrl));
                }

            }

        }
        return returnList;
    }

    /**
     * 读取指定Controller中的所有接口信息
     * @param clzz
     * @param apiCode
     * @param resourceUrl
     * @return
     * @throws Exception
     */
    private  List<TyApiInfo> readByMethod(Class clzz,String apiCode,String resourceUrl) throws Exception
    {
        List<TyApiInfo> returnList = new ArrayList<>();
        for(Method method:clzz.getDeclaredMethods())
        {
            if(method.getReturnType().newInstance() instanceof ResponseModel)
            {
                SortedSet<String> requestParams       = new TreeSet<>();
                SortedSet <String> uriParams          = new TreeSet<>();
                RequestMapping requestMapping   = method.getAnnotation(RequestMapping.class);
                String newapiCode               = apiCode + "." + method.getName() ;
                String apiUrl                   = resourceUrl  + requestMapping.value()[0];
                RequestMethod apiMethod         = requestMapping.method()[0];


                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for(int i = 0;i<parameterAnnotations.length ;i++)
                {
                    if(parameterAnnotations[i].length>0)
                    {
                        Annotation parameterAnot    = parameterAnnotations[i][0];
                        if(parameterAnot instanceof RequestParam)
                        {
                            String paramName        = ((RequestParam) parameterAnot).value();
                            if(ValidationUtil.isEmpty(paramName))throw new Exception("RequestParam.value can not be null.method:"+method.toString());
                            requestParams.add(paramName);
                        }
                        if(parameterAnot instanceof PathVariable)
                        {
                            String paramName        = ((PathVariable) parameterAnot).value();
                            if(ValidationUtil.isEmpty(paramName))throw new Exception("PathVariable.value can not be null.method:"+method.toString());
                            uriParams.add(((PathVariable) parameterAnot).value());
                        }
                    }
                }


                TyApiInfo api = new TyApiInfo();
                api.setApiCode(newapiCode.toLowerCase());
                api.setMethod(HttpMethod.resolve(apiMethod.name()));
                api.setUrl(apiUrl);
                api.setRequestParamsName(requestParams);
                api.setUriParamsName(uriParams);
                returnList.add(api);
            }
        }
        return returnList;
    }


    /**
     * 根据报名获取其中所有的类
     * @param packageName
     * @return
     * @throws Exception
     */
    public static  List<Class<?>> getRequestMappingValue(String packageName) throws Exception{


        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<>();

        //是否循环迭代
        boolean recursive = true;

        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');

        //定义一个枚举的集合 并进行循环来处理这个目录下的文件
        Enumeration<URL> dirs;
        try {
            //读取指定package下的所有class
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();

                //得到协议的名称
                String protocol = url.getProtocol();

                //判断是否以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");

                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }


    /**
     * findAndAddClassesInPackageByFile方法描述:
     * 作者:thh
     * 日期:2016年7月18日 下午5:41:12
     * 异常对象:@param packageName
     * 异常对象:@param packagePath
     * 异常对象:@param recursive
     * 异常对象:@param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){

        //获取此包的目录 建立一个File
        File dir = new File(packagePath);

        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {

                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classes);
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }










}
