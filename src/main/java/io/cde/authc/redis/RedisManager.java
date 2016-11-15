package io.cde.authc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 3, 2016 2:31:44 PM
* 类说明
*/
@Component
public class RedisManager {

  @Autowired
  private JedisPool jedisPool;
  /**
   * 从redis中获取值.
   * @param key
   * @return
   */
  public String get(String key){
    Jedis jedis = this.jedisPool.getResource();
    try {
      return jedis.get(key);
    } finally {
      jedis.close();
    }
  }
  /**
   * 存储数据到redis中.
   * @param key
   * @param value
   */
  public void set(String key, String value){
    Jedis jedis = this.jedisPool.getResource();
    try {
      jedis.set(key, value);
    } finally {
      jedis.close();
    }
  }
  /**
   * 更新redis中的数据.
   * @param key
   * @param value
   * @param timeToLiveSeconds
   */
  public void setex(String key, String value, int timeToLiveSeconds){
    Jedis jedis = this.jedisPool.getResource();
    try {
      jedis.setex(key, timeToLiveSeconds, value);
    } finally {
      jedis.close();
    }
  }
  /**
   * 删除数据.
   * @param key
   */
  public void del(String key){
    Jedis jedis = this.jedisPool.getResource();
    try {
      jedis.del(key);
    } finally {
      jedis.close();
    }
  }
  /**
   * 根据数据模式获取keys.
   * @param pattern
   * @return
   */
  public Set<String> keys(String pattern){
    Jedis jedis = this.jedisPool.getResource();
    try {
      return jedis.keys(pattern);
    } finally {
      jedis.close();
    }
  }
  /**
   * 根据数据模式获取keys.
   * @param keys
   * @return
   */
  @SuppressWarnings("null")
  public Collection<String> mget(String... keys) {
    if (keys == null && keys.length == 0) {
      Collections.emptySet();
    }
    Jedis jedis = this.jedisPool.getResource();
    try {
      return jedis.mget(keys);
    } finally {
      jedis.close();
    }
  }
  /**
   * 发送消息.
   * @param channel
   * @param value
   */
  public void publish(String channel, String value) {
    Jedis jedis = this.jedisPool.getResource();
    try {
      jedis.publish(channel, value);
    } finally {
      jedis.close();
    }
  }
}
