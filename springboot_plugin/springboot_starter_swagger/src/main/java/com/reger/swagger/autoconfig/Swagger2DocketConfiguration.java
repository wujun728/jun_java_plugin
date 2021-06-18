package com.reger.swagger.autoconfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Predicate;
import com.reger.swagger.properties.Swagger2GroupProperties;
import com.reger.swagger.properties.Swagger2Properties;
import com.reger.swagger.properties.SwaggerProperties;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SuppressWarnings("deprecation")
public class Swagger2DocketConfiguration implements BeanFactoryPostProcessor, EnvironmentAware {

	private static final Logger log = LoggerFactory.getLogger(Swagger2DocketConfiguration.class);

	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment =  environment;
	}

	private SwaggerProperties getSwaggerProperties() {
		try {
			SwaggerProperties existingValue = Binder.get(environment).bind(SwaggerProperties.prefix, SwaggerProperties.class).get();
			return existingValue;
		} catch (Exception e) {
			return new SwaggerProperties();
		}
	}

	private Swagger2Properties getSwagger2Properties() {
		Swagger2Properties existingValue = Binder.get(environment).bind(Swagger2Properties.prefix, Swagger2Properties.class).get();
		return existingValue;
	}

	private String[] splitBasePackages(String basePackage) {
		if (StringUtils.isEmpty(basePackage) || (basePackage = basePackage.trim()).isEmpty()) {
			return null;
		} else {
			return basePackage.split(",");
		}
	}

	private Docket getSwagger2Docket(final Swagger2GroupProperties groupProperties, final List<String> pathUrls) {
		final String[] basePackages = this.splitBasePackages(groupProperties.getBasePackage());
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title(groupProperties.getTitle())
						.description(groupProperties.getDescription()).version(groupProperties.getVersion())
						.license(groupProperties.getLicense()).licenseUrl(groupProperties.getLicenseUrl())
						.termsOfServiceUrl(groupProperties.getTermsOfServiceUrl())
						.contact(groupProperties.getContact().toContact()).build())
				.groupName(groupProperties.getGroupName()).pathMapping(groupProperties.getPathMapping())// 最终调用接口后会和paths拼接在一起
				.select()
				.apis(new Predicate<RequestHandler>() {
					@Override
					public boolean apply(RequestHandler input) {
						if (basePackages == null)
							return true;
						String packageName = input.declaringClass().getName();
						for (String basePackage : basePackages) {
							if (packageName.startsWith(basePackage) || packageName.matches(basePackage+".*")) {
								return true;
							}
						}
						return false;
					}
				})
				.paths(new Predicate<String>() {
					@Override
					public boolean apply(String input) {
						String pathRegex = groupProperties.getPathRegex();
						if (StringUtils.isEmpty(pathRegex) || input.matches(pathRegex)) {
							pathUrls.add(input);
							return true;
						} else {
							return false;
						}
					}
				})
				.build();
	}

	private Docket getOtherSwagger2Docket(final List<String> pathUrls) {
		Swagger2GroupProperties otherSwagger = otherSwagger();
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title(otherSwagger.getTitle()).description(otherSwagger.getDescription())
						.termsOfServiceUrl(otherSwagger.getTermsOfServiceUrl()).version(otherSwagger.getVersion())
						.contact(otherSwagger.getContact().toContact()).license(otherSwagger.getLicense())
						.licenseUrl(otherSwagger.getLicense()).build())
				.groupName(otherSwagger.getGroupName()).pathMapping(otherSwagger.getPathMapping())// 最终调用接口后会和paths拼接在一起
				.select().apis(new Predicate<RequestHandler>() {
					@Override
					public boolean apply(RequestHandler input) {
						return input.isAnnotatedWith(GetMapping.class) || input.isAnnotatedWith(PostMapping.class)
								|| input.isAnnotatedWith(DeleteMapping.class) || input.isAnnotatedWith(PutMapping.class)
								|| input.isAnnotatedWith(RequestMapping.class);
					}
				}).paths(new Predicate<String>() {
					@Override
					public boolean apply(String input) {
						return !pathUrls.contains(input);
					}
				}).build();
	}

	private Swagger2GroupProperties otherSwagger() {
		Swagger2GroupProperties otherSwagger = new Swagger2GroupProperties();
		otherSwagger.setGroupName("other-api");
		otherSwagger.setDescription("以上api中未被包含进来得接口");
		otherSwagger.setPathMapping("/");
		otherSwagger.setVersion("");
		otherSwagger.setPathRegex(null);
		otherSwagger.setTitle("其它api");
		return otherSwagger;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Swagger2Properties swagger2Properties = getSwagger2Properties();
		SwaggerProperties swaggerProperties = getSwaggerProperties();
		List<String> pathUrls = new ArrayList<String>();
		if (swagger2Properties.getGroup() != null && !swagger2Properties.getGroup().isEmpty()) {
			Iterator<Entry<String, Swagger2GroupProperties>> it = swagger2Properties.getGroup().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Swagger2GroupProperties> entry = (Map.Entry<String, Swagger2GroupProperties>) it
						.next();
				Docket swagger2Docket = this.getSwagger2Docket(entry.getValue(), pathUrls);
				beanFactory.registerSingleton(entry.getKey(), swagger2Docket);
			}
		}
		if (swaggerProperties.getSwaggerGroup() != null && !swaggerProperties.getSwaggerGroup().isEmpty()) {
			Iterator<Entry<String, Swagger2GroupProperties>> it = swaggerProperties.getSwaggerGroup().entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Swagger2GroupProperties> entry = (Map.Entry<String, Swagger2GroupProperties>) it
						.next();
				Docket swagger2Docket = this.getSwagger2Docket(entry.getValue(), pathUrls);
				beanFactory.registerSingleton(entry.getKey(), swagger2Docket);
			}
		}
		beanFactory.registerSingleton("other-api", this.getOtherSwagger2Docket(pathUrls));
	}
}
