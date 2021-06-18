package io.springside.springtime.swagger;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;

@Component
@Primary
public class SpringTimeSwaggerProvider extends InMemorySwaggerResourcesProvider {

	public SpringTimeSwaggerProvider(DocumentationCache documentationCache) {
		super(documentationCache);
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = super.get();
		SwaggerResource springTimeResource = new SwaggerResource();
		springTimeResource.setName("SpringTime");
		springTimeResource.setLocation("/v2/rfc-api-docs");
		resources.add(0, springTimeResource);
		return resources;
	}

}
