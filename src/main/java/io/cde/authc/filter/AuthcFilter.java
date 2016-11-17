package io.cde.authc.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Map;

/**
 * Created by liaofangcai on 11/16/16.
 */
public class AuthcFilter extends FormAuthenticationFilter {

  private String principal;

  private String password;

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    Map<String, String[]> map = request.getParameterMap();
    principal = map.get("principal")[0];
    password = map.get("password")[0];

    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(principal, password);
    token.setRememberMe(true);
    try{
      subject.login(token);
      return true;
    }catch (IncorrectCredentialsException e){
      e.printStackTrace();
      request.setAttribute("shiroLoginFailure", IncorrectCredentialsException.class.getName());
      return true;
    }catch (UnknownAccountException e) {
      e.printStackTrace();
      request.setAttribute("shiroLoginFailure", UnknownAccountException.class.getName());
      return true;
    }catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("shiroLoginFailure", Exception.class.getName());
      return true;
    }
  }
}
