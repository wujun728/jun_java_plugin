package com.caland.common.web.springmvc;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.core.ParseException;
import freemarker.template.Configuration;

/**
 * 轻量级的FreemarkerView
 * 
 * 不支持jsp标签、不支持request、session、application等对象，可用于前台模板页面。
 */
public class SimpleFreeMarkerView extends AbstractTemplateView {
	/**
	 * 部署路径调用名称
	 */
	public static final String CONTEXT_PATH = "base";

	private Configuration configuration;

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	protected Configuration getConfiguration() {
		return this.configuration;
	}

	/**
	 * 自动检测FreeMarkerConfig
	 * 
	 * @return
	 * @throws BeansException
	 */
	protected FreeMarkerConfig autodetectConfiguration() throws BeansException {
		try {
			return (FreeMarkerConfig) BeanFactoryUtils
					.beanOfTypeIncludingAncestors(getApplicationContext(),
							FreeMarkerConfig.class, true, false);
		} catch (NoSuchBeanDefinitionException ex) {
			throw new ApplicationContextException(
					"Must define a single FreeMarkerConfig bean in this web application context "
							+ "(may be inherited): FreeMarkerConfigurer is the usual implementation. "
							+ "This bean may be given any name.", ex);
		}
	}

	/**
	 * Invoked on startup. Looks for a single FreeMarkerConfig bean to find the
	 * relevant Configuration for this factory.
	 * <p>
	 * Checks that the template for the default Locale can be found: FreeMarker
	 * will check non-Locale-specific templates if a locale-specific one is not
	 * found.
	 * 
	 * @see freemarker.cache.TemplateCache#getTemplate
	 */
	protected void initApplicationContext() throws BeansException {
		super.initApplicationContext();

		if (getConfiguration() == null) {
			FreeMarkerConfig config = autodetectConfiguration();
			setConfiguration(config.getConfiguration());
		}
		checkTemplate();
	}

	/**
	 * Check that the FreeMarker template used for this view exists and is
	 * valid.
	 * <p>
	 * Can be overridden to customize the behavior, for example in case of
	 * multiple templates to be rendered into a single view.
	 * 
	 * @throws ApplicationContextException
	 *             if the template cannot be found or is invalid
	 */
	protected void checkTemplate() throws ApplicationContextException {
		try {
			// Check that we can get the template, even if we might subsequently
			// get it again.
			getConfiguration().getTemplate(getUrl());
		} catch (ParseException ex) {
			throw new ApplicationContextException(
					"Failed to parse FreeMarker template for URL [" + getUrl()
							+ "]", ex);
		} catch (IOException ex) {
			throw new ApplicationContextException(
					"Could not load FreeMarker template for URL [" + getUrl()
							+ "]", ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedTemplateModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		model.put(CONTEXT_PATH, request.getContextPath());
		getConfiguration().getTemplate(getUrl()).process(model,
				response.getWriter());
	}
}
