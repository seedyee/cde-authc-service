package io.cde.authc.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.cde.authc.tools.SerializationUtils;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 3, 2016 2:27:33 PM
* 类说明
*/
public class RedisSessionDAO extends CachingSessionDAO{

  @Value("${shiro.session.keyPrefix}")
  private String keyPrefix;

  @Value("${shiro.session.validtime}")
  private int validTime;

  @Autowired
  private RedisManager redisManager;

  /**
   * DefaultSessionManager 创建完 session 后会调用该方法。
   * 把 session 保存到 Redis。
   * 返回 Session ID；主要此处返回的 ID.equals(session.getId())
   */
  @Override
  protected Serializable doCreate(Session session) {
    // 创建一个Id并设置给Session
    Serializable sessionId = this.generateSessionId(session);
    assignSessionId(session, sessionId);
    // session 由 Redis 缓存失效决定
    String key = SerializationUtils.sessionKey(this.keyPrefix, session);
    String value = SerializationUtils.sessionFromString(session);
    this.redisManager.setex(key, value, this.validTime);

    return sessionId;
  }
  /**
   * 从 Redis 读取 Session.
   * @param sessionId
   * @return
   * @throws UnknownSessionException
   */
  @Override
  public Session readSession(Serializable sessionId) throws UnknownSessionException {
    Session session = doReadSession(sessionId);
    if (session == null) {
      throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
    }

    return session;
  }
  /**
   * 从 Redis 上读取 session.
   * @param sessionId
   * @return
   */
  @Override
  protected Session doReadSession(Serializable sessionId) {
    String value = this.redisManager.get(SerializationUtils.sessionKey(this.keyPrefix, sessionId));
    //例如 Redis 调用 flushdb 情况了所有的数据，读到的 session 就是空的
    if (value != null) {
      Session session = SerializationUtils.sessionToString(value);

      return session;
    }
    return null;
  }
  /**
   * 更新 session 到 Redis.
   * @param session
   */
  @Override
  protected void doUpdate(Session session) {
    // 如果会话过期/停止，没必要再更新了
    if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
      return;
    }
    String key = SerializationUtils.sessionKey(this.keyPrefix, session);
    String value = SerializationUtils.sessionFromString(session);
    this.redisManager.setex(key, value, this.validTime);
  }
  /**
   * 从 Redis 删除 session
   * @param session
   */
  @Override
  protected void doDelete(Session session) {
    this.redisManager.del(SerializationUtils.sessionKey(this.keyPrefix, session));
  }
  /**
   * 取得所有有效的 session.
   * @return
   */
  @Override
  public Collection<Session> getActiveSessions() {
    Set<String> keys = this.redisManager.keys(this.keyPrefix + "*");
    Collection<String> values = this.redisManager.mget(keys.toArray(new String[0]));
    List<Session> sessions = new LinkedList<>();
    for (String value : values) {
      sessions.add(SerializationUtils.sessionToString(value));
    }

    return sessions;
  }

  public String getKeyPrefix() {
    return this.keyPrefix;
  }
  public void setKeyPrefix(String keyPrefix) {
    this.keyPrefix = keyPrefix;
  }
  public RedisManager getRedisManager() {
    return this.redisManager;
  }
  public void setRedisManager(RedisManager redisManager) {
    this.redisManager = redisManager;
  }
  public int getValidTime() {
    return this.validTime;
  }
  public void setValidTime(int validTime) {
    this.validTime = validTime;
  }
}
