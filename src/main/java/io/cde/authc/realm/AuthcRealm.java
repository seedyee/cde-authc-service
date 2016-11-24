package io.cde.authc.realm;


import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DBObject;
import io.cde.authc.dao.AuthcDao;

/**
* @author 作者 Fangcai Liao.
* @version 创建时间：Nov 1, 2016 10:38:46 PM.
* 类说明.
*/
public class AuthcRealm extends AuthenticatingRealm {

    @Autowired
    private AuthcDao authcDao;
    /**
     * 根据用户请求登录的数据查询资源数据.
     * @param token this token.
     * @return 资源数据信息.
     * @throws AuthenticationException this AuthenticationException.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
        final UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        final String principal = usernamePasswordToken.getUsername();
        final DBObject account = authcDao.getAccountByPrincipal(principal);
        if (account == null) {
            throw new UnknownAccountException();
        }
        final DBObject email = authcDao.getEmailByname(account.get("email").toString());
        if (email.get("isVerified").toString().equals("false")) {
            throw new UnknownAccountException();
        }
        final String username = account.get("name").toString();
        final String password = account.get("password").toString();

        final SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            principal,
            password,
            ByteSource.Util.bytes(principal),
            getName());
        return authenticationInfo;
    }
}
