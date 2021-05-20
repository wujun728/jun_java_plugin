package com.reger.swagger.autoconfig;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

class ConditionApi implements Condition{

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment environment =context.getEnvironment();
		if (environment != null) {
			String enabled = environment.getProperty( "spring.swagger.enabled" );
			if("true".equals(enabled)){
				return true;
			}else if("false".equals(enabled)){
				return false;
			}else if (environment.acceptsProfiles(("api"))) {
				return true;
			}
		}
		return false;
	}
}