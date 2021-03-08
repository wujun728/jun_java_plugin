package com.mycompany.myproject.spring.selecter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


public class MyImportSelector implements ImportSelector {

	private final static Logger logger = LoggerFactory.getLogger(MyImportSelector.class);

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {

		logger.debug("Custom ImportSelector : MyImportSelector.selectImports");

		return new String[0];
	}
}
