package com.baomidou.shiro;

import com.baomidou.shiro.cache.SpringCacheManager;
import com.baomidou.shiro.exception.NoRealmConfiguredException;
import com.baomidou.shiro.model.CookieDefinition;
import com.baomidou.shiro.model.CredentialsMatcherDefinition;
import com.baomidou.shiro.model.LockDefinition;
import com.baomidou.shiro.model.RememberMeDefinition;
import com.baomidou.shiro.model.SessionDefinition;
import com.baomidou.shiro.model.ShiroProperties;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;

/**
 * shiro集成spring-boot启动器
 *
 * @author Wujun
 */
@Configuration
@Import({ShiroAnnotationConfiguration.class})
@ConditionalOnClass(ShiroFilterFactoryBean.class)
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration {

  private final ShiroProperties properties;

  public ShiroAutoConfiguration(ShiroProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
    filterFactoryBean.setLoginUrl(properties.getLoginUrl());
    filterFactoryBean.setSuccessUrl(properties.getSuccessUrl());
    filterFactoryBean.setUnauthorizedUrl(properties.getUnauthorizedUrl());
    filterFactoryBean.setSecurityManager(securityManager);
    filterFactoryBean.setFilterChainDefinitionMap(properties.getFilterChain());
    return filterFactoryBean;
  }

  @Bean
  @ConditionalOnMissingBean
  public HashedCredentialsMatcher hashedCredentialsMatcher(CacheManager cacheManager) {
    CredentialsMatcherDefinition credentialsMatcher = properties.getCredentialsMatcher();
    LockDefinition lock = properties.getLock();
    HashedCredentialsMatcher hashedCredentialsMatcher =
        lock.isEnabled() ?
            new RetryLimitHashedCredentialsMatcher(cacheManager.getCache(lock.getCacheName()),
                lock.getRetryLimit()) :
            new HashedCredentialsMatcher();
    hashedCredentialsMatcher.setHashAlgorithmName(credentialsMatcher.getAlgorithm().getName());
    hashedCredentialsMatcher.setHashIterations(credentialsMatcher.getIterations());
    hashedCredentialsMatcher.setStoredCredentialsHexEncoded(credentialsMatcher.isHexEncoded());
    return hashedCredentialsMatcher;
  }

  @Bean
  @ConditionalOnMissingBean
  public PasswordEncoder passwordEncoder(HashedCredentialsMatcher hashedCredentialsMatcher) {
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    passwordEncoder.setHashedCredentialsMatcher(hashedCredentialsMatcher);
    return passwordEncoder;
  }

  @Bean
  @ConditionalOnMissingBean
  public SecurityManager securityManager(
      CacheManager cacheManager, SubjectFactory subjectFactory, SubjectDAO subjectDAO,
      SessionManager sessionManager, RememberMeManager rememberMeManager, List<Realm> realms,
      HashedCredentialsMatcher hashedCredentialsMatcher) {
    if (CollectionUtils.isEmpty(realms)) {
      throw new NoRealmConfiguredException();
    }
    for (Realm realm : realms) {
      if (realm instanceof AuthenticatingRealm) {
        ((AuthenticatingRealm) realm).setCredentialsMatcher(hashedCredentialsMatcher);
      }
    }
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setCacheManager(cacheManager);
    securityManager.setSessionManager(sessionManager);
    securityManager.setRememberMeManager(rememberMeManager);
    securityManager.setSubjectFactory(subjectFactory);
    securityManager.setSubjectDAO(subjectDAO);
    securityManager.setRealms(realms);
    SecurityUtils.setSecurityManager(securityManager);
    return securityManager;
  }

  @Bean
  @ConditionalOnMissingBean
  public CacheManager shiroCacheManager(org.springframework.cache.CacheManager cacheManager) {
    return new SpringCacheManager(cacheManager);
  }

  @Bean
  @ConditionalOnMissingBean
  public SubjectFactory subjectFactory() {
    return new DefaultWebSubjectFactory() {
      @Override
      public Subject createSubject(SubjectContext context) {
        context.setSessionCreationEnabled(properties.getSession().isEnabled());
        return super.createSubject(context);
      }
    };
  }

  @Bean
  @ConditionalOnMissingBean
  public SubjectDAO subjectDAO(SessionStorageEvaluator sessionStorageEvaluator) {
    DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
    return subjectDAO;
  }

  @Bean
  @ConditionalOnMissingBean
  public SessionStorageEvaluator sessionStorageEvaluator() {
    DefaultSessionStorageEvaluator evaluator = new DefaultWebSessionStorageEvaluator();
    evaluator.setSessionStorageEnabled(properties.getSession().isEnabled());
    return evaluator;
  }

  @Bean
  @ConditionalOnMissingBean(ignored = SecurityManager.class)
  public SessionManager sessionManager(SessionFactory sessionFactory, SessionDAO sessionDAO) {
    SessionDefinition session = properties.getSession();
    if (session.isEnabled()) {
      DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
      sessionManager.setGlobalSessionTimeout(session.getTimeOut());
      sessionManager.setSessionFactory(sessionFactory);
      sessionManager.setSessionDAO(sessionDAO);
      sessionManager.setSessionIdCookie(createCookie(session.getCookie()));
      sessionManager.setSessionIdCookieEnabled(session.isSessionIdCookieEnabled());
      sessionManager.setSessionIdUrlRewritingEnabled(session.isSessionIdUrlRewritingEnabled());
      return sessionManager;
    }
    return new ServletContainerSessionManager();
  }

  private Cookie createCookie(CookieDefinition cookieDefinition) {
    Cookie cookie = new SimpleCookie(cookieDefinition.getName());
    cookie.setDomain(cookieDefinition.getDomain());
    cookie.setPath(cookieDefinition.getPath());
    cookie.setMaxAge(cookieDefinition.getMaxAge());
    cookie.setHttpOnly(cookieDefinition.isHttpOnly());
    return cookie;
  }

  @Bean
  @ConditionalOnMissingBean
  public SessionFactory sessionFactory() {
    return new SimpleSessionFactory();
  }

  @Bean
  @ConditionalOnMissingBean
  public SessionDAO sessionDAO(CacheManager cacheManager, SessionIdGenerator sessionIdGenerator) {
    EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
    sessionDAO.setCacheManager(cacheManager);
    sessionDAO.setSessionIdGenerator(sessionIdGenerator);
    return sessionDAO;
  }

  @Bean
  @ConditionalOnMissingBean
  public SessionIdGenerator sessionIdGenerator() {
    return new JavaUuidSessionIdGenerator();
  }

  @Bean
  @ConditionalOnMissingBean
  public RememberMeManager rememberMeManager() {
    RememberMeDefinition rememberMe = properties.getRememberMe();
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(createCookie(rememberMe.getCookie()));
    return cookieRememberMeManager;
  }

}