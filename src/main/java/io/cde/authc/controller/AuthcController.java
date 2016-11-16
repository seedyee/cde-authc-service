package io.cde.authc.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.cde.authc.domaim.Error;
import io.cde.authc.domaim.Model;
import io.cde.authc.tools.ResultUtils;

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
  public Object signin(@RequestParam String principal, @RequestParam String password) {
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(principal,password);
    try{
      subject.login(token);
      return null;
    }catch (IncorrectCredentialsException e){
      e.printStackTrace();
      return ResultUtils.resultError(1000004, "密码和用户不匹配");
    }catch (UnknownAccountException e) {
      e.printStackTrace();
      return ResultUtils.resultError(1000005, "用户不存在");
    }catch (Exception e) {
      e.printStackTrace();
      return ResultUtils.resultError(1000005, "系统错误");
    }
  }
  /**
   * 用户登出.
   */
  @RequestMapping(value = "/signout", method = RequestMethod.POST)
  public Object signousignoutt() {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return null;
  }
}
