package session;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 
 * 
 * @author Wujun
 *
 */
public class AppInitializer implements WebApplicationInitializer {

	// @Override
	// public void onStartup(javax.servlet.ServletContext sc)
	// throws ServletException {
	//
	// // WebApplicationContext
	// // AnnotationConfigWebApplicationContext rootContext = new
	// AnnotationConfigWebApplicationContext();
	// // rootContext.register(AppConfig.class);
	// // sc.addListener(new ContextLoaderListener(rootContext));
	//
	// // 2、springmvc上下文
	// AnnotationConfigWebApplicationContext springMvcContext = new
	// AnnotationConfigWebApplicationContext();
	// springMvcContext.register(MvcConfiguration.class);
	// // 3、DispatcherServlet
	// DispatcherServlet dispatcherServlet = new DispatcherServlet(
	// springMvcContext);
	// ServletRegistration.Dynamic dynamic = sc.addServlet(
	// "dispatcherServlet", dispatcherServlet);
	// dynamic.setLoadOnStartup(1);
	// dynamic.addMapping("/web/*");
	//
	// // 4、CharacterEncodingFilter
	// FilterRegistration filterRegistration = sc.addFilter(
	// "characterEncodingFilter", CharacterEncodingFilter.class);
	// filterRegistration.addMappingForUrlPatterns(
	// EnumSet.of(DispatcherType.REQUEST), false, "/*");
	//
	// }

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(AppConfig.class);
		DispatcherServlet dispatcherServlet = new DispatcherServlet(
				webApplicationContext);
		ServletRegistration.Dynamic dynamic = servletContext.addServlet(
				"dispatcherServlet", dispatcherServlet);
		dynamic.setLoadOnStartup(1);
		dynamic.addMapping("/");
	}
}
