package io.cde.authc.controller;

import javax.servlet.ServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

import io.cde.authc.tools.ResultUtils;

/**
* @author 作者 Fangcai Liao.
* @version 创建时间：Nov 7, 2016 2:41:29 PM.
* 类说明.
*/
@RestController
@RequestMapping("/authc")
public class AuthcController {

    /**
     * 用户请求登录.
     * @param req this rep.
     * @return this Object.
     */
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public Object signin(final ServletRequest req) {
        final String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            return ResultUtils.resultError(1000005, "用户不存在");
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            return ResultUtils.resultError(1000004, "密码和用户不匹配");
        } else if (exceptionClassName != null) {
            return ResultUtils.resultError(1000005, "系统错误");
        }
        return null;
    }
    /**
     * 用户请求登出.
     * @return this Object.
     */
    @RequestMapping(value = "/signout", method = RequestMethod.POST)
    public Object signout() {
        final Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return null;
    }
}
