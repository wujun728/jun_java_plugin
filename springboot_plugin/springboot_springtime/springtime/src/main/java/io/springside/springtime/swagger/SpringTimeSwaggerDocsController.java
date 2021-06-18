package io.springside.springtime.swagger;

import static org.springframework.http.MediaType.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.springside.springtime.springboot.SpringTimeService;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.ReaderConfigUtils;
import io.swagger.models.Info;
import io.swagger.models.Swagger;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class SpringTimeSwaggerDocsController implements InitializingBean, ApplicationContextAware {
	private Swagger swagger;

	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		swagger = new Swagger();
		Info info = new Info();
		info.setTitle("GreetingService");
		swagger.setInfo(info);

		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(SpringTimeService.class);
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Object bean : beans.values()) {
			classes.add(bean.getClass());
		}

		Reader reader = new Reader(swagger, ReaderConfigUtils.getReaderConfig(null));
		swagger = reader.read(classes);
	}

	@RequestMapping(value = "/v2/rfc-api-docs", method = RequestMethod.GET, produces = { APPLICATION_JSON_VALUE })
	public @ResponseBody Swagger getDocumentation(HttpServletRequest servletRequest) {
		return swagger;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

}
