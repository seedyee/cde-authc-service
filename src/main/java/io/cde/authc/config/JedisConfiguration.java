package io.cde.authc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
* @author 作者 Fangcai Liao.
* @version 创建时间：Nov 8, 2016 6:43:26 PM.
* 类说明.
*/
@Configuration
public class JedisConfiguration {

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.pool.max-wait}")
    private int maxWait;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int mainIdle;

    private JedisPool jedisPool = null;
    /**
     * redis服务器地址.
     */
    @Value("${spring.redis.host}")
    private String host;
    /**
     * 服务器端口.
     */
    @Value("${spring.redis.port}")
    private int port;
    /**
     * 连接服务器的密码.
     */
    @Value("${spring.redis.password}")
    private String password;
    /**
     * 配置JedisPool，注入bean.
     * @return JedisPool.
     */
    @Bean
    public JedisPool initJedisPool() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(this.maxIdle);
        poolConfig.setMaxWaitMillis(this.maxWait);
        poolConfig.setMinIdle(this.mainIdle);
        if (this.password != null) {
            this.jedisPool = new JedisPool(poolConfig, this.host, this.port, this.timeout, this.password);
        } else {
            this.jedisPool = new JedisPool(poolConfig, this.host, this.port);
        }
        return this.jedisPool;
    }
}
