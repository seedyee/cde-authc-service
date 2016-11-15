package io.cde.authc.controller;

import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 7, 2016 2:41:29 PM
* 类说明
*/
@RestController
@RequestMapping("/authc")
public class AuthcController {

  /**
   * 用户登录.
   */
  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public String signin(@RequestParam String principal, @RequestParam String password) {
    Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    Collection collection =  session.getAttributeKeys();
    System.out.println(subject.isAuthenticated()+"**"+subject.isRemembered());
    for (Object key: collection) {
      System.out.println("key = "+key+"****"+"value = "+session.getAttribute(key));
    }
    subject.isRemembered();
    subject.isAuthenticated();
    UsernamePasswordToken token = new UsernamePasswordToken(principal,password);
    try{
      subject.login(token);
      return "signin success";
    }catch (IncorrectCredentialsException e){
      e.printStackTrace();
      return "密码和用户不匹配";
    }catch (UnknownAccountException e) {
      e.printStackTrace();
      return "该用户不存在";
    }
  }
  /**
   * 用户登出.
   */
  @RequestMapping(value = "/signout", method = RequestMethod.POST)
  public String signout() {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return "signout success";
  }
}
