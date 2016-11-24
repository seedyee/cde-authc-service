package io.cde.authc.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * @author 作者 Fangcai Liao
 * @version 创建时间：Nov 1, 2016 10:38:46 PM
 * 类说明 拦截登录验证.
 */
public class AuthcFilter extends FormAuthenticationFilter {

    /**
     * 用户登录拦截验证.
     * @param request this request.
     * @param response this response.
     * @return this boolean.
     * @throws Exception this Exception.
     */
    @Override
    protected boolean onAccessDenied(final ServletRequest request, final ServletResponse response) throws Exception {
        final Map<String, String[]> map = request.getParameterMap();
        final String principal = map.get("principal")[0];
        final String password = map.get("password")[0];
        final Subject subject = SecurityUtils.getSubject();
        final UsernamePasswordToken token = new UsernamePasswordToken(principal, password);
        token.setRememberMe(true);
        try {
            subject.login(token);
            return true;
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            request.setAttribute("shiroLoginFailure", IncorrectCredentialsException.class.getName());
            return true;
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            request.setAttribute("shiroLoginFailure", UnknownAccountException.class.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("shiroLoginFailure", Exception.class.getName());
            return true;
        }
    }
}
