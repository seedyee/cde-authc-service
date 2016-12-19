package io.cde.authc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * Created by liaofangcai.
 * Created time 12/14/16.
 */
@Configuration
public class RedisConfiguration {

    /**
     * 配置StringRedisTemplate实体.
     * @return StringRedisTemplate实体
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(final JedisConnectionFactory jedisConnectionFactory) {
        final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        return stringRedisTemplate;
    }

    /**
     *
     * 配置RedisTemplate.
     * @return 返回redisTemplate实体
     * @throws UnknownHostException this UnknownHostException
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(final JedisConnectionFactory jedisConnectionFactory) throws UnknownHostException {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory);
        final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        final ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        om.enableDefaultTyping(DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //使用StringRedisSerializer作为序列化key，默认是使用JdkSerializationRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
