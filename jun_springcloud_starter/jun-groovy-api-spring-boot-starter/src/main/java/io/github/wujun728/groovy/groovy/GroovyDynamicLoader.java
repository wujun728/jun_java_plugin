package io.github.wujun728.groovy.groovy;

import cn.hutool.core.lang.Console;
//import com.jfinal.plugin.activerecord.ActiveRecordException;
//import io.github.wujun728.db.record.Db;
//import io.github.wujun728.db.record.kit.DbKit;
import cn.hutool.log.StaticLog;
import io.github.wujun728.groovy.cache.IApiConfigCache;
import io.github.wujun728.sql.entity.ApiConfig;
import io.github.wujun728.groovy.service.ApiService;
import io.github.wujun728.groovy.cache.ApiConfigCache;
import io.github.wujun728.groovy.mapping.http.RequestMappingService;
//import com.jfinal.plugin.activerecord.ActiveRecordException;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.CompilationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static io.github.wujun728.db.DataSourcePool.main;


@Slf4j
@Configuration
@Service
public class GroovyDynamicLoader implements ApplicationContextAware, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(GroovyDynamicLoader.class);

	private Map<String,Class> classMap = new HashMap<>();

	private ConfigurableApplicationContext applicationContext;

	private BeanDefinitionRegistry registry;

	private static final GroovyClassLoader groovyClassLoader = new GroovyClassLoader(
			GroovyDynamicLoader.class.getClassLoader());

	@Resource
	private ApiService apiService;

	@Resource
	private IApiConfigCache apiInfoCache;

	@Resource
	private RequestMappingService requestMappingService;

	public static final String main = "main";

	@Override
	public void afterPropertiesSet() throws Exception {

		long start = System.currentTimeMillis();
		System.out.println("开始解析groovy脚本...");
		initNew();
		long cost = System.currentTimeMillis() - start;
		System.out.println("结束解析groovy脚本...，耗时：" + cost);
	}

	private void initNew() {
		try {
			apiService.init();

			List<ApiConfig> groovyScripts = apiService.queryApiConfigList();

			apiInfoCache.putAll(groovyScripts);


			initNew(groovyScripts);

			refreshMapping(groovyScripts);
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
			Console.log("数据源配置有误："+e.getMessage());
			if(e.getMessage().contains("Config not found by configName")) {
				Console.error("数据源配置有误："+e.getMessage());
			}
//		} catch (ActiveRecordException e) {
//			//e.printStackTrace();
//			if(e.getMessage().contains("doesn't exist")) {
//				Console.error("api_config表或关联表结构在数据库不存在，配置有误："+e.getMessage());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initNew(List<ApiConfig> groovyInfos) {
		if (CollectionUtils.isEmpty(groovyInfos)) {
			return;
		}
		for (ApiConfig groovyInfo : groovyInfos) {
			Boolean isSucess = true;
			try {
				if(groovyInfo.getScriptType().equalsIgnoreCase("Class")){
					Class clazz = groovyClassLoader.parseClass(groovyInfo.getScriptContent());
					String clazzname = clazz.getName();
					StaticLog.info("clazzName = "+ clazzname);
					registerBean(clazzname+groovyInfo.getId(), clazz);
//					registerBean(groovyInfo.getPath()+groovyInfo.getBeanName(), clazz);
				}else if(groovyInfo.getScriptType().equalsIgnoreCase("SQL")){
					log.info("当前Groovy脚本类型SQL类型脚本1：className-{},path-{},beanName-{},BeanType-{}：",groovyInfo.getBeanName(),groovyInfo.getPath(),groovyInfo.getBeanName(),groovyInfo.getScriptType());
				}else {
					log.error("当前Groovy脚本类型不支持1：className-{},path-{},beanName-{},BeanType-{}：",groovyInfo.getBeanName(),groovyInfo.getPath(),groovyInfo.getBeanName(),groovyInfo.getScriptType());
				}
			} catch (BeanDefinitionStoreException e) {
				isSucess = false;
				StaticLog.error(e.getMessage());//e.printStackTrace();
			} catch (CompilationFailedException e) {
				isSucess = false;
				StaticLog.error(e.getMessage());//e.printStackTrace();
			}catch (Exception e) {
				isSucess = false;
				StaticLog.error(e.getMessage());//e.printStackTrace();
			}
			if(!isSucess){
				log.info("当前groovyInfo加载成功1,className-{},path-{},beanName-{},BeanType-{}：",groovyInfo.getBeanName(),groovyInfo.getPath(),groovyInfo.getBeanName(),groovyInfo.getScriptType());
			}
		}
		ApiConfigCache.put2map(groovyInfos);
	}

	public void registerBean(String beanName, Class clazz) {
		this.registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		BeanDefinition beanDefinition = builder.getBeanDefinition();
		registry.registerBeanDefinition(beanName, beanDefinition);
	}


	public void refreshNew() {

		List<ApiConfig> groovyInfos = apiService.queryApiConfigList();

		apiInfoCache.putAll(groovyInfos);

		if (CollectionUtils.isEmpty(groovyInfos)) {
			return;
		}

		destroyBeanDefinition(groovyInfos);

		//destroyScriptBeanFactory();
		initNew(groovyInfos);

		ApiConfigCache.put2map(groovyInfos);

		refreshMapping(groovyInfos);
	}

	private void destroyBeanDefinition(List<ApiConfig> groovyInfos) {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		for (ApiConfig groovyInfo : groovyInfos) {
			try {
				beanFactory.removeBeanDefinition(groovyInfo.getBeanName());
			} catch (Exception e) {
				System.out
						.println("【Groovy】 delete groovy bean definition exception. skip:" + groovyInfo.getBeanName());
			}
		}
	}

//	private void destroyScriptBeanFactory() {
//		String[] postProcessorNames = applicationContext.getBeanFactory()
//				.getBeanNamesForType(CustomScriptFactoryPostProcessor.class, true, false);
//		for (String postProcessorName : postProcessorNames) {
//			CustomScriptFactoryPostProcessor processor = (CustomScriptFactoryPostProcessor) applicationContext
//					.getBean(postProcessorName);
//			processor.destroy();
//		}
//	}

	/**
	 * 重建单一请求的注册与缓存
	 */
	public void refreshMapping(List<ApiConfig> groovyScripts) {
		try {
			for (ApiConfig apiInfo : groovyScripts) {
				// 取消历史注册
				if (apiInfo != null) {
					requestMappingService.unregisterMappingForApiConfig(apiInfo);
					apiInfoCache.remove(apiInfo);
				}

				// 重新注册mapping
				if (apiInfo != null) {
					requestMappingService.registerMappingForApiConfig(apiInfo);
					apiInfoCache.put(apiInfo);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}
}
