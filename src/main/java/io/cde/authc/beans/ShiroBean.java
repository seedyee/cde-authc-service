package io.cde.authc.beans;

import io.cde.authc.filter.AuthcFilter;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cde.authc.realm.AuthcRealm;
import io.cde.authc.redis.RedisDAO;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 1, 2016 9:07:20 PM
* 类说明
*/
@Configuration
public class ShiroBean {

  @Value("${SimpleCookie.name}")
  private String simpleCookieName;

  @Value("${SimpleCookie.HttpOnly}")
  private boolean simpleCookieHttpOnly;

  @Value("${SimpleCookie.MaxAge}")
  private int simpleCookieMaxAge;

  @Value("${RememberMeCookie.name}")
  private String rememberMeCookieName;

  @Value("${RememberMeCookie.HttpOnly}")
  private boolean rememberMeCookieHttpOnly;

  @Value("${RememberMeCookie.MaxAge}")
  private int rememberMeCookieMaxAge;

  private static Map<String, String> filterChainDefinitionMap = new HashMap<>();

  private static Map<String, Filter> filterHashMap = new HashMap<>();

  @Bean
  public SessionManager getWebSessionManager(){
    DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
    defaultWebSessionManager.setSessionDAO(getRedisSessionDAO());
    defaultWebSessionManager.setSessionIdCookie(getSimpleCookie());
    defaultWebSessionManager.setSessionIdCookieEnabled(true);

    return defaultWebSessionManager;
  }

  @Bean
  public RedisDAO getRedisSessionDAO() {
    RedisDAO redisDAO = new RedisDAO();

    return redisDAO;
  }

  @Bean
  public WebSecurityManager getWebSecurityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(getAuthcRealm());
    securityManager.setSessionManager(getWebSessionManager());

    securityManager.setRememberMeManager(getCookieRememberMeManager());
    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(getWebSecurityManager());
    filterChainDefinitionMap.put("/authc/signin", "authc");
    shiroFilterFactoryBean
            .setFilterChainDefinitionMap(filterChainDefinitionMap);
    filterHashMap.put("authc", getFilter());
    shiroFilterFactoryBean.setFilters(filterHashMap);

    return shiroFilterFactoryBean;
  }

  @Bean
  public AuthcRealm getAuthcRealm(){
    AuthcRealm authcRealm = new AuthcRealm();
    authcRealm.setCachingEnabled(true);

    return authcRealm;
  }

  @Bean
  public CookieRememberMeManager getCookieRememberMeManager() {
    CookieRememberMeManager cookieRememberMeManager= new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(getRememberMeCookie());

    return  cookieRememberMeManager;
  }

  @Bean
  public Filter getFilter() {
    AuthcFilter authcFilter = new AuthcFilter();

    return authcFilter;
  }

  public SimpleCookie getRememberMeCookie() {
    SimpleCookie simpleCookie = new SimpleCookie(rememberMeCookieName);
    simpleCookie.setHttpOnly(rememberMeCookieHttpOnly);
    simpleCookie.setMaxAge(rememberMeCookieMaxAge);

    return simpleCookie;
  }

  public SimpleCookie getSimpleCookie() {
    SimpleCookie simpleCookie = new SimpleCookie(simpleCookieName);
    simpleCookie.setHttpOnly(simpleCookieHttpOnly);
    simpleCookie.setMaxAge(simpleCookieMaxAge);

    return simpleCookie;
  }
}
