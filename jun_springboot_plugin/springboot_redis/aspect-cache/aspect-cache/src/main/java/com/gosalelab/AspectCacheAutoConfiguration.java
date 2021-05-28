package com.gosalelab;

import com.gosalelab.constants.CacheConstants;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Wujun
 */
@ComponentScan({CacheConstants.MAIN_PACKAGE})
@EnableAspectJAutoProxy
class AspectCacheAutoConfiguration {

}
