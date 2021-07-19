package com.buxiaoxia.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * AuthorizationServerConfigurer 需要配置三个配置-重写几个方法：
 * ClientDetailsServiceConfigurer：用于配置客户详情服务，指定存储位置
 * AuthorizationServerSecurityConfigurer：定义安全约束
 * AuthorizationServerEndpointsConfigurer：定义认证和token服务
 * <p>
 * <p>
 * Created by xw on 2017/3/16.
 * 2017-03-16 22:28
 */
@EnableAuthorizationServer
@Configuration
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	// 注入认证管理器
	@Autowired
	private AuthenticationManager authenticationManager;
//	private Base64UrlCodec base64UrlCodec = new Base64UrlCodec();

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 使用特定的方式存储client detail
		clients.withClientDetails(clientDetails());
	}


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//指定认证管理器
		endpoints.authenticationManager(authenticationManager);
		//指定token存储位置
		endpoints.tokenStore(tokenStore());
		// 自定义token生成方式
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customerEnhancer(), accessTokenConverter()));
		endpoints.tokenEnhancer(tokenEnhancerChain);

		// 配置TokenServices参数
		DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
		tokenServices.setTokenStore(endpoints.getTokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
		tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天
		endpoints.tokenServices(tokenServices);

		super.configure(endpoints);
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("permitAll()");
		security.allowFormAuthenticationForClients();
	}


	/**
	 * 定义clientDetails存储的方式-》Jdbc的方式，注入DataSource
	 *
	 * @return
	 */
	@Bean
	public ClientDetailsService clientDetails() {
		return new JdbcClientDetailsService(dataSource);
	}


	/**
	 * 指定token存储的位置
	 *
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}


	/**
	 * 注入自定义token生成方式
	 *
	 * @return
	 */
	@Bean
	public TokenEnhancer customerEnhancer() {
		return new CustomTokenEnhancer();
	}


	@Bean
	public TokenEnhancer accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory =
				new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));

		converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
		return converter;
	}

}
