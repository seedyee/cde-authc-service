package io.cde.authc.realm;

import com.mongodb.DBObject;
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

  private DBObject account = null;

  private DBObject email = null;

  private String principal = null;

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
    principal = usernamePasswordToken.getUsername();
    account = authcDao.getAccountByPrincipal(principal);
    if (account == null) {
      throw new UnknownAccountException();
    }

    email = authcDao.getEmailByname(account.get("email").toString());
    if (email.get("isVerified").toString() == "false") {
      throw new UnknownAccountException();
    }
    String username = account.get("name").toString();
    String password = account.get("password").toString();

    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
      principal,
      password,
      ByteSource.Util.bytes(principal),
      getName());
    return authenticationInfo;
    }
}
