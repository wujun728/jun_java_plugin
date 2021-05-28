package net.dreamlu.weixin.config;

import lombok.AllArgsConstructor;
import net.dreamlu.weixin.cache.SpringAccessTokenCache;
import net.dreamlu.weixin.properties.DreamWeixinProperties;
import net.dreamlu.weixin.spring.MsgInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@AutoConfigureAfter(DreamWeixinProperties.class)
@AllArgsConstructor
public class DreamWeixinAutoConfiguration {
	private final CacheManager cacheManager;

	@Bean
	public SpringAccessTokenCache springAccessTokenCache(DreamWeixinProperties properties) {
		Cache cache = cacheManager.getCache(properties.getAccessTokenCache());
		return new SpringAccessTokenCache(cache);
	}

	@Configuration
	public static class MsgConfiguration extends WebMvcConfigurerAdapter {
		private final DreamWeixinProperties properties;

		public MsgConfiguration(DreamWeixinProperties properties) {
			this.properties = properties;
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			String urlPattern = properties.getUrlPatterns();
			MsgInterceptor httpCacheInterceptor = new MsgInterceptor(properties);
			registry.addInterceptor(httpCacheInterceptor)
					.addPathPatterns(urlPattern);
		}
	}
}
