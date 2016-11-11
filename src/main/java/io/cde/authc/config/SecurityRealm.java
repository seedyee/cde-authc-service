package io.cde.authc.config;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import io.cde.authc.server.UserService;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 1, 2016 10:38:46 PM
* 类说明
*/
public class SecurityRealm extends AuthorizingRealm{

  @Resource
  UserService userService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    Map<String, Object> map = this.userService.getUser((String)token.getPrincipal());
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
      map.get("username").toString(),
      map.get("password").toString(),
      ByteSource.Util.bytes(map.get("username").toString()),
      getName()
  );
    return authenticationInfo;
  }
}
