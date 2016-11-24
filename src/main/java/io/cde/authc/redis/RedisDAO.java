package io.cde.authc.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import io.cde.authc.tools.SerializationUtils;

/**
* @author 作者 Fangcai Liao.
* @version 创建时间：Nov 3, 2016 2:27:33 PM.
* 类说明.
*/
public class RedisDAO extends AbstractSessionDAO {

    @Autowired
    private RedisManager redisManager;
    /**
     * 存于redis中的key的前缀标识.
     */
    @Value("${shiro.session.keyPrefix}")
    private String keyPrefix;
    /**
     * 存于redis中的有效时间.
     */
    @Value("${shiro.session.validtime}")
    private int validTime;
    /**
    * DefaultSessionManager 创建完 session 后会调用该方法.
    * 把 session 保存到 Redis.
    * 返回 Session ID；主要此处返回的 ID.equals(session.getId()).
    */
    @Override
    protected Serializable doCreate(final Session session) {
        // 创建一个Id并设置给Session
        final Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        // session 由 Redis 缓存失效决定
        final String key = SerializationUtils.sessionKey(this.keyPrefix, session);
        final String value = SerializationUtils.sessionFromString(session);
        this.redisManager.setex(key, value, this.validTime);
        return sessionId;
    }
    /**
    * 从 Redis 上读取 session.
    * @param sessionId this sessionId.
    * @return this session.
    */
    @Override
    protected Session doReadSession(final Serializable sessionId) {
        final String value = this.redisManager.get(SerializationUtils.sessionKey(this.keyPrefix, sessionId));
        //例如 Redis 调用 flushdb 情况了所有的数据，读到的 session 就是空的
        if (value != null) {
            final Session session = SerializationUtils.sessionToString(value);
            return session;
        }
        return null;
    }
    /**
    * 更新 session 到 Redis.
    * @param session this session.
    */
    @Override
    public void update(final Session session) throws UnknownSessionException {
        final String key = SerializationUtils.sessionKey(this.keyPrefix, session);
        final String value = SerializationUtils.sessionFromString(session);
        this.redisManager.setex(key, value, this.validTime);
    }
    /**
    * 从 Redis 删除 session
    * @param session this session.
    */
    @Override
    public void delete(final Session session) {
        this.redisManager.del(SerializationUtils.sessionKey(this.keyPrefix, session));
    }
    /**
    * 取得所有有效的 session.
    * @return this Collection.
    */
    @Override
    public Collection<Session> getActiveSessions() {
        final Set<String> keys = this.redisManager.keys(this.keyPrefix);
        final Collection<String> values = this.redisManager.mget(keys.toArray(new String[0]));
        final List<Session> sessions = new LinkedList<>();
        for (String value : values) {
            sessions.add(SerializationUtils.sessionToString(value));
        }
        return sessions;
    }
}
