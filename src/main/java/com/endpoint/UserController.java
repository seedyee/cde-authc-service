package com.endpoint;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.UserService;
/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 7, 2016 2:41:29 PM
* 类说明
*/
@RestController
@RequestMapping("/authc")
public class UserController {

    @Autowired
    UserService userService;
    /**
     * 用户登录.
     */

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public Map<String, String> signin(@RequestParam String principal, @RequestParam String password) {
        return this.userService.signin(principal, password);
    }
    /**
     * 用户登出.
     */
    @RequestMapping(value = "/signout", method = RequestMethod.POST)
    public Map<String, String> signout() {
        return this.userService.signout();
    }
}
