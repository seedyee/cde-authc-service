package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 8, 2016 6:43:26 PM
* 类说明
*/
@Component
public class BeansOfConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int mainIdle;

    private JedisPool jedisPool = null;

    public JedisPool initJedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(this.maxIdle);
        poolConfig.setMaxWaitMillis(this.maxWait);
        poolConfig.setMinIdle(this.mainIdle);
        if(this.password != null ){
            this.jedisPool = new JedisPool(poolConfig, this.host, this.port, this.timeout, this.password);
        }else {
            this.jedisPool = new JedisPool(poolConfig, this.host, this.port);
        }
        return this.jedisPool;
    }
    public String getHost() {
        return this.host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return this.port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public int getTimeout() {
        return this.timeout;
    }
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getMaxActive() {
        return this.maxActive;
    }
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }
    public int getMaxWait() {
        return this.maxWait;
    }
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
    public int getMaxIdle() {
        return this.maxIdle;
    }
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }
    public int getMainIdle() {
        return this.mainIdle;
    }
    public void setMainIdle(int mainIdle) {
        this.mainIdle = mainIdle;
    }
}
