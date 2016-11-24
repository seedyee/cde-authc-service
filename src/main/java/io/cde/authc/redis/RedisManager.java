package io.cde.authc.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
* @author 作者 Fangcai Liao.
* @version 创建时间：Nov 3, 2016 2:31:44 PM.
* 类说明.
*/
@Component
public class RedisManager {

    @Autowired
    private JedisPool jedisPool;
    /**
    * 从redis中获取值.
    * @param key this key.
    * @return this value.
    */
    public String get(final String key) {
        final Jedis jedis = this.jedisPool.getResource();
        try {
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }
    /**
    * 存储数据到redis中.
    * @param key this key.
    * @param value this value.
    */
    public void set(final String key, final String value) {
        final Jedis jedis = this.jedisPool.getResource();
        try {
            jedis.set(key, value);
        } finally {
            jedis.close();
        }
    }
    /**
    * 更新redis中的数据.
    * @param key this key.
    * @param value this value.
    * @param timeToLiveSeconds this timeToLiveSeconds.
    */
    public void setex(final String key, final String value, final int timeToLiveSeconds) {
        final Jedis jedis = this.jedisPool.getResource();
        try {
            jedis.setex(key, timeToLiveSeconds, value);
        } finally {
            jedis.close();
        }
    }
    /**
    * 删除数据.
    * @param key this key.
    */
    public void del(final String key) {
        final Jedis jedis = this.jedisPool.getResource();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }
    /**
    * 根据数据模式获取keys.
    * @param pattern this pattern.
    * @return this Set.
    */
    public Set<String> keys(final String pattern) {
        final Jedis jedis = this.jedisPool.getResource();
        try {
            return jedis.keys(pattern);
        } finally {
            jedis.close();
        }
    }
    /**
    * 根据数据模式获取keys.
    * @param keys this keys.
    * @return this Collection.
    */
    public Collection<String> mget(final String... keys) {
        if (keys == null && keys.length == 0) {
            Collections.emptySet();
        }
        final Jedis jedis = this.jedisPool.getResource();
        try {
            return jedis.mget(keys);
        } finally {
            jedis.close();
        }
    }
    /**
    * 发送消息.
    * @param channel this channel.
    * @param value this calue.
    */
    public void publish(final String channel, final String value) {
        final Jedis jedis = this.jedisPool.getResource();
        try {
            jedis.publish(channel, value);
        } finally {
            jedis.close();
        }
    }
}
