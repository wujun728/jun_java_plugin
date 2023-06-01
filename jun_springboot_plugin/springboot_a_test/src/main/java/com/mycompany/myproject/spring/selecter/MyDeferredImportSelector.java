package com.mycompany.myproject.spring.selecter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyDeferredImportSelector implements DeferredImportSelector {

	private final static Logger logger = LoggerFactory.getLogger(MyDeferredImportSelector.class);

	@Override
	public Class<? extends Group> getImportGroup() {

		logger.debug("Custom DeferredImportSelector : MyDeferredImportSelector.getImportGroup");

		return null;
	}

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[0];
	}


}
