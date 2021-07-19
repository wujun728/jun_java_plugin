## 结合JWT做认证以及携带用户信息

此处涉及到两个服务

spring-boot-oauth-jwt-server

spring-boot-oauth-jwt-resource-server

认证方式一一对应

## 认证服务

主要复写AuthorizationServerConfigurerAdapter适配器做自定义配置

### 认证服务1-对称加密方式

#### 处理步骤
1. 注入JwtAccessToken转换器

          @Bean
          public JwtAccessTokenConverter accessTokenConverter() {
              JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
              converter.setSigningKey("123");
              return converter;
          }

2. AccessToken转换器选择1中注入的转换器

        @Override
            public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
                endpoints.tokenStore(tokenStore())
                         .accessTokenConverter(accessTokenConverter())
                         .authenticationManager(authenticationManager);
            }

#### 额外信息

1. 自定义生成token携带的信息

有时候需要额外的信息加到token返回中，这部分也可以自定义，此时我们可以自定义一个TokenEnhancer 

            @Override
        	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        		final Map<String, Object> additionalInfo = new HashMap<>();
        		User user = (User) authentication.getUserAuthentication().getPrincipal();
        		additionalInfo.put("username", user.getUsername());
        		additionalInfo.put("authorities", user.getAuthorities());
        		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        		return accessToken;
        	}

这里我们加入了用户的授权信息。然后还需要把这个TokenEnhancer加入到TokenEnhancer链中

        // 自定义token生成方式
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customerEnhancer(), accessTokenConverter()));
        endpoints.tokenEnhancer(tokenEnhancerChain);


2. 自定义token信息中添加的信息

JWT中，需要在token中携带额外的信息，这样可以在服务之间共享部分用户信息，spring security默认在JWT的token中加入了user_name，如果我们需要额外的信息，需要自定义这部分内容。

JwtAccessTokenConverter中默认使用DefaultAccessTokenConverter来处理token的生成、装换、获取。DefaultAccessTokenConverter中使用UserAuthenticationConverter来对应处理token与userinfo的获取、转换。因此我们需要重写下UserAuthenticationConverter对应的转换方法就可以

        @Override
        public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        	LinkedHashMap response = new LinkedHashMap();
        	response.put("user_name", authentication.getName());
        	response.put("name", ((User) authentication.getPrincipal()).getName());
        	response.put("id", ((User) authentication.getPrincipal()).getId());
        	response.put("createAt", ((User) authentication.getPrincipal()).getCreateAt());
        	if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
        		response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        	}
        
        	return response;
        }

然后把我们的方式替换默认的方式

        @Bean
        public TokenEnhancer accessTokenConverter() {
        	final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        	converter.setSigningKey("123");
        	converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
        	return converter;
        }


### 认证服务2-非对称加密方式（公钥密钥）

#### 处理步骤
1. 生成JKS文件

        keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore mytest.jks -storepass mypass

    ![jks](http://git.oschina.net/buxiaoxia/spring-demo/raw/master/spring-oauth2/spring-boot-oauth-jwt-server/pic/JKS.png)  

具体参数的意思不另说明。

2. 导出公钥

        keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey

    ![publicKey](http://git.oschina.net/buxiaoxia/spring-demo/raw/master/spring-oauth2/spring-boot-oauth-jwt-server/pic/publicKey.jpg)  

3. 生成公钥文本

        -----BEGIN PUBLIC KEY-----
        MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhAF1qpL+8On3rF2M77lR
        +l3WXKpGXIc2SwIXHwQvml/4SG7fJcupYVOkiaXj4f8g1e7qQCU4VJGvC/gGJ7sW
        fn+L+QKVaRhs9HuLsTzHcTVl2h5BeawzZoOi+bzQncLclhoMYXQJJ5fULnadRbKN
        HO7WyvrvYCANhCmdDKsDMDKxHTV9ViCIDpbyvdtjgT1fYLu66xZhubSHPowXXO15
        LGDkROF0onqc8j4V29qy5iSnx8I9UIMEgrRpd6raJftlAeLXFa7BYlE2hf7cL+oG
        hY+q4S8CjHRuiDfebKFC1FJA3v3G9p9K4slrHlovxoVfe6QdduD8repoH07jWULu
        qQIDAQAB
        -----END PUBLIC KEY-----

存储为public.txt。把两个文件放入resource目录下

4. 修改JwtAccessTokenConverter

        @Bean
        public TokenEnhancer accessTokenConverter() {
        	final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        	KeyStoreKeyFactory keyStoreKeyFactory =
        			new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        	converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        
        	converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
        	return converter;
        }


## 资源服务

主要复写ResourceServerConfigurerAdapter适配器做自定义配置

### 资源服务1-对称加密方式

此处配置与认证服务基本一致，不同的是，资源服务器配置是在ResourceServerConfigurerAdapter做配置

### 资源服务2-非对称加密方式（公钥）

把对应的public.txt公钥引入到resource目录下

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
        	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        	Resource resource = new ClassPathResource("public.txt");
        	String publicKey = null;
        	try {
        		publicKey = inputStream2String(resource.getInputStream());
        	} catch (final IOException e) {
        		throw new RuntimeException(e);
        	}
        	converter.setVerifierKey(publicKey);
        	converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
        	return converter;
        }

文档支持：http://www.baeldung.com/spring-security-oauth-jwt