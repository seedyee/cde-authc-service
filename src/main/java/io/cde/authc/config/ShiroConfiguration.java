package io.cde.authc.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import io.cde.authc.filter.AuthcFilter;
import io.cde.authc.realm.AuthcRealm;
import io.cde.authc.redis.RedisDAO;

/**
* @author 作者 Fangcai Liao.
* @version 创建时间：Nov 1, 2016 9:07:20 PM.
* 类说明.
*/
@Configuration
public class ShiroConfiguration {

    @Value("${RememberMeCookie.name}")
    private String rememberMeCookieName;

    @Value("${RememberMeCookie.HttpOnly}")
    private boolean rememberMeCookieHttpOnly;

    @Value("${RememberMeCookie.MaxAge}")
    private int rememberMeCookieMaxAge;

    @Value("${SimpleCookie.HttpOnly}")
    private boolean simpleCookieHttpOnly;
    /**
     * SimpleCookie模板的名称.
     */
    @Value("${SimpleCookie.name}")
    private String simpleCookieName;
    /**
     * 有效时间.
     */
    @Value("${SimpleCookie.MaxAge}")
    private int simpleCookieMaxAge;
    /**
     * shiro过滤路径所设集合.
     */
    private Map<String, String> filterChainDefinitionMap = new HashMap<>();
    /**
     * 装载过滤器集合.
     */
    private Map<String, Filter> filterHashMap = new HashMap<>();
    /**
     * 声明SessionManager，管理session.
     * @return this SessionManager.
     */
    @Bean
    public SessionManager getWebSessionManager() {
        final DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(getRedisSessionDAO());
        defaultWebSessionManager.setSessionIdCookie(getSimpleCookie());
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(false);
        return defaultWebSessionManager;
    }
    /**
     * 声明RedisDAO，管理对redis的相关操作.
     * @return this RedisDAO.
     */
    @Bean
    public RedisDAO getRedisSessionDAO() {
        final RedisDAO redisDAO = new RedisDAO();
        return redisDAO;
    }
    /**
     * 声明WebSecurityManager，管理Security.
     * @return this SecurityManager.
     */
    @Bean
    public WebSecurityManager getWebSecurityManager() {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getAuthcRealm());
        securityManager.setSessionManager(getWebSessionManager());
        securityManager.setRememberMeManager(getCookieRememberMeManager());
        return securityManager;
    }
    /**
     * 声明ShiroFilterFactoryBean，初始化shiro配置，添加过滤和登录权限的验证.
     * @return this ShiroFilterFactoryBean.
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        final ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getWebSecurityManager());
        filterChainDefinitionMap.put("/authc/signin", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        filterHashMap.put("authc", getFilter());
        shiroFilterFactoryBean.setFilters(filterHashMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 声明AuthcRealm，自定义Realm实现，对登录做验证.
     * @return this AuthcRealm.
     */
    @Bean
    public AuthcRealm getAuthcRealm() {
        final AuthcRealm authcRealm = new AuthcRealm();
        authcRealm.setCachingEnabled(false);
        return authcRealm;
    }
    /**
     * 声明CookieRememberMeManager，管理Cookie.
     * @return this CookieRememberMeManager.
     */
    @Bean
    public CookieRememberMeManager getCookieRememberMeManager() {
        final CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(getRememberMeCookie());
        return  cookieRememberMeManager;
    }
    /**
     * 声明Filter，完成shiro的拦截登录验证.
     * @return this Filter.
     */
    @Bean
    public Filter getFilter() {
        final AuthcFilter authcFilter = new AuthcFilter();
        return authcFilter;
    }
    /**
     * 设置 SimpleCookie，根据配置参数设置模板.
     * @return this RememberMeCookie.
     */
    private SimpleCookie getRememberMeCookie() {
        final SimpleCookie simpleCookie = new SimpleCookie(rememberMeCookieName);
        simpleCookie.setHttpOnly(rememberMeCookieHttpOnly);
        simpleCookie.setMaxAge(rememberMeCookieMaxAge);
        return simpleCookie;
    }
    /**
     * 设置 SimpleCookie，根据配置参数设置模板.
     * @return this SimpleCookie.
     */
    private SimpleCookie getSimpleCookie() {
        final SimpleCookie simpleCookie = new SimpleCookie(simpleCookieName);
        simpleCookie.setHttpOnly(simpleCookieHttpOnly);
        simpleCookie.setMaxAge(simpleCookieMaxAge);
        return simpleCookie;
    }
}
