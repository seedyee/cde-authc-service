package io.cde.authc.realm;

import io.cde.authc.dao.AuthcDao;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.AuthenticatingRealm;

import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 1, 2016 10:38:46 PM
* 类说明
*/
public class AuthcRealm extends AuthenticatingRealm{

  @Autowired
  private AuthcDao authcDao;

  private Map accountmap = null;

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
    String principal = usernamePasswordToken.getUsername();
    try{
      accountmap = authcDao.getAccountByPrincipal(principal);
    }catch (Exception e) {
      e.printStackTrace();
      throw new UnknownAccountException();
    }
    if (accountmap.get("verified").toString().equals("0")) {
      throw new UnknownAccountException();
    }
    String username = accountmap.get("username").toString();
    String password = accountmap.get("password").toString();

    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
      principal,
      password,
      ByteSource.Util.bytes(principal),
      getName());
    return authenticationInfo;
    }
}
