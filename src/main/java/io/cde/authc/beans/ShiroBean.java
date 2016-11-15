package io.cde.authc.beans;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cde.authc.realm.AuthcRealm;
import io.cde.authc.redis.RedisDAO;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 1, 2016 9:07:20 PM
* 类说明
*/
@Configuration
public class ShiroBean {

  @Bean
  public WebSessionManager getWebSessionManager(){
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

    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(getWebSecurityManager());

    return shiroFilterFactoryBean;
  }

  @Bean
  public AuthcRealm getAuthcRealm(){
    AuthcRealm authcRealm = new AuthcRealm();

    return authcRealm;
  }

  public SimpleCookie getSimpleCookie() {
    SimpleCookie simpleCookie = new SimpleCookie("io.cde.auth.session.id");
    simpleCookie.setHttpOnly(true);
    simpleCookie.setMaxAge(-1);

    return simpleCookie;
  }
}
