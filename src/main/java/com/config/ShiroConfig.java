package com.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 1, 2016 9:07:20 PM
* 类说明
*/
@Configuration
public class ShiroConfig {

    @Autowired
    BeansOfConfig beansOfConfig;

    @SuppressWarnings("static-method")
    @Bean(name = "SecurityRealm")
    public SecurityRealm getShiroRealm(){
        SecurityRealm securityRealm = new SecurityRealm();
        return securityRealm;
    }
    @SuppressWarnings("static-method")
    @Bean(name = "simpleCookie")
    public SimpleCookie getSimpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("authc.session.id");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean(name="defaultWebSessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(getRedisSessionDAO());
        defaultWebSessionManager.setSessionIdCookie(getSimpleCookie());
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        return defaultWebSessionManager;
    }

    @SuppressWarnings("static-method")
    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO getRedisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        return redisSessionDAO;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(getShiroRealm());
        dwsm.setSessionManager(getDefaultWebSessionManager());
        return dwsm;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        return shiroFilterFactoryBean;
    }

    @Bean(name = "jedisPool")
    public JedisPool getJedisPool() {
        return this.beansOfConfig.initJedisPool();
    }
}
