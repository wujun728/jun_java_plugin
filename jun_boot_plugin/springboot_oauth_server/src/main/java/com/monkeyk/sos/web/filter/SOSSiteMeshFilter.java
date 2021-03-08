package com.monkeyk.sos.web.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * 2018/2/3
 * <p>
 * Replace decorator.xml
 * <p>
 * Sitemesh
 *
 * @author Wujun
 */
public class SOSSiteMeshFilter extends ConfigurableSiteMeshFilter {


    public SOSSiteMeshFilter() {
    }


    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {

        builder.addDecoratorPath("/*", "/WEB-INF/jsp/decorators/main.jsp")

                .addExcludedPath("/static/**");


    }
}
