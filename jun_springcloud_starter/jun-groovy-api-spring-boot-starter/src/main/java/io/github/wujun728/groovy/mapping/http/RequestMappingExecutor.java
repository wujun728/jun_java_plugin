package io.github.wujun728.groovy.mapping.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson2.JSON;
import io.github.wujun728.common.utils.HttpRequestUtil;
import io.github.wujun728.common.utils.RequestWrapper;
import io.github.wujun728.groovy.cache.ApiConfigCache;
import io.github.wujun728.groovy.cache.IApiConfigCache;
import io.github.wujun728.groovy.common.model.ApiConfig;
import io.github.wujun728.rest.entity.ApiSql;
import io.github.wujun728.common.base.interfaces.AbstractExecutor;
import io.github.wujun728.common.base.interfaces.IExecutor;
//import io.github.wujun728.groovy.cache.ApiConfigCache;
//import io.github.wujun728.groovy.cache.IApiConfigCache;
import io.github.wujun728.groovy.service.ApiService;
import io.github.wujun728.common.Result;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.rest.service.IRestApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;

import cn.hutool.extra.spring.SpringUtil;
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.lang.Console;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RequestMappingExecutor implements IMappingExecutor,ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ApiService apiService;

	@Autowired
	private IApiConfigCache apiInfoCache;

	@Autowired
	private ServerProperties serverProperties;

	public void init(Boolean isStart) throws Exception {

	}

	private String getIpAndAdress() {
		String content = serverProperties.getServlet().getContextPath() == null ? ""
				: serverProperties.getServlet().getContextPath();
		Integer port = serverProperties.getPort() == null ? 8080 : serverProperties.getPort();
		String context = SpringUtil.getProperty("project.groovy-api.context");
		return "http://localhost:" + port + ("/" + content + context).replace("//", "/");
	}

	/**
	 * 执行脚本逻辑
	 */
	@RequestMapping
	@ResponseBody
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		Class<? extends RequestMappingExecutor> cls = this.getClass();
		// 使用方法
		try {
			// 获取方法 Method 对象
			Method method = cls.getDeclaredMethod("process", HttpServletRequest.class, HttpServletResponse.class);
			// 执行方法
			method.invoke(this, request, response);
		} catch (NoSuchMethodException e) {
			log.warn("当前子类未实现自定义方法[process]，走默认Bean定义的逻辑[无自定义执行逻辑]");
			IMappingExecutor executor = null;
			try {
				executor = SpringUtil.getBean("HttpMappingExecutor");
				executor.execute(request, response);
			} catch (NoSuchBeanDefinitionException ex ) {
				//log.warn("找不到默认执行Bean[HttpMappingExecutor],No bean named 'HttpMappingExecutor' available，走系统默认执行逻辑[无自定义执行逻辑]");
				//throw new RuntimeException(ex);
			} catch (RuntimeException  ex ) {
				log.warn(ex.getMessage());
				//throw new RuntimeException(ex);
			} catch (Exception  ex ) {
				log.warn(ex.getMessage());
				//throw new RuntimeException(ex);
			}
//			Set<Class<?>> clazzs = ClassUtil.scanPackageBySuper("io.github.wujun728", RequestMappingExecutor.class);
//			for(Class clazz: clazzs) {
//				ReflectUtil.invoke(clazzs, null, null)
//			}
			log.info("执行默认方法的逻辑[无自定义执行逻辑]");
			defaultMetod(request, response);
			// 找不到当前子类实现的方法[process]，走默认方法的逻辑
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}


	public static Map<String, Object> getParameters(HttpServletRequest request1, ApiConfig apiConfig) {
		HttpServletRequest request = null;
		try {
			request = new RequestWrapper((HttpServletRequest) request1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String unParseContentType = request.getContentType();

		// 如果是浏览器get请求过来，取出来的contentType是null
		if (unParseContentType == null) {
			unParseContentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE;
		}
		// issues/I57ZG2
		// 解析contentType 格式: appliation/json;charset=utf-8
		String[] contentTypeArr = unParseContentType.split(";");
		String contentType = contentTypeArr[0];
		Map<String, Object> params = null;
		// 如果是application/json请求，不管接口规定的content-type是什么，接口都可以访问，且请求参数都以json body 为准
		if (contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
			cn.hutool.json.JSONObject jo = HttpRequestUtil.getHttpJsonBody(request);
			if (!ObjectUtils.isEmpty(jo)) {
				params = JSONObject.parseObject(jo.toStringPretty(), new TypeReference<Map<String, Object>>() {
				});
			}
		}
		// 如果是application/x-www-form-urlencoded请求，先判断接口规定的content-type是不是确实是application/x-www-form-urlencoded
		else if (contentType.equalsIgnoreCase(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
			if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType)) {
				params = getSqlParam(request, apiConfig);
			} else if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(apiConfig.getContentType())) {
				params = getSqlParam(request, apiConfig);
			} else {
				params = getSqlParam(request, apiConfig);
				System.err.println("this API only support content-type: " + apiConfig.getContentType()
						+ ", but you use: " + contentType);
			}
		} 
		// 如果multipart/form-data请求，先判断接口规定的content-type是不是确实是multipart/form-data
		else if (contentType.equalsIgnoreCase(MediaType.MULTIPART_FORM_DATA_VALUE)) {
			params = getSqlParam(request, apiConfig);
		} 
		else {
			params = getSqlParam(request, apiConfig);
			throw new RuntimeException("content-type not supported: " + contentType);
		}
		String uri = request.getRequestURI();
		Map<String, String> header = HttpRequestUtil.buildHeaderParams(request);
		Map<String, Object> session = HttpRequestUtil.buildSessionParams(request);
		Map<String, Object> urivar = HttpRequestUtil.getParam(request);
		String pattern = HttpRequestUtil.buildPattern(request);
		Map<String, String> pathvar = HttpRequestUtil.getPathVar(pattern, uri);
		Map<String, Object> params1 = HttpRequestUtil.getFromParams(request);
		if (!CollectionUtils.isEmpty(session)) {
			params.putAll(session);
		}
		if (!CollectionUtils.isEmpty(header)) {
			params.putAll(header);
		}
		if (!CollectionUtils.isEmpty(pathvar)) {
			params.putAll(pathvar);
		}
		if (!CollectionUtils.isEmpty(urivar)) {
			params.putAll(urivar);
		}
		if (!CollectionUtils.isEmpty(params1)) {
			params.putAll(params1);
		}
		params.put("path",apiConfig.getPath());
		return params;
	}

	@SneakyThrows
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		ApplicationContext parent = contextRefreshedEvent.getApplicationContext().getParent();
		if (parent == null) {
			init(true);
		}
	}

    public static Map<String, Object> getSqlParam(HttpServletRequest request, ApiConfig config) {
        Map<String, Object> map = new HashMap<>();

        JSONArray requestParams = JSON.parseArray(config.getParams());
        for (int i = 0; i < requestParams.size(); i++) {
            JSONObject jo = requestParams.getJSONObject(i);
            String name = jo.getString("name");
            String type = jo.getString("type");

            //数组类型参数
            if (type.startsWith("Array")) {
                String[] values = request.getParameterValues(name);
                if (values != null) {
                    List<String> list = Arrays.asList(values);
                    if (values.length > 0) {
                        switch (type) {
                            case "Array<double>":
                                List<Double> collect = list.stream().map(value -> Double.valueOf(value)).collect(Collectors.toList());
                                map.put(name, collect);
                                break;
                            case "Array<bigint>":
                                List<Long> longs = list.stream().map(value -> Long.valueOf(value)).collect(Collectors.toList());
                                map.put(name, longs);
                                break;
                            case "Array<string>":
                            case "Array<date>":
                                map.put(name, list);
                                break;
                        }
                    } else {
                        map.put(name, list);
                    }
                } else {
                    map.put(name, null);
                }
            } else {

                String value = request.getParameter(name);
                if (StringUtils.isNotBlank(value)) {

                    switch (type) {
                        case "double":
                            Double v = Double.valueOf(value);
                            map.put(name, v);
                            break;
                        case "bigint":
                            Long longV = Long.valueOf(value);
                            map.put(name, longV);
                            break;
                        case "string":
                        case "date":
                            map.put(name, value);
                            break;
                    }
                } else {
                    map.put(name, value);
                }
            }
        }
        return map;
    }


	//*****************************************************************************************************************
	//*****************************************************************************************************************
	//*****************************************************************************************************************


	@Resource
	IRestApiService restApiService;
	/**
	 * 执行脚本逻辑
	 */
	public void defaultMetod(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		log.info("servlet execute");

//		this.request = request;
//		this.response = response;
		log.info("HttpMappingExecutor execute  begin ");
//		R r = null ;
		Object data = null;
		String servletPath = request.getRequestURI();
		PrintWriter out = null;
		try {
			//  执行SQL逻辑  *****************************************************************************************************
			// 校验接口是否存在
			ApiConfig config = apiInfoCache.get(servletPath);
			if (config == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.setContentType(request.getContentType());
				response.setCharacterEncoding(CharsetUtil.UTF_8);
				out = response.getWriter();
				out.append(JSON.toJSONString(Result.fail("Api not exists")));
			}
			switch (config.getScriptType()) {
				case "SQL":
					ApiSql apiSql = new ApiSql();
					apiSql.setSqlId(config.getPath());
					apiSql.setSqlText(config.getScriptContent());
					Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
					data = restApiService.doSQLProcess(apiSql, parameters);
					break;
				case "Class":
					data = doGroovyProcess(config, request, response);
					break;
				case "Groovy":
					data = doGroovyProcess(config, request, response);
					break;
				case "Jython": // TODO
					data = doGroovyProcess(config, request, response);
					break; // TODO
				case "JavaScript": // TODO
					data = doGroovyProcess(config, request, response);
				case "Jruby":// TODO
					data = doGroovyProcess(config, request, response);
					break;
				default:
					break;
			}
			response.setContentType(request.getContentType());
			response.setCharacterEncoding(CharsetUtil.UTF_8);
			out = response.getWriter();
			out.append(JSON.toJSONString(data));
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType(request.getContentType());
			response.setCharacterEncoding(CharsetUtil.UTF_8);
			out = response.getWriter();
			out.append(JSON.toJSONString(Result.fail(e.toString())));
			log.error(e.toString(), e);
		} finally {
			if (out != null)
				out.close();
		}
		log.info("HttpMappingExecutor execute  end ");
	}


	public static void timeOut(String[] args) {
		// 定义超时时间为3秒
		long timeout = 3000;

		// 创建一个新的CompletableFuture
		CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
			// 这里是要执行的方法
			//return longRunningMethod();
			return new Object();
		});
		// 获取执行结果
		try {
			Object result = future.get(timeout, TimeUnit.MILLISECONDS);
			System.out.println("方法执行完毕，结果：" + result.toString());
		} catch (InterruptedException e) {
			System.out.println("出现异常，结束该方法的执行");
			future.cancel(true);
		} catch (ExecutionException e) {
			System.out.println("出现异常，结束该方法的执行");
			future.cancel(true);
		} catch (TimeoutException e) {
			// 超时了，结束该方法的执行
			System.out.println("超时了，结束该方法的执行");
			future.cancel(true);
		}
	}


	public Object doGroovyProcess(ApiConfig config, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String beanName = ApiConfigCache.getByPath(config.getPath());
		//GroovyInfo groovyInfo = GroovyInnerCache.getGroovyInfoByPath(config.getPath());
		Map<String, Object> params = getParameters(request, config);
		Object beanObj = SpringUtil.getBean(beanName);
		try {
			if(beanObj instanceof IExecutor){
				IExecutor bean = (IExecutor) beanObj;
				return bean.execute(params);
			}else if(beanObj instanceof AbstractExecutor){
				AbstractExecutor bean = (AbstractExecutor) beanObj;
				bean.init(request,response);
				return bean.execute(params);
			}
		} catch (BusinessException e) {
			return Result.fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
//			if(beanObj instanceof  IExecutor){
//				IExecutor bean = (IExecutor) beanObj;
//				bean.rollback(params);
//			}else if(beanObj instanceof  AbstractExecutor){
//				AbstractExecutor bean = (AbstractExecutor) beanObj;
//				bean.init(request,response);
//				bean.rollback(params);
//			}
			throw e;
		}
		return "ERROR：执行错误，请检查执行日志并捕获并处理异常！";
	}



}
